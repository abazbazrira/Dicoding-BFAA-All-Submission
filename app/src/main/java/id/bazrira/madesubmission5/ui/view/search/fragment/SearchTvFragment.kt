package id.bazrira.madesubmission5.ui.view.search.fragment


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.ui.view.tv.adapter.TvShowAdapter
import id.bazrira.madesubmission5.ui.viewmodel.TvShowsViewModel
import kotlinx.android.synthetic.main.fragment_search_tv.*

/**
 * A simple [Fragment] subclass.
 */
class SearchTvFragment : Fragment() {

    private val API_KEY = "b59545fa485ebb68339cb053f3164aac"

    private lateinit var mainViewModel: TvShowsViewModel
    private lateinit var adapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataName = SearchTvFragmentArgs.fromBundle(
            arguments as Bundle
        ).extraName

        sw_layout.setOnRefreshListener {
            setViewModel(dataName)
        }

        rv_tv_show.setHasFixedSize(true)

        adapter = TvShowAdapter()
        adapter.notifyDataSetChanged()

        rv_tv_show.layoutManager = LinearLayoutManager(context)
        rv_tv_show.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvShowsViewModel::class.java)

        setViewModel(dataName)
    }

    private fun setViewModel(sName: String) {
        val url = "search/tv?api_key=$API_KEY&language=en-En&query=$sName"

        showLoading(true)

        mainViewModel.setTvShow(url)

        mainViewModel.getTvShows().observe(viewLifecycleOwner, Observer { items ->
            if (items.size > 0) {
                adapter.setData(items)
            }

            showLoading(false)
        })

        if (sw_layout.isRefreshing && !isNetworkAvailable()) {
            Toast.makeText(context, getString(R.string.failed_to_load_data), Toast.LENGTH_SHORT)
                .show()
            showLoading(false)
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

}
