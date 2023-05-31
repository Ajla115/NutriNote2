package com.example.nutrinote2

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutrinote2.databasedata.DBHandler
import com.example.nutrinote2.screens.RegisterScreen
import com.example.nutrinote2.screens.HomeScreen
import com.example.nutrinote2.screens.ProfileScreen
import com.example.nutrinote2.screens.RecipesScreen
import com.example.nutrinote2.screens.SplashScreen
import com.example.nutrinote2.screens.WaterScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

enum class NutriNoteScreen(@StringRes val title: Int){
    Start(title = R.string.start),
    Register(title = R.string.register),
    Login(title = R.string.login),
    Home(title = R.string.title_home),
    Profile(title = R.string.profile)
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun NutriNoteApp() {


    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val context = LocalContext.current
    val dbHandler = DBHandler(context)


    val coroutineScope = rememberCoroutineScope()

        Scaffold() {

            NavHost(
            navController = navController,
            startDestination = NutriNoteScreen.Start.name,
            modifier = Modifier.padding()
        ) {
            composable(route = NutriNoteScreen.Start.name) {
                SplashScreen(onLoginButtonClicked = { navController.navigate(NutriNoteScreen.Login.name)},
                    onRegisterButtonClicked = { navController.navigate(NutriNoteScreen.Register.name)
                } )
            }
            composable(route = NutriNoteScreen.Register.name){
                RegisterScreen( onRegisterButtonClicked = {navController.navigate(NutriNoteScreen.Home.name)})
            }
            composable(route = NutriNoteScreen.Login.name){
                com.example.nutrinote2.screens.LoginScreen(
                    onLoginButtonClicked = { navController.navigate(NutriNoteScreen.Home.name) },
                )
            }
            composable(route = NutriNoteScreen.Home.name){
                MainScreen()
            }


            }

        }
    }


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { TopBar(navController, currentRoute) },
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        },
        backgroundColor = colorResource(R.color.colorPrimaryDark)
    )
}


@Composable
fun TopBar(navController: NavController, currentRoute: String?) {
    val title = when (currentRoute) {
        NavigationItem.Home.route -> stringResource(R.string.title_home)
        NavigationItem.Water.route -> stringResource(R.string.title_water)
        NavigationItem.Recipes.route -> stringResource(R.string.title_recipes)
        NavigationItem.Profile.route -> stringResource(R.string.title_profile)
        else -> stringResource(R.string.app_name)
    }

    TopAppBar(
        title = { Text(text = title, fontSize = 18.sp) },
        backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.White
    )
}





@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Water.route) {
            WaterScreen()
        }
        composable(NavigationItem.Recipes.route) {
            RecipesScreen()
        }
        composable(NavigationItem.Profile.route) {
                ProfileScreen()
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Water,
        NavigationItem.Recipes,
        NavigationItem.Profile
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.colorPrimary),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    //BottomNavigationBar()
}



