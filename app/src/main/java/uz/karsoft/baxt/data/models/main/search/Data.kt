package uz.karsoft.baxt.data.models.main.search

import com.google.gson.annotations.SerializedName
import uz.karsoft.baxt.data.models.main.home.Name

data class Data(
    val children: List<Children>,
    val id: Int,
    val name: Name,
    @SerializedName("collection_id") val collectionId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)