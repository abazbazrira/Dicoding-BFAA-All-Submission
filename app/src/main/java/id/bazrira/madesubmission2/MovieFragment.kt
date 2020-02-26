package id.bazrira.madesubmission2


import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var dataTitle: Array<String>
    private lateinit var dataDuration: Array<String>
    private lateinit var dataGenre: Array<String>
    private lateinit var dataDesc: Array<String>
    private lateinit var dataImage: TypedArray

    private val list = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_movies.setHasFixedSize(true)

        list.addAll(getListMovies())
        showRecyclerList()
    }

    fun getListMovies(): ArrayList<Movie> {
        dataTitle = resources.getStringArray(R.array.data_title)
        dataDesc = resources.getStringArray(R.array.data_description)
        dataGenre = resources.getStringArray(R.array.data_genre)
        dataImage = resources.obtainTypedArray(R.array.data_image)
        dataDuration = resources.getStringArray(R.array.data_duration)

        val listMovie = ArrayList<Movie>()

        for (position in dataTitle.indices) {
            val movie = Movie(
                dataTitle[position],
                dataDuration[position],
                dataGenre[position],
                dataImage.getResourceId(position, -1),
                dataDesc[position]
            )
            listMovie.add(movie)
        }

        return listMovie
    }

    private fun showRecyclerList() {
        rv_movies.layoutManager = LinearLayoutManager(context)
        val listMovieAdapter = ListMovieAdapter(list)
        rv_movies.adapter = listMovieAdapter
    }

}
