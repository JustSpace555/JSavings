package ru.jsavings.presentation.ui.fragments.graph

import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.jsavings.presentation.ui.fragments.common.BaseViewModel
import ru.jsavings.presentation.ui.fragments.common.ThreadProvider

class GraphViewModel(
	compositeDisposable: CompositeDisposable = CompositeDisposable(),
	threadProvider: ThreadProvider = ThreadProvider()
) : BaseViewModel(compositeDisposable, threadProvider)