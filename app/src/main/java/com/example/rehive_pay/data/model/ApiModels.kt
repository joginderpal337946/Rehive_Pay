package com.example.rehive_pay.data.model

import com.google.gson.annotations.SerializedName

// Auth
data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class RegisterRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String
)

data class AuthResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: UserProfileResponse
)

// Profile
data class UpdateProfileRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class UserProfileResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("balance") val balance: Double
)

// Cards
data class AddCardRequest(
    @SerializedName("cardNumber") val cardNumber: String,
    @SerializedName("cardholderName") val cardholderName: String,
    @SerializedName("expiry") val expiry: String,
    @SerializedName("cvv") val cvv: String
)

data class CardResponse(
    @SerializedName("id") val id: String,
    @SerializedName("cardNumber") val cardNumber: String,
    @SerializedName("cardholderName") val cardholderName: String,
    @SerializedName("expiry") val expiry: String
)

// Transactions
data class TransferRequest(
    @SerializedName("amount") val amount: Double,
    @SerializedName("recipientEmail") val recipientEmail: String,
    @SerializedName("note") val note: String? = null
)

data class TransactionResponse(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String, // "send", "receive", "deposit", "withdrawal"
    @SerializedName("amount") val amount: Double,
    @SerializedName("date") val date: String,
    @SerializedName("status") val status: String,
    @SerializedName("counterparty") val counterparty: String? = null
)
