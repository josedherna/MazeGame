package com.jhproject.mazegame.ui.parentlandingscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhproject.mazegame.R

@Composable
fun ParentLandingScreen(
    viewModel: ParentLandingScreenViewModel = viewModel()
) {
    val parent by viewModel.parent.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var childName by remember { mutableStateOf("") }
    var childUsername by remember { mutableStateOf("") }
    var childPassword by remember { mutableStateOf("") }

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
        if (parent != null) {
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
                    Text(text = "Welcome, ${parent!!.parent.name}",
                        style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                        contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = "Children:",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .heightIn(
                                min = 100.dp,
                                max = 150.dp
                            )
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    ) {
                        parent!!.children.forEach { child ->
                            item {
                                Text(text = child.name)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(onClick = { showDialog = true }) {
                        Text("Add Child")
                    }
                }
            }
        } else {
            Text("Loading...")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Add Child") },
                text = {
                    Column {
                        TextField(value = childName, onValueChange = { childName = it }, placeholder = { Text("Name") })
                        TextField(value = childUsername, onValueChange = { childUsername = it }, placeholder = { Text("Username") })
                        TextField(value = childPassword, onValueChange = { childPassword = it }, placeholder = { Text("Password") })
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.addChild(childName, childUsername, childPassword)
                        showDialog = false
                    }) {
                        Text("Add")
                    }
                }
            )
        }
    }
}
