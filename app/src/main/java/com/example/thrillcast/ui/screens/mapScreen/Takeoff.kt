import com.google.android.gms.maps.model.LatLng

data class Takeoff(
    val id: Int,
    val coordinates: LatLng,
    val name: String,
    val greenStart: Int,
    val greenStop: Int,
    //ADD Altitude(MOH)
    val moh : Int

)