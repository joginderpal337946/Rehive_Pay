package com.example.rehive_pay.data.api

import com.example.rehive_pay.data.model.*

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun ping(): BaseResponse<String> = apiService.ping()
    override suspend fun login(request: LoginRequest): BaseResponse<AuthResponse> = apiService.login(request)
    override suspend fun register(request: RegisterRequest): BaseResponse<AuthResponse> = apiService.register(request)
    override suspend fun forgotPassword(email: Map<String, String>): BaseResponse<String> = apiService.forgotPassword(email)
    override suspend fun logout(): BaseResponse<String> = apiService.logout()
    override suspend fun getProfile(): BaseResponse<UserProfileResponse> = apiService.getProfile()
    override suspend fun updateProfile(request: UpdateProfileRequest): BaseResponse<UserProfileResponse> = apiService.updateProfile(request)
    override suspend fun deactivateAccount(): BaseResponse<String> = apiService.deactivateAccount()
    override suspend fun deleteAccount(): BaseResponse<String> = apiService.deleteAccount()
    override suspend fun getCards(): BaseResponse<List<CardResponse>> = apiService.getCards()
    override suspend fun addCard(request: AddCardRequest): BaseResponse<CardResponse> = apiService.addCard(request)
    override suspend fun deleteCard(id: String): BaseResponse<String> = apiService.deleteCard(id)
    override suspend fun getTransactions(): BaseResponse<List<TransactionResponse>> = apiService.getTransactions()
    override suspend fun transferMoney(request: TransferRequest): BaseResponse<TransactionResponse> = apiService.transferMoney(request)
}
