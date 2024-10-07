package uz.karsoft.baxt.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.data.models.main.home.Data
import uz.karsoft.baxt.databinding.LayoutHomeBinding
import uz.karsoft.baxt.extensions.showMessage

class HomeScreen: Fragment(R.layout.layout_home) {

    private lateinit var binding: LayoutHomeBinding
    private val vm: HomeVM by viewModel()
    private val adapter = CollectionAdapter()
    private lateinit var navController: NavController
    private val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutHomeBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.apply {
            rvCategories.adapter = adapter
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

        vm.getCollections()
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
    }

    private fun setLoading(loading: Boolean) = binding.apply {
        progressBar.isVisible = loading
    }
}