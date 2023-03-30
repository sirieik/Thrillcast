

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.R
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: MapViewModel = viewModel(),) {

    val norway = LatLng(62.0, 10.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(norway, 5.5f)
    }

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SearchBarDemo() },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    uiState.value.takeoffs.forEach{
                        Marker(
                            state = MarkerState(it.value),
                            title = it.key,
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.parachuting)
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBarDemo() {
    val searchInput = remember { mutableStateOf("") }
    val hideKeyboard = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
            /*TODO - legge til NAV bar der man går tilbake til start skjerm*/
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Go back"
            )
        }
        OutlinedTextField(
            value = searchInput.value,
            onValueChange = { searchInput.value = it },
            label = { Text("Find takeoff locations") },
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                searchInput.value = ""
                hideKeyboard.clearFocus()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Exit search"
            )
        }
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    //MapScreen()
}

