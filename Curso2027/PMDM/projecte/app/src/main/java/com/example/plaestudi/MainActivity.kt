package com.example.plaestudi

import android.os.*
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import com.example.plaestudi.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaEstudiTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "PMDM",
                        modifier = Modifier.padding(innerPadding)

                    )

                }
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hola $name!",
        modifier = modifier
    )
    // Inici zona de treball
    var minutsText by remember { mutableStateOf("25") }
    val minuts = minutsText.toIntOrNull() ?: 0
    val sessions = 2
    Column(
        modifier = modifier.fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Calculadora de dues sessions")
        OutlinedTextField(
            value = minutsText,
            onValueChange = { minutsText = it },
            label = { Text("Minuts per sessió") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (minuts in 1..180) {
            val total = minuts * sessions
            Text("Total: $total minuts")
        } else {
            Text("Escriu un enter entre 1 i 180")
        }
    }
    // Fin zona de treball
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlaEstudiTheme {
        Greeting("PMDM")
    }
}