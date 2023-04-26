package com.example.thrillcast.ui.screens.introScreen2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.theme.GreenDark
import com.example.thrillcast.ui.theme.GreenLight

@Composable
fun IntroScreen2(onNavigate: () -> Unit) {

    Column(
        Modifier
            .background(color = GreenDark)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Icon(
                painter = painterResource(id = com.example.thrillcast.R.drawable.skjermbilde_2023_03_27_kl__17_52_54),
                null,
                Modifier.size(80.dp),
                tint = Color.White
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontSize = 40.sp,
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Column(
            Modifier
                .height(290.dp)
                .width(250.dp)
                //.padding(60.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = GreenLight)
                .fillMaxSize()
                .border(7.dp, color = Color(0xFFD6EF3E), shape = RoundedCornerShape(20.dp) ),
            verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = stringResource(id = R.string.choose_sport),
                style = MaterialTheme.typography.titleSmall,
                color = GreenDark,
                fontSize = 30.sp,
            )

            ElevatedButton(
                onClick = onNavigate,
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.width(200.dp),
                shape = RoundedCornerShape(30)
            ) {
                Text(
                    text = stringResource(id = R.string.paragliding),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 3.dp),
                    fontSize = 20.sp,
                )
            }

            ElevatedButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.width(200.dp),
                shape = RoundedCornerShape(30)
            ) {
                Text(
                    text = stringResource(id = R.string.hanggliding),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 3.dp),
                    fontSize = 20.sp,
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}