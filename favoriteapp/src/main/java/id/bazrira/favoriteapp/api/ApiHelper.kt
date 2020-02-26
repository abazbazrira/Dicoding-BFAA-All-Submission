package id.bazrira.favoriteapp.api

object ApiHelper {

    private val BASE_URL = "https://api.themoviedb.org/3/"

    val apiService: ApiService
        get() = ApiClient.getClient(BASE_URL)!!.create(ApiService::class.java)
}