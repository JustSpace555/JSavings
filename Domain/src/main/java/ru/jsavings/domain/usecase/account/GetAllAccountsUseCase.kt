package ru.jsavings.domain.usecase.account

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.Account
import ru.jsavings.domain.usecase.common.BaseUseCase

interface GetAllAccountsUseCase : BaseUseCase<Single<List<Account>>>