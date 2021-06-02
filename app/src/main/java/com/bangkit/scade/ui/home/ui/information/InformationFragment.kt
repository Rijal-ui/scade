package com.bangkit.scade.ui.home.ui.information

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.scade.R
import com.bangkit.scade.databinding.FragmentInformationBinding
import com.bangkit.scade.ui.detailinformation.DetailInformationActivity
import com.bangkit.scade.ui.detailinformation.DetailInformationActivity.Companion.EXTRA_DATA
import com.bangkit.scade.viewmodel.ViewModelFactory
import com.bangkit.scade.vo.Status

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

        viewModel.getListArticle()
        viewModel.listArticle.observe(viewLifecycleOwner,{ list ->
            when(list.status) {
                Status.SUCCESS -> {
                    list.data?.let { adapter.setInformation(list.data) }
                    adapter.notifyDataSetChanged()
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, getString(R.string.error_message), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        adapter.onItemClick = { selectedData ->
            val intent = Intent(requireActivity(), DetailInformationActivity::class.java)
            intent.putExtra(EXTRA_DATA, selectedData)
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}