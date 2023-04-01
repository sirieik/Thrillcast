package com.example.thrillcast.ui.screens.introScreen

import ThrillCastApp
import android.content.Context
import android.graphics.Paint.Align
import android.media.browse.MediaBrowser
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thrillcast.R
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import com.google.android.exoplayer2.ui.StyledPlayerView
import androidx.compose.foundation.shape.RoundedCornerShape

///////////////////

import android.view.ViewGroup.LayoutParams.MATCH_PARENT

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*

import androidx.compose.ui.focus.focusOrder

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM

private fun Context.buildExoPlayer(uri: Uri) =
    ExoPlayer.Builder(this).build().apply {
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = Player.REPEAT_MODE_ALL
        playWhenReady = true
        prepare()
    }

private fun Context.buildPlayerView(exoPlayer: ExoPlayer) =
    StyledPlayerView(this).apply {
        player = exoPlayer
        layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        useController = false
        resizeMode = RESIZE_MODE_ZOOM
    }

@ExperimentalMaterial3Api
@Composable
fun IntroScreen(videoUri: Uri) {
    val context = LocalContext.current
    //val passwordFocusRequester = FocusRequester()
    //val focusManager = LocalFocusManager.current
    val exoPlayer = remember { context.buildExoPlayer(videoUri) }

    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    ProvideWindowInsets {

        Column(
            Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*Icon(
                painter = painterResource(id = R.drawable.logo),
                null,
                Modifier.size(80.dp),
                tint = Color.Black
            )*/
            Spacer(modifier = Modifier.padding(25.dp))
            Icon(
                painter = painterResource(id = R.drawable.paragliding),
                null,
                Modifier.size(80.dp),
                tint = Color.White
            )
            Text(
                text = "ThrillCast",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontSize = 40.sp
            )
        }
        Column(
            Modifier
                .navigationBarsWithImePadding()
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                //ThrillCastApp()
            },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Paragliding",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 6.dp),
                )
            }
            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Skydiving",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 6.dp),
                    color = Color.Black)
            }
            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Hanggliding",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 6.dp),
                )
            }
            Spacer(modifier = Modifier.padding(50.dp))
        }
    }
}

