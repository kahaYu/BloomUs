package com.yurikolesnikov.bloomus.presentation

import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.DestinationsNavHost
import com.yurikolesnikov.designsystem.theme.BloomUsTheme
import com.yurikolesnikov.login.presentation.logInScreen.NavGraphs

@Composable
fun BloomUsBaseContainer() {

    BloomUsTheme {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}