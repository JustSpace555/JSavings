package ru.jsavings.domain.mappers.database

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import ru.jsavings.domain.common.BaseUnitTest

class TransactionMapperTest : BaseUnitTest() {

	private lateinit var transactionMapper: TransactionMapper
	private lateinit var categoryMapper: TransactionCategoryMapper
	private lateinit var walletMapper: WalletMapper

	@Before
	fun setUp() {
		categoryMapper = mockk()
		walletMapper = mockk()
		transactionMapper = TransactionMapper(categoryMapper, walletMapper)
	}

	@Test
	fun mapEntityToModel() {

		//Arrange
		val expectedResult = transactionModel

		//Act
		every { categoryMapper.mapEntityToModel(categoryEntity) } returns categoryModel
		every { walletMapper.mapEntityToModel(walletEntity) } returns walletModel
		val actualResult = transactionMapper.mapEntityToModel(transactionGroupEntity)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun mapModelToTransactionEntity() {

		//Arrange
		val expectedResult = transactionEntity

		//Act
		every { categoryMapper.mapModelToEntity(categoryModel) } returns categoryEntity
		every { walletMapper.mapModelToEntity(walletModel) } returns walletEntity
		val actualResult = transactionMapper.mapModelToTransactionEntity(transactionModel)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun mapModelToGroupEntity() {

		//Arrange
		val expectedResult = transactionGroupEntity

		//Act
		every { categoryMapper.mapModelToEntity(categoryModel) } returns categoryEntity
		every { walletMapper.mapModelToEntity(walletModel) } returns walletEntity
		val actualResult = transactionMapper.mapModelToGroupEntity(transactionModel)

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}