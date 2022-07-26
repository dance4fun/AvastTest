package com.avast.test.feature.main

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.avast.test.R
import com.avast.test.ui.theme.AvastTestTheme
import dagger.hilt.android.AndroidEntryPoint

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
        Input(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        ParseButton(viewModel)
        Spacer(modifier = Modifier.height(8.dp))
        ParseResult(viewModel)
    }
}

@Composable
fun Input(viewModel: MainViewModel) {
    var input by remember { viewModel.input }
    OutlinedTextField(
        value = input,
        singleLine = true,
        onValueChange = { input = it },
        label = { Text(stringResource(id = R.string.enter_text_label)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun ParseButton(viewModel: MainViewModel) {
    Button(onClick = viewModel::onParseClicked) {
        Text(
            stringResource(R.string.parse_button),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun ParseResult(viewModel: MainViewModel) {
    val result by viewModel.result
    Text(
        stringResource(R.string.parse_result, result),
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
