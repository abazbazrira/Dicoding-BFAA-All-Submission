package id.bazrira.favoriteapp.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTvShowResponse(
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,
    @SerializedName("created_by")
    @Expose
    val createdBy: List<ListCreatedByResponse>? = null,
    @SerializedName("episode_run_time")
    @Expose
    val episodeRunTime: List<Int>? = null,
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String? = null,
    @SerializedName("genres")
    @Expose
    val genres: List<ListGenreResponse>? = null,
    @SerializedName("homepage")
    @Expose
    val homepage: String? = null,
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("in_production")
    @Expose
    val inProduction: Boolean? = null,
    @SerializedName("languages")
    @Expose
    val languages: List<String>? = null,
    @SerializedName("last_air_date")
    @Expose
    val lastAirDate: String? = null,
    @SerializedName("last_episode_to_air")
    @Expose
    val lastEpisodeToAir: EpisodeResponse? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("next_episode_to_air")
    @Expose
    val nextEpisodeToAir: EpisodeResponse? = null,
    @SerializedName("networks")
    @Expose
    val networks: List<NetworkResponse>? = null,
    @SerializedName("number_of_episodes")
    @Expose
    val numberOfEpisodes: Int? = null,
    @SerializedName("number_of_seasons")
    @Expose
    val numberOfSeasons: Int? = null,
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String>? = null,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,
    @SerializedName("original_name")
    @Expose
    val originalName: String? = null,
    @SerializedName("overview")
    @Expose
    val overview: String? = null,
    @SerializedName("popularity")
    @Expose
    val popularity: Double? = null,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,
    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ListProductionCompanyResponse>? = null,
    @SerializedName("seasons")
    @Expose
    val seasons: List<SeasonResponse>? = null,
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null
) : Parcelable