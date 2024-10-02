package uz.karsoft.baxt.ui.lang

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.android.ext.android.inject
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.lang.LanguageData
import uz.karsoft.baxt.databinding.LayoutLanguageBinding
import uz.karsoft.baxt.extensions.onClick
import uz.karsoft.baxt.extensions.showMessage
import uz.karsoft.baxt.settings.Settings
import uz.karsoft.baxt.settings.Settings.Companion.ENGLISH
import uz.karsoft.baxt.settings.Settings.Companion.KARAKALPAK
import uz.karsoft.baxt.settings.Settings.Companion.RUSSIAN
import uz.karsoft.baxt.settings.Settings.Companion.UZBEK

class LanguageScreen: Fragment(R.layout.layout_language){

    private lateinit var binding: LayoutLanguageBinding
    private lateinit var navController: NavController
    private val settings: Settings by inject()
    private val adapter = LanguageAdapter()

    private var languages = mutableListOf<LanguageData>()
    //private var selectedLang = LanguageData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutLanguageBinding.bind(view)
        navController = Navigation.findNavController(view)

        //selectedLang = LanguageData()

        if(settings.language != "") {
            navController.navigate(R.id.action_languageScreen_to_loginScreen)
        }

        binding.apply {
            rvLanguages.adapter = adapter

            /*btnNext.onClick {
                if(selectedLang.code != ""){
                    moveToLogin(selectedLang)
                } else {
                    showMessage(getString(R.string.please_select_language))
                }
            }*/
        }
        setLangData()

        adapter.setOnItemClickedListener { model ->
            //selectedLang = model
            moveToLogin(model)
        }

    }

    private fun setLangData(){
        languages = mutableListOf()

        languages.add(LanguageData(name = getString(R.string.english), code = ENGLISH, id = 0))
        languages.add(LanguageData(name = getString(R.string.karakalpak), code = KARAKALPAK, id = 1))
        languages.add(LanguageData(name = getString(R.string.russian), code = RUSSIAN, id = 2))
        languages.add(LanguageData(name = getString(R.string.uzbek), code = UZBEK, id = 3))

        adapter.models = languages
    }

    private fun moveToLogin(lang: LanguageData){
        settings.language = lang.code

        val action = LanguageScreenDirections.actionLanguageScreenToLoginScreen()
        navController.navigate(action)
    }
}