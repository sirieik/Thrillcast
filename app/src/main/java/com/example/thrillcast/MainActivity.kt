package com.example.thrillcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.thrillcast.ui.ThrillCastApp
import com.example.thrillcast.ui.theme.ThrillCastTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity-klassen for ThrillCast-appen.
 *
 * Denne aktiviteten er det primære inngangspunktet for applikasjonen. Den setter opp hoved theme-et og innholdet i appen,
 * som leveres av composable-funksjonen [ThrillCastApp].
 *
 * Denne klassen er merket med `@AndroidEntryPoint`-annoteringen, noe som betyr at den kan motta avhengigheter
 * fra dependency injection systemet Hilt. Derfor kan avhengigheter injiseres direkte i denne
 * aktiviteten fra et Hilt DI-komponent.
 *
 * Hvis aktiviteten blir re-initialisert etter å ha blitt lukket tidligere, inneholder savedInstanceState
 * Bundle dataene den sist leverte i [onSaveInstanceState], ellers er den null.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThrillCastTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ThrillCastApp(context = this)
                }
            }
        }
    }
}