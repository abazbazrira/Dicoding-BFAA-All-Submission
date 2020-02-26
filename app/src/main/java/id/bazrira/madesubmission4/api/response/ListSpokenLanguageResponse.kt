package id.bazrira.madesubmission4.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListSpokenLanguageResponse(
    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null
) : Parcelable
