package com.example.capstoneproduct.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproduct.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvHome: RecyclerView
    private lateinit var homeAdapter: HomeAdapter

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHome = binding.rvHome
        homeAdapter = HomeAdapter()
        rvHome.layoutManager = LinearLayoutManager(context)
        rvHome.adapter = homeAdapter

        viewModel.companiesList.observe(viewLifecycleOwner) { companies ->
            homeAdapter.submitList(companies)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getCompanies()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}