package uz.karsoft.baxt.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutLoginBinding
import uz.karsoft.baxt.databinding.LayoutMainBinding

class LoginScreen : Fragment(R.layout.layout_login){
    private lateinit var binding: LayoutLoginBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =  Navigation.findNavController(view)
        binding = LayoutLoginBinding.bind(view)

        binding.apply {
            btnLogin.setOnClickListener{
                navController.navigate(R.id.action_loginScreen_to_mainScreen)
            }
            btnRegistration.setOnClickListener {
                navController.navigate(R.id.action_loginScreen_to_registrationScreen)
            }
        }

    }
}