package uz.karsoft.baxt.ui.lang

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutLanguageBinding

class LanguageScreen: Fragment(R.layout.layout_language){

    private lateinit var binding: LayoutLanguageBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutLanguageBinding.bind(view)
        navController = Navigation.findNavController(view)

        binding.apply {
            tvEn.setOnClickListener {
                //changeLang()
                moveToLogin()
            }
            tvRu.setOnClickListener {
                //changeLang()
                moveToLogin()
            }
            tvKaa.setOnClickListener {
                //changeLang()
                moveToLogin()
            }
            tvUz.setOnClickListener {
                //changeLang()
                moveToLogin()
            }
        }
    }

    private fun moveToLogin(){
        navController.navigate(R.id.action_languageScreen_to_loginScreen)
    }

    private fun changeLang(){
        //todo
    }
}