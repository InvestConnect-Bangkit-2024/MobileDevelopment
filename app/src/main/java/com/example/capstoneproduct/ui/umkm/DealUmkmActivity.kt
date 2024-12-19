package com.example.capstoneproduct.ui.umkm


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproduct.R
import com.example.capstoneproduct.databinding.ActivityDealUmkmBinding
import com.example.capstoneproduct.ui.DealViewModelFactory
import com.example.capstoneproduct.ui.status.StatusFragment
import kotlinx.coroutines.launch


class DealUmkmActivity : AppCompatActivity() {

    private val viewModel by viewModels<DealUmkmViewModel> {
        DealViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDealUmkmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDealUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val umkmId = intent.getStringExtra("UMKM_ID") ?: ""
        val umkmName = intent.getStringExtra("UMKM_NAME") ?: ""

        binding.tvUmkm.text = umkmName
        setupAction(umkmId)
    }

    private fun setupAction(umkmId: String) {
        binding.btnDeal.setOnClickListener {
            Log.d("DealUmkmActivity", "Button clicked!")
            val amount = binding.amountEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0

            if (umkmId.isEmpty()) {
                Toast.makeText(this, "UMKM ID is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response =
                        viewModel.enterAmount(amount, umkmId)
                    // Handle successful response
                    if (response != null) {
                        Toast.makeText(
                            this@DealUmkmActivity,
                            "Request successful: ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToFragment()
                        showLoading(true)
                    } else {
                        Toast.makeText(this@DealUmkmActivity, "Request failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@DealUmkmActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToFragment() {
        val statusFragment = StatusFragment() // Create an instance of the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_status, statusFragment) // Replace the current fragment
            .addToBackStack(null) // Optional: Add to back stack to allow back navigation
            .commit() // Commit the transaction
    }
}
