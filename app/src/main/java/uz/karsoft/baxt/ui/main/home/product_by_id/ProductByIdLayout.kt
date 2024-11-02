package uz.karsoft.baxt.ui.main.home.product_by_id

import DatabaseHelper
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.MainActivity
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.cart.CartData
import uz.karsoft.baxt.data.models.main.home.detail.product.ProductData
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data.ProductByIdData
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData
import uz.karsoft.baxt.databinding.FragmentProductByIdLayoutBinding
import uz.karsoft.baxt.databinding.LayoutHomeBinding
import uz.karsoft.baxt.extensions.onClick
import uz.karsoft.baxt.ui.main.home.category.products.ProductsVM

class ProductByIdLayout : Fragment(R.layout.fragment_product_by_id_layout) {

    private val vm: ProductsVM by viewModel()
    private var productId: Int? = null
    private lateinit var binding: FragmentProductByIdLayoutBinding

    private var myQuantity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt("productId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductByIdLayoutBinding.bind(view)



        productId?.let { id ->
            vm.getProductsById(id)
            observeProduct()
        }
    }

    private fun observeProduct() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.productsState.collect { result ->
                when (result) {
                    is General.SuccessData<ProductByIdData> -> {
                        val products = result.data
                        showProductDetails(products)
                    }

                    else -> {

                    }
                }
            }
        }
    }

    private fun showProductDetails(products: ProductByIdData) {
        binding.apply {
           productName.text = products.data.name
            productMagazinName.text = products.data.market.name
            descriptionById.text = products.data.description
            shopNameById.text = products.data.market.name
            productQuantityById.text = products.data.quantity.toString()


            btnAddToCard.setOnClickListener{

                val dbHelper = DatabaseHelper(requireContext())

                val isCart = dbHelper.isProductINCart(products.data.id)
                if(!isCart){

                    val imageUrl = if (products.data.images.isNotEmpty()) products.data.images[0].url else ""
                    val priceProduct = if(products.data.stock.isNotEmpty()) products.data.stock[0].price else 0
                    var addToCart = CartData(
                        id= products.data.id,
                        category_id = products.data.category_id,
                        name = products.data.name,
                        image = imageUrl,
                        market_id = products.data.market.id,
                        market_name = products.data.market.name,
                        quantity = myQuantity,
                        price = priceProduct

                    )

                    dbHelper.addProductToCart(addToCart)

                    Toast.makeText(requireContext(), "Product kozinkag'a qosildi", Toast.LENGTH_SHORT).show()
                }



            }



            if (products.data.stock.isNotEmpty()) {
                val firstStock = products.data.stock[0]
                val price = firstStock.price
                binding.priceById.text = price.toString()
            } else {
                binding.priceById.text = "No stock available"
            }


            val images = products.data.images.map { it.url}

            val adapterImage = ImagePagerAdapter(images)
            binding.productImageViewPager.adapter = adapterImage


            btnQosiwById.setOnClickListener{
                if (myQuantity<products.data.quantity){
                    myQuantity++
                    schetchikById.text = myQuantity.toString()
                }
            }

            btnAliwById.setOnClickListener{
                if(myQuantity>0){
                    myQuantity--
                    schetchikById.text = myQuantity.toString()
                }
            }



        }
    }
}
