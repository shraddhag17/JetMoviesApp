package com.example.moviesapp.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun MoviesShowMoreLessText(
    modifier: Modifier,
    text: String?,
    minimizedMaxLines: Int = 3,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    color: Color = MaterialTheme.colorScheme.secondary,
    textAlign: TextAlign = TextAlign.Start
) {
    if (!text.isNullOrBlank()) {
        var isExpanded by remember { mutableStateOf(false) }

        Column (modifier = modifier){
            Text(
                text = text,
                maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
                overflow = TextOverflow.Ellipsis,
                style = style,
                color = color,
                textAlign = textAlign
            )
            Text(
                text = if (isExpanded) "Show Less" else "Show More",
                modifier = Modifier.clickable { isExpanded = !isExpanded },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}