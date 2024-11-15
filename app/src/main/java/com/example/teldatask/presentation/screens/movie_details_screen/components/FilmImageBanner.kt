package com.example.teldatask.presentation.screens.movie_details_screen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teldatask.R

@Composable
fun MovieImageBanner(
    modifier: Modifier = Modifier,
    movieImage: String,
    ) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(movieImage)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_placeholder),
        contentDescription = "Movie Banner",
        contentScale = ContentScale.FillBounds,
        modifier = modifier.fillMaxSize(),
    )
}