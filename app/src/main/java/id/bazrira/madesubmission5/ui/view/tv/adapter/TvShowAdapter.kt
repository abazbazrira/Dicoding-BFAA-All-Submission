package id.bazrira.madesubmission5.ui.view.tv.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.api.data.tv.TvShowsDAO
import id.bazrira.madesubmission5.ui.view.tv.activity.DetailTvShowActivity
import kotlinx.android.synthetic.main.item_row_tv.view.*

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowsViewHolder>() {

    private val mData = ArrayList<TvShowsDAO>()

    fun setData(items: ArrayList<TvShowsDAO>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowsViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_tv, parent, false)
        return TvShowsViewHolder(mView)
    }

    inner class TvShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvDAO: TvShowsDAO) {
            with(itemView) {

                tv_title_t.text = tvDAO.title
                tv_content_release_date_t.text = tvDAO.releaseDate
                tv_content_rating_t.text = tvDAO.rating.toString()

                Glide.with(itemView.context)
                    .load(tvDAO.poster)
                    .apply(RequestOptions().override(500, 750))
                    .into(iv_poster_t)

                btn_detail_movie_t.setOnClickListener {
                    val tv = TvShowsDAO(
                        tvDAO.id,
                        tvDAO.title,
                        tvDAO.rating,
                        tvDAO.releaseDate,
                        tvDAO.poster,
                        tvDAO.backDrop,
                        tvDAO.desc
                    )

                    val moveWithObjectIntent = Intent(context, DetailTvShowActivity::class.java)
                    moveWithObjectIntent.putExtra(DetailTvShowActivity.EXTRA_TV, tv)
                    context.startActivity(moveWithObjectIntent)
                }
            }
        }
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        holder.bind(mData[position])
    }

}