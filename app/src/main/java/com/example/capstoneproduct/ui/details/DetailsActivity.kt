package com.example.capstoneproduct.ui.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.capstoneproduct.data.investors.DataItem
import com.example.capstoneproduct.data.response.upload.investors.Data
import com.example.capstoneproduct.databinding.ActivityDetailsBinding
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {

    private lateinit var imgPhoto: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvSector: TextView
    private lateinit var tvStages: TextView
    private lateinit var tvThesis: TextView
    private lateinit var tvDeal: TextView
    private lateinit var tvInvestments: TextView
    private lateinit var tvDealType: TextView
    private lateinit var tvGeographic: TextView
    private lateinit var tvCriteria: TextView
    private lateinit var tvPhone: TextView

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        imgPhoto = binding.imgItemPhoto
        tvName = binding.tvItemName
        tvLocation = binding.tvLocation
        tvSector = binding.tvInvestmentFocus
        tvStages = binding.tvStages
        tvThesis = binding.tvThesis
        tvDeal = binding.tvTotalDeals
        tvInvestments = binding.tvTotalInvestments
        tvDealType = binding.tvDealType
        tvGeographic = binding.tvGeographicFocus
        tvCriteria = binding.tvCriteria
        tvPhone = binding.tvPhoneNumber

        lifecycleScope.launch {
            showLoading(true)
            try {
                getInvestorDetails(data = DataItem())
            } finally {
                showLoading(false)
            }
        }
    }

    private fun getInvestorDetails(data: DataItem) {

        // Update TextViews with data from the Data object
        binding.tvItemName.text = data.investorName
        binding.tvLocation.text = data.location
        binding.tvInvestmentFocus.text = data.investmentFocus
        binding.tvStages.text = data.stages
        binding.tvThesis.text = data.thesis
        binding.tvTotalDeals.text = data.totalDeals
        binding.tvTotalInvestments.text = data.totalInvestments
        binding.tvDealType.text = data.dealType
        binding.tvGeographicFocus.text = data.geographicFocus
        binding.tvCriteria.text = data.criteria
        binding.tvPhoneNumber.text = data.phoneNumber

        // Load the image using Glide
        Glide.with(this)
            .load(data.imgUrl)
            .into(binding.imgItemPhoto)

        val name = intent.getStringExtra("name")
        val location = intent.getStringExtra("location")
        val sector = intent.getStringExtra("sector")
        val stages = intent.getStringExtra("stages")
        val thesis = intent.getStringExtra("thesis")
        val totalDeal = intent.getIntExtra("totalDeal", -1)
        val totalInvestments = intent.getIntExtra("totalInvestments", -1)
        val dealType = intent.getStringExtra("dealType")
        val geographic = intent.getStringExtra("geographic")
        val criteria = intent.getStringExtra("criteria")
        val img = intent.getStringExtra("img")
        val phone = intent.getStringExtra("phone")

        tvName.text = name
        tvLocation.text = location
        tvSector.text = sector
        tvStages.text = stages
        tvThesis.text = thesis
        tvDeal.text = totalDeal.toString()
        tvInvestments.text = totalInvestments.toString()
        tvDealType.text = dealType
        tvGeographic.text = geographic
        tvCriteria.text = criteria
        tvPhone.text = phone

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