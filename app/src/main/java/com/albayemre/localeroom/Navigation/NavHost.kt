package com.ibrahimcanerdogan.notepadapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.albayemre.localeroom.ViewModel.NoteViewModel
import com.albayemre.localeroom.ui.Screen.SplashScreen
import com.albayemre.localeroom.ui.screens.NoteAddScreen
import com.albayemre.localeroom.ui.screens.NoteListScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: NoteViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.SPLASH_SCREEN.name
    ) {

        composable(AppScreen.SPLASH_SCREEN.name)
        {
            SplashScreen(navController)
        }

        composable(AppScreen.NOTE_LIST.name) {
            NoteListScreen(
                navController = navController,
                viewModel = viewModel,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }

        composable(
            route = AppScreen.NOTE_ADD.name + "?noteId={noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val noteId = it.arguments?.getString("noteId")
            NoteAddScreen(navController, viewModel, noteId)
        }


    }


}
