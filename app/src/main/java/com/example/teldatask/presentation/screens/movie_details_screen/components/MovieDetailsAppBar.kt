package com.example.teldatask.presentation.screens.movie_details_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.teldatask.R
import com.example.teldatask.presentation.ui.theme.LightGray
import com.example.teldatask.presentation.ui.theme.Red
import com.example.teldatask.presentation.ui.theme.TeldaTaskTheme


@Composable
fun MovieDetailsAppBar(
    modifier: Modifier = Modifier,
    isLiked: Boolean,
    onFavouriteButtonClicked: (makeFavouriteMovie: Boolean) -> Unit,
    onBackArrowPressed: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CircleButton(
            onClick = {
                onBackArrowPressed()
            },
            containerColor = MaterialTheme.colorScheme.onSurface,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                tint = MaterialTheme.colorScheme.surface,
                contentDescription = null
            )
        }

        CircleButton(
            onClick = {
                if (isLiked) {
                    onFavouriteButtonClicked(false)

                } else {
                    onFavouriteButtonClicked(true)
                }
            },
            containerColor = if (isLiked) Red.copy(alpha = 0.5f) else LightGray.copy(alpha = 0.5f)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) {
                    Red
                } else {
                    LightGray
                },
                contentDescription = if (isLiked) {
                    stringResource(id = R.string.unlike)
                } else {
                    stringResource(id = R.string.like)
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMovieDetailsAppBar() {
    TeldaTaskTheme {
        MovieDetailsAppBar(
            isLiked = false,
            onBackArrowPressed = {},
            onFavouriteButtonClicked = {}
        )
    }
}