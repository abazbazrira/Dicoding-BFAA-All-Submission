package id.bazrira.favoriteapp.db.persistance.tvshow

import android.net.Uri

/**
 * Enables injection of data sources.
 */
object Injection {

    const val AUTHORITY = "id.bazrira.madesubmission5"
    const val SCHEME = "content"

    class Injection {
        companion object {
            const val TABLE_NAME_TV = "tvshow"
            const val _ID_TV = "tvid"
            const val TITLE_TV = "title"
            const val RELEASE_DATE_TV = "releasedate"
            const val POSTER_TV = "poster"
            const val BACKDROP_TV = "backdrop"
            const val DESCRIPTION_TV = "desc"
            const val RATING_TV = "rating"

            val CONTENT_URI_TV: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath("favorite")
                .appendPath(TABLE_NAME_TV)
                .build()

        }
    }
}