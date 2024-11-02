package uz.karsoft.baxt.ui.main.saved

import DatabaseHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData
import uz.karsoft.baxt.databinding.LayoutSavedBinding

class SavedScreen : Fragment(R.layout.layout_saved) {

    private var _binding: LayoutSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var savedProductsAdapter: SavedProductsAdapter
    private var savedProducts: MutableList<SavedProductsData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DatabaseHelper(requireContext())
        savedProductsAdapter = SavedProductsAdapter { product ->
            removeProduct(product)
        }

        binding.rvSavedProducts.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvSavedProducts.adapter = savedProductsAdapter


        savedProductsAdapter.setOnItemClickedListener { model->
            val action = SavedScreenDirections.actionNavSavedToProductByIdLayout(model.id)
            findNavController().navigate(action)
            Log.d("TTT", "onViewCreated:--->item basildi modelId${model.id} ")
        }


        loadSavedProducts()
    }

    private fun loadSavedProducts() {
        savedProducts = dbHelper.getAllSavedProducts().toMutableList()
        savedProductsAdapter.setProducts(savedProducts)
    }

    private fun removeProduct(product: SavedProductsData) {
        dbHelper.deleteProduct(product.id)
        savedProducts.remove(product)
        savedProductsAdapter.setProducts(savedProducts)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


