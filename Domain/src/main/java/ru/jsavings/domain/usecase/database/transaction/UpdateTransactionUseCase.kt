package ru.jsavings.domain.usecase.database.transaction

import io.reactivex.rxjava3.core.Completable
import ru.jsavings.data.entity.database.AccountEntity
import ru.jsavings.data.entity.database.TransactionGroupEntity
import ru.jsavings.data.entity.database.WalletEntity
import ru.jsavings.data.repository.database.account.AccountRepository
import ru.jsavings.data.repository.database.transaction.TransactionRepository
import ru.jsavings.data.repository.database.wallet.WalletRepository
import ru.jsavings.data.repository.network.currency.CurrencyRepository
import ru.jsavings.domain.mappers.database.TransactionMapper
import ru.jsavings.domain.model.database.category.TransactionCategoryType
import ru.jsavings.domain.model.database.transaction.Transaction
import ru.jsavings.domain.usecase.common.BaseUseCase

/**
 * Update transaction info in database usecase
 * @param transactionRepository [TransactionRepository] to interact with database
 * @param transactionMapper [TransactionMapper]
 *
 * @author JustSpace
 */
class UpdateTransactionUseCase(
	private val transactionRepository: TransactionRepository,
	private val transactionMapper: TransactionMapper,
	private val currencyRepository: CurrencyRepository,
	private val walletRepository: WalletRepository,
	private val accountRepository: AccountRepository,
) : BaseUseCase() {

	private fun AccountEntity.updateCompletable(update: (AccountEntity) -> Double) = accountRepository.updateAccount(
		copy(balanceInMainCurrency = update(this))
	)

	private fun WalletEntity.updateCompletable(update: (WalletEntity) -> Double) =
		walletRepository.updateWallet(copy(balance = update(this)))

	private fun getUpdateDataCompletable(
		firstWalletWithSameOrNotIdToUpdate: WalletEntity,
		secondWalletWithSameOrNotIdToUpdate: WalletEntity,
		firstUpdateSum: Double,
		secondUpdateSum: Double,
		firstChangeFunction: (Double, Double) -> Double,
		secondChangeFunction: (Double, Double) -> Double,
		accountUpdateCompletable: Completable = Completable.complete(),
		additionalWalletUpdateCompletable: Completable = Completable.complete(),
	): Completable {

		val walletsUpdateCompletable =
			if (firstWalletWithSameOrNotIdToUpdate.walletId == secondWalletWithSameOrNotIdToUpdate.walletId) {
				firstWalletWithSameOrNotIdToUpdate.updateCompletable {
					secondChangeFunction(firstChangeFunction(it.balance, firstUpdateSum), secondUpdateSum)
				}
			} else {
				Completable.mergeArray(
					firstWalletWithSameOrNotIdToUpdate.updateCompletable {
						firstChangeFunction(it.balance, firstUpdateSum)
					},
					secondWalletWithSameOrNotIdToUpdate.updateCompletable {
						secondChangeFunction(it.balance, secondUpdateSum)
					}
				)
			}

		return Completable.mergeArray(
			accountUpdateCompletable, walletsUpdateCompletable, additionalWalletUpdateCompletable
		)
	}

	private fun getUpdateCompletable(
		account: AccountEntity,
		oldTransaction: TransactionGroupEntity,
		newTransaction: TransactionGroupEntity,
	): Completable = when {

		/**
		 * [TransactionCategoryType.INCOME] -> [TransactionCategoryType.INCOME]
		 */
		oldTransaction.categoryEntity?.type == TransactionCategoryType.INCOME.toString() &&
		newTransaction.categoryEntity?.type == TransactionCategoryType.INCOME.toString() -> getUpdateDataCompletable(
			firstWalletWithSameOrNotIdToUpdate = oldTransaction.toWalletEntity!!,
			secondWalletWithSameOrNotIdToUpdate = newTransaction.toWalletEntity!!,
			firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
			secondUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
			firstChangeFunction = Double::minus,
			secondChangeFunction = Double::plus,
			accountUpdateCompletable = account.updateCompletable {
				it.balanceInMainCurrency -
						oldTransaction.transactionEntity.sumInAccountCurrency +
						newTransaction.transactionEntity.sumInAccountCurrency
			}
		)

		/**
		 * [TransactionCategoryType.INCOME] -> [TransactionCategoryType.CONSUMPTION]
		 */
		oldTransaction.categoryEntity?.type == TransactionCategoryType.INCOME.toString() &&
		newTransaction.categoryEntity?.type == TransactionCategoryType.CONSUMPTION.toString() ->
			getUpdateDataCompletable(
				firstWalletWithSameOrNotIdToUpdate = oldTransaction.toWalletEntity!!,
				secondWalletWithSameOrNotIdToUpdate = newTransaction.fromWalletEntity!!,
				firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
				secondUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
				firstChangeFunction = Double::minus,
				secondChangeFunction = Double::minus,
				accountUpdateCompletable = account.updateCompletable {
					it.balanceInMainCurrency -
							oldTransaction.transactionEntity.sumInAccountCurrency -
							newTransaction.transactionEntity.sumInAccountCurrency
				}
			)

		/**
		 * [TransactionCategoryType.INCOME] -> [TransactionCategoryType.TRANSFER]
		 */
		oldTransaction.categoryEntity?.type == TransactionCategoryType.INCOME.toString() &&
		newTransaction.categoryEntity == null ->
			getUpdateDataCompletable(
				firstWalletWithSameOrNotIdToUpdate = oldTransaction.toWalletEntity!!,
				secondWalletWithSameOrNotIdToUpdate = newTransaction.toWalletEntity!!,
				firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
				secondUpdateSum = newTransaction.transactionEntity.transferSum!!,
				firstChangeFunction = Double::minus,
				secondChangeFunction = Double::plus,
				accountUpdateCompletable = account.updateCompletable {
					it.balanceInMainCurrency - oldTransaction.transactionEntity.sumInAccountCurrency
				},
				additionalWalletUpdateCompletable = newTransaction.fromWalletEntity!!.updateCompletable {
					it.balance - newTransaction.transactionEntity.sumInWalletCurrency
				}
			)

		/**
		 * [TransactionCategoryType.CONSUMPTION] -> [TransactionCategoryType.INCOME]
		 */
		oldTransaction.categoryEntity?.type == TransactionCategoryType.CONSUMPTION.toString() &&
		newTransaction.categoryEntity?.type == TransactionCategoryType.INCOME.toString() -> getUpdateDataCompletable(
			firstWalletWithSameOrNotIdToUpdate = oldTransaction.fromWalletEntity!!,
			secondWalletWithSameOrNotIdToUpdate = newTransaction.toWalletEntity!!,
			firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
			secondUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
			firstChangeFunction = Double::plus,
			secondChangeFunction = Double::plus,
			accountUpdateCompletable = account.updateCompletable {
				it.balanceInMainCurrency +
						oldTransaction.transactionEntity.sumInAccountCurrency +
						newTransaction.transactionEntity.sumInAccountCurrency
			}
		)

		/**
		 * [TransactionCategoryType.CONSUMPTION] -> [TransactionCategoryType.CONSUMPTION]
		 */
		oldTransaction.categoryEntity?.type == TransactionCategoryType.CONSUMPTION.toString() &&
		newTransaction.categoryEntity?.type == TransactionCategoryType.CONSUMPTION.toString() ->
			getUpdateDataCompletable(
				firstWalletWithSameOrNotIdToUpdate = oldTransaction.fromWalletEntity!!,
				secondWalletWithSameOrNotIdToUpdate = newTransaction.fromWalletEntity!!,
				firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
				secondUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
				firstChangeFunction = Double::plus,
				secondChangeFunction = Double::minus,
				accountUpdateCompletable = account.updateCompletable {
					it.balanceInMainCurrency +
							oldTransaction.transactionEntity.sumInAccountCurrency -
							newTransaction.transactionEntity.sumInAccountCurrency
				}
			)

		/**
		 * [TransactionCategoryType.CONSUMPTION] -> [TransactionCategoryType.TRANSFER]
		 */
		oldTransaction.categoryEntity?.type == TransactionCategoryType.CONSUMPTION.toString() &&
		newTransaction.categoryEntity == null -> getUpdateDataCompletable(
			firstWalletWithSameOrNotIdToUpdate = oldTransaction.fromWalletEntity!!,
			secondWalletWithSameOrNotIdToUpdate = newTransaction.fromWalletEntity!!,
			firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
			secondUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
			firstChangeFunction = Double::plus,
			secondChangeFunction = Double::minus,
			accountUpdateCompletable = account.updateCompletable {
				it.balanceInMainCurrency + oldTransaction.transactionEntity.sumInAccountCurrency
			},
			additionalWalletUpdateCompletable = newTransaction.toWalletEntity!!.updateCompletable {
				it.balance + newTransaction.transactionEntity.transferSum!!
			}
		)

		/**
		 * [TransactionCategoryType.TRANSFER] -> [TransactionCategoryType.INCOME]
		 */
		oldTransaction.categoryEntity == null &&
		newTransaction.categoryEntity?.type == TransactionCategoryType.INCOME.toString() -> getUpdateDataCompletable(
			firstWalletWithSameOrNotIdToUpdate = oldTransaction.toWalletEntity!!,
			secondWalletWithSameOrNotIdToUpdate = newTransaction.toWalletEntity!!,
			firstUpdateSum = oldTransaction.transactionEntity.transferSum!!,
			secondUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
			firstChangeFunction = Double::minus,
			secondChangeFunction = Double::plus,
			accountUpdateCompletable = account.updateCompletable {
				it.balanceInMainCurrency + newTransaction.transactionEntity.sumInAccountCurrency
			},
			additionalWalletUpdateCompletable = oldTransaction.fromWalletEntity!!.updateCompletable {
				it.balance + oldTransaction.transactionEntity.sumInWalletCurrency
			}
		)

		/**
		 * [TransactionCategoryType.TRANSFER] -> [TransactionCategoryType.CONSUMPTION]
		 */
		oldTransaction.categoryEntity == null &&
		newTransaction.categoryEntity?.type == TransactionCategoryType.CONSUMPTION.toString() ->
			getUpdateDataCompletable(
				firstWalletWithSameOrNotIdToUpdate = oldTransaction.fromWalletEntity!!,
				secondWalletWithSameOrNotIdToUpdate = newTransaction.fromWalletEntity!!,
				firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
				secondUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
				firstChangeFunction = Double::plus,
				secondChangeFunction = Double::minus,
				accountUpdateCompletable = account.updateCompletable {
					it.balanceInMainCurrency - newTransaction.transactionEntity.sumInAccountCurrency
				},
				additionalWalletUpdateCompletable = oldTransaction.toWalletEntity!!.updateCompletable {
					it.balance - oldTransaction.transactionEntity.transferSum!!
				}
			)

		/**
		 * [TransactionCategoryType.TRANSFER] -> [TransactionCategoryType.TRANSFER]
		 */
		oldTransaction.categoryEntity == null && newTransaction.categoryEntity == null -> Completable.mergeArray(
			getUpdateDataCompletable(
				firstWalletWithSameOrNotIdToUpdate = oldTransaction.fromWalletEntity!!,
				secondWalletWithSameOrNotIdToUpdate = oldTransaction.toWalletEntity!!,
				firstUpdateSum = oldTransaction.transactionEntity.sumInWalletCurrency,
				secondUpdateSum = oldTransaction.transactionEntity.transferSum!!,
				firstChangeFunction = Double::plus,
				secondChangeFunction = Double::minus
			),
			getUpdateDataCompletable(
				firstWalletWithSameOrNotIdToUpdate = newTransaction.fromWalletEntity!!,
				secondWalletWithSameOrNotIdToUpdate = newTransaction.toWalletEntity!!,
				firstUpdateSum = newTransaction.transactionEntity.sumInWalletCurrency,
				secondUpdateSum = newTransaction.transactionEntity.transferSum!!,
				firstChangeFunction = Double::minus,
				secondChangeFunction = Double::plus
			)
		)

		else -> {
			throw IllegalStateException()
		}
	}.andThen(
		transactionRepository.updateTransaction(newTransaction.transactionEntity)
	)

	/**
	 * Invokes usecase
	 * @param newTransaction Transaction with new data to update
	 * @return [Completable] source of action
	 *
	 * @author JustSpace
	 */
	operator fun invoke(newTransaction: Transaction): Completable =
		accountRepository.getAccountByIdSingle(newTransaction.accountId).flatMapCompletable { account ->
			transactionRepository.getTransactionById(newTransaction.transactionId)
				.flatMapCompletable { oldTransaction ->

					val (fromCurrencyCode, toCurrencyCode) = when(newTransaction.category?.categoryType) {
						TransactionCategoryType.INCOME -> newTransaction.toWallet!!.currency to account.mainCurrencyCode
						TransactionCategoryType.CONSUMPTION ->
							newTransaction.fromWallet!!.currency to account.mainCurrencyCode
						else -> newTransaction.fromWallet!!.currency to newTransaction.toWallet!!.currency
					}

					getConversion(
						currencyRepository,
						fromCurrency = fromCurrencyCode,
						toCurrency = toCurrencyCode,
						amount = newTransaction.sumInWalletCurrency
					).flatMapCompletable { conversionResult ->
						val newTransactionCopy = if (newTransaction.category == null) {
							newTransaction.copy(transferSum = conversionResult, sumInAccountCurrency = 0.0)
						} else {
							newTransaction.copy(sumInAccountCurrency = conversionResult, transferSum = null)
						}

						val newTransactionGroup = transactionMapper.mapModelToGroupEntity(newTransactionCopy)
						getUpdateCompletable(account, oldTransaction, newTransactionGroup)
					}
				}
		}
}