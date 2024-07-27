package com.example.moviesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.common.theme.MoviesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeNavGraph(
                        navController = rememberNavController(),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeNavGraph(navController: NavHostController, modifier: Modifier) {

    NavHost(
        navController = navController, startDestination = AppDestination.List.route,
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        composable(route = AppDestination.List.route) {
            MoviesScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) { movieId ->
                navController.navigate(
                    AppDestination.Detail.createRoute(movieId)
                )
            }
        }
        composable(
            route = AppDestination.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) { entry ->
            val movieId = entry.arguments?.getString("movieId")

            MoviesDetailScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                movieId,
            )
        }

    }
}

sealed class AppDestination(val route: String) {
    data object List : AppDestination("list")
    data object Detail : AppDestination("detail/{MOVIE_ID}") {
        fun createRoute(movieId: String) = "detail/$movieId"
    }
}
