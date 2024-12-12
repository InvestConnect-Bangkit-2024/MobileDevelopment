package com.example.capstoneproduct.ui.umkm


import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproduct.R
import com.example.capstoneproduct.data.repository.Result
import com.example.capstoneproduct.databinding.ActivityDealUmkmBinding
import com.example.capstoneproduct.ui.DealViewModelFactory
import com.example.capstoneproduct.ui.ViewModelFactory
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


        // Get the UMKM name from the Intent
        val umkmName = intent.getStringExtra("UMKM_NAME")

        // Now you can use the UMKM name wherever needed in this activity
        // Example usage:
        println("UMKM Name: $umkmName")  // For demonstration, you can log or use this name in the UI

        // Set up amount (replace with your logic to get the amount)
        setupAction()
    }

    private fun setupAction() {
        binding.btnDeal.setOnClickListener {
            Log.d("DealUmkmActivity", "Button clicked!")  // Add a log to check if the click is being detected

            val amount = binding.amountEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            lifecycleScope.launch {
                try {
                    val response =
                        viewModel.enterAmount(amount) // Call ViewModel method inside coroutine

                    // Handle successful response
                    if (response != null) {
                        Toast.makeText(
                            this@DealUmkmActivity,
                            "Request successful: ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Handle failure (if response is null)
                        Toast.makeText(this@DealUmkmActivity, "Request failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    // Handle any exceptions (e.g., network issues)
                    Toast.makeText(this@DealUmkmActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
