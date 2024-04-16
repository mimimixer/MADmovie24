package com.example.movieappmad24.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.movieappmad24.R
import kotlinx.coroutines.delay

@Composable
fun PlayerTrailer (movieName: String){

    var lifecycle by rememberSaveable {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    var playbackPosition: Long = 0

    val context = LocalContext.current

    var trailerLocation = context.resources.getIdentifier(movieName, "raw", context.packageName)

    //println("the trailer URI is: android.resource://${context.packageName}/${trailerLocation}")
    //println("or is URI better: android.resource://${context.packageName}/${R.raw.trailer_placeholder}")

    val movieTrailer = MediaItem.fromUri(
            //"https://file-examples.com/storage/fe793dd9be65a9b389251ea/2017/04/file_example_MP4_480_1_5MG.mp4"
        //"android.resource://${context.packageName}/${trailerLocation}"
        "android.resource://${context.packageName}/${R.raw.mon_film}"

    )
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            println("playing again")
            setMediaItem(movieTrailer)
            prepare()
            playWhenReady = true
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            println(event)
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    it.onPause()
                    it.player?.seekTo(playbackPosition)
                    it.player?.play()
                }

                Lifecycle.Event.ON_STOP -> {
                        it.onResume()
                        playbackPosition = it.player?.currentPosition!!
                    }

                Lifecycle.Event.ON_DESTROY -> {
                    it.onResume()
                }

                else -> Unit
            }
        }
    )
}


