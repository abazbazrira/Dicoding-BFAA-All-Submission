package id.bazrira.madesubmission5.db.persistance.movie

import android.content.Context
import android.net.Uri
import id.bazrira.madesubmission5.db.DatabaseInterface
import id.bazrira.madesubmission5.ui.viewmodel.favorite.movie.FavMovieViewModelFactory

/**
 * Enables injection of data sources.
 */
object Injection {

    const val AUTHORITY = "id.bazrira.madesubmission5"
    const val SCHEME = "content"

    class Injection {
        companion object {
            const val TABLE_NAME_MV = "movies"
            const val _ID_MV = "movieid"
            const val TITLE_MV = "title"
            const val RELEASE_DATE_MV = "releasedate"
            const val POSTER_MV = "poster"
            const val BACKDROP_MV = "backdrop"
            const val DESCRIPTION_MV = "desc"
            const val RATING_MV = "rating"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath("favorite")
                .build()

            val CONTENT_URI_MV: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath("favorite")
                .appendPath(TABLE_NAME_MV)
                .build()

        }
    }

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