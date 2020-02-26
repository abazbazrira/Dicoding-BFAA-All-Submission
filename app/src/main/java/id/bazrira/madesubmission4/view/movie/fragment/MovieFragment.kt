package id.bazrira.madesubmission4.view.movie.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.bazrira.madesubmission4.R
import id.bazrira.madesubmission4.view.movie.adapter.MovieAdapter
import id.bazrira.madesubmission4.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private val API_KEY = "b59545fa485ebb68339cb053f3164aac"

    private lateinit var mainViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movies.setHasFixedSize(true)

        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()

        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieViewModel::class.java)

        setViewModel()

        swlayout.setOnRefreshListener {
            setViewModel()
        }
    }

    private fun setViewModel() {
        val url = "discover/movie?api_key=$API_KEY&language=en-En"

        showLoading(true)

        mainViewModel.setMovie(url)

        mainViewModel.getMovies().observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                adapter.setData(items)

                showLoading(false)
            }
        })

        if (swlayout.isRefreshing && !isNetworkAvailable()) {
            showLoading(false)
            Toast.makeText(context, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(state: Boolean) {
        swlayout.isRefreshing = state
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
