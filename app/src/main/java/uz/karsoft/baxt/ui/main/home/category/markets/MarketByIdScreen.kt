package uz.karsoft.baxt.ui.main.home.category.markets

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.local.DatabaseHelper
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.all_markets_data.AllMarketsData
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllProductsData
import uz.karsoft.baxt.data.models.main.market_by_id_data.MarketByIdData
import uz.karsoft.baxt.databinding.FragmentMarketByIdBinding
import uz.karsoft.baxt.databinding.LayoutHomeBinding
import uz.karsoft.baxt.extensions.showMessage
import uz.karsoft.baxt.ui.main.home.HomeScreenDirections
import uz.karsoft.baxt.ui.main.home.HomeVM
import uz.karsoft.baxt.ui.main.home.category.products.ProductsAdapter
import uz.karsoft.baxt.ui.main.home.category.products.ProductsVM

class MarketByIdScreen : Fragment(R.layout.fragment_market_by_id) {

    private lateinit var binding: FragmentMarketByIdBinding
    private val vm: MarketByIdVM by viewModel()
    private var marketId: Int? = null

    private val vmProduct: ProductsVM by viewModel()
    private lateinit var adapterProducts : ProductsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            marketId = it.getInt("marketId")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMarketByIdBinding.bind(view)

        adapterProducts = ProductsAdapter(requireContext())


        binding.apply {
            rvProducts.adapter = adapterProducts

        }
        marketId?.let { id ->
            vm.marketById(id)
            vmProduct.getAllProducts(id)
            setUpObserver()
        }
        setUpObserver()

        adapterProducts.setOnItemClickedListener { model->


            val action = MarketByIdScreenDirections.actionMarketByIdScreenToProductByIdLayout(model.id)
            findNavController().navigate(action)


        }

    }


    fun setUpObserver(){
        lifecycleScope.launch {
            vm.marketState.collect { result ->
                when (result) {
                    is General.SuccessData<MarketByIdData> -> {
                        setLoading(false)
                        val market = result.data

                        observe(market)

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
            vmProduct.allProductsState.collect { result ->
                when (result) {
                    is General.SuccessData<AllProductsData> -> {
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


    fun observe(market : MarketByIdData){
        binding.apply {
            marketName.text = market.data.name
            descriptionMarket.text = market.data.description
            Glide.with(ivMarket.context)
                .load(market.data.image.url)
                .into(ivMarket)
        }
    }
    private fun setLoading(loading: Boolean) = binding.apply {
        //progressBar.isVisible = loading
    }

}