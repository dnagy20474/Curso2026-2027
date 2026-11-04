package com.example.plaestudi

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
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

    var minutsText by rememberSaveable { mutableStateOf("25") }
    var sessions by rememberSaveable { mutableStateOf(0) }
    val minuts = minutsText.toIntOrNull() ?: 0
    Column(
        modifier = modifier.fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Ultima Calculadora d'estudi",
            style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = minutsText,
            onValueChange = { minutsText = it },
            label = { Text("Minuts per sessió") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Sessions: $sessions")
        Button(
            onClick = { if (sessions < 20) sessions += 1 },
            enabled = sessions < 20
        ) { Text("Afegir sessió") }
        Button(
            onClick = { if (sessions > 0) sessions -= 1 },
            enabled = sessions > 0
        ) { Text("Restar sessió") }
        Button(onClick = { sessions = 0 }) {
            Text("Reinici de sessions")
        }
        if (minuts in 1..180) {
            val total = minuts * sessions
            Text("Total: $total minuts")
            Text("${total / 60} h ${total % 60} min")
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