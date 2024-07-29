package com.example.moviesapp

import com.example.moviesapp.common.service.Resource
import com.example.moviesapp.domain.data.MovieItemResponse
import com.example.moviesapp.domain.repo.MoviesRepository
import com.example.moviesapp.domain.data.MoviesResponse
import com.example.moviesapp.domain.usecase.MoviesUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MoviesUseCaseTest {
    @MockK
    private lateinit var repository: MoviesRepository

    private lateinit var useCase: MoviesUseCaseImpl

    @Before
    fun init() {
        MockKAnnotations.init(this)
        repository = mockk()
        useCase = MoviesUseCaseImpl(repository)
    }

    @Test
    fun testMoviesSuccess() = runTest {
        val mockResponse = MoviesResponse(
            movies = listOf(
                MovieItemResponse(
                    id = "1",
                    title = "Movie 1",
                    url = "/path1",
                    voteAverage = 8.756
                ),
                MovieItemResponse(
                    id = "2",
                    title = "Movie 2",
                    url = "/path2",
                    voteAverage = 7.123
                )
            )
        )

        coEvery { repository.fetchMovies() } returns flow {
            emit(Resource.Success(mockResponse))
        }
        val result = useCase.getMovies().first()
        assert(result is Resource.Success)
        val movies = (result as Resource.Success).data?.movies

        assertNotNull(movies)

        //Url is modified and average vote is formatted to 2 decimal places
        assertEquals("https://media.themoviedb.org/t/p/w100_and_h100_face/path1", movies!![0].url)
        assertEquals(8.76, movies[0].voteAverage)
        assertEquals("https://media.themoviedb.org/t/p/w100_and_h100_face/path2", movies[1].url)
        assertEquals(7.12, movies[1].voteAverage)

    }

    @Test
    fun testMoviesErrorScenario() = runTest {
        val errorMessage = "Network Error"

        coEvery { repository.fetchMovies() } returns flow {
            emit(Resource.Error(errorMessage))
        }

        val result = useCase.getMovies().first()
        assert(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }

}