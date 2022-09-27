/*
package com.yurikolesnikov.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yurikolesnikov.login.presentation.destinations.LogInScreenDestination
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    LaunchedEffect(key1 = Unit) {
        delay(2000L)
        navigator.navigate(LogInScreenDestination())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(0.4f)
        ) {
            Image(
                painter = painterResource(com.yurikolesnikov.designsystem.R.drawable.logo_splash_screen),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.tertiary)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewSplashScreen() {

    val navigatorDummy = object : DestinationsNavigator {
        override fun clearBackStack(route: String): Boolean = false
        override fun navigate(
            route: String,
            onlyIfResumed: Boolean,
            builder: NavOptionsBuilder.() -> Unit
        ) {}
        override fun navigateUp(): Boolean = false
        override fun popBackStack(): Boolean = false
        override fun popBackStack(route: String, inclusive: Boolean, saveState: Boolean): Boolean = false
    }

    SplashScreen(navigatorDummy)
}*/
