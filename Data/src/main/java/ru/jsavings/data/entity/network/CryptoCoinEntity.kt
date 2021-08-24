package ru.jsavings.data.entity.network

import com.google.gson.annotations.SerializedName
import ru.jsavings.data.entity.network.common.BaseNetworkEntity

/**
 * Entity of crypto coin network answer
 * @param success Was request successful or not
 * @param cryptocurrencies Map of currency id and [CryptoCoinInfoEntity]
 *
 * @author JustSpace
 */
data class CryptoCoinEntity(

	@SerializedName("success")
	val success: Boolean,

	@SerializedName("cryptocurrencies")
	val cryptocurrencies: Map<String, CryptoCoinInfoEntity>
) : BaseNetworkEntity

/**
 * Info about crypto coin from api
 * @param symbol Symbol of crypto coin
 * @param name Name of crypto coin
 *
 * @author JustSpace
 */
data class CryptoCoinInfoEntity(

	@SerializedName("symbol")
	val symbol: String,

	@SerializedName("name")
	val name: String
)