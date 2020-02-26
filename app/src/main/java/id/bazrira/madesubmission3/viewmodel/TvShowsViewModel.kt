package id.bazrira.madesubmission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.bazrira.madesubmission3.api.ApiHelper.apiService
import id.bazrira.madesubmission3.api.response.DetailTvShowResponse
import id.bazrira.madesubmission3.api.response.TvShowResponse
import id.bazrira.madesubmission3.data.tv.TvShowsDAO
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
                        if (response.isSuccessful() || response.code() == 200) {
                            response.body()?.results?.let {
                                for (t in it) {
                                    val poster = t.posterPath
                                    val backDrop = t.backdropPath

                                    val sdfDate =
                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    sdfDate.timeZone = TimeZone.getTimeZone("UTC")

                                    val parseDate = sdfDate.parse(t.firstAirDate)
                                    val formatDate =
                                        SimpleDateFormat("dd-MM-yyyy").format(parseDate)

                                    val tvItems = TvShowsDAO(
                                        t.id,
                                        t.originalName,
                                        t.voteAverage,
                                        formatDate,
                                        "https://image.tmdb.org/t/p/w185/$poster",
                                        "https://image.tmdb.org/t/p/w500/$backDrop",
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
                        if (response.isSuccessful() || response.code() == 200) {
                            response.body()?.let {
                                val poster = it.posterPath
                                val backDrop = it.backdropPath

                                val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                sdfDate.timeZone = TimeZone.getTimeZone("UTC")

                                val parseDate = sdfDate.parse(it.firstAirDate)
                                val formatDate = SimpleDateFormat("dd-MM-yyyy").format(parseDate)

                                val tvItems = TvShowsDAO(
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