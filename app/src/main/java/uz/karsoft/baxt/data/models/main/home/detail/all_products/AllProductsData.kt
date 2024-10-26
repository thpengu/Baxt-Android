package uz.karsoft.baxt.data.models.main.home.detail.all_products

data class AllProductsData(
    val `data`: List<AllData>,
    val links: AllLinks,
    val meta: AllMeta
)

data class AllData(
    val category_id: Int,
    val created_at: String,
    val discount_price: Int,
    val id: Int,
    val image_url: String,
    val name: String,
    val price: Int,
    val rating: Double,
    val review_count: Int,
    val updated_at: String
)

data class AllLinks(
    val first: Any,
    val last: Any,
    val next: Any,
    val prev: Any
)

data class AllMeta(
    val next_cursor: Any,
    val path: String,
    val per_page: Int,
    val prev_cursor: Any
)

