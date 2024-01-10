package com.vin.imdbtestapp.movie_details.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vin.imdbtestapp.R
import com.vin.imdbtestapp.movie_details.viewmodel.MovieDetailViewModel

@Composable
fun MovieDetailScreen(movieId: Int, movieDetailViewModel: MovieDetailViewModel) {
    LaunchedEffect(Unit) {
        movieDetailViewModel.retrieveCachedMovie(movieId)
    }

    val movie by movieDetailViewModel.movie.collectAsState()

    Column(modifier = Modifier.padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = movie?.name ?: "-",
            fontWeight = FontWeight(600)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box {
            AsyncImage(
                model = movie?.contentUrl,
                contentDescription = null,
            )
            Surface(modifier = Modifier.size(20.dp).align(Alignment.BottomEnd), onClick = { movieDetailViewModel.likeMovie(movie?.isLiked?.not() ?: true) }) {
                Icon(
                    painter = painterResource(id = R.drawable.heart_icon),
                    contentDescription = "Like",
                    tint = if (movie?.isLiked == true) Color.Red else Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(modifier = Modifier.fillMaxWidth(), text = movie?.desc ?: "-")
    }
}