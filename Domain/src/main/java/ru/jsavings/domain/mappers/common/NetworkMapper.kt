package ru.jsavings.domain.mappers.common

import ru.jsavings.data.entity.common.BaseEntity

/**
 * Interface for all network data mappers
 * @param E Some class that inherits from [BaseEntity] to map from
 * @param M Some class to map to
 *
 * @author JustSpace
 */
internal interface NetworkMapper<E: BaseEntity, M> : BaseMapper {

	/**
	 * Function to map som [BaseEntity] to some model [M]
	 * @param entity [BaseEntity] to map from
	 * @return Model representation of [entity]
	 *
	 * @author JustSpace
	 */
	fun mapEntityToModel(entity: E): M
}