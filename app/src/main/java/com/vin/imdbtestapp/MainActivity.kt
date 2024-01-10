package com.vin.imdbtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vin.imdbtestapp.home.view.HomeScreen
import com.vin.imdbtestapp.home.viewmodel.HomeViewModel
import com.vin.imdbtestapp.movie_details.view.MovieDetailScreen
import com.vin.imdbtestapp.movie_details.viewmodel.MovieDetailViewModel
import com.vin.imdbtestapp.repo.model.Movie
import com.vin.imdbtestapp.ui.theme.IMDBTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMDBTestAppTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController, startDestination = "/home") {
                        composable("/home") {
                            val homeViewModel = hiltViewModel<HomeViewModel>()

                            HomeScreen(homeViewModel) { movie: Movie ->
                                navController.navigate("/details/${movie.id}")
                            }
                        }
                        composable("/details/{movieId}") { navBackStackEntry ->
                            val movieId = navBackStackEntry.arguments?.getString("movieId")

                            val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()

                            MovieDetailScreen(movieId = movieId?.toIntOrNull()?:-1, movieDetailViewModel)
                        }
                    }

                }
            }
        }
    }
}