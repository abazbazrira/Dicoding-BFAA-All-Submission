package id.bazrira.madesubmission3.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeasonResponse(
    @SerializedName("air_date")
    @Expose
    val airDate: String? = null,
    @SerializedName("episode_count")
    @Expose
    val episodeCount: Int? = null,
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("overview")
    @Expose
    val overview: String? = null,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,
    @SerializedName("season_number")
    @Expose
    val seasonNumber: Int? = null
) : Parcelable