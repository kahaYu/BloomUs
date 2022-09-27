package com.yurikolesnikov.login.presentation.logInScreen

sealed class LogInScreenEvent {
    data class OnEmailInputChange(val text: String) : LogInScreenEvent()
    data class OnPasswordInputChange(val text: String) : LogInScreenEvent()
    object OnLogInButtonClick : LogInScreenEvent()
    object OnRegisterButtonClick : LogInScreenEvent()
}