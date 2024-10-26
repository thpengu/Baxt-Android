package uz.karsoft.baxt.ui.main.home.category.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllProductsData
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data.ProductByIdData
import uz.karsoft.baxt.repo.MainRepository

class ProductsVM(private val repository: MainRepository): ViewModel() {

    private val _products = MutableStateFlow<General<ProductByIdData>>(General.Empty)
    val productsState: StateFlow<General<ProductByIdData>> = _products

    fun getProductsById(id: Int) {
        _products.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProductsById(id).collect { result ->
                _products.value = result
                Log.d("ASD", "getProductsById viewmodel:--->$id ")
            }
        }
    }

    private val _allProducts = MutableStateFlow<General<AllProductsData>>(General.Empty)
    val allProductsState: StateFlow<General<AllProductsData>> = _allProducts

    fun getAllProducts(marketId: Int) {
        _allProducts.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllProducts(marketId).collect { result ->
                _allProducts.value = result
                Log.d("ASD", "getAllProducts:--->$result ")
            }
        }
    }
}