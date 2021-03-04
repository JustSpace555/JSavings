package ru.jsavings.presentation.ui.fragments.common

import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment : Fragment() {

	protected val disposable = mutableListOf<Disposable>()
}