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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import ru.jsavings.R
import ru.jsavings.presentation.viewmodels.common.BaseViewModel
import java.util.*

/**
 * Base fragment class for all fragments in JSavings app
 *
 * @author JustSpace
 */
abstract class BaseFragment : Fragment() {

	/**
	 * ViewModel for fragment
	 *
	 * @author JustSpace
	 */
	protected abstract val viewModel: BaseViewModel

	/**
	 * ViewBindingUtil for fragment
	 *
	 * @author JustSpace
	 */
	protected abstract val bindingUtil: ViewBinding

	private var dialog: Dialog? = null

	/**
	 * Hide keyboard
	 *
	 * @author JustSpace
	 */
	protected fun hideKeyBoard() {
		val inputMethodManager = requireContext()
			.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

		inputMethodManager.hideSoftInputFromWindow(bindingUtil.root.windowToken, 0)

		requireActivity().window.currentFocus?.clearFocus()
	}

	/**
	 * Set hide keyboard on root touch click listener
	 *
	 * @author JustSpace
	 */
	@SuppressLint("ClickableViewAccessibility")
	protected fun hideKeyBoardOnRootTouchClick() = bindingUtil.root.setOnTouchListener { _, _ ->
		hideKeyBoard()
		false
	}

	/**
	 * Show text snack bar
	 * @param text Text of snack bar
	 * @param length Length show time of snack bar
	 * @param actionText Text of action button (if empty there won't be action button on snack bar)
	 * @param action Lambda of action what to do when action button clicked
	 *
	 * @author JustSpace
	 */
	protected fun showTextSnackBar(
		text: String,
		length: Int = Snackbar.LENGTH_LONG,
		actionText: String = "",
		action: (View) -> Unit = {},
	) {
		val snackBar = Snackbar.make(bindingUtil.root, text, length)
		if (actionText.isNotEmpty())
			snackBar.setAction(actionText, action)
		snackBar.show()
	}

	/**
	 * Show loading dialog
	 * @param text Text of loading
	 *
	 * @author JustSpace
	 */
	protected fun showLoading(text: String = "") {
		dialog?.let {
			val textLoading = it.findViewById<TextView>(R.id.text_loading)
			textLoading.text = text
			textLoading.visibility = View.VISIBLE
			return
		}

		dialog = Dialog(requireContext()).apply {
			requestWindowFeature(Window.FEATURE_NO_TITLE)
			setContentView(R.layout.dialog_loading)

			val textLoading = findViewById<TextView>(R.id.text_loading)
			if (text.isNotEmpty())
				textLoading.text = text
			else
				textLoading.visibility = View.GONE

			window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
			setCancelable(false)
			setCanceledOnTouchOutside(false)
			show()
		}
	}

	/**
	 * Show loading dialog
	 * @param textId Id of string in res/strings
	 *
	 * @author JustSpace
	 */
	protected fun showLoading(textId: Int) = showLoading(getString(textId))

	/**
	 * Hide loading dialog
	 *
	 * @author JustSpace
	 */
	protected fun hideLoading() {
		dialog?.dismiss()
		dialog = null
	}

	/**
	 * Check if there is internet connection
	 *
	 * @author JustSpace
	 */
	//TODO Переделать на нормальную версию
	protected val isInternetAvailable: Boolean
		get() {
			val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) ?: return false
			cm as ConnectivityManager

			return cm.activeNetworkInfo?.isConnected ?: false
		}

	/**
	 * Get error localizedMessage or default string
	 * @return String of error
	 *
	 * @author JustSpace
	 */
	protected fun Throwable.getErrorString(): String =
		localizedMessage ?: getString(R.string.something_went_wrong)

	protected val locale: Locale = if (Locale.getDefault() != Locale("ru", "RU"))
		Locale.US
	else
		Locale("ru", "RU")

	/**
	 *
	 * @author JustSpace
	 */
	protected fun <T> LiveData<BaseViewModel.RequestState>.subscribe(
		hideLoading: Boolean,
		onSuccess: (T) -> Unit,
		onError: (Throwable) -> Unit,
		onSending: () -> Unit = {},
	) = observe(viewLifecycleOwner) { state ->
		when (state) {
			is BaseViewModel.RequestState.SuccessState<*> -> {
				try {
					onSuccess(state.data as T)
				} catch (e: TypeCastException) {
					onError(e)
				} finally {
					if (hideLoading) hideLoading()
				}
			}
			is BaseViewModel.RequestState.ErrorState<*> -> {
				hideLoading()
				onError(state.t)
			}
			is BaseViewModel.RequestState.SendingState -> onSending()
		}
	}
}
