package com.example.capstoneproduct.ui.account.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.capstoneproduct.R
import com.example.capstoneproduct.databinding.ActivityPilihSignupBinding

class PilihSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.btnInvestorSignup.setOnClickListener{
            startActivity(Intent(this, SignupInvestorActivity::class.java))
        }

//        binding.btnUmkm.setOnClickListener{
//            startActivity(Intent(this, SignupUmkmActivity::class.java))
//        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val investor = ObjectAnimator.ofFloat(binding.btnInvestorSignup, View.ALPHA, 1f).setDuration(100)
        //val umkm = ObjectAnimator.ofFloat(binding.btnUmkm, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)

        val together = AnimatorSet().apply {
            playTogether(investor)
        }

        AnimatorSet().apply {
            playSequentially(title, together)
            start()
        }
    }
}