import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel : ViewModel() {

    //MVP hashMap
    private val takeoffsLocations = hashMapOf(
        "Sundvollen" to LatLng(60.05388889, 10.32250000),
        "Loen Skylift" to LatLng(61.884722, 6.835000),
        "VossHPK Hangur Øst" to LatLng(60.645556, 6.407778)
    )


    private val _uiState = MutableStateFlow(MapUiState(takeoffs = takeoffsLocations))

    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val HolfuyClient = HolfuyModel()

    init {

    }
}