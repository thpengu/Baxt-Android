package uz.karsoft.baxt.data.models.main.home.detail.product

data class ProductData(
    val category_id: Int,
    val collection_id: Int,
    val created_at: String,
    val id: Int,
    val images: List<Any>,
    val is_negotiable: Boolean,
    val name: String,
    val price: Int,
    val user: User
)