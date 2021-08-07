package ru.jsavings.presentation.ui.fragments.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import ru.jsavings.R

abstract class BaseFragment : Fragment() {

	protected abstract val viewModel: BaseViewModel

	protected abstract val bindingUtil: ViewBinding

	private var dialog: Dialog? = null

	@SuppressLint("ClickableViewAccessibility")
	protected fun hideKeyBoardOnRootTouch(root: View) {
		root.setOnTouchListener { v, _ ->
			val inputMethodManager = requireContext()
				.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

			inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)

			requireActivity().window.currentFocus?.clearFocus()
			false
		}
	}

	protected fun showTextSnackBar(
		text: String,
		length: Int = Snackbar.LENGTH_LONG,
		actionText: String = "",
		action: (View) -> Unit = {},
	) {
		val snackBar = Snackbar.make(requireView(), text, length)
		if (actionText.isNotEmpty())
			snackBar.setAction(actionText, action)
		snackBar.show()
	}

	protected fun showLoading() {
		dialog = Dialog(requireContext()).apply {
			requestWindowFeature(Window.FEATURE_NO_TITLE)
			setContentView(R.layout.common_loading_dialog)
			window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
			setCancelable(false)
			setCanceledOnTouchOutside(false)
			show()
		}
	}

	protected fun hideLoading() {
		dialog?.dismiss()
		dialog = null
	}

	//TODO Переделать на нормальную версию
	protected val isInternetAvailable: Boolean
		get() {
			val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) ?: return false
			cm as ConnectivityManager

			return cm.activeNetworkInfo?.isConnected ?: false
		}

	protected fun Throwable.getErrorString(): String =
		localizedMessage ?: getString(R.string.something_went_wrong)
}
