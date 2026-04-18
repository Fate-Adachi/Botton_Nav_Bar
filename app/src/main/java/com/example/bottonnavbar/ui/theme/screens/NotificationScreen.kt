package com.example.bottonnavbar.ui.theme.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun NotificationScreen(modifier: Modifier = Modifier) {

    var count by rememberSaveable { mutableStateOf(0) }
    var textValue by rememberSaveable { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation ==
            Configuration.ORIENTATION_LANDSCAPE

    val controls: @Composable () -> Unit = {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Contador: $count",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = { count++ }) {
                Text("Incrementar")
            }
        }
    }

    val inputField: @Composable () -> Unit = {
        OutlinedTextField(
            value = textValue,
            onValueChange = { textValue = it },
            label = { Text("Escribe algo aquí") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }

    Scaffold { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF498D9D))
        ) {
            if (isLandscape) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        controls()
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        inputField()
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    controls()
                    inputField()
                }
            }
        }
    }
}