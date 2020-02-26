package id.bazrira.madesubmission2


import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tv_show.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var dataTitle: Array<String>
    private lateinit var dataDuration: Array<String>
    private lateinit var dataGenre: Array<String>
    private lateinit var dataDesc: Array<String>
    private lateinit var dataImage: TypedArray

    private val list = ArrayList<TvShows>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_tv_show.setHasFixedSize(true)

        list.addAll(getListTvShows())
        showRecyclerList()
    }

    fun getListTvShows(): ArrayList<TvShows> {
        dataTitle = resources.getStringArray(R.array.tv_title)
        dataDesc = resources.getStringArray(R.array.tv_desc)
        dataGenre = resources.getStringArray(R.array.tv_genre)
        dataImage = resources.obtainTypedArray(R.array.tv_image)
        dataDuration = resources.getStringArray(R.array.tv_duration)


        val listTvs = ArrayList<TvShows>()
        for (position in dataTitle.indices) {
            val tvShows = TvShows(
                dataTitle[position],
                dataDuration[position],
                dataGenre[position],
                dataImage.getResourceId(position, -1),
                dataDesc[position]
            )
            listTvs.add(tvShows)
        }

        return listTvs
    }

    private fun showRecyclerList() {
        rv_tv_show.layoutManager = LinearLayoutManager(context)
        val listTvAdapter = ListTvShowAdapter(list)
        rv_tv_show.adapter = listTvAdapter
    }
}
