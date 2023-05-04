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
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            },
            text = {
                Text(
                    text = "This weather app for paragliders is " +
                            "intended for informational purposes only " +
                            "and should not be relied upon as the " +
                            "sole source of information for " +
                            "paragliding activities. Paragliding is an " +
                            "inherently dangerous activity, and users " +
                            "should always exercise caution and use " +
                            "their own judgment when making " +
                            "decisions based on the weather " +
                            "information provided by the app. The " +
                            "app does not provide any warranties, " +
                            "express or implied, regarding the " +
                            "accuracy or reliability of the information " +
                            "provided, and shall not be liable for any " +
                            "damages or injuries resulting from the " +
                            "use or inability to use the app or the " +
                            "information provided by it. By using this " +
                            "app, users acknowledge and accept the risks " +
                            "associated with paragliding and assume full " +
                            "responsibility for their actions.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
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

@Composable
@Preview
fun prevDisc() {
    DisclaimerDialog()
}
