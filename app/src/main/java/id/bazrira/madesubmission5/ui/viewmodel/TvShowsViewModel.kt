package id.bazrira.madesubmission5.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.bazrira.madesubmission5.api.ApiHelper.apiService
import id.bazrira.madesubmission5.api.response.DetailTvShowResponse
import id.bazrira.madesubmission5.api.response.TvShowResponse
import id.bazrira.madesubmission5.api.data.tv.TvShowsDAO
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TvShowsViewModel : ViewModel() {

    val listTvShows = MutableLiveData<ArrayList<TvShowsDAO>>()
    val detailTvShow = MutableLiveData<TvShowsDAO>()

    internal fun setTvShow(url: String) {

        val listItems = ArrayList<TvShowsDAO>()

        apiService.getTvShow(url)
            .enqueue(object : retrofit2.Callback<TvShowResponse> {
                override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>
                ) {
                    try {
                        if (response.isSuccessful || response.code() == 200) {
                            listItems.clear()
                            response.body()?.results?.let {
                                for (t in it) {

                                    val poster = if (t.posterPath != null) "https://image.tmdb.org/t/p/w185/${t.posterPath}" else ""

                                    val backDrop = if (t.backdropPath != null) "https://image.tmdb.org/t/p/w500/${t.backdropPath}" else ""

                                    val sdfDate =
                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    sdfDate.timeZone = TimeZone.getTimeZone("UTC")

                                    val formatDate = try {
                                        val parseDate = sdfDate.parse(t.firstAirDate)
                                        SimpleDateFormat("dd-MM-yyyy").format(parseDate)
                                    } catch (e: Exception) {
                                        ""
                                    }

                                    val tvItems =
                                        TvShowsDAO(
                                            t.id,
                                            t.originalName,
                                            t.voteAverage,
                                            formatDate,
                                            poster,
                                            backDrop,
                                            t.overview
                                        )

                                    listItems.add(tvItems)
                                }
                            }

                            listTvShows.postValue(listItems)
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    internal fun getTvShows(): LiveData<ArrayList<TvShowsDAO>> {
        return listTvShows
    }

    internal fun setDetailTvShow(url: String) {

        apiService.getDetailTvShow(url)
            .enqueue(object : retrofit2.Callback<DetailTvShowResponse> {
                override fun onResponse(
                    call: Call<DetailTvShowResponse>,
                    response: Response<DetailTvShowResponse>
                ) {
                    try {
                        if (response.isSuccessful || response.code() == 200) {
                            response.body()?.let {
                                val poster = it.posterPath
                                val backDrop = it.backdropPath

                                val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                sdfDate.timeZone = TimeZone.getTimeZone("UTC")

                                val parseDate = sdfDate.parse(it.firstAirDate)
                                val formatDate = SimpleDateFormat("dd-MM-yyyy").format(parseDate)

                                val tvItems =
                                    TvShowsDAO(
                                        it.id,
                                        it.originalName,
                                        it.voteAverage,
                                        formatDate,
                                        "https://image.tmdb.org/t/p/w185/$poster",
                                        "https://image.tmdb.org/t/p/w500/$backDrop",
                                        it.overview
                                    )
                                detailTvShow.postValue(tvItems)
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    internal fun getDetailTvShow(): LiveData<TvShowsDAO> {
        return detailTvShow
    }
}