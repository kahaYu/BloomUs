package com.yurikolesnikov.login.presentation.logInScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _state = mutableStateOf(LogInScreenState())
    val state: State<LogInScreenState> = _state

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _destination = MutableSharedFlow<Destinations>()
    val destination = _destination.asSharedFlow()

    fun onEvent(event: LogInScreenEvent) {
        when (event) {
            is LogInScreenEvent.OnEmailInputChange -> {
                _state.value = _state.value.copy(emailInputField = event.text)
            }
            is LogInScreenEvent.OnPasswordInputChange -> {
                _state.value = _state.value.copy(passwordInputField = event.text)
            }
            is LogInScreenEvent.OnLogInButtonClick -> {

            }
            is LogInScreenEvent.OnRegisterButtonClick -> registerUser()
        }
    }

    private fun registerUser() {
        val email = state.value.emailInputField
        val password = state.value.passwordInputField
        if (email.isNotEmpty() &&  password.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    navigate(Destinations.NEXT_SCREEN)
                } catch (exception: Exception) {
                    showToast("Some error")
                }
            }
        }
    }

    private suspend fun showToast(message: String) {
        _toastMessage.emit(message)
    }

    private suspend fun navigate(destination: Destinations) {
        _destination.emit(destination)
    }
}