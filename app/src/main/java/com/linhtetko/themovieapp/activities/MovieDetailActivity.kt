package com.linhtetko.themovieapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.linhtetko.themovieapp.R
import com.linhtetko.themovieapp.data.model.MovieModel
import com.linhtetko.themovieapp.data.model.MovieModelImpl
import com.linhtetko.themovieapp.data.vos.GenreVO
import com.linhtetko.themovieapp.data.vos.MovieVO
import com.linhtetko.themovieapp.utils.IMAGE_BASE_URL
import com.linhtetko.themovieapp.viewpods.ActorListViewPod
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var mMovieModel: MovieModel

    companion object {

        private const val IE_MOVIE_DETAIL = "movie_id_for_detail"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(IE_MOVIE_DETAIL, movieId)
            return intent
        }
    }

    private lateinit var mvpActors: ActorListViewPod
    private lateinit var mvpCreators: ActorListViewPod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setUpMovieModel()

        setUpViewPods()
        setUpListener()

        getIntentData()
    }

    private fun setUpMovieModel() {
        mMovieModel = MovieModelImpl
    }

    private fun getIntentData() {
        requestDetail(intent.getIntExtra(IE_MOVIE_DETAIL, 0))
    }

    private fun requestDetail(id: Int) {
        mMovieModel.getMovieDetail(
            movieId = id,
            onFailure = {
                showError(it)
            }
        )?.observe(this){
            bindData(it)
        }

        mMovieModel.getMovieCredit(
            movieId = id,
            onSuccess = {
                mvpActors.setData(it.first)
                mvpCreators.setData(it.second)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun bindData(movie: MovieVO?) {
        movie?.apply {
            Glide.with(this@MovieDetailActivity).load("$IMAGE_BASE_URL${posterPath}")
                .into(ivMovieDetailPoster)
            ctlDetail.title = title
            tvMovieName.text = title
            tvMovieDetailReleaseYear.text = releaseDate?.substring(0, 4)
            tvRating.text = voteAverage?.toString() ?: ""
            rbRating.rating = setRatingBaseOnFiveStar()
            tvNumVotes.text = "${voteCount?.toString()} VOTES"
            bindForGenres(
                movie,
                genres ?: listOf()
            )
            tvOverview.text = overview ?: ""
            tvOriginalTitle.text = title ?: ""
            tvType.text = getGenresAsCommaSeparatedString()
            tvProduction.text = getProductionCountriesAsCommaSeparatedString()
            tvPremiere.text = releaseDate ?: ""
            tvDescription.text = overview ?: ""
        }
    }

    private fun bindForGenres(movie: MovieVO, genres: List<GenreVO>) {
        val size = genres.size ?: 0
        if (size < 3) {
            tvThirdGenre.visibility = View.GONE
        } else if (size < 2) {
            tvThirdGenre.visibility = View.GONE
            tvSecondGenre.visibility = View.GONE
        } else {
            genres.getOrNull(0)?.let { genreVO ->
                tvFirstGenre.text = genreVO.name
            }
            genres.getOrNull(1)?.let { genreVO ->
                tvSecondGenre.text = genreVO.name
            }
            genres.getOrNull(2)?.let { genreVO ->
                tvThirdGenre.text = genreVO.name
            }
        }
    }

    private fun showError(text: String) {
        Snackbar.make(clMovieDetail, text, Snackbar.LENGTH_LONG).show()
    }

    private fun setUpListener() {
        btnBack.setOnClickListener { super.onBackPressed() }
    }

    private fun setUpViewPods() {
        mvpActors = vpActors as ActorListViewPod
        mvpActors.setUpViewPod(R.color.colorPrimary, getString(R.string.lbl_actors), "")

        mvpCreators = vpActorCreator as ActorListViewPod
        mvpCreators.setUpViewPod(
            R.color.colorPrimary,
            getString(R.string.lbl_creators),
            getString(R.string.lbl_more_creators)
        )
    }
}