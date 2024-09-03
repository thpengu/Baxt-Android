package uz.karsoft.baxt.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutLoginBinding
import uz.karsoft.baxt.databinding.LayoutRegistrationBinding

class RegistrationScreen : Fragment(R.layout.layout_registration){
    private lateinit var binding: LayoutRegistrationBinding
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =  Navigation.findNavController(view)
        binding = LayoutRegistrationBinding.bind(view)

        binding.apply {
            btnRegistration.setOnClickListener{
                navController.navigate(R.id.action_registrationScreen_to_mainScreen)
            }
            actionBar.apply {
                tvActionBarTitle.text = "Dizimnen otiw"

                ivExit.setOnClickListener{
                    navController.popBackStack()
                }
            }
        }

    }
}