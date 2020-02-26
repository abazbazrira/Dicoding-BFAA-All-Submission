package id.bazrira.madesubmission4.db.persistance.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movieid")
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "rating")
    val rating: Double?,
    @ColumnInfo(name = "releasedate")
    val releaseDate: String?,
    @ColumnInfo(name = "poster")
    val poster: String?,
    @ColumnInfo(name = "backdrop")
    val backDrop: String?,
    @ColumnInfo(name = "desc")
    val desc: String?
)