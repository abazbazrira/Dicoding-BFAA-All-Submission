package id.bazrira.madesubmission5.db.persistance.movie

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.TABLE_NAME_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion._ID_MV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.TABLE_NAME_TV
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDbDAO {

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM $TABLE_NAME_MV WHERE $_ID_MV = :id")
    fun getMovieById(id: Int?): Flowable<Movie>

    @Query("SELECT * FROM $TABLE_NAME_MV")
    fun getMovies(): Flowable<List<Movie>>

    @Query("SELECT * FROM $TABLE_NAME_MV")
    fun getMovieCursor(): Cursor

    @Query("SELECT * FROM $TABLE_NAME_MV UNION SELECT * FROM $TABLE_NAME_TV")
    fun getAllFavorite(): Cursor

    @Query("SELECT * FROM $TABLE_NAME_MV WHERE $_ID_MV = :id")
    fun getMoviesByIdCursor(id: Int?): Cursor

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieCursor(movie: Movie): Long

    /**
     * Delete all users.
     */
    @Query("DELETE FROM $TABLE_NAME_MV WHERE $_ID_MV = :id")
    fun deleteMovie(id: Int?): Completable
}