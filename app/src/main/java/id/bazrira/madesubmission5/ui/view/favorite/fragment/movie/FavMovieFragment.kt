package id.bazrira.madesubmission5.ui.view.favorite.fragment.movie


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.api.data.movie.MovieDAO
import id.bazrira.madesubmission5.db.persistance.movie.Injection
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.BACKDROP_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.CONTENT_URI_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.DESCRIPTION_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.POSTER_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.RATING_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.RELEASE_DATE_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.TITLE_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion._ID_MV
import id.bazrira.madesubmission5.ui.view.movie.adapter.MovieAdapter
import id.bazrira.madesubmission5.ui.viewmodel.favorite.movie.FavMovieViewModel
import id.bazrira.madesubmission5.ui.viewmodel.favorite.movie.FavMovieViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_fav_movie.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException

/**
 * A simple [Fragment] subclass.
 */
class FavMovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter

    private lateinit var favMovieViewModelFactory: FavMovieViewModelFactory
    private val viewModel: FavMovieViewModel by viewModels { favMovieViewModelFactory }
    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        favMovieViewModelFactory = Injection.provideViewModelFactory(requireContext())

        return inflater.inflate(R.layout.fragment_fav_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movies.setHasFixedSize(true)

        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()

        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.adapter = adapter

        sw_layout.setOnRefreshListener {
            setViewModel()
        }
    }

    override fun onStart() {
        super.onStart()

        setViewModel()
    }

    private fun setViewModel() {
        showLoading(true)

        GlobalScope.launch {
            val cursor = requireContext().contentResolver?.query(
                CONTENT_URI_MV,
                null,
                null,
                null,
                null
            )

            cursor?.apply {
                val listItems = ArrayList<MovieDAO>()

                while (moveToNext()) {
                    try {
                        val data =
                            MovieDAO(
                                this.getInt(this.getColumnIndex(_ID_MV)),
                                this.getString(this.getColumnIndex(TITLE_MV)),
                                this.getDouble(this.getColumnIndex(RATING_MV)),
                                this.getString(this.getColumnIndex(RELEASE_DATE_MV)),
                                this.getString(this.getColumnIndex(POSTER_MV)),
                                this.getString(this.getColumnIndex(BACKDROP_MV)),
                                this.getString(this.getColumnIndex(DESCRIPTION_MV))
                            )

                        listItems.add(data)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } catch (e: ExecutionException) {
                        e.printStackTrace()
                    }
                }

                adapter.setData(listItems)
                showLoading(false)
            }
        }

        if (sw_layout.isRefreshing && !isNetworkAvailable()) {
            showLoading(false)
            Toast.makeText(context, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showLoading(state: Boolean) {
        sw_layout.isRefreshing = state
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        disposable.clear()
    }
}
