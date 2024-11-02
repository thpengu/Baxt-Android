package uz.karsoft.baxt.ui.main.basket

import CartAdapter
import DatabaseHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.main.cart.CartData
import uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data.ProductByIdData
import uz.karsoft.baxt.databinding.LayoutBasketBinding

class BasketScreen: Fragment(R.layout.layout_basket),CartAdapter.OnDeleteClickListener {

    private var _binding: LayoutBasketBinding? = null
    private val binding get() = _binding!!

    private  lateinit var cartAdapter: CartAdapter
    private var cartItems: MutableList<CartData> = mutableListOf()
    private lateinit var dbHelper : DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cartAdapter = CartAdapter(requireContext(), emptyList(),this)

        dbHelper = DatabaseHelper(requireContext())

        binding.apply {
           rvBasket.layoutManager = LinearLayoutManager(requireContext())
            rvBasket.adapter = cartAdapter

            btnOformit.setOnClickListener{
                showBottomSheet()
            }

        }


        loadCartProducts()
    }

    private fun loadCartProducts() {
        cartItems = dbHelper.getAllCartProducts().toMutableList()
        cartAdapter.updateCart(cartItems)

        binding.apply {
            if(cartItems.isEmpty()){
                btnOformit.isVisible = false
            }
            else{
                btnOformit.isVisible = true
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showBottomSheet(){
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_balance, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetView.findViewById<CardView>(R.id.cardView_3).setOnClickListener {

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    override fun onDeleteClicked(cartData: CartData) {
        dbHelper.deleteCartProduct(cartData.id)
        val updatedCartItems = cartAdapter.models.toMutableList()
        updatedCartItems.remove(cartData)
        cartAdapter.updateCart(updatedCartItems)
        loadCartProducts()
    }
}