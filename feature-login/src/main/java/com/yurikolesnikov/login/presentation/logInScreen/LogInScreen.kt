@file:Suppress("UNCHECKED_CAST")

package com.yurikolesnikov.login.presentation.logInScreen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yurikolesnikov.designsystem.theme.BloomUsTheme

@RootNavGraph(start = true)
@Destination
@Composable
fun LogInScreen(
    showToastMessage: (String) -> Unit,
    showProgressBar: (Boolean) -> Unit,
    navigateTo: (DestinationFromLogInScreen) -> Unit
) {
    val viewModel: LogInScreenViewModel = logInScreenViewModel(showToastMessage, showProgressBar, navigateTo)
    val state by viewModel.state.collectAsState()

    listenOneTapSignIn(state = state, onEvent = viewModel::onEvent)

    LogInScreenContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogInScreenContent(
    state: LogInScreenState,
    onEvent: (LogInScreenEvent) -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    val infiniteTransition = rememberInfiniteTransition()
    val logInButtonWidth by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 0.73f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            }
    ) {
        val (
            emailInputField,
            passwordInputField,
            passwordRegisterSpacer,
            logInButton,
            registerText,
            orText,
            divider,
            signInMethodsPanel
        ) = createRefs()
        val bottomGuideline = createGuidelineFromTop(0.8f)
        OutlinedTextField(
            value = state.emailInputField,
            onValueChange = { text ->
                onEvent(LogInScreenEvent.OnEmailInputChange(text))
            },
            maxLines = 1,
            modifier = Modifier
                .constrainAs(emailInputField) {
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.7f)
                .aspectRatio(5f),
            shape = ButtonDefaults.shape,
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "E-mail",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        OutlinedTextField(
            value = state.passwordInputField,
            onValueChange = { text ->
                onEvent(LogInScreenEvent.OnPasswordInputChange(text))
            },
            visualTransformation = PasswordVisualTransformation(),
            maxLines = 1,
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(passwordInputField) {
                    top.linkTo(emailInputField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(0.7f)
                .aspectRatio(5f),
            shape = ButtonDefaults.shape,
            textStyle = MaterialTheme.typography.bodyMedium,
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(3.4f)
            .constrainAs(passwordRegisterSpacer) {
                top.linkTo(passwordInputField.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Button(
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(logInButton) {
                    top.linkTo(passwordInputField.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(logInButtonWidth)
                .aspectRatio(5f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            onClick = {
                onEvent(LogInScreenEvent.OnLogInButtonClick)
            }
        ) {
            Text(
                text = "LogIn",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = "Register",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable {
                    onEvent(LogInScreenEvent.OnRegisterButtonClick)
                }
                .constrainAs(registerText) {
                    top.linkTo(passwordRegisterSpacer.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = MaterialTheme.colorScheme.surfaceVariant,
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = "OR",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .constrainAs(orText) {
                    top.linkTo(bottomGuideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = MaterialTheme.colorScheme.onSurface
        )
        Divider(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.7f)
                .constrainAs(divider) {
                    top.linkTo(orText.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            color = MaterialTheme.colorScheme.tertiary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .constrainAs(signInMethodsPanel) {
                    top.linkTo(divider.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(com.yurikolesnikov.designsystem.R.drawable.logo_google_round),
                contentDescription = null,
                modifier = Modifier
                    .width(40.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(50)) // Makes ripple round
                    .clickable {
                        onEvent(LogInScreenEvent.OnGoogleLogInButtonClick)
                    }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(com.yurikolesnikov.designsystem.R.drawable.logo_facebook_round),
                contentDescription = null,
                modifier = Modifier
                    .width(40.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        /*TODO*/
                    }
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(com.yurikolesnikov.designsystem.R.drawable.logo_twitter_round),
                contentDescription = null,
                modifier = Modifier
                    .width(40.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        /*TODO*/
                    }
            )
        }
    }
}

@Composable
private fun listenOneTapSignIn(state: LogInScreenState, onEvent: (LogInScreenEvent) -> Unit) {
    if (state.beginSignInResult != null)
        startGoogleChoiceActivity(signInResult = state.beginSignInResult, onEvent = onEvent)
}

@Composable
private fun startGoogleChoiceActivity(signInResult: BeginSignInResult, onEvent: (LogInScreenEvent) -> Unit) {
    val googleChoiceActivityLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        onEvent(LogInScreenEvent.GoogleChoiceWindowResultIsReceived)
        if (result.resultCode == Activity.RESULT_OK) {
            onEvent(LogInScreenEvent.LogInInUserWithGoogle(result))
        }
    }
    LaunchedEffect(key1 = signInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        googleChoiceActivityLauncher.launch(intent)
    }
}

@Composable
private fun logInScreenViewModel(
    showToastMessage: (String) -> Unit,
    showProgressBar: (Boolean) -> Unit,
    navigateTo: (DestinationFromLogInScreen) -> Unit
): LogInScreenViewModel {

    val savedStateHandle = SavedStateHandle()

    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LogInScreenViewModel(
                savedStateHandle,
                showToastMessage,
                showProgressBar,
                navigateTo
            ) as T
        }
    }
    return ViewModelProvider(
        LocalViewModelStoreOwner.current!!,
        factory
    ).get(LogInScreenViewModel::class.java)
}

@Preview(showBackground = true)
@Composable
private fun LogInScreenContentPreview() {
    BloomUsTheme {
        LogInScreenContent(
            LogInScreenState(),
            { LogInScreenEvent.OnLogInButtonClick }
        )
    }
}



