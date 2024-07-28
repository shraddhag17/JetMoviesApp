package com.example.moviesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.R
import com.example.moviesapp.common.ui.HorizontalKeyValueCell
import com.example.moviesapp.common.ui.MoviesAppImage
import com.example.moviesapp.common.ui.MoviesAppText
import com.example.moviesapp.common.ui.MoviesShowMoreLessText
import com.example.moviesapp.common.ui.UIState
import com.example.moviesapp.ui.viewmodel.MoviesDetailViewModel

@Composable
fun MoviesDetailScreen(
    modifier: Modifier, id: String?,
    viewModel: MoviesDetailViewModel = viewModel(
        factory = MoviesDetailViewModel.provideFactory(LocalContext.current)
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is UIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
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
                MovieDetailsScreen(
                    modifier = modifier,
                    model = (uiState as UIState.Success<MovieDetailModel>).data
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fetchMovieDetails(id)
    }
}

@Composable
fun MovieDetailsScreen(modifier: Modifier, model: MovieDetailModel?) {

    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MoviesAppText(
            modifier = modifier,
            text = model?.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        MoviesAppImage(
            modifier = modifier
                .background(Color.Transparent, CircleShape)
                .size(width = 200.dp, height = 200.dp)
                .padding(4.dp), url = model?.url
        )
        HorizontalKeyValueCell(
            modifier = modifier.wrapContentWidth(),
            key = stringResource(R.string.release_date),
            value = model?.releaseDate
        )
        HorizontalKeyValueCell(
            modifier = modifier.wrapContentWidth(),
            key = stringResource(R.string.duration),
            value = model?.runTime
        )
        Spacer(modifier = Modifier.height(20.dp))
        MoviesShowMoreLessText(
            modifier = modifier.wrapContentWidth(),
            text = model?.overview,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start
        )
    }
}