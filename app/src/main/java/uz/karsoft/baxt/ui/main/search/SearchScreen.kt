package uz.karsoft.baxt.ui.main.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.data.models.main.search.Categories
import uz.karsoft.baxt.databinding.LayoutSearchBinding
import uz.karsoft.baxt.extensions.showMessage

class SearchScreen: Fragment(R.layout.layout_search) {
    private lateinit var binding: LayoutSearchBinding
    private val vm: SearchVM by viewModel()
    private val adapter = SearchAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LayoutSearchBinding.bind(view)

        binding.apply {
            rvSearch.adapter = adapter
        }

        vm.getCategories()
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