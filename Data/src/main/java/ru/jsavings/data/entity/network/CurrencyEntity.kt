package ru.jsavings.data.entity.network

import com.google.gson.annotations.SerializedName
import ru.jsavings.data.entity.network.common.BaseNetworkEntity

/**
 * Entity of standard currency from api
 * @param success Was request successful or not
 * @param symbols map of currency code and [CurrencyInfoEntity]
 *
 * @author JustSpace
 */
data class CurrencyEntity(

	@SerializedName("success")
	val success: Boolean,

	@SerializedName("symbols")
	val symbols: Map<String, CurrencyInfoEntity>
) : BaseNetworkEntity

/**
 * Entity of currency info from api
 * @param description Name of currency
 * @param code Code of currency
 *
 * @author JustSpace
 */
data class CurrencyInfoEntity(

	@SerializedName("description")
	val description: String,

	@SerializedName("code")
	val code: String
)