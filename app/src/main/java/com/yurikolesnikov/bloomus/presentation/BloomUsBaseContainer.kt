package com.yurikolesnikov.bloomus.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.yurikolesnikov.designsystem.theme.BloomUsTheme
import com.yurikolesnikov.login.presentation.logInScreen.Destinations
import com.yurikolesnikov.login.presentation.logInScreen.LogInScreen
import com.yurikolesnikov.login.presentation.logInScreen.NavGraphs
import com.yurikolesnikov.login.presentation.logInScreen.destinations.LogInScreenDestination

@Composable
fun BloomUsBaseContainer(
    viewModel: BloomUsBaseViewModel = hiltViewModel()
) {

    val context = LocalContext.current

/*    fun showProgressBar(show: Boolean) {
        state.value = state.value.copy(shouldShowProgressBar = show)
    }*/

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
                    Destinations.NEXT_SCREEN -> {
                        //navigator.navigate(NextScreenDestination())
                    }
                }
            }
    }

    BloomUsTheme {
        DestinationsNavHost(navGraph = NavGraphs.root) {
            composable(LogInScreenDestination) { //this: DestinationScope<SomeScreenDestination.NavArgs>
                LogInScreen(
                    showToastMessage = viewModel::showToast,
                    navigator = destinationsNavigator
                )
            }

            //All screens that don't need the scaffoldState don't need to be specified here
        }
    }
}