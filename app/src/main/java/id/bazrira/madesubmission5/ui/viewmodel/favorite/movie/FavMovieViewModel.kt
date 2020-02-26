package id.bazrira.madesubmission5.ui.viewmodel.favorite.movie

import androidx.lifecycle.ViewModel
import id.bazrira.madesubmission5.api.data.movie.MovieDAO
import id.bazrira.madesubmission5.db.persistance.movie.Movie
import id.bazrira.madesubmission5.db.persistance.movie.MovieDbDAO
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * View Model for the [FavMovieFragment]
 */
class FavMovieViewModel(private val dataSource: MovieDbDAO) : ViewModel() {

    /**
     * Get the user name of the user.
     * @return a [Flowable] that will emit every time the user name has been updated.
     */
    // for every emission of the user, get the user name
    fun detailMovie(movieId: Int?): Flowable<ArrayList<MovieDAO>> {
        return dataSource.getMovieById(movieId)
            .map {
                arrayListOf(
                    MovieDAO(
                        it.id,
                        it.title,
                        it.rating,
                        it.releaseDate,
                        it.poster,
                        it.backDrop,
                        it.desc
                    )
                )
            }
    }

    /**
     * Update the user name.
     * @param userName the new user name
     * *
     * @return a [Completable] that completes when the user name is updated
     */
    fun updateMovie(movie: MovieDAO): Completable {
        val m = Movie(
            movie.id,
            movie.title,
            movie.rating,
            movie.releaseDate,
            movie.poster,
            movie.backDrop,
            movie.desc
        )

        return dataSource.insertMovie(m)
    }

    fun deleteMovie(id: Int?): Completable {
        return dataSource.deleteMovie(id)
    }

    fun getMovies(): Flowable<List<Movie>> {
        return dataSource.getMovies()
    }
}
