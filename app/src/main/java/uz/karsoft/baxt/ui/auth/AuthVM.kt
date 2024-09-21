package uz.karsoft.baxt.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.auth.AuthSuccess
import uz.karsoft.baxt.repo.AuthRepository

class AuthVM(private val authRepository: AuthRepository): ViewModel() {
    private val _loginState = MutableStateFlow<General<AuthSuccess>>(General.Empty)
    val loginState: StateFlow<General<AuthSuccess>> = _loginState

    fun signIn(phone: String, password: String) {
        _loginState.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn(phone, password).collect { result ->
                _loginState.value = result
            }
        }
    }

    private val _signUpState = MutableStateFlow<General<AuthSuccess>>(General.Empty)
    val signUpState: StateFlow<General<AuthSuccess>> = _signUpState

    fun signUp(
        name: String,
        phone: String,
        password: String,
    ) {
        _signUpState.value = General.Loading
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(
                name = name,
                phone = phone,
                password = password
            ).collect { result ->
                _signUpState.value = result
            }
        }
    }
}