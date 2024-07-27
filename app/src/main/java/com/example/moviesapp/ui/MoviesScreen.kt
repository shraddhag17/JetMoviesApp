package com.example.moviesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.common.ui.MaterialAppCard
import com.example.moviesapp.common.ui.MoviesAppImage
import com.example.moviesapp.common.ui.MoviesAppText
import com.example.moviesapp.common.ui.UIState
import com.example.moviesapp.ui.viewmodel.MoviesViewModel

@Composable
fun MoviesScreen(
    modifier: Modifier,
    viewModel: MoviesViewModel = viewModel(
        factory = MoviesViewModel.provideFactory(
            LocalContext.current
        )
    ),
    onCardClicked: (String) -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier, verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is UIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            is UIState.Error -> {
                EmptyScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    error = (uiState as? UIState.Error)?.message
                )
            }

            is UIState.Success -> {
                MoviesScreen(
                    modifier = modifier,
                    cards = (uiState as UIState.Success<List<MovieItemCard>>).data
                ) {
                    if(!it.isNullOrBlank()) onCardClicked(it)
                }
            }
        }
    }
}

@Composable
fun EmptyScreen(modifier: Modifier, error: String? = null) {
    Box(modifier = modifier) {
        MoviesAppText(
            modifier = Modifier.align(Alignment.Center),
            text = error ?: "No movies found. Please try again later",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MoviesScreen(
    modifier: Modifier,
    cards: List<MovieItemCard>?,
    onCardClicked: (String?) -> Unit
) {
    cards?.let {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
        ) {
            items(items = it, key = { it.id }) { card ->
                ItemCard(card = card) {
                    onCardClicked("")
                }
            }
        }
    } ?: run {
        EmptyScreen(modifier)
    }
}

@Composable
private fun ItemCard(card: MovieItemCard?, onCardClicked: (String?) -> Unit) {

    card?.let {
        MaterialAppCard(modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClicked("") }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                MoviesAppImage(
                    modifier = Modifier
                        .background(Color.Transparent, CircleShape)
                        .size(width = 100.dp, height = 100.dp)
                        .padding(4.dp), url = card.url
                )

                Column(
                    modifier = Modifier.weight(0.7f), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    MoviesAppText(
                        modifier = Modifier.wrapContentWidth(),
                        text = card.name,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start
                    )

                    Row(
                        modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MoviesAppImage(modifier = Modifier, vector = Icons.Default.Star)
                        MoviesAppText(
                            modifier = Modifier.wrapContentWidth(),
                            text = card.avgVote,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.End
                        )
                    }


                }

                MoviesAppImage(
                    modifier = Modifier,
                    vector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    color = Color.Gray
                )

            }
        }

    }
}