package id.bazrira.madesubmission4.db.persistance.movie

import android.content.Context
import id.bazrira.madesubmission4.db.DatabaseInterface
import id.bazrira.madesubmission4.viewmodel.fav.movie.FavMovieViewModelFactory

/**
 * Enables injection of data sources.
 */
object Injection {

    private fun provideUserDataSource(context: Context): MovieDbDAO {
        val database =
            DatabaseInterface.getInstance(
                context
            )
        return database.movieDao()
    }

    fun provideViewModelFactory(context: Context): FavMovieViewModelFactory {
        val dataSource =
            provideUserDataSource(
                context
            )
        return FavMovieViewModelFactory(
            dataSource
        )
    }
}