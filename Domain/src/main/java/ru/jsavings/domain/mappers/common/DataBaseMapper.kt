package ru.jsavings.domain.mappers.common

import ru.jsavings.data.entity.common.BaseEntity
import ru.jsavings.domain.model.common.BaseModel

/**
 * Interface for all database mappers from [BaseEntity] to [BaseModel]
 * @param E class that inherits from [BaseEntity]
 * @param M class that inherits from [BaseModel]
 * @author JustSpace
 */
internal interface DataBaseMapper <E: BaseEntity, M: BaseModel> : BaseMapper {

	/**
	 * Mapping some [BaseEntity] to more convenient for ui [BaseModel]
	 * @param entity some [BaseEntity]
	 * @return some [BaseModel]
	 *
	 * @author JustSpace
	 */
	fun mapEntityToModel(entity: E): M

	/**
	 * Mapping some [BaseModel] to necessary for database or api [BaseEntity]
	 * @param model some [BaseModel]
	 * @return some [BaseEntity]
	 *
	 * @author JustSpace
	 */
	fun mapModelToEntity(model: M): E
}