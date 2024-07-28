package com.example.moviesapp.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MoviesRatingRow(modifier: Modifier, rating: String?, voters: String? = null) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoviesAppImage(modifier = Modifier, vector = Icons.Default.Star, color = MaterialTheme.colorScheme.tertiary)
        MoviesAppText(
            modifier = Modifier.wrapContentWidth(),
            text = rating ?: "0.0",
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End
        )
    }
}