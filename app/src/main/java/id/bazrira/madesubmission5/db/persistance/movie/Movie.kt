package id.bazrira.madesubmission5.db.persistance.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.BACKDROP_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.DESCRIPTION_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.POSTER_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.RATING_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.RELEASE_DATE_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.TABLE_NAME_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion.TITLE_MV
import id.bazrira.madesubmission5.db.persistance.movie.Injection.Injection.Companion._ID_MV

@Entity(tableName = TABLE_NAME_MV)
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = _ID_MV)
    val id: Int?,
    @ColumnInfo(name = TITLE_MV)
    val title: String?,
    @ColumnInfo(name = RATING_MV)
    val rating: Double?,
    @ColumnInfo(name = RELEASE_DATE_MV)
    val releaseDate: String?,
    @ColumnInfo(name = POSTER_MV)
    val poster: String?,
    @ColumnInfo(name = BACKDROP_MV)
    val backDrop: String?,
    @ColumnInfo(name = DESCRIPTION_MV)
    val desc: String?
)