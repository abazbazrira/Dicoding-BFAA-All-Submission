package id.bazrira.madesubmission4.db.persistance.tvshow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TvShowDbDAO {

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM TvShows WHERE tvid = :id")
    fun getTvShowById(id: Int?): Flowable<TvShow>

    @Query("SELECT * FROM TvShows")
    fun getTvShows(): Flowable<List<TvShow>>

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tv: TvShow): Completable

    /**
     * Delete all users.
     */
    @Query("DELETE FROM TvShows WHERE tvid = :id")
    fun deleteTvShow(id: Int?): Completable
}