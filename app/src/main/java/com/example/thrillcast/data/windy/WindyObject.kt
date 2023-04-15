import com.example.thrillcast.data.windy.Units
import com.google.gson.annotations.SerializedName

data class WindyObject (

    @SerializedName("ts"          ) var ts         : ArrayList<Long>    = arrayListOf(),
    @SerializedName("units"       ) var units      : Units?            = Units(),
    @SerializedName("wind_u-950h" ) var windU950h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_u-900h" ) var windU900h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_u-850h" ) var windU850h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_u-800h" ) var windU800h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-950h" ) var windV950h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-900h" ) var windV900h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-850h" ) var windV850h : ArrayList<Double> = arrayListOf(),
    @SerializedName("wind_v-800h" ) var windV800h : ArrayList<Double> = arrayListOf(),
    @SerializedName("warning"     ) var warning    : String?           = null

)
