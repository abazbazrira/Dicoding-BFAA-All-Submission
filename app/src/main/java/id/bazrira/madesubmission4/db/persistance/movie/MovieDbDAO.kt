package id.bazrira.madesubmission4.db.persistance.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDbDAO {

    /**
     * Get a user by id.
     * @return the user from the table with a specific id.
     */
    @Query("SELECT * FROM Movies WHERE movieid = :id")
    fun getMovieById(id: Int?): Flowable<Movie>

    @Query("SELECT * FROM Movies")
    fun getMovies(): Flowable<List<Movie>>

    /**
     * Insert a user in the database. If the user already exists, replace it.
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie): Completable

    /**
     * Delete all users.
     */
    @Query("DELETE FROM Movies WHERE movieId = :id")
    fun deleteMovie(id: Int?): Completable
}