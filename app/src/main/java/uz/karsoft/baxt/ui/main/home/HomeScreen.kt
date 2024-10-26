package uz.karsoft.baxt.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.all_markets_data.AllMarketsData
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.data.models.main.home.Data
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllProductsData
import uz.karsoft.baxt.data.models.main.home.detail.product.ProductData
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.data.models.main.home.detail.product.User
import uz.karsoft.baxt.databinding.LayoutHomeBinding
import uz.karsoft.baxt.extensions.showMessage
import uz.karsoft.baxt.ui.main.home.category.markets.MarketsAdapter
import uz.karsoft.baxt.ui.main.home.category.products.ProductsAdapter
import uz.karsoft.baxt.ui.main.home.category.products.ProductsVM

class HomeScreen: Fragment(R.layout.layout_home) {

    private lateinit var binding: LayoutHomeBinding
    private val vm: HomeVM by viewModel()
    private val adapter = CollectionAdapter()
    private lateinit var navController: NavController
    private val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    private val vmProduct: ProductsVM by viewModel()
    private val adapterProducts = MarketsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutHomeBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.apply {
            rvCategories.adapter = adapter
            rvProducts.adapter = adapterProducts

            swipeResfreshLayout.setOnRefreshListener {
                vm.getCollections()
                swipeResfreshLayout.isRefreshing = false
            }


        }

        adapter.setOnItemClickedListener { model ->
            val jsonString = gsonPretty.toJson(
                Data(
                    id = model.id,
                    name = model.name,
                    icon = model.icon,
                    iconUrl = model.iconUrl,
                    categoryCount = model.categoryCount
                )
            )
            val action = HomeScreenDirections.actionNavHomeToCategoryItemsScreen(jsonString)
            navController.navigate(action)
        }

        adapterProducts.setOnItemClickedListener { model->


            val action = HomeScreenDirections.actionNavHomeToMarketByIdScreen(model.id)
            findNavController().navigate(action)


        }

        vm.getCollections()
        vm.getAllMarkets()
        setUpObservers()
    }

    private fun setUpObservers() = binding.apply {
        lifecycleScope.launch {
            vm.collectionState.collect { result ->
                when (result) {
                    is General.SuccessData<Collections> -> {
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
                    }
                }
            }
        }

        lifecycleScope.launch {
            vm.marketsState.collect { result ->
                when (result) {
                    is General.SuccessData<AllMarketsData> -> {
                        setLoading(false)
                        adapterProducts.models = result.data.data
                        adapterProducts.notifyDataSetChanged() // Yangilanish uchun
                        Log.d("AAA", "setUpObservers:${result.data.data} ")
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
                    }
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) = binding.apply {
        progressBar.isVisible = loading
    }
}