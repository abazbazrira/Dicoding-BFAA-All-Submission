package id.bazrira.madesubmission5.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import id.bazrira.madesubmission5.db.DatabaseInterface
import id.bazrira.madesubmission5.db.persistance.movie.Injection.AUTHORITY
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.BACKDROP_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.CONTENT_URI
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.DESCRIPTION_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.POSTER_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.RATING_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.RELEASE_DATE_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.TABLE_NAME_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.TITLE_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion._ID_MV
import id.bazrira.madesubmission5.db.persistance.movie.Movie
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.TABLE_NAME_TV


class FavoriteProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private const val TV_SHOW = 3
        private const val TV_SHOW_ID = 4
        private const val ALL_FAVORITE = 5
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var dbInterface: DatabaseInterface

        init {
            sUriMatcher.addURI(AUTHORITY, "favorite/$TABLE_NAME_MV", MOVIE)
            sUriMatcher.addURI(
                AUTHORITY,
                "favorite/$TABLE_NAME_MV/#",
                MOVIE_ID
            )

            sUriMatcher.addURI(AUTHORITY, "favorite/$TABLE_NAME_TV", TV_SHOW)
            sUriMatcher.addURI(
                AUTHORITY,
                "favorite/$TABLE_NAME_TV/#",
                TV_SHOW_ID
            )

            sUriMatcher.addURI(AUTHORITY, "favorite", ALL_FAVORITE)
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        dbInterface = this.context?.let { DatabaseInterface.getInstance(it) }!!
        return when (sUriMatcher.match(uri)) {
            ALL_FAVORITE -> dbInterface.movieDao().getAllFavorite()
            MOVIE -> dbInterface.movieDao().getMovieCursor()
            MOVIE_ID -> dbInterface.movieDao().getMoviesByIdCursor(uri.lastPathSegment?.toInt())
            TV_SHOW -> dbInterface.tvShowDao().getTvShowsCursor()
            TV_SHOW_ID -> dbInterface.tvShowDao().getTvShowsByIdCursor(uri.lastPathSegment?.toInt())
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (MOVIE) {
            sUriMatcher.match(uri) -> {
                val m = Movie(
                    values?.get(_ID_MV) as Int?,
                    values?.get(TITLE_MV) as String?,
                    values?.get(RATING_MV) as Double?,
                    values?.get(RELEASE_DATE_MV) as String?,
                    values?.get(POSTER_MV) as String?,
                    values?.get(BACKDROP_MV) as String?,
                    values?.get(DESCRIPTION_MV) as String?
                )
                dbInterface.movieDao().insertMovieCursor(m)
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}
