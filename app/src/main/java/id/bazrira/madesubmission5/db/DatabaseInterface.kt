package id.bazrira.madesubmission5.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import id.bazrira.madesubmission5.db.persistance.movie.Movie
import id.bazrira.madesubmission5.db.persistance.movie.MovieDbDAO
import id.bazrira.madesubmission5.db.persistance.tvshow.TvShow
import id.bazrira.madesubmission5.db.persistance.tvshow.TvShowDbDAO

/**
 * The Room database that contains the Users table
 */
@Database(entities = [Movie::class, TvShow::class], version = 2)
abstract class DatabaseInterface : RoomDatabase() {

    abstract fun movieDao(): MovieDbDAO
    abstract fun tvShowDao(): TvShowDbDAO

    companion object {

        @Volatile private var INSTANCE: DatabaseInterface? = null

        fun getInstance(context: Context): DatabaseInterface =
            INSTANCE
                ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                DatabaseInterface::class.java, "Movie.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}