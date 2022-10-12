package com.yurikolesnikov.bloomus.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurikolesnikov.login.presentation.logInScreen.DestinationFromLogInScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BloomUsBaseViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state =
        savedStateHandle.getStateFlow(
            key = "BaseContainerState",
            initialValue = BloomUsBaseContainerState()
        )

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _destination = MutableSharedFlow<DestinationFromLogInScreen>()
    val destination = _destination.asSharedFlow()

    fun showToastMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _toastMessage.emit(message)
        }
    }

    fun showProgressBar(shouldShow: Boolean) {
        savedStateHandle["BaseContainerState"] =
            state.value.copy(shouldShowProgressBar = shouldShow)
    }

    fun navigateTo(destination: DestinationFromLogInScreen) {
        viewModelScope.launch(Dispatchers.IO) {
            _destination.emit(destination)
        }
    }
}