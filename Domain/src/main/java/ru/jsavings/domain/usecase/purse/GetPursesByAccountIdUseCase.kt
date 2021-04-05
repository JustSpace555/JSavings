package ru.jsavings.domain.usecase.purse

import io.reactivex.rxjava3.core.Single
import ru.jsavings.data.model.purse.Purse
import ru.jsavings.domain.usecase.common.BaseUseCase

interface GetPursesByAccountIdUseCase : BaseUseCase<Single<List<Purse>>>