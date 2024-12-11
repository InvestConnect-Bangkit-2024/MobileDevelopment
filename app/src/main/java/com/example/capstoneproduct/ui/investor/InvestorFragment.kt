package com.example.capstoneproduct.ui.investor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproduct.databinding.FragmentInvestorBinding
import com.example.capstoneproduct.ui.adapter.InvestorAdapter

class InvestorFragment : Fragment() {

    private var _binding: FragmentInvestorBinding? = null
    private val binding get() = _binding!!

    private lateinit var investorRv: RecyclerView
    private lateinit var investorAdapter: InvestorAdapter

    private val investorViewModel: InvestorViewModel by viewModels {
        InvestorViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvestorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isNetworkAvailable(requireContext())) {
            investorViewModel.getInvestor()
        } else {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT).show()
        }


        setupRecyclerView()
        observeViewModel()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION") return networkInfo.isConnected
        }
    }

    private fun observeViewModel() {
        investorViewModel.investor.observe(viewLifecycleOwner) { investor ->
            Log.d("InvestorFragment", "Investors: $investor") // Debug log
            investorAdapter.submitList(investor)
        }

        investorViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            Log.d("InvestorFragment", "Loading: $loading") // Debug log
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        investorViewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupRecyclerView() {
        investorRv = binding.rvInvestor
        investorAdapter = InvestorAdapter()
        investorRv.layoutManager = LinearLayoutManager(context)
        investorRv.adapter = investorAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}