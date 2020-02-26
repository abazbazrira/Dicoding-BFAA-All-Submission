package id.bazrira.madesubmission5.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeResponse(
    @SerializedName("air_date")
    @Expose
    val airDate: String? = null,
    @SerializedName("episode_number")
    @Expose
    val episodeNumber: Int? = null,
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("overview")
    @Expose
    val overview: String? = null,
    @SerializedName("production_code")
    @Expose
    val productionCode: String? = null,
    @SerializedName("season_number")
    @Expose
    val seasonNumber: Int? = null,
    @SerializedName("show_id")
    @Expose
    val showId: Int? = null,
    @SerializedName("still_path")
    @Expose
    val stillPath: String? = null,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null
) : Parcelable