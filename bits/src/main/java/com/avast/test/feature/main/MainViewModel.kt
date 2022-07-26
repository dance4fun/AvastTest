package com.avast.test.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.avast.test.domain.CountBitsDifferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    private val countBitsDifferenceUseCase: CountBitsDifferenceUseCase
) : ViewModel() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val first = mutableStateOf("")
    val second = mutableStateOf("")
    val result = mutableStateOf("")

    private val _showError = MutableSharedFlow<Unit>()
    val showError = _showError.asSharedFlow()

    override fun onCleared() {
        scope.cancel()
    }

    fun onCalculateClicked() {
        scope.launch {
            val firstNumber = first.value.toIntOrNull()
            val secondNumber = second.value.toIntOrNull()

            if (firstNumber != null && secondNumber != null) {
                result.value = countBitsDifferenceUseCase(firstNumber, secondNumber).toString()
            } else {
                _showError.emit(Unit)
            }
        }
    }
}
