package com.yurikolesnikov.login.presentation.logInScreen

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInScreenViewModel @AssistedInject constructor(
    private val auth: FirebaseAuth,
    @Assisted private val savedStateHandle: SavedStateHandle,
    @Assisted private val showToastMessage: (String) -> Unit
) : ViewModel() {

    val state =
        savedStateHandle.getStateFlow(key = "LogInScreenState", initialValue = LogInScreenState())

    //var showToastMessage: (String) -> Unit = {}

    fun onEvent(event: LogInScreenEvent) {
        when (event) {
            is LogInScreenEvent.OnEmailInputChange -> {
                savedStateHandle["LogInScreenState"] =
                    state.value.copy(emailInputField = event.text)
            }
            is LogInScreenEvent.OnPasswordInputChange -> {
                savedStateHandle["LogInScreenState"] =
                    state.value.copy(passwordInputField = event.text)
            }
            is LogInScreenEvent.OnLogInButtonClick -> {
                showToastMessage.invoke("Message")
            }
            is LogInScreenEvent.OnRegisterButtonClick -> {}//registerUser()
        }
    }

    /*private fun registerUser() {
        val email = state.value.emailInputField
        val password = state.value.passwordInputField
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    navigate(Destinations.NEXT_SCREEN)
                } catch (exception: Exception) {
                    showToast("Some error")
                }
            }
        }
    }*/

    @AssistedFactory
    interface LogInScreenViewModelFactory {
        fun create(savedStateHandle: SavedStateHandle, showToastMessage: (String) -> Unit): LogInScreenViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: LogInScreenViewModelFactory,
            savedStateHandle: SavedStateHandle,
            showToastMessage: (String) -> Unit
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(savedStateHandle, showToastMessage) as T
            }
        }
    }
}