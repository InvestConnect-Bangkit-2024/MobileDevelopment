package com.example.capstoneproduct.ui.account.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.capstoneproduct.data.repository.UserRepository
import com.example.capstoneproduct.data.response.register.ResponseRegister
import java.io.File

class SignupInvestorViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun registerInvestor (file: File, username: String, email: String, password: String, fullname: String, phone_number: String, investor_name: String, location: String, investment_focus: String, stages: String, thesis: String, total_deals: Int, total_investments: Int, deal_type: String, geographic_focus: String, criteria: String) =
        userRepository.registerInvestor(file, username, email, password, fullname, phone_number, investor_name, location, investment_focus, stages, thesis, total_deals, total_investments, deal_type, geographic_focus, criteria)

}