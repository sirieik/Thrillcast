package com.example.thrillcast.ui.screens.mapScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapBottomInfoSheet(modifier: Modifier = Modifier, selectedMarker: Marker?, bottomSheetVisible: Boolean, onCloseSheet: () -> Unit) {
    // Important: the selectedMarker is used to retrieve data on the chosen location. An example behaviour below.
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)

    // This is the control for whether the sheet is visible or not. It uses parameters to the composable, ie. handled in the MapScreen
    LaunchedEffect(bottomSheetVisible) {
        coroutineScope.launch {
            if (bottomSheetVisible) {
                bottomSheetState.expand()
            } else {
                bottomSheetState.collapse()
            }
        }
    }

    // The actual scaffold; what we see on screen
    BottomSheetScaffold(
        sheetContent = {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    /* three buttons */
                    Text(text = selectedMarker?.title ?: "")
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            top = 12.dp,
                            end = 20.dp,
                            bottom = 12.dp
                        )
                    ) {
                        /* content: an icon */
                        Image(
                            modifier = modifier
                                .size(64.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(50)),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(com.example.thrillcast.R.drawable.baseline_info_24),
                            /*
                             * Content Description is not needed here - image is decorative, and setting a null content
                             * description allows accessibility services to skip this element during navigation.
                             */
                            contentDescription = null
                            // NB : burde ha med dette for å sikre UU
                        )
                    }
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            top = 12.dp,
                            end = 20.dp,
                            bottom = 12.dp
                        )
                    ) {
                        Text(
                            text = "NOW"
                        )
                    }
                    Button(
                        onClick = {},
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            top = 12.dp,
                            end = 20.dp,
                            bottom = 12.dp
                        )
                    ) {
                        Text(
                            text = "TOMORROW"
                        )
                    }
                }
            },
        sheetPeekHeight = 235.dp,
        sheetGesturesEnabled = true,
        scaffoldState = rememberBottomSheetScaffoldState(),
    ) {
        // A closing button for the sheet
        Button(onClick = { onCloseSheet() }) {
            Text(text = "Close")
        }
    }
}

