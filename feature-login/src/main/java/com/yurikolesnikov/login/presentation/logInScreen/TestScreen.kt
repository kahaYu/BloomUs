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
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.sp
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

@Destination
@Composable
fun TestScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(modifier = Modifier.padding(100.dp), textAlign = TextAlign.Center, fontSize = 20.sp, text = "You are logged in")
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = { /*TODO*/ }) {
            Text(textAlign = TextAlign.Center, fontSize = 16.sp, text = "Log out")
        }
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = { /*TODO*/ }) {
            Text(textAlign = TextAlign.Center, fontSize = 16.sp, text = "Delete user")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TestScreenPreview() {
    BloomUsTheme {
        TestScreen()
    }
}



