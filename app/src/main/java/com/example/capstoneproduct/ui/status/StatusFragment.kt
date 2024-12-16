package com.example.capstoneproduct.ui.status

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.dataStore
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproduct.R
import com.example.capstoneproduct.databinding.FragmentStatusBinding
import com.example.capstoneproduct.ui.StatusViewModelFactory

class StatusFragment : Fragment() {

    private var _binding: FragmentStatusBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvOffer: RecyclerView
    private lateinit var rvSentOffer: RecyclerView
    private lateinit var offerAdapter: OfferAdapter
    private lateinit var requestAdapter: RequestAdapter

    private val viewModel: StatusViewModel by viewModels() {
        StatusViewModelFactory.getInstance(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatusBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvOffer = binding.rvSentOffer // Assuming this is the ID for offers RecyclerView
        offerAdapter = OfferAdapter()
        rvOffer.layoutManager = LinearLayoutManager(context)
        rvOffer.adapter = offerAdapter

        // Observe offers data
        viewModel.statusOffer.observe(viewLifecycleOwner) { offers ->
            Log.d("StatusFragment", "Offers received: ${offers.size}")
            offerAdapter.submitList(offers)
        }

        // Observe loading state for offers
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        // Observe error state for offers
        viewModel.isError.observe(viewLifecycleOwner) { error ->
            if (error) {
                Toast.makeText(context, "Error loading offers", Toast.LENGTH_SHORT).show()
            }
        }

        // Call the method to fetch status
        viewModel.getStatus()
    }


//        // Setup RecyclerView for requests
//        val requestRv = binding.recyclerView2 // Assuming this is the ID for requests RecyclerView
//        requestAdapter = RequestAdapter()
//        requestRv.layoutManager = LinearLayoutManager(context)
//        requestRv.adapter = requestAdapter
//
//        // Observe requests data
//        requestViewModel.requests.observe(viewLifecycleOwner) { requests ->
//            requestAdapter.submitList(requests)
//        }
//
//        // Observe loading state for requests
//        requestViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
//            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
//        }
//
//        // Observe error state for requests
//        requestViewModel.isError.observe(viewLifecycleOwner) { error ->
//            if (error) {
//                Toast.makeText(context, "Error loading requests", Toast.LENGTH_SHORT).show()
//            }
//        }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("InvestorFragment", "onDestroyView: ")
        _binding = null // Clear binding reference to avoid memory leaks
    }
}