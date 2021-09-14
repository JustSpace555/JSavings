package ru.jsavings.data.entity.database

import androidx.room.Embedded
import androidx.room.Relation
import ru.jsavings.data.entity.common.BaseEntity

/**
 * Transaction entity with all other necessary entities. Is needed for more convenient way to get transaction without
 * invoking dao methods to get all other entities.
 * @param transactionEntity [TransactionEntity]
 * @param fromWalletEntity [WalletEntity]. Must be not null only if [TransactionCategoryEntity.type] is CONSUMPTION or
 * TRANSFER. Represents wallet from which money were writing off
 * @param toWalletEntity [WalletEntity]. Must be not null only if [TransactionCategoryEntity.type] is INCOME or TRANSFER.
 * Represents wallet to which money were added.
 * @param categoryEntity [TransactionCategoryEntity]
 *
 * @author JustSpace
 */
data class TransactionGroupEntity(

	@Embedded
	val transactionEntity: TransactionEntity,

	@Relation(
		parentColumn = "from_wallet_fk_id",
		entityColumn = "wallet_id"
	)
	val fromWalletEntity: WalletEntity?,

	@Relation(
		parentColumn = "to_wallet_fk_id",
		entityColumn = "wallet_id"
	)
	val toWalletEntity: WalletEntity?,

	@Relation(
		parentColumn = "category_fk_id",
		entityColumn = "category_id"
	)
	val categoryEntity: TransactionCategoryEntity?
) : BaseEntity