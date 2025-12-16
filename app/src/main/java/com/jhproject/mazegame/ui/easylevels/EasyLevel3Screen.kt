package com.jhproject.mazegame.ui.easylevels

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jhproject.mazegame.R
import kotlin.collections.plus

@Preview
@Composable
fun EasyLevel3Screen(
    viewModel: EasyLevel3ScreenViewModel = viewModel(),
    playerBitmap: ImageBitmap = ImageBitmap.imageResource(R.drawable.sentiment_satisfied_45dp_e3e3e3_fill1_wght400_grad0_opsz48),
    onBack: () -> Unit = {}
) {
    val maze = listOf(
        listOf(1,1,1,1,1,1,1,1,1),
        listOf(1,0,0,0,0,0,0,0,1),
        listOf(1,0,1,1,1,1,0,1,1),
        listOf(1,0,1,0,0,0,0,1,1),
        listOf(1,0,1,0,1,0,1,1,1),
        listOf(1,0,0,0,1,0,0,0,1),
        listOf(1,1,1,0,1,1,1,0,1),
        listOf(1,0,0,0,0,0,0,0,1),
        listOf(1,1,1,1,1,1,1,2,1)
    )

    var playerRow by remember { mutableIntStateOf(1) }
    var playerCol by remember { mutableIntStateOf(1) }
    var hasWon by remember { mutableStateOf(false) }
    var commandList by remember { mutableStateOf(listOf<Command>()) }

    var draggingCommand by remember { mutableStateOf<Command?>(null) }
    var dragPosition by remember { mutableStateOf(Offset.Zero) }
    var dragVisible by remember { mutableStateOf(false) }

    val secondaryColor = MaterialTheme.colorScheme.secondary


    var dropBounds by remember { mutableStateOf<androidx.compose.ui.geometry.Rect?>(null) }

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val context = LocalContext.current


    fun handleDrop() {
        val cmd = draggingCommand
        val bounds = dropBounds
        if (cmd != null && bounds != null) {
            // check the pointer position is inside the drop bounds
            if (bounds.contains(dragPosition)) {
                commandList = commandList + cmd
            }
        }
        draggingCommand = null
        dragVisible = false
        dragPosition = Offset.Zero
    }

    fun tryMove(dr: Int, dc: Int) {
        val newR = playerRow + dr
        val newC = playerCol + dc
        if (newR !in maze.indices || newC !in maze[0].indices) return

        when (maze[newR][newC]) {
            0 -> { playerRow = newR; playerCol = newC }
            2 -> {
                playerRow = newR
                playerCol = newC
                hasWon = true
            }
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
        if (isPortrait) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MazeCanvas(
                    maze = maze,
                    playerRow = playerRow,
                    playerCol = playerCol,
                    playerBitmap = playerBitmap,
                    modifier = Modifier.size(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    CommandDraggable(
                        command = Command.Up,
                        onDragStart = { startWindowPos ->
                            draggingCommand = Command.Up
                            dragPosition = startWindowPos
                            dragVisible = true
                        },
                        onDrag = { delta -> dragPosition += delta },
                        onDragEnd = { handleDrop() }
                    )
                    CommandDraggable(
                        command = Command.Down,
                        onDragStart = { startWindowPos ->
                            draggingCommand = Command.Down
                            dragPosition = startWindowPos
                            dragVisible = true
                        },
                        onDrag = { delta -> dragPosition += delta },
                        onDragEnd = { handleDrop() }
                    )
                    CommandDraggable(
                        command = Command.Left,
                        onDragStart = { startWindowPos ->
                            draggingCommand = Command.Left
                            dragPosition = startWindowPos
                            dragVisible = true
                        },
                        onDrag = { delta -> dragPosition += delta },
                        onDragEnd = { handleDrop() }
                    )
                    CommandDraggable(
                        command = Command.Right,
                        onDragStart = { startWindowPos ->
                            draggingCommand = Command.Right
                            dragPosition = startWindowPos
                            dragVisible = true
                        },
                        onDrag = { delta -> dragPosition += delta },
                        onDragEnd = { handleDrop() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Black.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                        .onGloballyPositioned { coords ->
                            // boundsInWindow returns a rectangle in window coordinates
                            dropBounds = coords.boundsInWindow()
                        }
                        .padding(8.dp)
                        .verticalScroll(scrollState)
                ) {
                    FlowRow(modifier = Modifier.align(Alignment.CenterStart)) {
                        commandList.forEach { cmd ->
                            Box(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .background(secondaryColor, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(cmd.label, color = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row() {
                    Button(onClick = onBack) {
                        Text("Home")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        // execute commands
                        executeCommands(
                            commandList,
                            tryMove = { dr, dc -> tryMove(dr, dc) },
                            onFinish = {
                                viewModel.saveProgress(hasWon)
                                if (!hasWon) {
                                    playerRow = 1; playerCol = 1; hasWon = false; commandList =
                                        emptyList()
                                }
                            }
                        )
                    }) {
                        Text("Play")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        playerRow = 1; playerCol = 1; hasWon = false; commandList = emptyList()
                    }) {
                        Text("Reset")
                    }
                }

                if (hasWon) {
                    Toast.makeText(context, "Successfully completed level", Toast.LENGTH_SHORT).show()
                    onBack()
                }
            }

            val density = LocalDensity.current
            if (dragVisible && draggingCommand != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset {
                            IntOffset(
                                x = dragPosition.x.toInt() - 40,
                                y = dragPosition.y.toInt() - 24
                            )
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 80.dp, height = 48.dp)
                            .shadow(4.dp, RoundedCornerShape(8.dp))
                            .background(Color.Blue, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            draggingCommand!!.label,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                MazeCanvas(
                    maze = maze,
                    playerRow = playerRow,
                    playerCol = playerCol,
                    playerBitmap = playerBitmap,
                    modifier = Modifier.size(300.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                ) {
                    Row {
                        CommandDraggable(
                            command = Command.Up,
                            onDragStart = { startWindowPos ->
                                draggingCommand = Command.Up
                                dragPosition = startWindowPos
                                dragVisible = true
                            },
                            onDrag = { delta -> dragPosition += delta },
                            onDragEnd = { handleDrop() }
                        )
                        CommandDraggable(
                            command = Command.Down,
                            onDragStart = { startWindowPos ->
                                draggingCommand = Command.Down
                                dragPosition = startWindowPos
                                dragVisible = true
                            },
                            onDrag = { delta -> dragPosition += delta },
                            onDragEnd = { handleDrop() }
                        )
                        CommandDraggable(
                            command = Command.Left,
                            onDragStart = { startWindowPos ->
                                draggingCommand = Command.Left
                                dragPosition = startWindowPos
                                dragVisible = true
                            },
                            onDrag = { delta -> dragPosition += delta },
                            onDragEnd = { handleDrop() }
                        )
                        CommandDraggable(
                            command = Command.Right,
                            onDragStart = { startWindowPos ->
                                draggingCommand = Command.Right
                                dragPosition = startWindowPos
                                dragVisible = true
                            },
                            onDrag = { delta -> dragPosition += delta },
                            onDragEnd = { handleDrop() }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                            .onGloballyPositioned { coords ->
                                dropBounds = coords.boundsInWindow()
                            }
                            .padding(8.dp)
                            .verticalScroll(scrollState)
                    ) {
                        FlowRow(modifier = Modifier.align(Alignment.CenterStart)) {
                            commandList.forEach { cmd ->
                                Box(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .background(secondaryColor, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 12.dp, vertical = 8.dp)
                                ) {
                                    Text(cmd.label, color = Color.White)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row() {
                        Button(onClick = { }) {
                            Text("Home")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(onClick = {
                            // execute commands
                            executeCommands(
                                commandList,
                                tryMove = { dr, dc -> tryMove(dr, dc) },
                                onFinish = {
                                    viewModel.saveProgress(hasWon)
                                    if (!hasWon) {
                                        playerRow = 1; playerCol = 1; hasWon = false; commandList =
                                            emptyList()
                                    }
                                }
                            )
                        }) {
                            Text("Play")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            playerRow = 1; playerCol = 1; hasWon = false; commandList = emptyList()
                        }) {
                            Text("Reset")
                        }
                    }

                    if (hasWon) {
                        Toast.makeText(context, "Successfully completed level", Toast.LENGTH_SHORT).show()
                        onBack()
                    }
                }
            }
        }
    }
}