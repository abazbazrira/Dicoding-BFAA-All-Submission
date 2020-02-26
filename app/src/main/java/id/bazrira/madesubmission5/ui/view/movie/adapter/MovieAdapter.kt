package id.bazrira.madesubmission5.ui.view.movie.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.api.data.movie.MovieDAO
import id.bazrira.madesubmission5.ui.view.movie.activity.DetailMovieActivity
import kotlinx.android.synthetic.main.item_row_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val mData = ArrayList<MovieDAO>()

    fun setData(items: ArrayList<MovieDAO>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return MovieViewHolder(mView)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieDAO: MovieDAO) {
            with(itemView) {

                tv_title_m.text = movieDAO.title
                tv_content_release_date_m.text = movieDAO.releaseDate
                tv_content_rating_m.text = movieDAO.rating.toString()

                Glide.with(itemView.context)
                    .load(movieDAO.poster)
                    .apply(RequestOptions().override(500, 750))
                    .into(iv_poster_m)

                btn_detail_movie_m.setOnClickListener {
                    val movie =
                        MovieDAO(
                            movieDAO.id,
                            movieDAO.title,
                            movieDAO.rating,
                            movieDAO.releaseDate,
                            movieDAO.poster,
                            movieDAO.backDrop,
                            movieDAO.desc
                        )

                    val moveWithObjectIntent = Intent(context, DetailMovieActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie)
                    context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(mData[position])
    }
}