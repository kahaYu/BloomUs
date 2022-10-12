package com.yurikolesnikov.bloomus.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yurikolesnikov.designsystem.theme.BloomUsTheme
import com.yurikolesnikov.login.presentation.logInScreen.*
import com.yurikolesnikov.login.presentation.logInScreen.destinations.LogInScreenDestination
import com.yurikolesnikov.login.presentation.logInScreen.destinations.TestScreenDestination

@Composable
fun BloomUsBaseContainer(
    viewModel: BloomUsBaseViewModel = hiltViewModel(),
    //navigator: DestinationsNavigator
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = 1) {
        viewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
    }
    LaunchedEffect(key1 = 1) {
        viewModel
            .destination
            .collect { destination ->
                when (destination) {
                    DestinationFromLogInScreen.REGISTER_SCREEN -> {
                        navigator.navigate(TestScreenDestination)
                    }
                }
            }
    }

    BloomUsTheme {
        DestinationsNavHost(navGraph = NavGraphs.root) {
            composable(LogInScreenDestination) {
                LogInScreen(
                    showToastMessage = viewModel::showToastMessage,
                    showProgressBar = viewModel::showProgressBar,
                    navigateTo = viewModel::navigateTo,
                )
            }
        }
        if (state.shouldShowProgressBar) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = SolidColor(MaterialTheme.colorScheme.background),
                        alpha = 0.6f
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}