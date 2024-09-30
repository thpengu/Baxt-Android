package uz.karsoft.baxt.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.karsoft.baxt.R
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.databinding.LayoutLoginBinding
import uz.karsoft.baxt.extensions.formatPhoneNumber
import uz.karsoft.baxt.extensions.onClick
import uz.karsoft.baxt.extensions.showMessage
import uz.karsoft.baxt.settings.Settings
import uz.karsoft.baxt.ui.auth.AuthVM

class LoginScreen : Fragment(R.layout.layout_login) {
    private lateinit var binding: LayoutLoginBinding
    private lateinit var navController: NavController
    private val vm: AuthVM by viewModel()
    private val settings: Settings by inject()
    private val safeArgs: LoginScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        binding = LayoutLoginBinding.bind(view)

        binding.apply {
            etPhone.formatPhoneNumber()
            if (settings.loggedIn) {
                navController.navigate(R.id.action_loginScreen_to_mainScreen)
            }
            tvPrivacyPolicy.onClick {
                navController.navigate(R.id.action_loginScreen_to_privacyPolicyScreen)
            }
            tvPasswordForget.onClick {
                navController.navigate(R.id.action_loginScreen_to_forgetPasswordScreen)
            }
            btnLogin.onClick {
                var phone = ""
                etPhone.text.toString().forEach {
                    if (it.isDigit()) {
                        phone += it
                    }
                }
                val password = etPassword.text.toString()

                if (phone.isNotEmpty() && password.isNotEmpty()) {
                    vm.signIn(
                        phone = phone,
                        password = password
                    )
                } else {
                    if (phone.isEmpty()) {
                        etPhone.error = getString(R.string.required)
                    }
                    if (password.isEmpty()) {
                        etPassword.error = getString(R.string.required)
                    }
                }
            }
            btnRegistration.setOnClickListener {
                val action = LoginScreenDirections.actionLoginScreenToRegistrationScreen(safeArgs.lang)
                navController.navigate(action)
            }
        }
        setUpObservers()
    }

    private fun setUpObservers() = binding.apply {
        lifecycleScope.launch {
            vm.loginState.collect { result ->
                when (result) {
                    is General.SuccessData<*> -> {
                        setLoading(false)
                        navController.navigate(R.id.action_loginScreen_to_mainScreen)
                    }

                    is General.NetworkError -> {
                        setLoading(false)
                        showMessage(result.msg ?: getString(R.string.connection_error))
                    }

                    is General.Error -> {
                        setLoading(false)
                        showMessage(result.toString())
                    }

                    is General.Loading -> {
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

    private fun setLoading(loading: Boolean) = binding.apply {
        progressBar.isVisible = loading
        etPassword.isEnabled = !loading
        etPhone.isEnabled = !loading
        btnLogin.isEnabled = !loading
        tvPrivacyPolicy.isEnabled = !loading
        tvPasswordForget.isEnabled = !loading
        btnRegistration.isEnabled = !loading
    }
}