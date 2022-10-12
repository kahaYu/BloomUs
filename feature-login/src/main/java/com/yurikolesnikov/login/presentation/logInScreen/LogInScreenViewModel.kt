package com.yurikolesnikov.login.presentation.logInScreen

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurikolesnikov.network.Resource
import com.yurikolesnikov.network.authentication.FirebaseAuthenticationManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LogInScreenViewModel /*@AssistedInject constructor*/(
    /*private val auth: FirebaseAuth,*/
    /*@Assisted*/ private val savedStateHandle: SavedStateHandle,
    /*private val startGoogleChoiceActivity: (result: BeginSignInResult) -> Unit,*/
    /*@Assisted*/ private val showToastMessage: (String) -> Unit,
    /*@Assisted*/ private val showProgressBar: (Boolean) -> Unit,
    /*@Assisted*/ private val navigateTo: (DestinationFromLogInScreen) -> Unit,
) : ViewModel() {

    val state =
        savedStateHandle.getStateFlow(key = "LogInScreenState", initialValue = LogInScreenState())

    fun onEvent(event: LogInScreenEvent) {
        when (event) {
            is LogInScreenEvent.OnEmailInputChange -> {
                savedStateHandle[stateKey] =
                    state.value.copy(emailInputField = event.text)
            }
            is LogInScreenEvent.OnPasswordInputChange -> {
                savedStateHandle[stateKey] =
                    state.value.copy(passwordInputField = event.text)
            }
            is LogInScreenEvent.OnLogInButtonClick -> logInUserWithEmailAndPassword(
                state.value.emailInputField,
                state.value.passwordInputField
            )
            is LogInScreenEvent.OnRegisterButtonClick -> navigateTo(DestinationFromLogInScreen.REGISTER_SCREEN)
            is LogInScreenEvent.OnGoogleLogInButtonClick -> getOneTapSignInResult()
            is LogInScreenEvent.GoogleChoiceWindowResultIsReceived -> resetBeginSignInResult()
            is LogInScreenEvent.LogInInUserWithGoogle -> logInUserWithGoogle(event.result)
        }
    }

/*    private fun registerUser(email: String, password: String) {
        FirebaseAuthenticationManager.registerUser(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    showProgressBar(false)
                    TODO: Navigate to next screen
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    showToastMessage("Error")
                }
                is Resource.Loading -> showProgressBar(true)
            }
        }.launchIn(viewModelScope)
    }*/

    private fun logInUserWithEmailAndPassword(email: String, password: String) {
        FirebaseAuthenticationManager.logInUserWithEmailAndPassword(email, password)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        showProgressBar(false)
                        /*TODO: Navigate to next screen*/
                    }
                    is Resource.Error -> {
                        showProgressBar(false)
                        showToastMessage("Error")
                    }
                    is Resource.Loading -> showProgressBar(true)
                }
            }.launchIn(viewModelScope)
    }

    private fun logInUserWithGoogle(result: ActivityResult) {
        FirebaseAuthenticationManager.logInWithGoogle(result = result)
            .onEach { resultFromFirebase ->
                when (resultFromFirebase) {
                    is Resource.Success -> {
                        showProgressBar(false)
                        /*TODO: Navigate to next screen*/
                    }
                    is Resource.Error -> {
                        showProgressBar(false)
                        showToastMessage("Error")
                        Log.e("Network", "${resultFromFirebase.message}")
                    }
                    is Resource.Loading -> showProgressBar(true)
                }
            }.launchIn(viewModelScope)
    }

    private fun getOneTapSignInResult() {
        FirebaseAuthenticationManager.oneTapLogInWithGoogle()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        showProgressBar(false)
                        result.data?.let { beginSignInResult ->
                            savedStateHandle[stateKey] =
                                state.value.copy(beginSignInResult = beginSignInResult)
                        }
                    }
                    is Resource.Error -> {
                        showProgressBar(false)
                        showToastMessage("Error")
                        Log.e("Network", "${result.message}")
                    }
                    is Resource.Loading -> showProgressBar(true)
                }
            }.launchIn(viewModelScope)
    }

    private fun resetBeginSignInResult() {
        savedStateHandle[stateKey] =
            state.value.copy(beginSignInResult = null)
    }

/*    @AssistedFactory
    interface LogInScreenViewModelFactory {
        fun create(
            savedStateHandle: SavedStateHandle,
            showToastMessage: (String) -> Unit,
            showProgressBar: (Boolean) -> Unit
        ): LogInScreenViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: LogInScreenViewModelFactory,
            savedStateHandle: SavedStateHandle,
            showToastMessage: (String) -> Unit,
            showProgressBar: (Boolean) -> Unit
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(
                    savedStateHandle,
                    showToastMessage,
                    showProgressBar
                ) as T
            }
        }
    }*/

    companion object {
        const val stateKey = "LogInScreenState"
    }
}