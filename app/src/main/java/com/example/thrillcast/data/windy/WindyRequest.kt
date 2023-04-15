data class WindyRequest(
    val lat: Double,
    val lon: Double,
    val model: String = "iconEu",
    val parameters: Array<String> = arrayOf("wind"),
    val levels: Array<String> = arrayOf("950h", "900h", "850h", "800h"),
    val key: String = "ZNn24b3G6rq28A1xMOmRFHJ6YYmzv45C"
)