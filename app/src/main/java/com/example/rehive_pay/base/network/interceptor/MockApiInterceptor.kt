package com.example.rehive_pay.base.network.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        // Simulate network delay
        Thread.sleep(1000)

        val responseString = when {
            path.contains("auth/login") || path.contains("auth/register") -> {
                """
                {
                    "status": "success",
                    "message": "Authentication successful",
                    "data": {
                        "token": "dummy_jwt_token_12345",
                        "user": {
                            "id": "usr_123",
                            "name": "Jane Doe",
                            "email": "jane.doe@example.com",
                            "balance": 1250.50
                        }
                    }
                }
                """
            }
            path.contains("auth/forgot-password") -> {
                """
                {
                    "status": "success",
                    "message": "Password reset instructions sent",
                    "data": "Success"
                }
                """
            }
            path.contains("auth/logout") || path.contains("user/deactivate") -> {
                """
                {
                    "status": "success",
                    "message": "Operation successful",
                    "data": "Success"
                }
                """
            }
            path.contains("user/profile/update") || path.contains("user/profile") -> {
                """
                {
                    "status": "success",
                    "message": "Profile retrieved/updated",
                    "data": {
                        "id": "usr_123",
                        "name": "Jane Doe",
                        "email": "jane.doe@example.com",
                        "balance": 1250.50
                    }
                }
                """
            }
            path.contains("cards/add") -> {
                """
                {
                    "status": "success",
                    "message": "Card added successfully",
                    "data": {
                        "id": "card_999",
                        "cardNumber": "************3456",
                        "cardholderName": "Jane Doe",
                        "expiry": "12/25"
                    }
                }
                """
            }
            path.contains("cards/delete") -> {
                """
                {
                    "status": "success",
                    "message": "Card deleted successfully",
                    "data": "Success"
                }
                """
            }
            path.contains("cards") -> {
                """
                {
                    "status": "success",
                    "message": "Cards retrieved",
                    "data": [
                        {
                            "id": "card_1",
                            "cardNumber": "************1234",
                            "cardholderName": "Jane Doe",
                            "expiry": "11/24"
                        },
                        {
                            "id": "card_2",
                            "cardNumber": "************9876",
                            "cardholderName": "Jane Doe",
                            "expiry": "05/26"
                        }
                    ]
                }
                """
            }
            path.contains("transactions") -> {
                """
                {
                    "status": "success",
                    "message": "Transactions retrieved",
                    "data": [
                        {
                            "id": "tx_1",
                            "type": "receive",
                            "amount": 500.0,
                            "date": "2023-10-25T10:30:00Z",
                            "status": "completed",
                            "counterparty": "John Smith"
                        },
                        {
                            "id": "tx_2",
                            "type": "send",
                            "amount": 120.5,
                            "date": "2023-10-24T14:15:00Z",
                            "status": "completed",
                            "counterparty": "Amazon"
                        }
                    ]
                }
                """
            }
            path.contains("transfer") -> {
                """
                {
                    "status": "success",
                    "message": "Transfer successful",
                    "data": {
                        "id": "tx_new",
                        "type": "send",
                        "amount": 0.0,
                        "date": "2023-10-26T12:00:00Z",
                        "status": "completed",
                        "counterparty": "Recipient"
                    }
                }
                """
            }
            path.contains("ping") -> {
                """
                {
                    "status": "success",
                    "message": "pong",
                    "data": "Server is alive"
                }
                """
            }
            else -> {
                """
                {
                    "status": "error",
                    "message": "Endpoint not found: $path",
                    "data": null
                }
                """
            }
        }

        return Response.Builder()
            .code(200)
            .message(responseString)
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .body(responseString.trimIndent().toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json")
            .build()
    }
}
