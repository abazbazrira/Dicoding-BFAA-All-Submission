package id.bazrira.madesubmission4.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailMovieResponse(
    @SerializedName("adult")
    @Expose
    val adult: Boolean? = null,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    @Expose
    val belongsToCollection: CollectionMovieResponse? = null,
    @SerializedName("budget")
    @Expose
    val budget: Int? = null,
    @SerializedName("listGenreResponses")
    @Expose
    val listGenreResponses: List<ListGenreResponse>? = null,
    @SerializedName("homepage")
    @Expose
    val homepage: String? = null,
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("imdb_id")
    @Expose
    val imdbId: String? = null,
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    @Expose
    val originalTitle: String? = null,
    @SerializedName("overview")
    @Expose
    val overview: String? = null,
    @SerializedName("popularity")
    @Expose
    val popularity: Double? = null,
    @SerializedName("poster_path")
    @Expose val posterPath: String? = null,
    @SerializedName("production_companies")
    @Expose
    val listProductionCompanyResponses: List<ListProductionCompanyResponse>? = null,
    @SerializedName("production_countries")
    @Expose
    val listProductionCountryResponses: List<ListProductionCountryResponse>? = null,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String? = null,
    @SerializedName("revenue")
    @Expose
    val revenue: Int? = null,
    @SerializedName("runtime")
    @Expose
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    @Expose
    val listSpokenLanguageResponses: List<ListSpokenLanguageResponse>? = null,
    @SerializedName("status")
    @Expose
    val status: String? = null,
    @SerializedName("tagline")
    @Expose
    val tagline: String? = null,
    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("video")
    @Expose
    val video: Boolean? = null,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Int? = null
) : Parcelable
