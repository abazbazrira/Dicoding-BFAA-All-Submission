package id.bazrira.madesubmission2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShows(
    var title: String,
    var duration: String,
    var genre: String,
    var image: Int,
    var desc: String
) : Parcelable