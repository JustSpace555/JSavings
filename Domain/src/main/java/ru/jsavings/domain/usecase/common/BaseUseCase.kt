package ru.jsavings.domain.usecase.common

interface BaseUseCase <RESULT> {
	operator fun invoke(): RESULT
}