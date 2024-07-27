package com.example.moviesapp.common.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MoviesAppText(
    modifier: Modifier, text: String?, style: TextStyle = MaterialTheme.typography.titleMedium,
    color: Color = MaterialTheme.colorScheme.secondary,
    textAlign: TextAlign = TextAlign.Start
) {
    if (!text.isNullOrBlank()) {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            color = color,
            textAlign = textAlign
        )
    }
}

@Composable
fun MoviesAppImage(modifier: Modifier, vector: ImageVector, color: Color = Color.Yellow) {
    Image(
        modifier = modifier, imageVector = vector,
        contentDescription = "Movie",
        colorFilter = ColorFilter.tint(color)
    )
}

@Composable
fun MoviesAppImage(modifier: Modifier, url: String?) {
    Log.d("Url", "Url " + url)
    if (!url.isNullOrBlank())
        AsyncImage(
            modifier = modifier, model = url,
            /*ImageRequest.Builder(LocalContext.current)
                            .data(url)
                            .setHeader(HeaderKeys.AUTHORIZATION, HeaderValues.TOKEN_TYPE + HeaderValues.ACCESS_TOKEN)
                            .setHeader(HeaderKeys.ACCEPT, HeaderValues.CONTENT_TYPE_VALUE)
                            .build()*/
            contentDescription = "Movie",
            /* colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)*/
        )

}

@Composable
fun MaterialAppCard(
    modifier: Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    elevation: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = MaterialTheme.colorScheme.tertiary,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        content = content
    )
}