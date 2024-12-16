package com.example.capstoneproduct.data.response.profile

data class ProfileResponse(
    val message: String,
    val data: UserProfile
)

data class UserProfile(
    val user_id: String,
    val username: String,
    val email: String,
    val fullname: String
)