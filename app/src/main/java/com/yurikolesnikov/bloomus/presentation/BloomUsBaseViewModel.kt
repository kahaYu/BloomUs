package com.yurikolesnikov.bloomus.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurikolesnikov.login.presentation.logInScreen.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BloomUsBaseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state =
        savedStateHandle.getStateFlow(
            key = "BaseContainerState",
            initialValue = BloomUsBaseContainerState()
        )

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _destination = MutableSharedFlow<Destinations>()
    val destination = _destination.asSharedFlow()

    fun showToast(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _toastMessage.emit(message)
        }
    }

    suspend fun navigate(destination: Destinations) {
        viewModelScope.launch(Dispatchers.IO) {
            _destination.emit(destination)
        }
    }
}