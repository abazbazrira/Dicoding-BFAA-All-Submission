package id.bazrira.madesubmission1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    var title: String,
    var duration: String,
    var genre: String,
    var image: Int,
    var desc: String

) : Parcelable