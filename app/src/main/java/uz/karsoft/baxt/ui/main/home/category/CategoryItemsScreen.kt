package uz.karsoft.baxt.ui.main.home.category

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.Data
import uz.karsoft.baxt.data.models.main.home.detail.NavigationData
import uz.karsoft.baxt.data.models.main.home.detail.product.ProductNavigationData
import uz.karsoft.baxt.data.models.main.search.Categories
import uz.karsoft.baxt.databinding.LayoutCategoryItemsBinding
import uz.karsoft.baxt.extensions.showMessage

class CategoryItemsScreen : Fragment(R.layout.layout_category_items) {
    private var _binding: LayoutCategoryItemsBinding? = null
    private val binding get() = _binding!!
    private val args: CategoryItemsScreenArgs by navArgs()
    private val vm: CategoryVM by viewModel()
    private val adapter = CategoryItemsAdapter()
    private val adapter2 = CategoryDetailItemsAdapter()
    private val navAdapter = NavigationAdapter()
    private var navModels = mutableListOf<NavigationData>()
    private val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    private val gson = Gson()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LayoutCategoryItemsBinding.bind(view)

        initViews()
        setupNavigation()

        navModels = mutableListOf()
        val model = gson.fromJson(args.model, Data::class.java)
        navModels.add(NavigationData(model.name.ru, navModels.size))
        navAdapter.models = navModels

        vm.getCategoriesById(model.id)

        setUpObservers()
    }

    private fun initViews() {
        binding.apply {
            rvCategoriesItems.adapter = adapter
            rvCategoriesItems2.adapter = adapter2
            rvNavigation.adapter = navAdapter
        }

        adapter.setOnItemClickedListener { data ->
            if (data.children.isNotEmpty()) {
                adapter2.models = data.children
                toggleViews(showFirst = false)
                addNavigationStep(data.name.ru, navModels.size)
            }
        }

        navAdapter.setOnItemClickedListener { model ->
            handleNavigationClick(model.id)
        }

        adapter2.setOnItemClickedListener { model ->
            val data = ProductNavigationData(navModels, model)
            val jsonString = gsonPretty.toJson(
                data
            )
            val action = CategoryItemsScreenDirections.actionCategoryItemsScreenToProductsScreen(jsonString)
            findNavController().navigate(action)
        }
    }

    private fun setupNavigation() {
        val navController = findNavController()

        navAdapter.setOnItemClickedListener { model ->
            when (model.id) {
                0 -> navController.popBackStack()
                1 -> {
                    navModels.removeLast()
                    navAdapter.models = navModels
                    toggleViews(showFirst = true)
                }
            }
        }
    }

    private fun handleNavigationClick(pos: Int) {
        when (pos) {
            0 -> findNavController().popBackStack()
            1 -> {
                navModels.removeLast()
                navAdapter.models = navModels
                toggleViews(showFirst = true)
            }
        }
    }

    private fun toggleViews(showFirst: Boolean) {
        binding.apply {
            rvCategoriesItems.isVisible = showFirst
            rvCategoriesItems2.isVisible = !showFirst
        }
    }

    private fun addNavigationStep(name: String, pos: Int) {
        navModels.add(NavigationData(name, pos))
        navAdapter.models = navModels
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.categoriesState.collect { result ->
                when (result) {
                    is General.SuccessData<Categories> -> {
                        setLoading(false)
                        adapter.models = result.data.data
                    }
                    is General.NetworkError -> handleError(result.msg)
                    is General.Error -> handleError(result.toString())
                    is General.Loading -> setLoading(true)
                    else -> setLoading(false)
                }
            }
        }
    }

    private fun handleError(errorMsg: String?) {
        setLoading(false)
        showMessage(errorMsg ?: getString(R.string.connection_error))
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
