package id.bazrira.madesubmission5.ui.viewmodel.favorite.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.bazrira.madesubmission5.db.persistance.tvshow.TvShowDbDAO

/**
 * Factory for ViewModels
 */
class TvShowViewModelFactory(private val dataSource: TvShowDbDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavTvShowViewModel::class.java)) {
            return FavTvShowViewModel(
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}