package uz.karsoft.baxt.ui.main.home.foods

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.foods.AllFoodsData
import uz.karsoft.baxt.repo.MainRepository

class AllFoodsVM(private val repository: MainRepository) : ViewModel() {

    private val _foods = MutableStateFlow<General<AllFoodsData>>(General.Empty)
    val foodsState: StateFlow<General<AllFoodsData>> = _foods

    fun getAllFoods() {
        _foods.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFoods().collect { result ->
                _foods.value = result

                Log.d("ASD", "getAllFoods:-->$result ")
            }
        }
    }
}