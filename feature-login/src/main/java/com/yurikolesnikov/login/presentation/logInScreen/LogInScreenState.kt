package com.yurikolesnikov.login.presentation.logInScreen

import android.os.Parcelable
import com.google.android.gms.auth.api.identity.BeginSignInResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogInScreenState(
    val emailInputField: String = "",
    val passwordInputField: String = "",
    val beginSignInResult: BeginSignInResult? = null,
) : Parcelable