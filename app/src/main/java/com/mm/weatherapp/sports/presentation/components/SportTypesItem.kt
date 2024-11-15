package com.mm.weatherapp.sports.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mm.weatherapp.R
import com.mm.weatherapp.sports.domain.SportItem
import com.mm.weatherapp.ui.theme.YellowLight

@Composable
fun SportTypesItem(
    sportType: String,
    list: List<SportItem>?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(PaddingValues(horizontal = 16.dp, vertical = 8.dp)),
    ) {
        Text(
            text = sportType,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        if (list.isNullOrEmpty()) {
            Text(
                text = stringResource(R.string.there_is_no_relevant_data),
                color = YellowLight
            )

        } else {
            list.forEach {
                SportsItemCard(
                    sportsItem = it
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}