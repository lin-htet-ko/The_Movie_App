package com.linhtetko.themovieapp.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.linhtetko.themovieapp.R
import com.linhtetko.themovieapp.adapters.BannerAdapter
import com.linhtetko.themovieapp.adapters.ShowCaseAdapter
import com.linhtetko.themovieapp.data.model.MovieModel
import com.linhtetko.themovieapp.data.model.MovieModelImpl
import com.linhtetko.themovieapp.data.vos.GenreVO
import com.linhtetko.themovieapp.delegates.BannerViewHolderDelegate
import com.linhtetko.themovieapp.delegates.MovieListViewHolderDelegate
import com.linhtetko.themovieapp.delegates.ShowCaseViewHolderDelegate
import com.linhtetko.themovieapp.viewpods.ActorListViewPod
import com.linhtetko.themovieapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BannerViewHolderDelegate, ShowCaseViewHolderDelegate,
    MovieListViewHolderDelegate {

    private lateinit var mBannerAdapter: BannerAdapter
    private lateinit var mShowCaseAdapter: ShowCaseAdapter
    private lateinit var mMovieList: MovieListViewPod
    private lateinit var mActorList: ActorListViewPod
    private lateinit var mMovieListByGenre: MovieListViewPod

    private val mMovieModel: MovieModel = MovieModelImpl
    private var genresList: List<GenreVO>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpToolbar()
        setUpViewPods()
        setBannerViewPager()
//        setUpGenreTabBar()
        setUpShowCaseRecyclerView()

        setUpListener()

        requestData()


    }

    private fun requestData() {

        //Get Banner Movies
        mMovieModel.getNowPlayingMovies(
            onFailure = {
                showError(it)
            }
        )?.observe(this){
            mBannerAdapter.setNewData(it)
        }

        //Get Popular Movies
        mMovieModel.getPopularMovies(
            onFailure = {
                showError(it)
            }
        )?.observe(this){
            mMovieList.setNewData(it)
        }

        //Get Genre List
        mMovieModel.getGenreList(
            onSuccess = {
                setUpGenreTabBar(it)
            },
            onFailure = {
                showError(it)
            }
        )

        //Get Showcase
        mMovieModel.getTopRatedMovies(
            onFailure = {
                showError(it)
            }
        )?.observe(this){
            mShowCaseAdapter.setNewData(it)
        }

        //Get Actor List
        mMovieModel.getActorList(
            onSuccess = {
                mActorList.setData(it)
            },
            onFailure = {
                showError(it)
            }
        )

    }

    private fun showError(text: String) {
        Snackbar.make(clMain, text, Snackbar.LENGTH_LONG).show()
    }

    private fun setUpViewPods() {
        mMovieList = vpMovieList as MovieListViewPod
        mMovieList.setUpMovieListViewPod(this)

        mMovieListByGenre = vpMovieByGenre as MovieListViewPod
        mMovieListByGenre.setUpMovieListViewPod(this)

        mActorList = vpMainActors as ActorListViewPod
        mActorList.setUpViewPod(
            R.color.colorPrimary,
            getString(R.string.lbl_best_actors),
            getString(R.string.lbl_more_actors)
        )
    }

    private fun setUpListener() {

        //Tab Layout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    genresList?.getOrNull(tab.position)?.let { genreVO ->
                        requestMovieByGenre(genreVO.id)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun requestMovieByGenre(genreId: Int) {

        //Get Genres
        mMovieModel.getMovieByGenre(
            genreId = genreId,
            onSuccess = {
                mMovieListByGenre.setNewData(it)
            },
            onFailure = {
                showError(it)
            }
        )
    }

    private fun setUpShowCaseRecyclerView() {
        mShowCaseAdapter = ShowCaseAdapter(this)
        rvShowCases.adapter = mShowCaseAdapter
    }

    private fun setUpGenreTabBar(genres: List<GenreVO>) {

        genresList = genres

        genres.forEach { genre ->
            tabLayout.newTab().apply {
                text = genre.name
                tabLayout.addTab(this)
            }
        }
    }

    private fun setBannerViewPager() {
        mBannerAdapter = BannerAdapter(this)
        viewPagerBanner.adapter = mBannerAdapter

        dotIndicatorBanner.attachTo(viewPagerBanner)
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.discover_menu, menu)
        return true
    }

    override fun onItemClick(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    override fun onShowCaseClick(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    override fun onMovieItemClick(movieId: Int) {
        startActivity(MovieDetailActivity.newIntent(this, movieId))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuSearch){
            startActivity(MovieSearchActivity.newIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }
}