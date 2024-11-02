package uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data


data class ProductByIdData(
    val `data`: ByIdData
)
data class Market(
    val id: Int,
    val name: String
)

data class ByIdData(
    val category_id: Int,
    val created_at: String,
    val description: String,
    val id: Int,
    val images: List<Images>,
    val market: Market,
    val name: String,
    val quantity: Int,
    val rating: Float,
    val review_count: Int,
    val stock: List<Stock>,
    val updated_at: String
)

data class Images(
    val id : Int,
    val collection_name : String,
    val name : String,
    val file_name : String,
    val mime_type : String,
    val size : Int,
    val url : String
)

data class Stock(
    val id : Int,
    val price : Int,
    val quantity : Int,
    val цвет :String,
    val размер : String
)
