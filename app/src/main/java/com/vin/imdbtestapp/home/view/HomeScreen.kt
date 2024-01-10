package com.vin.imdbtestapp.home.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vin.imdbtestapp.R
import com.vin.imdbtestapp.home.viewmodel.HomeViewModel
import com.vin.imdbtestapp.repo.model.Movie

const val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, onMovieClicked: (movie: Movie) -> Unit) {
    LaunchedEffect(homeViewModel.isInit) {
        Log.d(TAG, "HomeScreen: launched effect")
        if (homeViewModel.isInit) {
            homeViewModel.isInit = false
        }
        else {
            Log.d(TAG, "HomeScreen: launched effect else")
            homeViewModel.refreshLikedMovies()
        }
    }

    val moviesState by homeViewModel.movies.collectAsState()
    val searchState by homeViewModel.search.collectAsState()

    Column {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            singleLine = true,
            value = searchState,
            onValueChange = { newValue ->
                homeViewModel.searchMovies(newValue)
            })

        LazyVerticalGrid(columns = GridCells.FixedSize(200.dp)) {
            items(items = moviesState, key = { movie -> movie.id }) { movie ->
                Card(modifier = Modifier.padding(4.dp), onClick = { onMovieClicked(movie) }) {
                    Column(
                        modifier = Modifier
                            .padding(6.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = movie.name,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(500)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Box {
                            AsyncImage(
                                model = movie.contentUrl,
                                contentDescription = null,
                            )
                            Icon(
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.BottomEnd),
                                painter = painterResource(id = R.drawable.heart_icon),
                                contentDescription = "Like",
                                tint = if (movie?.isLiked == true) Color.Red else Color.Gray
                            )
                        }
                        Text(text = movie.desc ?: "-", maxLines = 4)
                    }
                }
            }
        }
    }
}