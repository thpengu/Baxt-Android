package uz.karsoft.baxt.data.models.main.saved_data

data class SavedProductsData(

    val category_id: Int=0,
    val created_at: String = "",
    val discount_price: Int = 0,
    val id: Int = 0,
    val image_url: String? = "",
    val name: String = "",
    val price: Int = 0,
    val rating: Double = 0.0,
    val review_count: Int = 0,
    val updated_at: String = ""
)
