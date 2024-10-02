package uz.karsoft.baxt.ui.main.home.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.Data
import uz.karsoft.baxt.data.models.main.home.detail.NavigationData
import uz.karsoft.baxt.data.models.main.search.Categories
import uz.karsoft.baxt.databinding.LayoutCategoryItemsBinding
import uz.karsoft.baxt.extensions.showMessage

class CategoryItemsScreen: Fragment(R.layout.layout_category_items) {
    private lateinit var binding: LayoutCategoryItemsBinding
    private lateinit var navController: NavController
    private val args: CategoryItemsScreenArgs by navArgs()
    private val vm: CategoryVM by viewModel()
    private val adapter = CategoryItemsAdapter()
    private val navAdapter = NavigationAdapter()
    private val navModels = mutableListOf<NavigationData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = LayoutCategoryItemsBinding.bind(view)

        binding.apply {
            rvCategoriesItems.adapter = adapter
            rvNavigation.adapter = navAdapter
        }

        val gson = Gson()
        val model = gson.fromJson(args.model, Data::class.java)

        navModels.add(NavigationData(model.name.ru, 1))
        navAdapter.models = navModels

        navAdapter.setOnItemClickedListener { pos ->
            when(pos){
                0 ->{
                    navController.popBackStack()
                }
            }

        }

        vm.getCategoriesById(model.id)
        setUpObservers()
    }

    private fun setUpObservers() = binding.apply {
        lifecycleScope.launch {
            vm.categoriesState.collect { result ->
                when (result) {
                    is General.SuccessData<Categories> -> {
                        setLoading(false)
                        adapter.models = result.data.data
                    }

                    is General.NetworkError -> {
                        setLoading(false)
                        showMessage(result.msg ?: getString(R.string.connection_error))
                    }

                    is General.Error -> {
                        setLoading(false)
                        showMessage(result.toString())
                    }

                    is General.Loading -> {
                        setLoading(true)
                    }
                    else -> {
                        setLoading(false)
                        Exception("unexpected error")
                    }
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) = binding.apply{
        progressBar.isVisible = loading
    }
}