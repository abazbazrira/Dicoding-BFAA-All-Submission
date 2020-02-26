package id.bazrira.madesubmission3.view.tv.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bazrira.madesubmission3.R
import id.bazrira.madesubmission3.data.tv.TvShowsDAO
import id.bazrira.madesubmission3.viewmodel.TvShowsViewModel
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    private val API_KEY = "b59545fa485ebb68339cb053f3164aac"

    private lateinit var mainViewModel: TvShowsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        supportActionBar?.title = getString(R.string.title_2)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvShowsViewModel::class.java)

        swlayout.setOnRefreshListener {
            setViewModel()
        }

        setViewModel()
    }

    private fun setViewModel() {
        val tv = intent.getParcelableExtra(EXTRA_TV) as TvShowsDAO

        val id = tv.id
        val url = "tv/$id?api_key=$API_KEY&language=en-En"

        showLoading(true)

        mainViewModel.setDetailTvShow(url)

        mainViewModel.getDetailTvShow().observe(this, Observer { items ->
            if (items != null) {
                tv_detail_rating.text = items.rating.toString()
                tv_detail_date.text = items.releaseDate
                tv_detail_title.text = items.title
                tv_detail_desc.text = items.desc

                Glide.with(this)
                    .load(items.poster)
                    .apply(RequestOptions().override(500, 750))
                    .into(iv_poster)

                Glide.with(this)
                    .load(items.backDrop)
                    .centerCrop()
                    .into(bg)

                showLoading(false)
            }
        })

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
