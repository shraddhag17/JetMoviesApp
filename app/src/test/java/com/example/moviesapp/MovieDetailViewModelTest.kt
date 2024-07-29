package com.example.moviesapp

import com.example.moviesapp.domain.usecase.MovieDetailUseCaseImpl
import com.example.moviesapp.domain.data.MovieDetailsResponse
import com.example.moviesapp.ui.viewmodel.MoviesDetailViewModel
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class MovieDetailViewModelTest {
    @MockK
    private lateinit var useCase: MovieDetailUseCaseImpl

    private lateinit var viewModel: MoviesDetailViewModel

    @Before
    fun setUp() {
        useCase = mockk()
        viewModel = MoviesDetailViewModel(useCase)
    }

    @Test
    fun testUICards() {
        var mockResponse = MovieDetailsResponse(
                    id = "1",
                    title = "Movie 1",
                    url = "baseUrl/path1",
                    voteAverage = 8.76,
            overview = "Movie overview",
            runtime = "2h40m",
            releaseDate = "2021-10-10"
        )
        var movieDetails = viewModel.getUICards(response = mockResponse)
        assertNotNull(movieDetails)
        assertEquals("Movie 1", movieDetails?.title)
        assertEquals("Movie overview", movieDetails?.overview)

        //If title blank
        mockResponse = MovieDetailsResponse(id = "1", title = " ")
        movieDetails = viewModel.getUICards(response = mockResponse)
        assertNull(movieDetails)

        //If title empty
        mockResponse = MovieDetailsResponse(id = "1", title = "")
        movieDetails = viewModel.getUICards(response = mockResponse)
        assertNull(movieDetails)

        //If title null
        mockResponse = MovieDetailsResponse(id = "1", title = null)
        movieDetails = viewModel.getUICards(response = mockResponse)
        assertNull(movieDetails)
    }
}