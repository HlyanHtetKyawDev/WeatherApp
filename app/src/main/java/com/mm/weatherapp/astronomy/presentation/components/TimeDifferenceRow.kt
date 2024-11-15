package com.mm.weatherapp.astronomy.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mm.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun TimeDifferenceRow(
    label: String,
    timeDifference: String,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = timeDifference,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
@Preview
fun PreviewTimeDifferenceRow(){
    WeatherAppTheme {
        TimeDifferenceRow(
            label = "Rise Difference",
            timeDifference = "+13h 15m",
            color = MaterialTheme.colorScheme.primary
        )
    }
}
