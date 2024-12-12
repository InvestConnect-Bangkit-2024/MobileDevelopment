package com.example.capstoneproduct.ui.upload.investor

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
import com.example.capstoneproduct.data.repository.Result
import com.example.capstoneproduct.databinding.ActivityUploadInvestorBinding
import com.example.capstoneproduct.ui.investor.InvestorFragment
import com.example.capstoneproduct.ui.reduceFileImage
import com.example.capstoneproduct.ui.uriToFile

class UploadInvestorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadInvestorBinding

    private var currentImageUri: Uri? = null

    private val viewModel by viewModels<UploadInvestorViewModel> {
        UploadInvestorViewModelFactory.getInstance(this)
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
        binding = ActivityUploadInvestorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadBtn.setOnClickListener { uploadInvestor() }

        setupView()
        playAnimation()
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

    private fun uploadInvestor() {
        currentImageUri?.let { uri ->
            val img_file = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${img_file.path}")

            val investorName = binding.investorNameEditTextLayout.editText?.text.toString().trim()
            val location = binding.locationEditTextLayout.editText?.text.toString().trim()
            val investmentFocus = binding.investmentFocusEditTextLayout.editText?.text.toString().trim()
            val stages = binding.stagesEditTextLayout.editText?.text.toString().trim()
            val thesis = binding.thesisEditTextLayout.editText?.text.toString().trim()
            val phoneNumber = binding.phoneNumberEditTextLayout.editText?.text.toString().trim()

            val totalDeals = binding.totalDealsEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0
            val totalInvestments = binding.totalInvestmentsEditTextLayout.editText?.text.toString().trim().toIntOrNull() ?: 0

            val dealType = binding.dealTypeEditTextLayout.editText?.text.toString().trim()
            val geographicFocus = binding.geographicFocusEditTextLayout.editText?.text.toString().trim()
            val criteria = binding.criteriaEditTextLayout.editText?.text.toString().trim()


            viewModel.uploadInvestor(img_file, investorName, location, investmentFocus, stages, thesis, totalDeals, totalInvestments, dealType, geographicFocus, criteria, phoneNumber).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            result.data.message?.let { showToast(it) }
                            navigateToInvestor()
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

    private fun navigateToInvestor() {
        val intent = Intent(this, InvestorFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val investorNameTextView =
            ObjectAnimator.ofFloat(binding.investorNameTextView, View.ALPHA, 1f).setDuration(100)
        val investorNameEditTextLayout =
            ObjectAnimator.ofFloat(binding.investorNameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val locationTextView =
            ObjectAnimator.ofFloat(binding.locationTextView, View.ALPHA, 1f).setDuration(100)
        val locationEditTextLayout =
            ObjectAnimator.ofFloat(binding.locationEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val investmentFocusTextView =
            ObjectAnimator.ofFloat(binding.investmentFocusTextView, View.ALPHA, 1f).setDuration(100)
        val investmentFocusEditTextLayout =
            ObjectAnimator.ofFloat(binding.investmentFocusEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val stagesTextView =
            ObjectAnimator.ofFloat(binding.stagesTextView, View.ALPHA, 1f).setDuration(100)
        val stagesEditTextLayout =
            ObjectAnimator.ofFloat(binding.stagesEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val thesisTextView =
            ObjectAnimator.ofFloat(binding.thesisTextView, View.ALPHA, 1f).setDuration(100)
        val thesisEditTextLayout =
            ObjectAnimator.ofFloat(binding.thesisEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val totalDealsTextView =
            ObjectAnimator.ofFloat(binding.totalDealsTextView, View.ALPHA, 1f).setDuration(100)
        val totalDealsEditTextLayout =
            ObjectAnimator.ofFloat(binding.totalDealsEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val totalInvestmentsTextView =
            ObjectAnimator.ofFloat(binding.totalInvestmentsTextView, View.ALPHA, 1f).setDuration(100)
        val totalInvestmentsEditTextLayout =
            ObjectAnimator.ofFloat(binding.totalInvestmentsEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val dealTypeTextView =
            ObjectAnimator.ofFloat(binding.dealTypeTextView, View.ALPHA, 1f).setDuration(100)
        val dealTypeEditTextLayout =
            ObjectAnimator.ofFloat(binding.dealTypeEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val geographicFocusTextView =
            ObjectAnimator.ofFloat(binding.geographicFocusTextView, View.ALPHA, 1f).setDuration(100)
        val geographicFocusEditTextLayout =
            ObjectAnimator.ofFloat(binding.geographicFocusEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val criteriaTextView =
            ObjectAnimator.ofFloat(binding.criteriaTextView, View.ALPHA, 1f).setDuration(100)
        val criteriaEditTextLayout =
            ObjectAnimator.ofFloat(binding.criteriaEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val phoneNumberTextView =
            ObjectAnimator.ofFloat(binding.phoneNumberTextView, View.ALPHA, 1f).setDuration(100)
        val phoneNumberEditTextLayout =
            ObjectAnimator.ofFloat(binding.phoneNumberEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val upload =
            ObjectAnimator.ofFloat(binding.uploadBtn, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                investorNameTextView,
                investorNameEditTextLayout,
                locationTextView,
                locationEditTextLayout,
                investmentFocusTextView,
                investmentFocusEditTextLayout,
                stagesTextView,
                stagesEditTextLayout,
                thesisTextView,
                thesisEditTextLayout,
                totalDealsTextView,
                totalDealsEditTextLayout,
                totalInvestmentsTextView,
                totalInvestmentsEditTextLayout,
                dealTypeTextView,
                dealTypeEditTextLayout,
                geographicFocusTextView,
                geographicFocusEditTextLayout,
                criteriaTextView,
                criteriaEditTextLayout,
                phoneNumberTextView,
                phoneNumberEditTextLayout,
                upload
            )
            startDelay = 100
        }.start()
    }
}