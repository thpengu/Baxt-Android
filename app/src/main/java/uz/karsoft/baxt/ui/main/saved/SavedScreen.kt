package uz.karsoft.baxt.ui.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.local.DatabaseHelper
import uz.karsoft.baxt.data.models.main.saved_data.SavedProductsData
import uz.karsoft.baxt.databinding.LayoutSavedBinding

class SavedScreen : Fragment(R.layout.layout_saved) {

    private var _binding: LayoutSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var savedProductsAdapter: SavedProductsAdapter
    private var savedProducts: MutableList<SavedProductsData> = mutableListOf() // Ro'yxatni e'lon qilish

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
            removeProduct(product) // Mahsulotni o'chirish
        }

        binding.rvSavedProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSavedProducts.adapter = savedProductsAdapter

        loadSavedProducts()
    }

    private fun loadSavedProducts() {
        savedProducts = dbHelper.getAllSavedProducts().toMutableList() // Ro'yxatni bazadan oling
        savedProductsAdapter.setProducts(savedProducts)
    }

    private fun removeProduct(product: SavedProductsData) {
        dbHelper.deleteProduct(product.id) // Mahsulotni bazadan o'chirish
        savedProducts.remove(product) // Ro'yxatdan o'chirish
        savedProductsAdapter.setProducts(savedProducts) // Yangilangan ro'yxatni o'rnating
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


