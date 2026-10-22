package com.example.plaestudi

import android.os.*
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.dp
import com.example.plaestudi.ui.theme.PlaEstudiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaEstudiTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
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
        text = "Hello $name!",
        modifier = modifier

    )
    Column(modifier.padding(24.dp)) {
        Text("Objectiu aconseguit")
        Text("La meva primera app")
        Text("Estic aprenent a modificar una pantalla")
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PlaEstudiTheme {
        Greeting("Android")
    }
}