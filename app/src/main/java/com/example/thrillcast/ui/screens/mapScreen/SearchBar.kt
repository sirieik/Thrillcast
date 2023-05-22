package com.example.thrillcast.ui.screens.mapScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.theme.Silver
import com.example.thrillcast.ui.theme.DarkBlue
import com.example.thrillcast.ui.theme.gruppo
import com.example.thrillcast.ui.theme.montserrat
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.map.Takeoff

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
    val focusRequester = remember { FocusRequester() }
    var isTakeoffSelected by remember { mutableStateOf(false) }
    val uiState = mapViewModel.takeoffsUiState.collectAsState()

    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkBlue)
                .focusRequester(focusRequester),
            verticalAlignment = Alignment.CenterVertically,

            ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(shape = RectangleShape)
                    .background(Color.Transparent)
                    .border(1.dp, color = Silver, RectangleShape),
                value = searchInput,
                onValueChange = { searchInput = it },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.find_takeoff),
                        color = Silver,
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 15.sp
                    )
                },
                singleLine = true,
                maxLines = 1,
                shape = RectangleShape,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Silver
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
                            tint = Silver
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Silver,
                    cursorColor = Silver,
                    unfocusedTrailingIconColor = Color.White,
                    focusedTrailingIconColor = Color.Black
                ),
                //Kan endre denne
                textStyle = MaterialTheme.typography.bodySmall,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),

            )
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (searchInput.isNotEmpty()) {
                items(uiState.value.takeoffs.filter {
                    it.name.contains(searchInput, ignoreCase = true)
                }) { takeoff ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Silver)
                            .border(1.dp, color = DarkBlue, shape = RectangleShape)
                            .height(60.dp),
                        content = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                                ClickableText(
                                    text = AnnotatedString(takeoff.name),
                                    onClick = {
                                        searchInput = ""
                                        hideKeyboard.clearFocus()
                                        onTakeoffSelected(takeoff)
                                        isTakeoffSelected = true
                                    },
                                    style = TextStyle(fontSize = 20.sp, color = DarkBlue, fontFamily = gruppo, fontWeight = FontWeight.ExtraBold),
                                    modifier = Modifier.padding(start = 3.dp)
                                )
                            }
                        }
                    )
                }
            }
        }
    }
    if (isTakeoffSelected) {
        TopBar(
            onSearchIconClick = {
                isTakeoffSelected = false
            },
            onNavigate = onNavigate,
            mapViewModel = mapViewModel,
            onTakeoffSelected = onTakeoffSelected
        )
    }
}