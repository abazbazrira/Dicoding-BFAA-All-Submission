package id.bazrira.madesubmission4.db.persistance.tvshow

import android.content.Context
import id.bazrira.madesubmission4.db.DatabaseInterface
import id.bazrira.madesubmission4.viewmodel.fav.tv.TvShowViewModelFactory

/**
 * Enables injection of data sources.
 */
object Injection {

    fun provideUserDataSource(context: Context): TvShowDbDAO {
        val database =
            DatabaseInterface.getInstance(
                context
            )
        return database.tvShowDao()
    }

    fun provideViewModelFactory(context: Context): TvShowViewModelFactory {
        val dataSource =
            provideUserDataSource(
                context
            )
        return TvShowViewModelFactory(
            dataSource
        )
    }
}