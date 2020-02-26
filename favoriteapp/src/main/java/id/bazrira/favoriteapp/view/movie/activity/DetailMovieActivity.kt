package id.bazrira.favoriteapp.view.movie.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bazrira.favoriteapp.R
import id.bazrira.favoriteapp.data.movie.MovieDAO
import kotlinx.android.synthetic.main.activity_detail_movie.*


class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        supportActionBar?.title = getString(R.string.title_1)

        swlayout.setOnRefreshListener {
            setViewModel()
        }

        setViewModel()
    }

    private fun setViewModel() {
        val mv = intent.getParcelableExtra(EXTRA_MOVIE) as MovieDAO

        showLoading(true)

        tv_detail_rating.text = mv.rating.toString()
        tv_detail_date.text = mv.releaseDate
        tv_detail_title.text = mv.title
        tv_detail_desc.text = mv.desc

        Glide.with(this)
            .load(mv.poster)
            .apply(RequestOptions().override(500, 750))
            .into(iv_poster)

        Glide.with(this)
            .load(mv.backDrop)
            .centerCrop()
            .into(bg)

        showLoading(false)

        if (swlayout.isRefreshing && !isNetworkAvailable()) {
            showLoading(false)
            Toast.makeText(this, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(state: Boolean) {
        swlayout.isRefreshing = state
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
