package com.linhtetko.themovieapp.network

import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.network.responses.ActorListResponse
import com.linhtetko.themovieapp.network.responses.CreditResponse
import com.linhtetko.themovieapp.network.responses.GenreResponse
import com.linhtetko.themovieapp.network.responses.MovieListResponse
import com.linhtetko.themovieapp.utils.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET(GET_NOW_PLAYING)
    fun getNowPlayingMovies(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Observable<MovieListResponse>

    @GET(GET_POPULAR)
    fun getPopularMovies(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Observable<MovieListResponse>

    @GET(GET_TOP_RATED)
    fun getTopRatedMovies(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Observable<MovieListResponse>

    @GET(GET_GENRE)
    fun getGenre(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY
    ): Observable<GenreResponse>

    @GET(GET_MOVIE_BY_GENRE)
    fun getMoviesByGenre(
        @Query(PARAM_GENRE) genreId: Int,
        @Query(PARAM_API_KEY) apiKey: String = API_KEY
    ): Observable<MovieListResponse>

    @GET(GET_ACTORS)
    fun getActorsLists(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY
    ): Observable<ActorListResponse>

    @GET(GET_MOVIE_DETAIL)
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query(PARAM_API_KEY) apiKey: String = API_KEY
    ): Observable<MovieVO>

    @GET(GET_MOVIE_CREDIT)
    fun getMovieCredit(
        @Path("movie_id") movieId: Int,
        @Query(PARAM_API_KEY) apiKey: String = API_KEY
    ): Observable<CreditResponse>

    @GET(GET_MOVIE_SEARCH)
    fun getMovieSearch(
        @Query(PARAM_API_KEY) apiKey: String = API_KEY,
        @Query(PARAM_QUERY) query: String
    ): Observable<MovieListResponse>

}