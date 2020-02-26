package id.bazrira.madesubmission1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_film.*

class DetailFilmActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILM = "extra_film"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        val film = intent.getParcelableExtra(EXTRA_FILM) as Film
        tv_detail_genre.text = film.genre
        tv_detail_duration.text = film.duration
        tv_detail_title.text = film.title
        tv_detail_desc.text = film.desc

        Glide.with(this)
            .load(film.image)
            .apply(RequestOptions().override(500, 750))
            .into(iv_poster)
    }
}
