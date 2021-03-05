package ru.jsavings.presentation.ui.fragments.purses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.jsavings.R

class PursesFragment : Fragment() {

    private lateinit var pursesViewModel: PursesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        pursesViewModel =
                ViewModelProvider(this).get(PursesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_calendar, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        pursesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}