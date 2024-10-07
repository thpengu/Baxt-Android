package uz.karsoft.baxt.ui.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.search.Categories
import uz.karsoft.baxt.repo.MainRepository

class SearchVM(private val repository: MainRepository): ViewModel() {
    private val _categories = MutableStateFlow<General<Categories>>(General.Empty)
    val categoriesState: StateFlow<General<Categories>> = _categories

    fun getCategories() {
        _categories.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories().collect { result ->
                _categories.value = result
            }
        }
    }
}