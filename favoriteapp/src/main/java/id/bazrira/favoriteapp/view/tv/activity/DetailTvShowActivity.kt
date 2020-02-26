package id.bazrira.favoriteapp.view.tv.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bazrira.favoriteapp.R
import id.bazrira.favoriteapp.data.tv.TvShowsDAO
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        supportActionBar?.title = getString(R.string.title_2)

        swlayout.setOnRefreshListener {
            setViewModel()
        }

        setViewModel()
    }

    private fun setViewModel() {
        val tv = intent.getParcelableExtra(EXTRA_TV) as TvShowsDAO

        showLoading(true)

        tv_detail_rating.text = tv.rating.toString()
        tv_detail_date.text = tv.releaseDate
        tv_detail_title.text = tv.title
        tv_detail_desc.text = tv.desc

        Glide.with(this)
            .load(tv.poster)
            .apply(RequestOptions().override(500, 750))
            .into(iv_poster)

        Glide.with(this)
            .load(tv.backDrop)
            .centerCrop()
            .into(bg)

        showLoading(false)

        if (swlayout.isRefreshing && !isNetworkAvailable()) {
            Toast.makeText(this, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT).show()
            showLoading(false)
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
