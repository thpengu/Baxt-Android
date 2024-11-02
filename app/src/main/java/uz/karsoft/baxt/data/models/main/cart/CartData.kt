package uz.karsoft.baxt.data.models.main.cart

data class CartData(
    val id: Int,
    val category_id: Int,
    val name : String,
    val image : String,
    val market_id: Int,
    val market_name : String,
    val quantity : Int,
    val price : Int

)
