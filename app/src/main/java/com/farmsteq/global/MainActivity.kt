package com.farmsteq.global

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.farmsteq.global.ui.AppNavigation
import com.farmsteq.global.ui.theme.FarmsteqTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            FarmsteqTheme(theme = state.theme, dynamicColor = state.dynamicColor) {
                AppNavigation(state = state, viewModel = viewModel)
            }
        }
    }
}
