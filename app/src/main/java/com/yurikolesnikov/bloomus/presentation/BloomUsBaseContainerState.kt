package com.yurikolesnikov.bloomus.presentation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.parcelize.Parcelize

@Composable
fun rememberBloomUsBaseContainerState(): MutableState<BloomUsBaseContainerState> {
    return remember { mutableStateOf(BloomUsBaseContainerState()) }
}
@Parcelize
data class BloomUsBaseContainerState(
    //val shouldShowToast: Boolean = false,
    val toastMessage: String = "",
    val shouldShowProgressBar: Boolean = false
) : Parcelable
