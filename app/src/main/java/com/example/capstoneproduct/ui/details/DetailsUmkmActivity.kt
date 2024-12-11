package com.example.capstoneproduct.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.capstoneproduct.data.umkm.Data
import com.example.capstoneproduct.databinding.ActivityDetailsUmkmBinding
import com.example.capstoneproduct.ui.umkm.DealUmkmActivity
import kotlinx.coroutines.launch

class DetailsUmkmActivity : AppCompatActivity() {

    private lateinit var imgPhoto: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvSector: TextView
    private lateinit var tvStages: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvFounder: TextView
    private lateinit var tvSeeking: TextView
    private lateinit var tvInvestments: TextView
    private lateinit var tvUseFunds: TextView

    private var _binding: ActivityDetailsUmkmBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailsUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnDeal.setOnClickListener {
            val intent = Intent(this, DealUmkmActivity::class.java)
            startActivity(intent)
        }

        imgPhoto = binding.imgItemPhoto
        tvName = binding.tvItemName
        tvLocation = binding.tvLocation
        tvSector = binding.tvSector
        tvStages = binding.tvStages
        tvDate   = binding.tvDate
        tvDescription = binding.tvDescription
        tvInvestments = binding.tvInvestType
        tvFounder = binding.tvFounder
        tvSeeking = binding.tvSeeking
        tvUseFunds = binding.tvUseFunds

        lifecycleScope.launch {
            showLoading(true)
            try {
                getInvestorDetails(data = Data())
            } finally {
                showLoading(false)
            }
        }
    }

    private fun getInvestorDetails(data: Data) {
        binding.tvItemName.text = data.companyName
        binding.tvLocation.text = data.location
        binding.tvSector.text = data.sector
        binding.tvStages.text = data.stage
        binding.tvDate.text = data.foundingDate
        binding.tvDescription.text = data.description
        binding.tvInvestType.text = data.preferredInvestmentType
        binding.tvFounder.text = data.founderName
        binding.tvSeeking.text = data.amountSeeking
        binding.tvUseFunds.text = data.intendedUseOfFunds
        Glide.with(this)
            .load(data.imgUrl)
            .into(binding.imgItemPhoto)

        val name = intent.getStringExtra("companyName")
        val location = intent.getStringExtra("location")
        val sector = intent.getStringExtra("sector")
        val stage = intent.getStringExtra("stage")
        val foundingDate = intent.getStringExtra("foundingDate")
        val description = intent.getStringExtra("description")
        val preferredInvestmentType = intent.getStringExtra("preferredInvestmentType")
        val founderName = intent.getStringExtra("founderName")
        val amountSeeking = intent.getIntExtra("amountSeeking", -1)
        val intendedUseOfFunds = intent.getStringExtra("intendedUseOfFunds")
        val img = intent.getStringExtra("img")

        tvName.text = name
        tvLocation.text = location
        tvSector.text = sector
        tvStages.text = stage
        tvDate.text = foundingDate
        tvDescription.text = description
        tvInvestments.text = preferredInvestmentType
        tvFounder.text = founderName
        tvSeeking.text = amountSeeking.toString()
        tvUseFunds.text = intendedUseOfFunds
        Glide.with(this)
            .load(img)
            .into(imgPhoto)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}