package com.example.thrillcast.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R

// Set of Material typography styles to start with

val Exo = FontFamily(
    Font(R.font.exo_regular, FontWeight.Bold),
    Font(R.font.exo_semibolditalic)
)

val Typography = Typography(
    /*h1 = TextStyle(
        fontFamily = Exo,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    ),
    body1 = TextStyle(
        fontFamily = Exo,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),*/
    // Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = Exo,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        //lineHeight = 28.sp,
        //letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Exo,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
        //lineHeight = 16.sp,
        //letterSpacing = 0.5.sp
    )

)
