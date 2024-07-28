package com.example.moviesapp.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HorizontalKeyValueCell(
    modifier: Modifier,
    key: String?,
    value: String?,

    ) {
    if (!key.isNullOrBlank()) {
        FlowRow(
            modifier = modifier, horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = key, style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value ?: "", style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}