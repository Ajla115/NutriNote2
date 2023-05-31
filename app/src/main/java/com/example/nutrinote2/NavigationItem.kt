package com.example.nutrinote2

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object Water : NavigationItem("water", R.drawable.ic_music, "Water")
    object Recipes : NavigationItem("recipes", R.drawable.ic_book, "Recipes")
    object Profile : NavigationItem("profile", R.drawable.ic_profile, "Profile")
    }

/*sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object Music : NavigationItem("music", R.drawable.ic_music, "Music")
    object Movies : NavigationItem("movies", R.drawable.ic_movie, "Movies")
    object Books : NavigationItem("books", R.drawable.ic_book, "Books")
    object Profile : NavigationItem("profile", R.drawable.ic_profile, "Profile")
}*/