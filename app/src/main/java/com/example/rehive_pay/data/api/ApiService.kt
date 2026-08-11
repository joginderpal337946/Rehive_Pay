package com.example.rehive_pay.data.api

import com.example.rehive_pay.data.model.*
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("ping")
    suspend fun ping(): BaseResponse<String>

    @POST(Constants.LOGIN)
    suspend fun login(@Body request: LoginRequest): BaseResponse<AuthResponse>

    @POST(Constants.REGISTER)
    suspend fun register(@Body request: RegisterRequest): BaseResponse<AuthResponse>

    @POST(Constants.FORGOT_PASSWORD)
    suspend fun forgotPassword(@Body email: Map<String, String>): BaseResponse<String>

    @POST(Constants.LOGOUT)
    suspend fun logout(): BaseResponse<String>

    @GET(Constants.GET_PROFILE)
    suspend fun getProfile(): BaseResponse<UserProfileResponse>

    @POST(Constants.UPDATE_PROFILE)
    suspend fun updateProfile(@Body request: UpdateProfileRequest): BaseResponse<UserProfileResponse>

    @POST(Constants.DEACTIVATE_ACCOUNT)
    suspend fun deactivateAccount(): BaseResponse<String>

    @POST(Constants.DELETE_ACCOUNT)
    suspend fun deleteAccount(): BaseResponse<String>

    @GET(Constants.GET_CARDS)
    suspend fun getCards(): BaseResponse<List<CardResponse>>

    @POST(Constants.ADD_CARD)
    suspend fun addCard(@Body request: AddCardRequest): BaseResponse<CardResponse>

    @DELETE(Constants.DELETE_CARD)
    suspend fun deleteCard(@Path("id") id: String): BaseResponse<String>

    @GET(Constants.GET_TRANSACTIONS)
    suspend fun getTransactions(): BaseResponse<List<TransactionResponse>>

    @POST(Constants.TRANSFER_MONEY)
    suspend fun transferMoney(@Body request: TransferRequest): BaseResponse<TransactionResponse>
}
