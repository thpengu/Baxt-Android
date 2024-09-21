package uz.karsoft.baxt.data.models.main.home

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("category_count")
    val categoryCount: Int,
    val icon: String,
    @SerializedName("icon_url")
    val iconUrl: String,
    val id: Int,
    val name: Name
)