package uz.karsoft.baxt.data.models.main.foods

data class FoodData(
    val category_id: Int,
    val created_at: String,
    val id: Int,
    val image_url: Any,
    val name: String,
    val price: Int,
    val updated_at: String
)