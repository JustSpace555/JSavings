package ru.jsavings.data.entity.database

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.data.entity.database.AccountEntity

/**
 * Entity of wallet in database
 * @param walletId Id of wallet in database
 * @param walletName Name of wallet
 * @param accountFkId Id of [AccountEntity] to which wallet belongs
 * @param currencyCode Code of main currency of wallet, according to api or [java.util.Currency] (usd, bitcoin, ...)
 * @param balance Balance of wallet if currency which code is [currencyCode]
 * @param category Category of this wallet: DEBIT_CARD, CRYPTO_WALLET, ...
 * @param creditLimit Credit limit of this wallet (valid only if [category] is CREDIT_CARD)
 * @param interestRate Credit interest rate (valid only if [category] is CREDIT_CARD)
 * @param paymentDay Credit day of payment (valid only if [category] is CREDIT_CARD)
 * @param gracePeriod Credit grace period (valid only if [category] is CREDIT_CARD)
 * @param color Number representation of a color
 * @param iconPath Path to an icon of this wallet
 *
 * @author JustSpace
 */
@Entity (
	tableName = "wallet_table",
	foreignKeys = [
		ForeignKey(
			entity = AccountEntity::class,
			childColumns = ["account_fk_id"],
			parentColumns = ["account_id"],
			onDelete = ForeignKey.CASCADE
		)
	]
)
data class WalletEntity (

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "wallet_id")
	val walletId: Long = 0,

	@ColumnInfo(name = "wallet_name")
	val walletName: String,

	@ColumnInfo(name = "account_fk_id", index = true)
	val accountFkId: Long,

	val currencyCode: String,
	val balance: Double,
	val category: String,
	val creditLimit: Double,
	val interestRate: Double,
	val paymentDay: Int,
	val gracePeriod: Int,

	@ColorInt
	val color: Int,

	@ColumnInfo(name = "icon_path")
	val iconPath: String

) : BaseEntity