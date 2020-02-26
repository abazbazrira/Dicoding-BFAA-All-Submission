package id.bazrira.favoriteapp.view.movie.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.bazrira.favoriteapp.R
import id.bazrira.favoriteapp.data.movie.MovieDAO
import id.bazrira.favoriteapp.view.movie.adapter.MovieAdapter
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion.BACKDROP_MV
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion.CONTENT_URI_MV
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion.DESCRIPTION_MV
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion.POSTER_MV
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion.RATING_MV
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion.RELEASE_DATE_MV
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion.TITLE_MV
import id.bazrira.favoriteapp.db.persistance.movie.Injection.Injection.Companion._ID_MV
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

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

        swlayout.setOnRefreshListener {
            getData()
        }

        rv_movies.setHasFixedSize(true)

        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()

        rv_movies.layoutManager = LinearLayoutManager(context)
        rv_movies.adapter = adapter

        getData()
    }

    private fun getData() {

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
