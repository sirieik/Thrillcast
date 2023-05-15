package com.example.thrillcast.ui.screens.mapScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

/**
 * Composable funksjon som viser en ansvarsfraskrivelsesdialog.
 * Brukeren må godta ansvarsfraskrivelsen før de kan fortsette.
 */
@Composable
fun DisclaimerDialog() {

    val dialogState: MutableState<Boolean> = remember { mutableStateOf(true) }

    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = { dialogState.value = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "DISCLAIMER:",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            text = {
                Text(
                    text = "The app is for reference purposes only. " +
                           "Users should exercise caution and use their " +
                            "judgment based on the provided information. " +
                    "The app provides no warranties and disclaims liability for damages " +
                    "or injuries resulting from its use. " +
                            "Users accept the risks associated with paragliding and " +
                            "assume responsibility for their actions."
                    ,


                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { dialogState.value = false },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = "Accept")
                    }
                }
            }
        )
    }
}