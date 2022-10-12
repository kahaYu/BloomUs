package com.yurikolesnikov.bloomus.presentation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BloomUsBaseContainerState(
    val shouldShowProgressBar: Boolean = false
) : Parcelable
