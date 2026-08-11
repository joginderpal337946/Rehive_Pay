package com.example.rehive_pay.feature.auth

import androidx.lifecycle.viewModelScope
import com.example.rehive_pay.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    fun onEmailChange(newValue: String) {
        _email.value = newValue
        _emailError.value = null
        _loginError.value = null
    }

    fun validateEmailFormat() {
        val currentEmail = _email.value
        if (currentEmail.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
            _emailError.value = "Please enter a valid email"
        }
    }

    fun onPasswordChange(newValue: String) {
        _password.value = newValue
        _loginError.value = null
    }

    fun onLoginClick() {
        val currentEmail = _email.value
        val currentPassword = _password.value

        var hasError = false

        if (currentEmail.isBlank()) {
            _emailError.value = "Email is required"
            hasError = true
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(currentEmail).matches()) {
            _emailError.value = "Please enter a valid email"
            hasError = true
        }

        if (!hasError) {
            launchNetwork {
                kotlinx.coroutines.delay(1000)
                if (currentEmail == "testAndroid@yopmail.com") {
                    _loginError.value = "Unable to log in with provided credentials."
                } else {
                    _loginSuccess.value = true
                }
            }
        }
    }
}
