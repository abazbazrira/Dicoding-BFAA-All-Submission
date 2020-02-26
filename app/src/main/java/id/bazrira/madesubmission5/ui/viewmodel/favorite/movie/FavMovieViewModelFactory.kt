package id.bazrira.madesubmission5.ui.viewmodel.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.bazrira.madesubmission5.db.persistance.movie.MovieDbDAO

/**
 * Factory for ViewModels
 */
class FavMovieViewModelFactory(private val dataSource: MovieDbDAO) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavMovieViewModel::class.java)) {
            return FavMovieViewModel(
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}