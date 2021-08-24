package ru.jsavings.data.entity.network

import com.google.gson.annotations.SerializedName
import ru.jsavings.data.entity.common.BaseEntity

/**
 * Entity of conversion request / answer from api
 * @param success Was request successful or not
 * @param query [QueryInfo]
 * @param info [RateInfo]
 * @param result The result of conversion
 *
 * @author JustSpace
 */
data class ConversionEntity(

	@SerializedName("success")
	val success: Boolean,

	@SerializedName("query")
	val query: QueryInfo,

	@SerializedName("info")
	val info: RateInfo,

	@SerializedName("result")
	val result: Double

) : BaseEntity

/**
 * Request / answer info about conversion
 * @param from Currency id from which conversion is made
 * @param to Currency id into which conversion is made
 * @param amount Amount of money to convert from currency with id [from]
 *
 * @author JustSpace
 */
data class QueryInfo(

	@SerializedName("from")
	val from: String,

	@SerializedName("to")
	val to: String,

	@SerializedName("amount")
	val amount: Double
)

/**
 * Answer info about conversion
 * @param rate conversion rate: currency [QueryInfo.from] currency [QueryInfo.to]
 *
 * @author JustSpace
 */
data class RateInfo(
	@SerializedName("rate")
	val rate: Double
)