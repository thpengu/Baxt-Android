package uz.karsoft.baxt.data.models.main.home.detail.product

data class Products(
    val data : List<ProductData> = listOf(),
    val links: Links,
    val meta: Meta
)