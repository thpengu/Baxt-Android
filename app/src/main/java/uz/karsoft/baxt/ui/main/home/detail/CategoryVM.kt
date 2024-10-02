package uz.karsoft.baxt.ui.main.home.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.search.Categories
import uz.karsoft.baxt.repo.MainRepository

class CategoryVM(private val repository: MainRepository): ViewModel() {
    private val _categories = MutableStateFlow<General<Categories>>(General.Empty)
    val categoriesState: StateFlow<General<Categories>> = _categories

    fun getCategoriesById(id: Int) {
        _categories.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCollectionsById(id).collect { result ->
                _categories.value = result
            }
        }
    }
}