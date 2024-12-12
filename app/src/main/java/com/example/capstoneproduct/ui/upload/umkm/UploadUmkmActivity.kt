package com.example.capstoneproduct.ui.upload.umkm

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.capstoneproduct.R
import com.example.capstoneproduct.databinding.ActivityUploadUmkmBinding
import com.example.capstoneproduct.ui.reduceFileImage
import com.example.capstoneproduct.ui.umkm.UmkmFragment
import com.example.capstoneproduct.ui.uriToFile
import com.example.capstoneproduct.data.repository.Result


class UploadUmkmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadUmkmBinding

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<UploadUmkmViewModel> {
        UploadUmkmViewModelFactory.getInstance(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadUmkmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadBtn.setOnClickListener { uploadUmkm() }

        setupView()
        playAnimation()

    }

    private fun uploadUmkm() {
        currentImageUri?.let { uri ->
            val img_file = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${img_file.path}")

            val companyName = binding.companyNameEditTextLayout.editText?.text.toString().trim()
            val foundingDate = binding.foundingDateEditTextLayout.editText?.text.toString().trim()
            val founderName = binding.founderNameEditTextLayout.editText?.text.toString().trim()
            val location = binding.locationEditTextLayout.editText?.text.toString().trim()
            val sector = binding.sectorEditTextLayout.editText?.text.toString().trim()
            val stage = binding.stageEditTextLayout.editText?.text.toString().trim()
            val description = binding.descriptionEditTextLayout.editText?.text.toString().trim()
            val teamMembers = binding.teamMembersEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val businessModel = binding.businessModelEditTextLayout.editText?.text.toString().trim()
            val loyalCustomers = binding.loyalCustomersEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val marketSize = binding.marketSizeEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val marketTarget = binding.marketTargetEditTextLayout.editText?.text.toString().trim()
            val amountSeeking = binding.amountSeekingEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val fundingRaised = binding.fundingRaisedEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val revenue = binding.revenueEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val profitability = binding.profitabilityEditTextLayout.editText?.text.toString().trim().toFloatOrNull() ?: 0f
            val growthRate = binding.growthRateEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val preferredInvestmentType = binding.preferredInvestmentTypeEditTextLayout.editText?.text.toString().trim()
            val intendedUseOfFunds = binding.intendedUseOfFundsEditTextLayout.editText?.text.toString().trim()
            val phoneNumber = binding.phoneNumberEditTextLayout.editText?.text.toString().trim()

            viewModel.uploadUmkm(
                img_file,
                companyName,
                foundingDate,
                founderName,
                location,
                sector,
                stage,
                description,
                teamMembers,
                businessModel,
                loyalCustomers,
                marketSize,
                marketTarget,
                amountSeeking,
                fundingRaised,
                revenue,
                profitability,
                growthRate,
                preferredInvestmentType,
                intendedUseOfFunds,
                phoneNumber
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            result.data.message?.let { showToast(it) }
                            navigateToUmkm()
                        }

                        is Result.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }
                    }
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }


    private fun navigateToUmkm() {
        val intent = Intent(this, UmkmFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage()  {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imageView.setImageURI(it)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.uploadBtn.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val companyNameTextView =
            ObjectAnimator.ofFloat(binding.companyNameTextView, View.ALPHA, 1f).setDuration(100)
        val companyNameEditTextLayout =
            ObjectAnimator.ofFloat(binding.companyNameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val foundingDateTextView =
            ObjectAnimator.ofFloat(binding.foundingDateTextView, View.ALPHA, 1f).setDuration(100)
        val foundingDateEditTextLayout =
            ObjectAnimator.ofFloat(binding.foundingDateEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val founderNameTextView =
            ObjectAnimator.ofFloat(binding.founderNameTextView, View.ALPHA, 1f).setDuration(100)
        val founderNameEditTextLayout =
            ObjectAnimator.ofFloat(binding.founderNameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val locationTextView =
            ObjectAnimator.ofFloat(binding.locationTextView, View.ALPHA, 1f).setDuration(100)
        val locationEditTextLayout =
            ObjectAnimator.ofFloat(binding.locationEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val sectorTextView =
            ObjectAnimator.ofFloat(binding.sectorTextView, View.ALPHA, 1f).setDuration(100)
        val sectorEditTextLayout =
            ObjectAnimator.ofFloat(binding.sectorEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val stageTextView =
            ObjectAnimator.ofFloat(binding.stageTextView, View.ALPHA, 1f).setDuration(100)
        val stageEditTextLayout =
            ObjectAnimator.ofFloat(binding.stageEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val descriptionTextView =
            ObjectAnimator.ofFloat(binding.descriptionTextView, View.ALPHA, 1f).setDuration(100)
        val descriptionEditTextLayout =
            ObjectAnimator.ofFloat(binding.descriptionEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val teamMembersTextView =
            ObjectAnimator.ofFloat(binding.teamMembersTextView, View.ALPHA, 1f).setDuration(100)
        val teamMembersEditTextLayout =
            ObjectAnimator.ofFloat(binding.teamMembersEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val businessModelTextView =
            ObjectAnimator.ofFloat(binding.businessModelTextView, View.ALPHA, 1f).setDuration(100)
        val businessModelEditTextLayout =
            ObjectAnimator.ofFloat(binding.businessModelEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val loyalCustomersTextView =
            ObjectAnimator.ofFloat(binding.loyalCustomersTextView, View.ALPHA, 1f).setDuration(100)
        val loyalCustomersEditTextLayout =
            ObjectAnimator.ofFloat(binding.loyalCustomersEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val marketSizeTextView =
            ObjectAnimator.ofFloat(binding.marketSizeTextView, View.ALPHA, 1f).setDuration(100)
        val marketSizeEditTextLayout =
            ObjectAnimator.ofFloat(binding.marketSizeEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val marketTargetTextView =
            ObjectAnimator.ofFloat(binding.marketTargetTextView, View.ALPHA, 1f).setDuration(100)
        val marketTargetEditTextLayout =
            ObjectAnimator.ofFloat(binding.marketTargetEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val amountSeekingTextView =
            ObjectAnimator.ofFloat(binding.amountSeekingTextView, View.ALPHA, 1f).setDuration(100)
        val amountSeekingEditTextLayout =
            ObjectAnimator.ofFloat(binding.amountSeekingEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val fundingRaisedTextView =
            ObjectAnimator.ofFloat(binding.fundingRaisedTextView, View.ALPHA, 1f).setDuration(100)
        val fundingRaisedEditTextLayout =
            ObjectAnimator.ofFloat(binding.fundingRaisedEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val revenueTextView =
            ObjectAnimator.ofFloat(binding.revenueTextView, View.ALPHA, 1f).setDuration(100)
        val revenueEditTextLayout =
            ObjectAnimator.ofFloat(binding.revenueEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val profitabilityTextView =
            ObjectAnimator.ofFloat(binding.profitabilityTextView, View.ALPHA, 1f).setDuration(100)
        val profitabilityEditTextLayout =
            ObjectAnimator.ofFloat(binding.profitabilityEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val growthRateTextView =
            ObjectAnimator.ofFloat(binding.growthRateTextView, View.ALPHA, 1f).setDuration(100)
        val growthRateEditTextLayout =
            ObjectAnimator.ofFloat(binding.growthRateEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val preferredInvestmentTypeTextView =
            ObjectAnimator.ofFloat(binding.preferredInvestmentTypeTextView, View.ALPHA, 1f).setDuration(100)
        val preferredInvestmentTypeEditTextLayout =
            ObjectAnimator.ofFloat(binding.preferredInvestmentTypeEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val intendedUseOfFundsTextView =
            ObjectAnimator.ofFloat(binding.intendedUseOfFundsTextView, View.ALPHA, 1f).setDuration(100)
        val intendedUseOfFundsEditTextLayout =
            ObjectAnimator.ofFloat(binding.intendedUseOfFundsEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val phoneNumberTextView =
            ObjectAnimator.ofFloat(binding.phoneNumberTextView, View.ALPHA, 1f).setDuration(100)
        val phoneNumberEditTextLayout =
            ObjectAnimator.ofFloat(binding.phoneNumberEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val upload =
            ObjectAnimator.ofFloat(binding.uploadBtn, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                companyNameTextView,
                companyNameEditTextLayout,
                foundingDateTextView,
                foundingDateEditTextLayout,
                founderNameTextView,
                founderNameEditTextLayout,
                locationTextView,
                locationEditTextLayout,
                sectorTextView,
                sectorEditTextLayout,
                stageTextView,
                stageEditTextLayout,
                descriptionTextView,
                descriptionEditTextLayout,
                teamMembersTextView,
                teamMembersEditTextLayout,
                businessModelTextView,
                businessModelEditTextLayout,
                loyalCustomersTextView,
                loyalCustomersEditTextLayout,
                marketSizeTextView,
                marketSizeEditTextLayout,
                marketTargetTextView,
                marketTargetEditTextLayout,
                amountSeekingTextView,
                amountSeekingEditTextLayout,
                fundingRaisedTextView,
                fundingRaisedEditTextLayout,
                revenueTextView,
                revenueEditTextLayout,
                profitabilityTextView,
                profitabilityEditTextLayout,
                growthRateTextView,
                growthRateEditTextLayout,
                preferredInvestmentTypeTextView,
                preferredInvestmentTypeEditTextLayout,
                intendedUseOfFundsTextView,
                intendedUseOfFundsEditTextLayout,
                phoneNumberTextView,
                phoneNumberEditTextLayout,
                upload
            )
            startDelay = 100
        }.start()
    }


}