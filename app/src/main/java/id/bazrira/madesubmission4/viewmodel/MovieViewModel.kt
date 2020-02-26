package id.bazrira.madesubmission4.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.bazrira.madesubmission4.api.ApiHelper.apiService
import id.bazrira.madesubmission4.api.response.DetailMovieResponse
import id.bazrira.madesubmission4.api.response.MovieResponse
import id.bazrira.madesubmission4.data.movie.MovieDAO
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MovieViewModel : ViewModel() {

    val listMovie = MutableLiveData<ArrayList<MovieDAO>>()
    val detailMovie = MutableLiveData<MovieDAO>()

    internal fun setMovie(url: String) {

        val listItems = ArrayList<MovieDAO>()

        apiService.getMovie(url)
            .enqueue(object : retrofit2.Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    try {
                        if (response.isSuccessful || response.code() == 200) {
                            response.body()?.results?.let {
                                for (m in it) {
                                    val poster = m.posterPath
                                    val backDrop = m.backdropPath

                                    val sdfDate =
                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    sdfDate.timeZone = TimeZone.getTimeZone("UTC")

                                    val parseDate = sdfDate.parse(m.releaseDate)
                                    val formatDate =
                                        SimpleDateFormat("dd-MM-yyyy").format(parseDate)

                                    val movieItems = MovieDAO(
                                        m.id,
                                        m.title,
                                        m.voteAverage,
                                        formatDate,
                                        "https://image.tmdb.org/t/p/w185/$poster",
                                        "https://image.tmdb.org/t/p/w500/$backDrop",
                                        m.overview
                                    )
                                    listItems.add(movieItems)
                                }
                            }

                            listMovie.postValue(listItems)
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    internal fun getMovies(): LiveData<ArrayList<MovieDAO>> {
        return listMovie
    }

    internal fun setDetailMovie(url: String) {

        apiService.getDetailMovie(url)
            .enqueue(object : retrofit2.Callback<DetailMovieResponse> {
                override fun onResponse(
                    call: Call<DetailMovieResponse>,
                    response: Response<DetailMovieResponse>
                ) {
                    try {
                        if (response.isSuccessful || response.code() == 200) {
                            response.body()?.let { it ->

                                val poster = it.posterPath
                                val backDrop = it.backdropPath

                                val sdfDate =
                                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                sdfDate.timeZone = TimeZone.getTimeZone("UTC")

                                val parseDate = sdfDate.parse(it.releaseDate)
                                val formatDate =
                                    SimpleDateFormat("dd-MM-yyyy").format(parseDate)

                                val movieItems = MovieDAO(
                                    it.id,
                                    it.title,
                                    it.voteAverage,
                                    formatDate,
                                    "https://image.tmdb.org/t/p/w185/$poster",
                                    "https://image.tmdb.org/t/p/w500/$backDrop",
                                    it.overview
                                )

                                detailMovie.postValue(movieItems)
                            }


                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    internal fun getDetailMovie(): LiveData<MovieDAO> {
        return detailMovie
    }
}