package com.linhtetko.themovieapp.data.model

import androidx.lifecycle.LiveData
import com.linhtetko.themovieapp.data.vos.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

object MovieModelImpl : BaseModel(), MovieModel {

    override fun getNowPlayingMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {

        mMovieApi.getNowPlayingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movieVO ->
                    movieVO.type = NOW_PLAYING
                }

                mMovieDao?.insertMovies(it.results ?: listOf())

            }, {
                onFailure(it.localizedMessage ?: "")
            })
        return mMovieDao?.getMovieByType(NOW_PLAYING)
    }

    override fun getPopularMovies(onFailure: (String) -> Unit): LiveData<List<MovieVO>>? {

        mMovieApi.getPopularMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val result = it.results
                result?.forEach { movieVO ->
                    movieVO.type = POPULAR
                }
                mMovieDao?.insertMovies(result ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
        return mMovieDao?.getMovieByType(POPULAR)
    }

    override fun getTopRatedMovies(
        onFailure: (String) -> Unit
    ): LiveData<List<MovieVO>>? {

        mMovieApi.getTopRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movieVO ->
                    movieVO.type = TOP_RATED
                }
                mMovieDao?.insertMovies(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDao?.getMovieByType(TOP_RATED)
    }

    override fun getGenreList(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.genres ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieByGenre(
        genreId: Int,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getMoviesByGenre(genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

    }

    override fun getActorList(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getActorsLists()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.result ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieDetail(
        movieId: Int,
        onFailure: (String) -> Unit
    ): LiveData<MovieVO?>? {


        mMovieApi.getMovieDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val movieFromDb = mMovieDao?.getMovieByIdSync(movieId)
                it.type = movieFromDb?.type
                mMovieDao?.insertMovie(it)

            }, {
                onFailure(it.localizedMessage ?: "")
            })

        return mMovieDao?.getMovieById(movieId)
    }

    override fun getMovieCredit(
        movieId: Int,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getMovieCredit(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getSearchedMovies(
        query: String,
    ): Observable<List<MovieVO>> {
        return mMovieApi.getMovieSearch(
            query = query
        ).map { it.results ?: listOf() }
            .onErrorResumeNext { Observable.just(listOf()) }
            .subscribeOn(Schedulers.io())
    }
}

/*
object MovieModelImpl : MovieModel, BaseModel() {

    override fun getNowPlayingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        onSuccess(
            mMovieDao?.getMovieByType(NOW_PLAYING) ?: emptyList()
        )

        mMovieApi.getNowPlayingMovies()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movieVO ->
                    movieVO.type = NOW_PLAYING
                }

                mMovieDao?.insertMovies(it.results ?: listOf())

                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getPopularMovies(onSuccess: (List<MovieVO>) -> Unit, onFailure: (String) -> Unit) {
        onSuccess(
            mMovieDao?.getMovieByType(POPULAR) ?: emptyList()
        )
        mMovieApi.getPopularMovies()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val result = it.results
                result?.forEach { movieVO ->
                    movieVO.type = POPULAR
                }
                mMovieDao?.insertMovies(result ?: listOf())
                onSuccess(result ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getTopRatedMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        onSuccess(
            mMovieDao?.getMovieByType(TOP_RATED) ?: emptyList()
        )
        mMovieApi.getTopRatedMovies()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.results?.forEach { movieVO ->
                    movieVO.type = TOP_RATED
                }
                mMovieDao?.insertMovies(it.results ?: listOf())
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getGenreList(onSuccess: (List<GenreVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getGenre()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.genres ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieByGenre(
        genreId: Int,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getMoviesByGenre(genreId)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.results ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })

    }

    override fun getActorList(onSuccess: (List<ActorVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieApi.getActorsLists()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(it.result ?: listOf())
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieDetail(
        movieId: Int,
        onSuccess: (MovieVO?) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val movieFromDb = mMovieDao?.getMovieById(movieId)
        onSuccess(movieFromDb)

        mMovieApi.getMovieDetail(movieId)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.type = movieFromDb?.type
                mMovieDao?.insertMovie(it)

                onSuccess(it)
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }

    override fun getMovieCredit(
        movieId: Int,
        onSuccess: (Pair<List<ActorVO>, List<ActorVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieApi.getMovieCredit(movieId)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onSuccess(Pair(it.cast ?: listOf(), it.crew ?: listOf()))
            }, {
                onFailure(it.localizedMessage ?: "")
            })
    }
}


 */