package com.example.thrillcast

import ThrillCastApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.thrillcast.ui.theme.ThrillCastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThrillCastTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThrillCastApp()
<<<<<<< HEAD
                    // Dette er en kommentar. Hvor er killa
=======
                    Text(text = "Lets start this shit!")
>>>>>>> b01dc16b349124f2baa771fdd4bb42f4d648ffa8
                }
            }
        }
    }
}