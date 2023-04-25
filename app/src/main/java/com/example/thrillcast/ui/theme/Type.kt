package com.example.thrillcast.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R

// Set of Material typography styles to start with

val goldman = FontFamily(
    Font(R.font.goldman_bold, FontWeight.Bold),
    Font(R.font.goldman_regular)
)
val gruppo = FontFamily(
    Font(R.font.goldman_regular)
)
val montserrat = FontFamily(
    Font(R.font.montserrat_extralight)
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
    headlineLarge = TextStyle(
        fontFamily = goldman,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        //lineHeight = 28.sp,
        //letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = gruppo,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp,
    )
)
