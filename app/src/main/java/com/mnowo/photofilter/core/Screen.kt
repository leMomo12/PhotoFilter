package com.mnowo.photofilter.core

sealed class Screen(val route: String) {
    object EditScreen : Screen("editScreen")
}
