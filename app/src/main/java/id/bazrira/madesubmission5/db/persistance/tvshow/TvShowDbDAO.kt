package id.bazrira.madesubmission5.db.persistance.tvshow

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.TABLE_NAME_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion._ID_TV
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TvShowDbDAO {

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM $TABLE_NAME_TV WHERE $_ID_TV = :id")
    fun getTvShowById(id: Int?): Flowable<TvShow>

    @Query("SELECT * FROM $TABLE_NAME_TV")
    fun getTvShows(): Flowable<List<TvShow>>

    @Query("SELECT * FROM $TABLE_NAME_TV")
    fun getTvShowsCursor(): Cursor

    @Query("SELECT * FROM $TABLE_NAME_TV WHERE $_ID_TV = :id")
    fun getTvShowsByIdCursor(id: Int?): Cursor

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tv: TvShow): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowCursor(tv: TvShow): Long

    /**
     * Delete all users.
     */
    @Query("DELETE FROM $TABLE_NAME_TV WHERE $_ID_TV = :id")
    fun deleteTvShow(id: Int?): Completable
}