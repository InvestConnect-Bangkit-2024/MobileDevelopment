package com.example.capstoneproduct.ui.account.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproduct.data.investors.*
import com.example.capstoneproduct.MainActivity
import com.example.capstoneproduct.data.pref.UserModel
import com.example.capstoneproduct.databinding.ActivityLoginInvestorBinding
import com.example.capstoneproduct.ui.ViewModelFactory
import com.example.capstoneproduct.ui.button.MyEditText
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class LoginInvestorActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginInvestorViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: MyEditText
    private lateinit var binding: ActivityLoginInvestorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginInvestorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameEditText  = binding.edLoginUsername
        passwordEditText = binding.edLoginPassword

        setupView()
        setupAction()
        playAnimation()
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
        binding.loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Show loading indicator
            showLoading(true)

            // Start the login process in a coroutine
            lifecycleScope.launch {
                try {
                    val response = viewModel.login(username, password)
                    // Handle response
                    if (response.error == false) {
                        viewModel.saveSession(
                            UserModel(
                                username,
                                response.data?.token.toString()
                            )
                        )
                        AlertDialog.Builder(this@LoginInvestorActivity).apply {
                            setTitle("Yeah!")
                            setMessage("Anda berhasil login!")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    } else {
                        // Login failed
                        showErrorDialog(response.message ?: "Login failed.")
                    }
                } catch (e: Exception) {
                    // Handle exception (e.g., network error)
                    showErrorDialog("Login failed: ${e.message}")
                }
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
        binding.loginButton.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val usernameTextView = ObjectAnimator.ofFloat(binding.usernameTextView, View.ALPHA, 1f).setDuration(100)
        val usernameEditText = ObjectAnimator.ofFloat(binding.usernameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditText = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply{
            playSequentially(
                title,
                message,
                usernameTextView,
                usernameEditText,
                passwordTextView,
                passwordEditText,
                login
            )
            startDelay = 100
        }.start()
    }
}