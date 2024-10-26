package uz.karsoft.baxt.ui.main.home.category.products

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
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.databinding.LayoutCategoryItemsBinding
import uz.karsoft.baxt.databinding.LayoutHomeBinding
import uz.karsoft.baxt.databinding.LayoutProductsBinding
import uz.karsoft.baxt.databinding.LayoutProfileBinding
import uz.karsoft.baxt.extensions.showMessage
import uz.karsoft.baxt.ui.main.home.category.NavigationAdapter

class ProductsScreen: Fragment(R.layout.layout_products) {
    private val vm: ProductsVM by viewModel()
    private var _binding: LayoutProductsBinding? = null
    private val binding get() = _binding!!
    private val adapter = ProductsAdapter()
    private val navAdapter = NavigationAdapter()
    private val args: ProductsScreenArgs by navArgs()
    private val gson = Gson()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LayoutProductsBinding.bind(view)
        val model = gson.fromJson(args.model, ProductNavigationData::class.java)
        model.navigationList.add(NavigationData(model.model.name.kaa_latin, model.navigationList.size))

        binding.apply {
            rvProducts.adapter = adapter
            rvNavigation.adapter = navAdapter
            navAdapter.models = model.navigationList

            vm.getProductsById(model.model.id)
            setUpObservers()
        }

        navAdapter.setOnItemClickedListener { model ->
            when(model.id){
                0 ->{
                    findNavController().navigate(R.id.action_productsScreen_to_nav_home)
                }
                1->{
                    findNavController().popBackStack()
                }
                else ->{
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.productsState.collect { result ->
                when (result) {
                    is General.SuccessData<Products> -> {
                        setLoading(false)
                        adapter.models = result.data.data
                    }
                    is General.NetworkError -> handleError(result.msg)
                    is General.Error -> handleError(result.toString())
                    is General.Loading -> setLoading(true)
                   else -> Exception("hello")
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