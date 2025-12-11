package com.jhproject.mazegame.ui.login

import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhproject.mazegame.R

@Preview
@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = viewModel(),
    navigateToRegistration: () -> Unit = {},
    navigateToParentLanding: (Int) -> Unit = {},
    navigateToChildLanding: (Int) -> Unit = {}
) {
    var childMode by rememberSaveable { mutableStateOf(true) }

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
        if (childMode) {
            ChildLogin(
                viewModel = viewModel,
                viewParentLogin = { childMode = !childMode },
                navigateToChildLanding = navigateToChildLanding
            )
        }
        else {
            ParentLogin(
                viewModel = viewModel,
                viewChildLogin = { childMode = !childMode },
                navigateToRegistration = navigateToRegistration,
                navigateToParentLanding = navigateToParentLanding
            )
        }
    }
}

@Composable
fun ChildLogin(
    viewModel: LoginScreenViewModel,
    viewParentLogin: () -> Unit,
    navigateToChildLanding: (Int) -> Unit
) {
    val childUsername by viewModel.childUsernameValue.collectAsState()
    val childPassword by viewModel.childPasswordValue.collectAsState()
    val focusManager = LocalFocusManager.current
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
                text = stringResource(R.string.child_login),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = childUsername,
                onValueChange = { newValue ->
                    viewModel.onChildUsernameValueChange(newValue)
                },
                label = {
                    Text(text = stringResource(R.string.username))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Spacer(Modifier.height(15.dp))
            OutlinedTextField(
                value = childPassword,
                onValueChange = { newValue ->
                    viewModel.onChildPasswordValueChange(newValue)
                },
                label = {
                    Text(text = stringResource(R.string.password))
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Spacer(Modifier.height(15.dp))
            Button(
                onClick = {
                    viewModel.childLogin(childUsername, childPassword, {
                        navigateToChildLanding(it)
                    }, {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    })
                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                    } else {
                        mediaPlayer.pause()
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.login_label)
                )
            }
            TextButton(
                onClick = viewParentLogin
            ) {
                Text(text = stringResource(R.string.parent_login))
            }
        }
    }
}

@Composable
fun ParentLogin(
    viewModel: LoginScreenViewModel,
    viewChildLogin: () -> Unit,
    navigateToRegistration: () -> Unit,
    navigateToParentLanding: (Int) -> Unit
) {
    val parentUsername by viewModel.parentUsernameValue.collectAsState()
    val parentPassword by viewModel.parentPasswordValue.collectAsState()
    val focusManager = LocalFocusManager.current
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
                text = stringResource(R.string.parent_login),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = parentUsername,
                onValueChange = { newValue ->
                    viewModel.onParentUsernameValueChange(newValue)
                },
                label = {
                    Text(text = stringResource(R.string.username))
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Spacer(Modifier.height(15.dp))
            OutlinedTextField(
                value = parentPassword,
                onValueChange = { newValue ->
                    viewModel.onParentPasswordValueChange(newValue)
                },
                label = {
                    Text(text = stringResource(R.string.password))
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )
            Spacer(Modifier.height(15.dp))
            Button(
                onClick = { 
                    viewModel.parentLogin(parentUsername, parentPassword, {
                        navigateToParentLanding(it)
                    }, {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    })
                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                    } else {
                        mediaPlayer.pause()
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.login_label)
                )
            }
            Row() {
                TextButton(
                    onClick = viewChildLogin
                ) {
                    Text(text = stringResource(R.string.child_login))
                }
                TextButton(
                    onClick = navigateToRegistration
                ) {
                    Text(text = stringResource(R.string.register_label))
                }
            }
        }
    }
}