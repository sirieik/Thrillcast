package com.example.thrillcast.ui.screens.introScreen2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IntroScreen2() {

    Column(
        Modifier
            .background(color = Color(0xFF93B3F3))
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(id = com.example.thrillcast.R.drawable.paragliding),
            null,
            Modifier.size(80.dp),
            tint = Color.White
        )
        Text(
            text = "ThrillCast",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White,
            fontSize = 40.sp,
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Column(
            Modifier
                .height(350.dp)
                .width(250.dp)
                //.padding(60.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = Color(0xFFF3EDF7))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Spacer(modifier = Modifier.padding(7.dp))

            Text(
                text = " Choose Sport", style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontSize = 30.sp,
            )
            
            Spacer(modifier = Modifier.padding(1.dp))

            Button(
                onClick = {
                    //ThrillCastApp()
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.width(200.dp),
                shape = RoundedCornerShape(30)
            ) {
                Text(
                    text = "Paragliding",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 3.dp),
                    fontSize = 20.sp,
                )
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.width(200.dp),
                shape = RoundedCornerShape(30)
            ) {
                Text(
                    text = "Skydiving",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 3.dp),
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.width(200.dp),
                shape = RoundedCornerShape(30)
            ) {
                Text(
                    text = "Hanggliding",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 3.dp),
                    fontSize = 20.sp,
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
        }
    }
}