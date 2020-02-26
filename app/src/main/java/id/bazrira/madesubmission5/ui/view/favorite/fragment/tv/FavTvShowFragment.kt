package id.bazrira.madesubmission5.ui.view.favorite.fragment.tv


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.api.data.tv.TvShowsDAO
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.BACKDROP_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.CONTENT_URI_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.DESCRIPTION_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.POSTER_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.RATING_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.RELEASE_DATE_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.TITLE_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion._ID_TV
import id.bazrira.madesubmission5.ui.view.tv.adapter.TvShowAdapter
import id.bazrira.madesubmission5.ui.viewmodel.favorite.tv.FavTvShowViewModel
import id.bazrira.madesubmission5.ui.viewmodel.favorite.tv.TvShowViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_fav_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show.rv_tv_show
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException

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

        GlobalScope.launch {
            val cursor = requireContext().contentResolver?.query(
                CONTENT_URI_TV,
                null,
                null,
                null,
                null
            )

            cursor?.apply {
                val listItems = ArrayList<TvShowsDAO>()

                while (moveToNext()) {
                    try {
                        val data =
                            TvShowsDAO(
                                this.getInt(this.getColumnIndex(_ID_TV)),
                                this.getString(this.getColumnIndex(TITLE_TV)),
                                this.getDouble(this.getColumnIndex(RATING_TV)),
                                this.getString(this.getColumnIndex(RELEASE_DATE_TV)),
                                this.getString(this.getColumnIndex(POSTER_TV)),
                                this.getString(this.getColumnIndex(BACKDROP_TV)),
                                this.getString(this.getColumnIndex(DESCRIPTION_TV))
                            )

                        listItems.add(data)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } catch (e: ExecutionException) {
                        e.printStackTrace()
                    }
                }

                adapter.setData(listItems)
                showLoading(false)
            }
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
