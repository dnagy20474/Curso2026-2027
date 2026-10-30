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
    var sessions by remember { mutableStateOf(0) }
    Column(
        modifier = modifier.fillMaxSize().imePadding()
            .verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Comptador de sessions")
        Text("Sessions: $sessions")
        Button(onClick = { sessions = sessions + 1 }) {
            Text("Afegir sessió")
        }
        Button(onClick = {
            if (sessions > 0) sessions = sessions - 1
        }) {
            Text("Restar sessió")
        }
        Button(onClick = { sessions = 0 }) {
            Text("Reiniciar")
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