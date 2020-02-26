package id.bazrira.madesubmission5.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListCreatedByResponse(
    @SerializedName("id")
    @Expose
    private val id: Int? = null,
    @SerializedName("credit_id")
    @Expose
    private val creditId: String? = null,
    @SerializedName("name")
    @Expose
    private val name: String? = null,
    @SerializedName("profile_path")
    @Expose
    private val profilePath: String? = null
) : Parcelable
