package com.jhproject.mazegame.ui.childlandingscreen

import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhproject.mazegame.R

@Composable
fun ChildLandingScreen(
    viewModel: ChildLandingScreenViewModel = viewModel(),
    navigateToLevel1: () -> Unit = {},
    navigateToLevel2: () -> Unit = {},
) {
    val child by viewModel.child.collectAsState()

    var easyMode by rememberSaveable { mutableStateOf(true) }
    var difficultySelection by rememberSaveable() { mutableStateOf(true) }
    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.buttonpressed) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.forest),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if (child != null) {
            if (difficultySelection) {
                Surface(
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                        .heightIn(max = 350.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Welcome, ${child!!.name}",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Text(
                            text = stringResource(R.string.difficulty_label),
                        )

                        Spacer(modifier = Modifier.height(55.dp))

                        Button(onClick = {
                            easyMode = true
                            difficultySelection = false
                            if (!mediaPlayer.isPlaying) {
                                mediaPlayer.start()
                            } else {
                                mediaPlayer.pause()
                            }
                        }) {
                            Text(text = stringResource(R.string.easy))
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Button(onClick = {
                            easyMode = false
                            difficultySelection = false
                            if (!mediaPlayer.isPlaying) {
                                mediaPlayer.start()
                            } else {
                                mediaPlayer.pause()
                            }
                        }) {
                            Text(text = stringResource(R.string.hard))
                        }

                        Spacer(modifier = Modifier.height(55.dp))
                    }
                }
            }
            else if (easyMode) {
                EasyDifficulty(
                    navigateBack = {
                        difficultySelection = true
                        easyMode = false
                    },
                    navigateToLevel1 = navigateToLevel1,
                    navigateToLevel2 = navigateToLevel2

                )
            }
            else {
                HardDifficulty(
                    navigateBack = {
                        difficultySelection = true
                    }
                )
            }
        } else {
            Surface(
                modifier = Modifier
                    .widthIn(max = 350.dp)
                    .heightIn(max = 350.dp)
            ) {
                Text(text = stringResource(R.string.loading))
            }
        }
    }
}

@Composable
fun EasyDifficulty(
    navigateBack: () -> Unit,
    navigateToLevel1: () -> Unit,
    navigateToLevel2: () -> Unit = {},
) {
    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.buttonpressed) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Surface(
        modifier = Modifier
            .widthIn(max = 350.dp)
            .heightIn(max = 350.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.easy),
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = stringResource(R.string.level_label),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                navigateToLevel1()
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                } else {
                    mediaPlayer.pause()
                }
            }) {
                Text(text = stringResource(R.string.level1))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                navigateToLevel2()
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                } else {
                    mediaPlayer.pause()
                }
            }) {
                Text(text = stringResource(R.string.level2))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                } else {
                    mediaPlayer.pause()
                }
            }) {
                Text(text = stringResource(R.string.level3))
            }

            TextButton(
                onClick = navigateBack
            ) {
                Text(text = stringResource(R.string.back))
            }
        }
    }
}

@Composable
fun HardDifficulty(
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.buttonpressed) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Surface(
        modifier = Modifier
            .widthIn(max = 350.dp)
            .heightIn(max = 350.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.hard),
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = stringResource(R.string.level_label),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { }) {
                Text(text = stringResource(R.string.level1))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { }) {
                Text(text = stringResource(R.string.level2))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { }) {
                Text(text = stringResource(R.string.level3))
            }

            TextButton(
                onClick = navigateBack
            ) {
                Text(text = stringResource(R.string.back))
            }
        }
    }
}
