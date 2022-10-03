package com.yurikolesnikov.login.presentation.logInScreen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogInScreenState(
    val emailInputField: String = "",
    val passwordInputField: String = ""
) : Parcelable