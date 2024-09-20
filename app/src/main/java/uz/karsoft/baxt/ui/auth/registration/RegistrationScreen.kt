package uz.karsoft.baxt.ui.auth.registration

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.Auth
import uz.karsoft.baxt.data.models.auth.AuthSuccess
import uz.karsoft.baxt.databinding.LayoutRegistrationBinding
import uz.karsoft.baxt.extensions.formatPhoneNumber
import uz.karsoft.baxt.extensions.showMessage
import uz.karsoft.baxt.settings.Settings
import uz.karsoft.baxt.ui.auth.AuthVM

class RegistrationScreen : Fragment(R.layout.layout_registration) {
    private lateinit var binding: LayoutRegistrationBinding
    private lateinit var navController: NavController
    private val settings: Settings by inject()
    private val vm: AuthVM by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding = LayoutRegistrationBinding.bind(view)

        binding.apply {
            etPhone.formatPhoneNumber()
            if (settings.loggedIn) {
                navController.navigate(R.id.action_loginScreen_to_mainScreen)
            }

            btnRegistration.setOnClickListener {
                val name = etName.text.toString()
                var phone = ""
                etPhone.text.toString().forEach {
                    if (it.isDigit()) {
                        phone += it
                    }
                }
                val password = etPassword.text.toString()

                if (phone.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && password.length > 5) {
                    vm.signUp(
                        phone = phone,
                        password = password,
                        name = name,
                        networkError = getString(R.string.connection_error)

                    )
                } else {
                    if (phone.isEmpty()) {
                        etPhone.error = getString(R.string.required)
                    }
                    if (password.isEmpty()) {
                        etPassword.error = getString(R.string.required)
                    }
                    if (name.isEmpty()) {
                        etName.error = getString(R.string.required)
                    }
                    if(password.length < 6){
                        etPassword.error = getString(R.string.password_length)
                    }
                }
            }
            actionBar.apply {
                tvActionBarTitle.text = getString(R.string.sign_up)

                ivExit.setOnClickListener {
                    navController.popBackStack()
                }
            }
            setUpObservers()
        }
    }

    private fun setUpObservers() = binding.apply {
        lifecycleScope.launch {
            vm.signUpState.collect { result ->
                when (result) {
                    is Auth.SuccessData<AuthSuccess> -> {
                        setLoading(false)
                        navController.navigate(R.id.action_registrationScreen_to_mainScreen)
                    }

                    is Auth.NetworkError -> {
                        setLoading(false)
                        showMessage(result.msg ?: getString(R.string.connection_error))
                    }

                    is Auth.Error -> {
                        setLoading(false)
                        //Log.d("qalay", "setUpObservers: ${result}")
                        showMessage(result.toString())
                    }

                    is Auth.Loading -> {
                        setLoading(true)
                    }

                    else -> {
                        setLoading(false)
                        Exception("unexpected error")
                    }
                }
            }
        }
    }
    private fun setLoading(loading: Boolean) = binding.apply{
        progressBar.isVisible = loading
        etName.isEnabled = !loading
        etPassword.isEnabled = !loading
        etPhone.isEnabled = !loading
        btnRegistration.isEnabled = !loading
    }
}