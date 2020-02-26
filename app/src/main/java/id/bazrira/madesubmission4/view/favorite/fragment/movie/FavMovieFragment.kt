package id.bazrira.madesubmission4.view.favorite.fragment.movie


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.EmptyResultSetException
import id.bazrira.madesubmission4.R
import id.bazrira.madesubmission4.data.movie.MovieDAO
import id.bazrira.madesubmission4.db.persistance.movie.Injection
import id.bazrira.madesubmission4.view.movie.adapter.MovieAdapter
import id.bazrira.madesubmission4.viewmodel.fav.movie.FavMovieViewModel
import id.bazrira.madesubmission4.viewmodel.fav.movie.FavMovieViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_fav_movie.*

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

        try {
            disposable.add(
                viewModel.getMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            val listItems = ArrayList<MovieDAO>()

                            for (m in it) {
                                val data = MovieDAO(
                                    m.id,
                                    m.title,
                                    m.rating,
                                    m.releaseDate,
                                    m.poster,
                                    m.backDrop,
                                    m.desc
                                )

                                listItems.add(data)
                            }
                            adapter.setData(listItems)
                            showLoading(false)
                        },
                        { error ->
                            Log.e("Error DB", "Unable to get movie", error)
                        }
                    )
            )
        } catch (e: EmptyResultSetException) {
            Log.e("Error DB", e.toString())
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
