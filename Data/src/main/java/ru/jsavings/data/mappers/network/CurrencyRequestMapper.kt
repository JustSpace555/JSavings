package ru.jsavings.data.mappers.network

import ru.jsavings.data.entity.network.currency.CurrencyEntity
import ru.jsavings.data.mappers.common.BaseNetworkMapper
import ru.jsavings.data.model.network.currency.Currency

internal class CurrencyRequestMapper : BaseNetworkMapper<CurrencyEntity, List<Currency>> {

	override fun mapEntityToModel(input: CurrencyEntity): List<Currency> {
		val currencyList = mutableListOf<Currency>()
		input.symbols.entries.forEach { entry ->
			currencyList.add(Currency(entry.value.code, entry.value.description))
		}
		return currencyList.sortedBy { it.code }
	}

}