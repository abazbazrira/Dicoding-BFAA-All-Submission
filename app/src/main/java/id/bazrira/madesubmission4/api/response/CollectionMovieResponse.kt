package id.bazrira.madesubmission4.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionMovieResponse (
    @SerializedName("id")
    @Expose
    private val id: Int? = null,
    @SerializedName("name")
    @Expose
    private val name: String? = null,
    @SerializedName("poster_path")
    @Expose
    private val posterPath: String? = null,
    @SerializedName("backdrop_path")
    @Expose
    private val backdropPath: String? = null
) : Parcelable