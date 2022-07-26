package com.avast.test.feature.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.avast.test.domain.ParseDictionaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.*

@HiltViewModel
class MainViewModel @Inject constructor(
    private val parseDictionaryUseCase: ParseDictionaryUseCase
) : ViewModel() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val input = mutableStateOf("aspineappleassecondaryapple")
    val result = mutableStateOf("")

    override fun onCleared() {
        scope.cancel()
    }

    fun onParseClicked() {
        scope.launch {
            result.value = parseDictionaryUseCase(input.value)
        }
    }
}
