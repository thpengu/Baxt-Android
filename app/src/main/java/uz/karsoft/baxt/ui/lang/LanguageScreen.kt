package uz.karsoft.baxt.ui.lang

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.android.ext.android.inject
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutLanguageBinding
import uz.karsoft.baxt.settings.Settings

class LanguageScreen: Fragment(R.layout.layout_language){

    private lateinit var binding: LayoutLanguageBinding
    private lateinit var navController: NavController
    private val settings: Settings by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutLanguageBinding.bind(view)
        navController = Navigation.findNavController(view)

        if(settings.language != ""){
            navController.navigate(R.id.action_languageScreen_to_loginScreen)
        }


        binding.apply {
            tvEn.setOnClickListener {
                //changeLang()
                moveToLogin("en")
            }
            tvRu.setOnClickListener {
                //changeLang()
                moveToLogin("ru")
            }
            tvKaa.setOnClickListener {
                //changeLang()
                moveToLogin("kaa")
            }
            tvUz.setOnClickListener {
                //changeLang()
                moveToLogin("uz")
            }
        }
    }

    private fun moveToLogin(lang: String){
        settings.language = lang
        navController.navigate(R.id.action_languageScreen_to_loginScreen)
    }

    private fun changeLang(){
        //todo
    }
}