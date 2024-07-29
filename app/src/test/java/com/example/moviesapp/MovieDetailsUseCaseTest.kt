package com.example.moviesapp

import com.example.moviesapp.common.service.Resource
import com.example.moviesapp.domain.usecase.MovieDetailUseCaseImpl
import com.example.moviesapp.domain.data.MovieDetailsResponse
import com.example.moviesapp.domain.repo.MoviesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class MovieDetailsUseCaseTest {
    @MockK
    private lateinit var repository: MoviesRepository

    private lateinit var useCase: MovieDetailUseCaseImpl

    @Before
    fun init() {
        MockKAnnotations.init(this)
        repository = mockk()
        useCase = MovieDetailUseCaseImpl(repository)
    }

    @Test
    fun testMoviesSuccess() = runTest {
        val mockResponse = MovieDetailsResponse(
            id = "1",
            title = "Movie 1",
            url = "/path1",
            voteAverage = 8.756,
            overview = "Movie overview",
            runtime = "220",
            releaseDate = "2021-02-22"
        )

        coEvery { repository.getMovieDetails("1") } returns flow {
            emit(Resource.Success(mockResponse))
        }
        val result = useCase.getMovieDetail("1").first()
        assert(result is Resource.Success)
        val movieDetails = (result as Resource.Success).data

        assertNotNull(movieDetails)

        //Url is modified
        assertEquals(
            "https://media.themoviedb.org/t/p/w220_and_h330_face/path1",
            movieDetails!!.url
        )
        //Average vote is formatted
        assertEquals(8.76, movieDetails.voteAverage)

        //Runtime is formatted
        assertEquals("3h40m", movieDetails.runtime)

    }

    @Test
    fun testMoviesErrorScenario() = runTest {
        val errorMessage = "Network Error"

        coEvery { repository.getMovieDetails("1") } returns flow {
            emit(Resource.Error(errorMessage))
        }

        val result = useCase.getMovieDetail("1").first()
        assert(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }
}