package ru.jsavings.data.entity

import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.jsavings.data.entity.common.BaseEntity

/**
 * Entity of wallet in database
 * @param walletId Id of wallet in database
 * @param walletName Name of wallet
 * @param accountFkId Id of [AccountEntity] to which purse belongs
 * @param currencyCode Code of main currency of wallet, according to api or [java.util.Currency] (usd, bitcoin, ...)
 * @param balance Balance of wallet if currency which code is [currencyCode]
 * @param category Category of this purse: DEBIT_CARD, CRYPTO_WALLET, ...
 * @param color Number representation of a color
 * @param iconPath Path to an icon of this purse
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
internal data class WalletEntity (

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "wallet_id")
	val walletId: Long = 0,

	@ColumnInfo(name = "purse_name")
	val walletName: String,

	@ColumnInfo(name = "account_fk_id", index = true)
	val accountFkId: Int,

	val currencyCode: String,
	val balance: Double,
	val category: String,

	@ColorInt
	val color: Int,

	@ColumnInfo(name = "icon_path")
	val iconPath: String

) : BaseEntity