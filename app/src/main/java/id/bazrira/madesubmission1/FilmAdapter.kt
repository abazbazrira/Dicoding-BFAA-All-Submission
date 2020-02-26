package id.bazrira.madesubmission1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_film.view.*

class FilmAdapter internal constructor(private val context: Context) : BaseAdapter() {

    internal var films = arrayListOf<Film>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_film, parent, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val film = getItem(position) as Film
        viewHolder.bind(film)

        return itemView
    }

    private inner class ViewHolder constructor(private val view: View) {
        fun bind(film: Film) {
            with(view) {
                tv_title.text = film.title
                tv_content_duration.text = film.duration
                tv_content_genre.text = film.genre
                Glide.with(view.context)
                    .load(film.image)
                    .apply(RequestOptions().override(500, 750))
                    .into(iv_poster)

                btn_detail_ticket.setOnClickListener {
                    val film = Film(
                        film.title,
                        film.duration,
                        film.genre,
                        film.image,
                        film.desc
                    )

                    val moveWithObjectIntent = Intent(context, DetailFilmActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailFilmActivity.EXTRA_FILM, film)
                    context.startActivity(moveWithObjectIntent)

                }
            }
        }
    }


    override fun getItem(position: Int): Any = films[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = films.size
}