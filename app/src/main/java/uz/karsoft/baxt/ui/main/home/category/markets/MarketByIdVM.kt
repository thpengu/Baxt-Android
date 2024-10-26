package uz.karsoft.baxt.ui.main.home.category.markets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data.ProductByIdData
import uz.karsoft.baxt.data.models.main.market_by_id_data.MarketByIdData
import uz.karsoft.baxt.repo.MainRepository

class MarketByIdVM(private val repository : MainRepository) : ViewModel() {

    private val _market = MutableStateFlow<General<MarketByIdData>>(General.Empty)
    val marketState: StateFlow<General<MarketByIdData>> = _market

    fun marketById(id: Int) {
        _market.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.marketById(id).collect { result ->
                _market.value = result
            }
        }
    }
}