package id.bazrira.madesubmission4.viewmodel.fav.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.bazrira.madesubmission4.db.persistance.movie.MovieDbDAO

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