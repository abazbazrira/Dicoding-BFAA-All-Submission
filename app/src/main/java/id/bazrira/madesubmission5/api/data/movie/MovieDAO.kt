package id.bazrira.madesubmission5.api.data.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDAO(
    var id: Int?,
    var title: String?,
    var rating: Double?,
    var releaseDate: String?,
    var poster: String?,
    var backDrop: String?,
    var desc: String?
) : Parcelable