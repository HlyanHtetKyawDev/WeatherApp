package com.mm.weatherapp.astronomy.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mm.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun TimeDistanceCard(
    distance: String,
    localTime: String
) {
    var isHovered by remember { mutableStateOf(false) }
    val elevation by animateDpAsState(
        targetValue = if (isHovered) 8.dp else 4.dp,
        animationSpec = tween(200), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isHovered = !isHovered },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AnimatedContent(
                targetState = distance,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                            fadeOut(animationSpec = tween(300))
                },
                label = "",
                modifier = Modifier.weight(1f)
            ) { targetDistance ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "distance",
                        modifier = Modifier.scale(if (isHovered) 1.1f else 1f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Distance",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = targetDistance,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            AnimatedContent(
                targetState = localTime,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                            fadeOut(animationSpec = tween(300))
                },
                label = "",
                modifier = Modifier.weight(1f)

            ) { targetTime ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "time",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(if (isHovered) 1.1f else 1f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Local Time",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = targetTime,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewTimeDistanceCard() {
    WeatherAppTheme {
        TimeDistanceCard(
            distance = "6000 m",
            localTime = "2024-11-15T20:49:00Z"
        )
    }
}