package id.bazrira.madesubmission2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_row_movie.view.*

class ListMovieAdapter(private val listMovie: ArrayList<Movie>) : RecyclerView.Adapter<ListMovieAdapter.ListViewHolder>() {


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                tv_title.text = movie.title
                tv_content_duration.text = movie.duration
                tv_content_genre.text = movie.genre
                Glide.with(itemView.context)
                    .load(movie.image)
                    .apply(RequestOptions().override(500, 750))
                    .into(iv_poster)

                btn_detail_movie.setOnClickListener {
                    val movie = Movie(
                        movie.title,
                        movie.duration,
                        movie.genre,
                        movie.image,
                        movie.desc
                    )

                    val moveWithObjectIntent = Intent(context, DetailMovieActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie)
                    context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

}