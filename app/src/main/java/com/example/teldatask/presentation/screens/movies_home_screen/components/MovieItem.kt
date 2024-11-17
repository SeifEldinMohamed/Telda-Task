package com.example.teldatask.presentation.screens.movies_home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel
import com.example.teldatask.presentation.screens.movies_home_screen.preview_data.fakeMovieUiModel
import com.example.teldatask.R
import com.example.teldatask.presentation.ui.theme.Red

@Composable
fun MovieItem(
    movieUiModel: MovieUiModel,
    onMovieClick: (id: Int) -> Unit,
) {
    val context = LocalContext.current
    val movieImagePosterFadeDuration = 1000
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onMovieClick(movieUiModel.id) }
            .testTag(stringResource(R.string.test_tag_movie_item, movieUiModel.id)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            Modifier.padding(16.dp)
        ) {
            Image(
                painter =
                rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(data = movieUiModel.posterPath)
                        .crossfade(movieImagePosterFadeDuration)
                        .build(),
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .width(110.dp)
                    .height(150.dp),
            )

            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = movieUiModel.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(7f)
                            .testTag("MovieTitle_${movieUiModel.id}")
                    )
                    if (movieUiModel.isFavourite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(R.string.favorite_content_description),
                            tint = Red,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1f)
                                .padding(start = 4.dp)
                                .testTag(
                                    stringResource(
                                        R.string.test_tag_favourite_icon,
                                        movieUiModel.id
                                    )
                                )
                        )
                    }
                }

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = movieUiModel.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.rating, movieUiModel.voteAverage),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.release_date_s, movieUiModel.releaseDate),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFEBEBEB)
@Composable
private fun PreviewPopularMovieItem() {
    MovieItem(
        movieUiModel = fakeMovieUiModel,
        onMovieClick = {}
    )
}