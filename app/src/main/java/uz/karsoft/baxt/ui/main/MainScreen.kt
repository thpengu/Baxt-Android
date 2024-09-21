package uz.karsoft.baxt.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutMainBinding

class MainScreen: Fragment(R.layout.layout_main) {
    private lateinit var binding: LayoutMainBinding
    private lateinit var navController: NavController
    private lateinit var childNavController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutMainBinding.bind(view)
        navController = Navigation.findNavController(view)

        childNavController = Navigation.findNavController(requireActivity(), R.id.main_fragment)

        binding.apply {
            bottomNavView.setupWithNavController(childNavController)

        }

    }
}