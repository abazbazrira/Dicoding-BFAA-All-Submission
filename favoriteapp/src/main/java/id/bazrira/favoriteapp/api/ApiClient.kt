package id.bazrira.favoriteapp.api

import java.util.concurrent.TimeUnit

import id.bazrira.favoriteapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

internal object ApiClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        val okhttpBuilder = OkHttpClient().newBuilder()
        okhttpBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okhttpBuilder.writeTimeout(60, TimeUnit.SECONDS)
        okhttpBuilder.readTimeout(60, TimeUnit.SECONDS)
        okhttpBuilder.retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okhttpBuilder.addInterceptor(interceptor)
        }

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okhttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        return retrofit
    }
}
