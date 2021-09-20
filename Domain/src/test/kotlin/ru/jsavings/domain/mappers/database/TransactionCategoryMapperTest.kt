package ru.jsavings.domain.mappers.database

import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import ru.jsavings.domain.common.BaseUnitTest

class TransactionCategoryMapperTest : BaseUnitTest() {

	private lateinit var mapper: TransactionCategoryMapper

	@Before
	fun setUp() {
		mapper = TransactionCategoryMapper()
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = categoryModel

		//Act
		val actualResult = mapper.mapEntityToModel(categoryEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun mapModelToEntity() {

		//Arrange
		val expectedResult = categoryEntity

		//Act
		val actualResult = mapper.mapModelToEntity(categoryModel)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}