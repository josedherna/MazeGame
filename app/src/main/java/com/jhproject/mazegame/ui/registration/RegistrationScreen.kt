package com.jhproject.mazegame.ui.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhproject.mazegame.R
import com.jhproject.mazegame.data.Parent

@Preview
@Composable
fun RegistrationScreen(
    viewModel: RegistrationScreenViewModel = viewModel(),
    navigateToLogin: () -> Unit = {}
) {
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
        ParentRegistration(
            viewModel = viewModel,
            viewParentLogin = navigateToLogin
        )
    }
}

@Composable
fun ParentRegistration(
    viewModel: RegistrationScreenViewModel,
    viewParentLogin: () -> Unit
) {
    val parentName by viewModel.parentNameValue.collectAsState()
    val parentUsername by viewModel.parentUsernameValue.collectAsState()
    val parentPassword by viewModel.parentPasswordValue.collectAsState()
    val focusManager = LocalFocusManager.current

    val validInput = !parentName.isEmpty() && !parentUsername.isEmpty() && !parentPassword.isEmpty()

    Surface(
        modifier = Modifier
            .widthIn(max = 350.dp)
            .heightIn(max = 450.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.register_label),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = parentName,
                onValueChange = { newValue ->
                    viewModel.onParentNameValueChange(newValue)
                },
                label = {
                    Text(text = stringResource(R.string.name))
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
                enabled = validInput,
                onClick = {
                    viewModel.createParent(Parent(name = parentName, username = parentUsername, password = parentPassword))
                    viewModel.onParentNameValueChange("")
                    viewModel.onParentUsernameValueChange("")
                    viewModel.onParentPasswordValueChange("")
                }
            ) {
                Text(
                    text = stringResource(R.string.register_label)
                )
            }
            TextButton(
                onClick = viewParentLogin
            ) {
                Text(text = stringResource(R.string.login_label))
            }
        }
    }
}