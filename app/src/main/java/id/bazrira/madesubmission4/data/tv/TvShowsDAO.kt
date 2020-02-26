package id.bazrira.madesubmission4.data.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShowsDAO(
    var id: Int?,
    var title: String?,
    var rating: Double?,
    var releaseDate: String?,
    var poster: String?,
    var backDrop: String?,
    var desc: String?
) : Parcelable