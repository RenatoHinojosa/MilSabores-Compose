package com.example.milsaborescompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaborescompose.data.model.PaymentMethod
import com.example.milsaborescompose.data.repository.PaymentMethodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PaymentMethodUiState(
    val isLoading: Boolean = false,
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val error: String? = null
)

class PaymentMethodViewModel(private val repository: PaymentMethodRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentMethodUiState())
    val uiState: StateFlow<PaymentMethodUiState> = _uiState.asStateFlow()

    init {
        loadPaymentMethods()
    }

    private fun loadPaymentMethods() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val methods = repository.getAllPaymentMethods()
                _uiState.update { it.copy(isLoading = false, paymentMethods = methods) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to load payment methods: ${e.message}") }
            }
        }
    }
}