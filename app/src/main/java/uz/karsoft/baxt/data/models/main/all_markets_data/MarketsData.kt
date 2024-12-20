package uz.karsoft.baxt.data.models.main.all_markets_data

data class MarketsData(
    val created_at: String,
    val description: String,
    val id: Int,
    val image: Image,
    val name: String,
    val order_count: Int,
    val rating: String,
    val review_count: Int
)