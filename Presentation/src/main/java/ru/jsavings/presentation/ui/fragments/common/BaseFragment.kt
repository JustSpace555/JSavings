package ru.jsavings.presentation.ui.fragments.common

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

	protected abstract val viewModel: BaseViewModel

	protected abstract val bindingUtil: ViewBinding

	protected fun showTextSnackBar(view: View, text: String, length: Int = Snackbar.LENGTH_SHORT) =
		Snackbar.make(view, text, length).show()
}
