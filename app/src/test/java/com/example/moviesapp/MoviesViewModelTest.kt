package com.example.moviesapp

import com.example.moviesapp.domain.data.MovieItemResponse
import com.example.moviesapp.domain.data.MoviesResponse
import com.example.moviesapp.domain.usecase.MoviesUseCaseImpl
import com.example.moviesapp.ui.viewmodel.MoviesViewModel
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesViewModelTest {
    @MockK
    private lateinit var useCase: MoviesUseCaseImpl

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        useCase = mockk()
        viewModel = MoviesViewModel(useCase)
    }

    @Test
    fun testUICards() {
        val mockResponse = MoviesResponse(
            movies = listOf(
                MovieItemResponse(
                    id = "1",
                    title = "Movie 1",
                    url = "baseUrl/path1",
                    voteAverage = 8.76
                ),
                MovieItemResponse(
                    id = "2",
                    title = "Movie 2",
                    url = "baseUrl/path2",
                    voteAverage = 7.12
                )
            )
        )
        val cards = viewModel.getUICards(response = mockResponse.movies)
        assertEquals(2, cards.size)
        assertEquals("Movie 1", cards[0].name)
        assertEquals("1", cards[0].id)
        assertEquals("Movie 2", cards[1].name)
        assertEquals("2", cards[1].id)
    }

}