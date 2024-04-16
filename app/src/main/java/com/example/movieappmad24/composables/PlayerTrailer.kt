package com.example.movieappmad24.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FabPosition
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
import androidx.compose.ui.platform.LocalConfiguration
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
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MoviesViewModel
import kotlinx.coroutines.delay


@Composable
fun PlayerTrailer (currentMovie: Movie, moviesViewModel: MoviesViewModel){

    var lifecycle by rememberSaveable {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val context = LocalContext.current

    var trailerLocation = context.resources.getIdentifier(currentMovie.trailer, "raw", context.packageName)

    //println("the trailer URI is: android.resource://${context.packageName}/${trailerLocation}")
    //println("or is URI better: android.resource://${context.packageName}/${R.raw.trailer_placeholder}")

    val movieTrailer = MediaItem.fromUri(
            //"https://file-examples.com/storage/fe793dd9be65a9b389251ea/2017/04/file_example_MP4_480_1_5MG.mp4"
        //"android.resource://${context.packageName}/${trailerLocation}"
        "android.resource://${context.packageName}/${R.raw.mon_film}"

    )
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            println("playing again at ${currentMovie.playerReset}")
            setMediaItem(movieTrailer)
            seekTo(currentMovie.playerReset)
            prepare()
            if(currentMovie.playerPlays){
                playWhenReady = true
            }
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
            //println("jetzt wirds released anStelle ${exoPlayer?.bufferedPosition!!}")
            println("bufferedPosition is ${exoPlayer?.contentBufferedPosition!!}")
            println("exoPlayer?.currentPosition!! is ${exoPlayer?.currentPosition!!}")
            moviesViewModel.setCurrentPosition(currentMovie, exoPlayer?.currentPosition!!)
            moviesViewModel.togglePlayer(currentMovie, exoPlayer.isPlaying)
            println("exoplayer is plaing - I toggled on dispose ${exoPlayer.isPlaying}")
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
                    println("lifecycle.ON_RESUME an position ${currentMovie.playerReset}")
                    it.player?.seekTo(currentMovie.playerReset)
                    if (currentMovie.playerPlays) {
                        it.player?.play()
                    }
                }

                Lifecycle.Event.ON_PAUSE ->{
                    println("player gestoppt")
                    moviesViewModel.togglePlayer(currentMovie, exoPlayer.isPlaying)
                    println("exoplayer is plaing - I toggled on pause ${exoPlayer.isPlaying}")

                }

                Lifecycle.Event.ON_STOP -> {
                    println("jetzt ist ON_STOP und ${it.player?.currentPosition!!}")
                    moviesViewModel.setCurrentPosition(currentMovie, it.player?.currentPosition!!)
                    //moviesViewModel.togglePlayer(currentMovie, exoPlayer.isPlaying)
                    //println("exoplayer is plaing - I toggled on stop ${exoPlayer.isPlaying}")
                    println("jetzt ist ON_STOP mit position ${currentMovie.playerReset}")
                    it.onPause()
                    it.player?.pause()
                }

                else -> Unit
            }
        }
    )
}


