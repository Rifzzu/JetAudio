package com.example.jetaudio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home

import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetaudio.ui.navigation.NavigationItem
import com.example.jetaudio.ui.navigation.Screen
import com.example.jetaudio.ui.screen.about.AboutScreen
import com.example.jetaudio.ui.screen.detail.DetailScreen
import com.example.jetaudio.ui.screen.favorite.FavoriteScreen
import com.example.jetaudio.ui.screen.home.HomeScreen
import com.example.jetaudio.ui.theme.JetAudioTheme

@Composable
fun JetAudioApp (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != Screen.Detail.route) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_android_black_24dp),
                        contentDescription = null,
                        modifier = Modifier.height(40.dp).size(90.dp)
                    )
                }
            }

        },
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { audioId ->
                        navController.navigate(Screen.Detail.createRoute(audioId)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { audioId ->
                        navController.navigate(Screen.Detail.createRoute(audioId)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Screen.About.route) {
                AboutScreen()
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("audioId") { type = NavType.LongType })
            ) {
                val id = it.arguments?.getLong("audioId") ?: -1L

                DetailScreen(
                    audioId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Rounded.Home,
                screen = Screen.Home,
                contentDescription = stringResource(id = R.string.home_page)
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_favorite),
                icon = Icons.Rounded.Favorite,
                screen = Screen.Favorite,
                contentDescription = stringResource(id = R.string.favorite_page)
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_about),
                icon = Icons.Rounded.Face,
                screen = Screen.About,
                contentDescription = stringResource(id = R.string.about_page)
            )
        )

        navigationItem.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription
                    )
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppPreview() {
    JetAudioTheme() {
        BottomBar(rememberNavController())
    }
}