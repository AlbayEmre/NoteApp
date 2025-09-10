package com.albayemre.localeroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.albayemre.localeroom.ViewModel.NoteViewModel
import com.albayemre.localeroom.ui.theme.LocaleRoomTheme
import com.ibrahimcanerdogan.notepadapp.ui.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Tema için state
            val isDarkTheme = remember { mutableStateOf(false) }

            LocaleRoomTheme(darkTheme = isDarkTheme.value) {
                val navController = rememberNavController()
                val noteViewModel: NoteViewModel = viewModel()

                NavigationGraph(
                    navController = navController,
                    viewModel = noteViewModel,
                    isDarkTheme = isDarkTheme.value,
                    onThemeChange = { isDarkTheme.value = it }
                )
            }
        }
    }
}
