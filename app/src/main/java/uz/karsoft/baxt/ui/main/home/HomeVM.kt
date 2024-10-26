package uz.karsoft.baxt.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.all_markets_data.AllMarketsData
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.repo.MainRepository

class HomeVM(private val repository: MainRepository): ViewModel() {
    private val _collections = MutableStateFlow<General<Collections>>(General.Empty)
    val collectionState: StateFlow<General<Collections>> = _collections

    fun getCollections() {
        _collections.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCollections().collect { result ->
                _collections.value = result
            }
        }
    }

    private val _markets = MutableStateFlow<General<AllMarketsData>>(General.Empty)
    val marketsState: StateFlow<General<AllMarketsData>> = _markets

    fun getAllMarkets() {
        _markets.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMarkets().collect { result ->
                _markets.value = result
            }
        }
    }
}