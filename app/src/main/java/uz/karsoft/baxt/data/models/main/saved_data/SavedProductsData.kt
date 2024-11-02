package uz.karsoft.baxt.data.models.main.saved_data

data class SavedProductsData(

    val category_id: Int,
    val created_at: String,
    val discount_price: Int,
    val id: Int ,
    val image_url: String? ,
    val name: String ,
    val price: Int ,
    val rating: Double,
    val review_count: Int,
    val updated_at: String
)
