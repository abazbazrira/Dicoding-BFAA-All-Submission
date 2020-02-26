package id.bazrira.madesubmission1

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_film.*

class MainActivity : AppCompatActivity(){

    private lateinit var adapter: FilmAdapter

    private lateinit var dataTitle: Array<String>
    private lateinit var dataDuration: Array<String>
    private lateinit var dataGenre: Array<String>
    private lateinit var dataDesc: Array<String>
    private lateinit var dataImage: TypedArray

    private var films = arrayListOf<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = FilmAdapter(this)
        lv_list_film.adapter = adapter

        prepare()
        addItem()
    }

    private fun prepare() {
        dataTitle = resources.getStringArray(R.array.data_title)
        dataDuration = resources.getStringArray(R.array.data_duration)
        dataGenre = resources.getStringArray(R.array.data_genre)
        dataDesc = resources.getStringArray(R.array.data_description)
        dataImage = resources.obtainTypedArray(R.array.data_image)
    }

    private fun addItem() {
        for (position in dataTitle.indices) {
            val film = Film(
                dataTitle[position],
                dataDuration[position],
                dataGenre[position],
                dataImage.getResourceId(position, -1),
                dataDesc[position]
            )
            films.add(film)
        }
        adapter.films = films
    }
}
