package id.bazrira.madesubmission5.db.persistance.tvshow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.BACKDROP_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.DESCRIPTION_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.POSTER_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.RATING_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.RELEASE_DATE_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.TABLE_NAME_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion.TITLE_TV
import id.bazrira.madesubmission5.db.persistance.tvshow.Injection.Injection.Companion._ID_TV

@Entity(tableName = TABLE_NAME_TV)
data class TvShow(
    @PrimaryKey
    @ColumnInfo(name = _ID_TV)
    val id: Int?,
    @ColumnInfo(name = TITLE_TV)
    val title: String?,
    @ColumnInfo(name = RATING_TV)
    val rating: Double?,
    @ColumnInfo(name = RELEASE_DATE_TV)
    val releaseDate: String?,
    @ColumnInfo(name = POSTER_TV)
    val poster: String?,
    @ColumnInfo(name = BACKDROP_TV)
    val backDrop: String?,
    @ColumnInfo(name = DESCRIPTION_TV)
    val desc: String?
)