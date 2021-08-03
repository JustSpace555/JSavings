package ru.jsavings.presentation.ui.fragments.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
			(requireContext()
				.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
					).hideSoftInputFromWindow(v.windowToken, 0)
			requireActivity().window.currentFocus?.clearFocus()
			true
		}
	}

	protected fun showTextSnackBar(view: View, text: String, length: Int = Snackbar.LENGTH_SHORT) =
		Snackbar.make(view, text, length).show()

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
}
