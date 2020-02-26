package id.bazrira.madesubmission4.view.favorite.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.bazrira.madesubmission4.R
import id.bazrira.madesubmission4.view.favorite.adapter.SectionsPagerAdapter
import id.bazrira.madesubmission4.view.favorite.fragment.movie.FavMovieFragment
import id.bazrira.madesubmission4.view.favorite.fragment.tv.FavTvShowFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                childFragmentManager
            )
        sectionsPagerAdapter.addFragment(FavMovieFragment(), getString(R.string.tab_text_1))
        sectionsPagerAdapter.addFragment(FavTvShowFragment(), getString(R.string.tab_text_2))

        v_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(v_pager)
    }
}
