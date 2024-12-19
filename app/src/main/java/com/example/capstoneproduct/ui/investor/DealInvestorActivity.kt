package com.example.capstoneproduct.ui.investor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproduct.MainActivity
import com.example.capstoneproduct.databinding.ActivityDealInvestorBinding
import com.example.capstoneproduct.ui.DealViewModelFactory
import kotlinx.coroutines.launch

class DealInvestorActivity : AppCompatActivity() {

    private val viewModel by viewModels<DealInvestorViewModel> {
        DealViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDealInvestorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDealInvestorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val investorId = intent.getStringExtra("INVESTOR_ID") ?: ""
        val investorName = intent.getStringExtra("INVESTOR_NAME") ?: ""

        Log.d("DealInvestorActivity", "Investor ID: $investorId")
        Log.d("DealInvestorActivity", "Investor Name: $investorName")

        binding.tvInvestor.text = investorName
        setupAction(investorId)

    }

    private fun setupAction(investorId: String) {
        binding.btnDeal.setOnClickListener {
            Log.d("DealInvestorActivity", "Button clicked!")
            val amount = binding.amountEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0

            if (investorId.isEmpty()) {
                Toast.makeText(this, "Investor ID is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = viewModel.amountRequest(amount, investorId)
                    // Handle successful response
                    if (response != null) {
                        Toast.makeText(
                            this@DealInvestorActivity,
                            "Request successful: ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToFragment()
                        showLoading(true)
                    } else {
                        Toast.makeText(this@DealInvestorActivity, "Request failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@DealInvestorActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToFragment() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}