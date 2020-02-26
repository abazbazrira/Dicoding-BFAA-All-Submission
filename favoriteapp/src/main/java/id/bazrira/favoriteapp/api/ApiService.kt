package id.bazrira.favoriteapp.api

import id.bazrira.favoriteapp.api.response.DetailMovieResponse
import id.bazrira.favoriteapp.api.response.DetailTvShowResponse
import id.bazrira.favoriteapp.api.response.MovieResponse
import id.bazrira.favoriteapp.api.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface ApiService {

    @GET
    fun getMovie(@Url Url: String): Call<MovieResponse>

    @GET
    fun getTvShow(@Url Url: String): Call<TvShowResponse>

    @GET
    fun getDetailMovie(@Url Url: String): Call<DetailMovieResponse>

    @GET
    fun getDetailTvShow(@Url Url: String): Call<DetailTvShowResponse>
}
