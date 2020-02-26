package id.bazrira.madesubmission2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.activity_detail_movie.bg
import kotlinx.android.synthetic.main.activity_detail_movie.iv_poster
import kotlinx.android.synthetic.main.activity_detail_movie.tv_detail_desc
import kotlinx.android.synthetic.main.activity_detail_movie.tv_detail_duration
import kotlinx.android.synthetic.main.activity_detail_movie.tv_detail_genre
import kotlinx.android.synthetic.main.activity_detail_movie.tv_detail_title
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)

        val tv = intent.getParcelableExtra(EXTRA_TV) as TvShows
        tv_detail_genre.text = tv.genre
        tv_detail_duration.text = tv.duration
        tv_detail_title.text = tv.title
        tv_detail_desc.text = tv.desc

        Glide.with(this)
            .load(tv.image)
            .apply(RequestOptions().override(500, 750))
            .into(iv_poster)

        Glide.with(this)
            .load(tv.image)
            .centerCrop()
            .into(bg)

        supportActionBar?.title = getString(R.string.title_2)
    }
}
