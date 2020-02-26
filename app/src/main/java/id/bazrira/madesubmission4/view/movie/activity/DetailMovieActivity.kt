package id.bazrira.madesubmission4.view.movie.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.EmptyResultSetException
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import id.bazrira.madesubmission4.R
import id.bazrira.madesubmission4.data.movie.MovieDAO
import id.bazrira.madesubmission4.db.persistance.movie.Injection
import id.bazrira.madesubmission4.viewmodel.MovieViewModel
import id.bazrira.madesubmission4.viewmodel.fav.movie.FavMovieViewModel
import id.bazrira.madesubmission4.viewmodel.fav.movie.FavMovieViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    private val API_KEY = "b59545fa485ebb68339cb053f3164aac"

    private lateinit var mainViewModel: MovieViewModel

    private lateinit var favMovieViewModelFactory: FavMovieViewModelFactory
    private val viewModel: FavMovieViewModel by viewModels { favMovieViewModelFactory }
    private val disposable = CompositeDisposable()

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private var id: Int? = null

    private var listItems = ArrayList<MovieDAO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        supportActionBar?.title = getString(R.string.title_1)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieViewModel::class.java)

        swlayout.setOnRefreshListener {
            setViewModel()
        }

        setViewModel()

        favMovieViewModelFactory = Injection.provideViewModelFactory(this)
    }

    private fun setViewModel() {
        val mv = intent.getParcelableExtra(EXTRA_MOVIE) as MovieDAO

        id = mv.id
        val url = "movie/$id?api_key=$API_KEY&language=en-En"

        showLoading(true)

        mainViewModel.setDetailMovie(url)

        mainViewModel.getDetailMovie().observe(this, Observer { items ->
            if (items != null) {
                listItems.clear()

                tv_detail_rating.text = items.rating.toString()
                tv_detail_date.text = items.releaseDate
                tv_detail_title.text = items.title
                tv_detail_desc.text = items.desc

                Glide.with(this)
                    .load(items.poster)
                    .apply(RequestOptions().override(500, 750))
                    .into(iv_poster)

                Glide.with(this)
                    .load(items.backDrop)
                    .centerCrop()
                    .into(bg)

                showLoading(false)

                listItems.add(items)
            }
        })

        if (swlayout.isRefreshing && !isNetworkAvailable()) {
            showLoading(false)
            Toast.makeText(this, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT).show()
        }
    }

    private fun addToFavorite() {
        try {
            disposable.add(
                viewModel.updateMovie(listItems[0])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            isFavorite = true
                            setFavorite()
                            Snackbar.make(swlayout, "Added to favorite", Snackbar.LENGTH_SHORT)
                                .show()
                        },
                        { error ->
                            Log.e("Error DB", "Unable to insert movie", error)
                        }
                    )
            )
        } catch (e: EmptyResultSetException) {
            Log.e("Error DB", e.toString())
        }
    }


    private fun removeFromFavorite() {
        try {
            disposable.add(
                viewModel.deleteMovie(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            isFavorite = false
                            setFavorite()
                            Snackbar.make(swlayout, "Removed from favorite", Snackbar.LENGTH_SHORT)
                                .show()
                        },
                        { error ->
                            Log.e("Error DB", "Unable to delete movie", error)
                        }
                    )
            )
        } catch (e: EmptyResultSetException) {
            Log.e("Error DB", e.toString())
        }
    }

    private fun favoriteState() {
        try {
            disposable.add(
                viewModel.detailMovie(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            if (it.isNotEmpty()) isFavorite = true
                            setFavorite()
                        },
                        { error ->
                            Log.e("Error DB", "Unable to get movie", error)
                        }
                    )
            )
        } catch (e: EmptyResultSetException) {
            Log.e("Error DB", e.toString())
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun showLoading(state: Boolean) {
        swlayout.isRefreshing = state
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        favoriteState()
        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        disposable.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
