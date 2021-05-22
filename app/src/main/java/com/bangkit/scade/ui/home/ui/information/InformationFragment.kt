package com.bangkit.scade.ui.home.ui.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.scade.databinding.FragmentInformationBinding
import com.bangkit.scade.viewmodel.ViewModelFactory

class InformationFragment : Fragment() {

    private lateinit var viewModel: InformationViewModel
    private var _binding: FragmentInformationBinding? = null
    private lateinit var adapter: InformationAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = InformationAdapter()
        adapter.notifyDataSetChanged()

        binding.rvInformation.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvInformation.adapter = adapter

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(
            requireActivity(),
            factory
        )[InformationViewModel::class.java]

        viewModel.listArticle.observe(viewLifecycleOwner,{list ->
            adapter.setInformation(list)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}