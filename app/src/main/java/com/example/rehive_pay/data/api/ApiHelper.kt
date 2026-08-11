package com.example.rehive_pay.data.api

import com.example.rehive_pay.data.model.*

interface ApiHelper {
    suspend fun ping(): BaseResponse<String>
    suspend fun login(request: LoginRequest): BaseResponse<AuthResponse>
    suspend fun register(request: RegisterRequest): BaseResponse<AuthResponse>
    suspend fun forgotPassword(email: Map<String, String>): BaseResponse<String>
    suspend fun logout(): BaseResponse<String>
    suspend fun getProfile(): BaseResponse<UserProfileResponse>
    suspend fun updateProfile(request: UpdateProfileRequest): BaseResponse<UserProfileResponse>
    suspend fun deactivateAccount(): BaseResponse<String>
    suspend fun deleteAccount(): BaseResponse<String>
    suspend fun getCards(): BaseResponse<List<CardResponse>>
    suspend fun addCard(request: AddCardRequest): BaseResponse<CardResponse>
    suspend fun deleteCard(id: String): BaseResponse<String>
    suspend fun getTransactions(): BaseResponse<List<TransactionResponse>>
    suspend fun transferMoney(request: TransferRequest): BaseResponse<TransactionResponse>
}
