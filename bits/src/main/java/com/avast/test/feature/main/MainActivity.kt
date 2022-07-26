package com.avast.test.feature.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.avast.test.R
import com.avast.test.ui.theme.AvastTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AvastTestTheme {
                MainScreen(viewModel)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.showError.collectLatest {
                    showInvalidInputError()
                }
            }
        }
    }

    private fun showInvalidInputError() {
        Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FirstNumberInput(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        SecondNumberInput(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        CalculateButton(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        CalculationResult(viewModel)
    }
}

@Composable
fun FirstNumberInput(viewModel: MainViewModel) {
    var input by remember { viewModel.first }
    OutlinedTextField(
        value = input,
        onValueChange = { input = it },
        label = { Text(stringResource(id = R.string.label_first_number)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun SecondNumberInput(viewModel: MainViewModel) {
    var input by remember { viewModel.second }
    OutlinedTextField(
        value = input,
        onValueChange = { input = it },
        label = { Text(stringResource(id = R.string.label_second_number)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun CalculateButton(viewModel: MainViewModel) {
    Button(onClick = viewModel::onCalculateClicked) {
        Text(
            stringResource(R.string.calculate_button),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun CalculationResult(viewModel: MainViewModel) {
    val result by viewModel.result
    Text(
        stringResource(R.string.calculation_result, result),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
