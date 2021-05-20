package com.bangkit.scade.ui.home.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.scade.R
import com.bangkit.scade.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        historyViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textHistory.text = resources.getString(R.string.history_booking)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}