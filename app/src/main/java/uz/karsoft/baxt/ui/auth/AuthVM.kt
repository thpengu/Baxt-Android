package uz.karsoft.baxt.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.karsoft.baxt.data.models.auth.Auth
import uz.karsoft.baxt.data.models.auth.AuthSuccess
import uz.karsoft.baxt.repo.AuthRepository

class AuthVM(private val authRepository: AuthRepository): ViewModel() {
    private val _loginState = MutableStateFlow<Auth<AuthSuccess>>(Auth.Empty)
    val loginState: StateFlow<Auth<AuthSuccess>> = _loginState

    fun signIn(phone: String, password: String, networkError: String) {
        _loginState.value = Auth.Loading
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn(phone, password, networkError).collect { result ->
                _loginState.value = result
            }
        }
    }

    private val _signUpState = MutableStateFlow<Auth<AuthSuccess>>(Auth.Empty)
    val signUpState: StateFlow<Auth<AuthSuccess>> = _signUpState

    fun signUp(
        name: String,
        phone: String,
        password: String,
        networkError :String,
    ) {
        _signUpState.value = Auth.Loading
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(
                name = name,
                phone = phone,
                password = password,
                networkError = networkError,
            ).collect { result ->
                _signUpState.value = result
            }
        }
    }
}