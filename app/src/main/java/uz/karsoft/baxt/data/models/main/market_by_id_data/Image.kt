package uz.karsoft.baxt.data.models.main.market_by_id_data

data class Image(
    val collection_name: String,
    val file_name: String,
    val id: Int,
    val mime_type: String,
    val name: String,
    val size: Int,
    val url: String
)