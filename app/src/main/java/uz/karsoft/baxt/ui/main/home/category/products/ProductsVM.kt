package uz.karsoft.baxt.ui.main.home.category.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.repo.MainRepository

class ProductsVM(private val repository: MainRepository): ViewModel() {

    private val _products = MutableStateFlow<General<Products>>(General.Empty)
    val productsState: StateFlow<General<Products>> = _products

    fun getProductsById(id: Int) {
        _products.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProductsById(id).collect { result ->
                _products.value = result
            }
        }
    }
}