package id.bazrira.madesubmission4.view.favorite.fragment.tv


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.EmptyResultSetException
import id.bazrira.madesubmission4.R
import id.bazrira.madesubmission4.data.tv.TvShowsDAO
import id.bazrira.madesubmission4.db.persistance.tvshow.Injection
import id.bazrira.madesubmission4.view.tv.adapter.TvShowAdapter
import id.bazrira.madesubmission4.viewmodel.fav.tv.FavTvShowViewModel
import id.bazrira.madesubmission4.viewmodel.fav.tv.TvShowViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_fav_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show.rv_tv_show

/**
 * A simple [Fragment] subclass.
 */
class FavTvShowFragment : Fragment() {

    private lateinit var adapter: TvShowAdapter

    private lateinit var favTvShowViewModelFactory: TvShowViewModelFactory
    private val viewModel: FavTvShowViewModel by viewModels { favTvShowViewModelFactory }
    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favTvShowViewModelFactory = Injection.provideViewModelFactory(requireContext())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_tv_show.setHasFixedSize(true)

        adapter = TvShowAdapter()
        adapter.notifyDataSetChanged()

        rv_tv_show.layoutManager = LinearLayoutManager(context)
        rv_tv_show.adapter = adapter

        setViewModel()

        sw_layout.setOnRefreshListener {
            setViewModel()
        }
    }

    override fun onStart() {
        super.onStart()

        setViewModel()
    }

    private fun setViewModel() {
        showLoading(true)

        try {
            disposable.add(
                viewModel.getTvShows()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            val listItems = ArrayList<TvShowsDAO>()

                            for (t in it) {
                                val data = TvShowsDAO(
                                    t.id,
                                    t.title,
                                    t.rating,
                                    t.releaseDate,
                                    t.poster,
                                    t.backDrop,
                                    t.desc
                                )

                                listItems.add(data)
                            }
                            adapter.setData(listItems)
                            showLoading(false)
                        },
                        { error ->
                            Log.e("Error DB", "Unable to get movie", error)
                        }
                    )
            )
        } catch (e: EmptyResultSetException) {
            Log.e("Error DB", e.toString())
        }

        if (sw_layout.isRefreshing && !isNetworkAvailable()) {
            showLoading(false)
            Toast.makeText(context, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun showLoading(state: Boolean) {
        sw_layout.isRefreshing = state
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onStop() {
        super.onStop()

        // clear all the subscription
        disposable.clear()
    }
}
