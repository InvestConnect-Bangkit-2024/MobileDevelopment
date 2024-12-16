package com.example.capstoneproduct.ui.account.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproduct.R
import com.example.capstoneproduct.databinding.ActivitySignupBinding
import com.example.capstoneproduct.ui.ViewModelFactory
import com.example.capstoneproduct.ui.button.MyEditText
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var nameEditText: TextInputEditText
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: MyEditText

    private lateinit var sectorSpinner: Spinner
    private lateinit var stageSpinner: Spinner
    private lateinit var businessModelSpinner: Spinner
    private lateinit var loyalCustomersSpinner: Spinner

    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nameEditText = binding.edRegisterName
        usernameEditText = binding.edRegisterUsername
        emailEditText = binding.edRegisterEmail
        passwordEditText = binding.edRegisterPassword

        if (checkForInternet(this)){
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
        }

        sectorSpinner = binding.sectorSpinner
        stageSpinner = binding.stageSpinner
        businessModelSpinner = binding.businessModelSpinner
        loyalCustomersSpinner = binding.loyalCustomersSpinner

        setupSpinners()

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupSpinners() {
        val sectors = arrayOf("Education Technology", "Health Technology", "Finance", "Agriculture")
        val sectorAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sectors)
        sectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sectorSpinner.adapter = sectorAdapter

        // Stage Spinner
        val stages = arrayOf("Seed", "Growth", "Mature")
        val stageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stages)
        stageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stageSpinner.adapter = stageAdapter

        // Business Model Spinner
        val businessModels = arrayOf("SaaS", "Marketplace", "Subscription", "Freemium")
        val businessModelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, businessModels)
        businessModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        businessModelSpinner.adapter = businessModelAdapter

        // Loyal Customers Spinner
        val loyalCustomers = arrayOf("0", "1000", "5000", "10000", "50000", "100000", "More than 100000")
        val loyalCustomersAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, loyalCustomers)
        loyalCustomersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        loyalCustomersSpinner.adapter = loyalCustomersAdapter
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

    private fun setupAction() {
        viewModel.registerResponse.observe(this) { response ->
            Log.d("SignupActivity", "Response: $response")
            showLoading(false)
            if (response != null) {
                if (response.error == false) {
                    val email = emailEditText.text.toString()
                    Toast.makeText(this, "Akun dengan email $email berhasil dibuat!", Toast.LENGTH_SHORT).show()
                } else {
                    showErrorDialog("Gagal melakukan registrasi: ${response.message}")
                }
            } else {
                Log.e("SignupActivity", "Response is null")
            }
        }

        binding.signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            AlertDialog.Builder(this).apply {
                setTitle("Yeah!")
                setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
            if (name.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                showLoading(true)
                lifecycleScope.launch {
                    viewModel.register(name, username, email, password)
                }
            } else {
                Toast.makeText(this, "Silakan isi data dengan lengkap", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("OK", null)
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.signupButton.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkForInternet(context: Context): Boolean {
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
            @Suppress("DEPRECATION") val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION") return networkInfo.isConnected

        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val usernameTextView =
            ObjectAnimator.ofFloat(binding.usernameTextView, View.ALPHA, 1f).setDuration(100)
        val usernameEditTextLayout =
            ObjectAnimator.ofFloat(binding.usernameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                usernameTextView,
                usernameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }
}