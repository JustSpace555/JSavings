package ru.jsavings.presentation.exceptions

import java.lang.RuntimeException

class DataAlreadyExistsException(val dataId: Long) : RuntimeException()