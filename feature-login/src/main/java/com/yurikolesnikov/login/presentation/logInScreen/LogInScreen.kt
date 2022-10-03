package com.yurikolesnikov.login.presentation.logInScreen

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yurikolesnikov.designsystem.theme.BloomUsTheme
import com.yurikolesnikov.login.di.ViewModelFactoryProvider
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@RootNavGraph(start = true)
@Destination
@Composable
fun LogInScreen(
    navigator: DestinationsNavigator,
    //viewModel: LogInScreenViewModel = hiltViewModel(),
    showToastMessage: (String) -> Unit
) {

    val viewModel: LogInScreenViewModel = logInScreenViewModel(showToastMessage)
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

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
                    /*TODO*/
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

                    }
            )
        }
    }
}

@Composable
fun logInScreenViewModel(showToastMessage: (String) -> Unit): LogInScreenViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).logInScreenViewModelFactory()

    val savedStateHandle = SavedStateHandle()

    return viewModel(factory = LogInScreenViewModel.providesFactory(factory, savedStateHandle, showToastMessage))
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



