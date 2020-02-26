package id.bazrira.madesubmission2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_row_movie.view.*

class ListTvShowAdapter(private val listTvShows: ArrayList<TvShows>) : RecyclerView.Adapter<ListTvShowAdapter.ListViewHolder>() {


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShows: TvShows) {
            with(itemView) {
                tv_title.text = tvShows.title
                tv_content_duration.text = tvShows.duration
                tv_content_genre.text = tvShows.genre
                Glide.with(itemView.context)
                    .load(tvShows.image)
                    .apply(RequestOptions().override(500, 750))
                    .into(iv_poster)

                btn_detail_movie.setOnClickListener {
                    val tv = TvShows(
                        tvShows.title,
                        tvShows.duration,
                        tvShows.genre,
                        tvShows.image,
                        tvShows.desc
                    )

                    val moveWithObjectIntent = Intent(context, DetailTvShowActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailTvShowActivity.EXTRA_TV, tv)
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

    override fun getItemCount(): Int = listTvShows.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listTvShows[position])
    }

}