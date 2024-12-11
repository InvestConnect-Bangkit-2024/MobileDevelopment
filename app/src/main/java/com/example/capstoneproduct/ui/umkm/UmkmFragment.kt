package com.example.capstoneproduct.ui.umkm

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
import com.example.capstoneproduct.databinding.FragmentUmkmBinding
import com.example.capstoneproduct.ui.adapter.UmkmAdapter

class UmkmFragment : Fragment() {

    private var _binding: FragmentUmkmBinding? = null
    private val binding get() = _binding!!

    private lateinit var umkmRv: RecyclerView
    private lateinit var umkmAdapter: UmkmAdapter

    private val umkmViewModel: UmkmViewModel by viewModels() {
        UmkmViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUmkmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        umkmRv = binding.rvUmkm
        umkmAdapter = UmkmAdapter()
        umkmRv.layoutManager = LinearLayoutManager(context)
        umkmRv.adapter = umkmAdapter

        umkmViewModel.umkm.observe(viewLifecycleOwner) { umkm ->
            umkmAdapter.submitList(umkm)
        }

        umkmViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        umkmViewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
        umkmViewModel.getUmkm()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("InvestorFragment", "onDestroyView: ")
        _binding = null
    }
}