package ru.jsavings.presentation.viewmodels.wallets

import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.presentation.extension.ThreadProvider
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

class WalletsViewModel(
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider)