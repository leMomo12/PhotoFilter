package com.mnowo.photofilter

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mnowo.photofilter.core.Screen

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.EditScreen.route) {
        composable(Screen.EditScreen.route) {
            EditScreen()
        }
    }
}