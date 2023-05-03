package com.example.thrillcast.ui.screens.mapScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.theme.GreenDark
import com.example.thrillcast.ui.theme.GreenLight
import com.example.thrillcast.ui.theme.gruppo
import com.example.thrillcast.ui.viemodels.map.MapViewModel
import com.example.thrillcast.ui.viemodels.map.Takeoff

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onCloseIconClick: () -> Unit,
    onNavigate: () -> Unit,
    mapViewModel: MapViewModel,
    onTakeoffSelected: (Takeoff) -> Unit
) {
    var searchInput by remember { mutableStateOf("") }
    val hideKeyboard = LocalFocusManager.current

    val uiState = mapViewModel.takeoffsUiState.collectAsState()

    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreenDark),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            OutlinedTextField(
                modifier = Modifier
                    //.width(320.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(shape = RectangleShape)
                    .background(Color.Transparent),
                value = searchInput,
                onValueChange = { searchInput = it },
                placeholder = { Text(text = stringResource(id = R.string.find_takeoff), color = GreenLight, style = MaterialTheme.typography.labelSmall, fontSize = 12.sp) },
                singleLine = true,
                maxLines = 1,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = GreenLight
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (searchInput.isNotEmpty()) {
                                searchInput = ""
                            } else {
                                onCloseIconClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Icon",
                            tint = GreenLight
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = GreenLight,
                    unfocusedBorderColor = GreenLight,
                    focusedBorderColor = GreenLight,
                    cursorColor = GreenLight,
                    unfocusedTrailingIconColor = Color.White,
                    focusedTrailingIconColor = Color.Black
                ),
                /*keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done,
                    locale = Locale("no", "NO")
                )*/
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (searchInput.isNotEmpty()) {
                items(uiState.value.takeoffs.filter {
                    it.name.contains(searchInput, ignoreCase = true)
                }) { takeoff ->
                    Card {
                        ClickableText(
                            text = AnnotatedString(takeoff.name),
                            onClick = {
                                searchInput = ""
                                hideKeyboard.clearFocus()
                                onTakeoffSelected(takeoff)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(GreenLight)
                                .border(2.dp, color = GreenDark, shape = RectangleShape)
                                .padding(vertical = 16.dp),

                            style = TextStyle(fontSize = 20.sp, color = GreenDark, fontFamily = gruppo)
                        )
                    }
                }
            }
        }
    }
}