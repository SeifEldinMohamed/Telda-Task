package com.example.teldatask.presentation.screens.movie_details_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieNameAndRating(
    modifier: Modifier = Modifier,
    movieName: String,
    rating: Float
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.83f),
                text = movieName,
                style = MaterialTheme.typography.titleMedium,
            )
            VoteAverageRatingIndicator(
                percentage = rating
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMovieNameAndRating() {
    MovieNameAndRating(movieName = "Deadpool", rating = 8.9f)
}