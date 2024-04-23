package com.example.movieappmad24.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MoviesViewModel


@Composable
fun PlayerTrailer (currentMovie: Movie, moviesViewModel: MoviesViewModel){

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val context = LocalContext.current

    val trailerLocation = context.resources.getIdentifier(currentMovie.trailer, "raw", context.packageName)

    val movieTrailer = MediaItem.fromUri(
            //"https://file-examples.com/storage/fe793dd9be65a9b389251ea/2017/04/file_example_MP4_480_1_5MG.mp4"
        "android.resource://${context.packageName}/${trailerLocation}"
        //"android.resource://${context.packageName}/${R.raw.mon_film}"

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
            println(lifecycleOwner)
            lifecycle = event
        }
        println(observer)
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            moviesViewModel.setCurrentPosition(currentMovie, exoPlayer.currentPosition)
            moviesViewModel.togglePlayer(currentMovie, exoPlayer.isPlaying)
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
                    it.onResume()
                    it.player?.seekTo(currentMovie.playerReset)
                    if (currentMovie.playerPlays) {
                        it.player?.play()
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    moviesViewModel.togglePlayer(currentMovie, exoPlayer.isPlaying)
                    moviesViewModel.setCurrentPosition(currentMovie, it.player?.currentPosition!!)
                    it.onPause()
                    it.player?.pause() //this makes it continue playing. if I use
                    //it.player?.stop() then on resume we will have to push the
                    //button again -> not so nice effect
                }

                else -> Unit
            }
        }
    )
}


