package com.yurikolesnikov.bloomus

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yurikolesnikov.bloomus.presentation.BloomUsBaseContainer
import com.yurikolesnikov.designsystem.theme.BloomUsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BloomUsBaseContainer()
        }
    }
}