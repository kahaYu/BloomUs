package com.yurikolesnikov.login.presentation.logInScreen

import androidx.activity.result.ActivityResult

sealed class LogInScreenEvent {
    data class OnEmailInputChange(val text: String) : LogInScreenEvent()
    data class OnPasswordInputChange(val text: String) : LogInScreenEvent()
    object OnLogInButtonClick : LogInScreenEvent()
    object OnRegisterButtonClick : LogInScreenEvent()
    object OnGoogleLogInButtonClick : LogInScreenEvent()
    data class LogInInUserWithGoogle(val result: ActivityResult) : LogInScreenEvent()
    object GoogleChoiceWindowResultIsReceived : LogInScreenEvent()
/*    object OnFacebookSignInButtonClick : LogInScreenEvent()
    object OnTwitterSignInButtonClick : LogInScreenEvent()*/
}