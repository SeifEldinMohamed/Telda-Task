package com.example.teldatask.presentation.screens.movies_home_screen.components.shimmer_loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimateShimmerMoviesList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(10) {
            val shimmerColors = listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            )

            val transition = rememberInfiniteTransition(label = "")
            val translateAnim = transition.animateFloat(
                initialValue = 0f,
                targetValue = 1000f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 700,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = ""
            )

            val brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset.Zero,
                end = Offset(x = translateAnim.value, y = translateAnim.value)
            )

            movieShimmerItem(brush = brush)

        }
    }

}

@Composable
private fun movieShimmerItem(brush: Brush) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp, top = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .width(110.dp)
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(brush)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Spacer(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .height(18.dp)
                            .clip(MaterialTheme.shapes.small)
                            .fillMaxWidth(fraction = 0.9f)
                            .background(brush)

                    )

                }

                Spacer(modifier = Modifier.padding(6.dp))
                Spacer(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxWidth(fraction = 0.9f)
                        .background(brush)
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxWidth(fraction = 0.5f)
                        .background(brush)
                )
                Spacer(modifier = Modifier.padding(6.dp))

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .clip(MaterialTheme.shapes.small)
                        .fillMaxWidth(fraction = 0.7f)
                        .background(brush)
                )
                Spacer(modifier = Modifier.padding(6.dp))

            }
        }
    }
    // HorizontalDivider()
}

@Composable
@Preview(showBackground = true)
fun ShimmerItemPreview() {
    movieShimmerItem(
        brush = Brush.linearGradient(
            listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            )
        )
    )
}