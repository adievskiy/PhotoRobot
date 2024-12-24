package com.example.photorobot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoRobot()
        }
    }
}

@Composable
fun PhotoRobot() {
    val face = listOf(
        Portrait("Лицо 01", R.drawable.face_01),
        Portrait("Лицо 02", R.drawable.face_02),
        Portrait("Лицо 03", R.drawable.face_03),
        Portrait("Лицо 04", R.drawable.face_04),
        Portrait("Лицо 05", R.drawable.face_05)
    )
    val faceState = remember { mutableStateOf(face[0]) }

    val hair = listOf(
        Portrait("Волосы 01", R.drawable.hair_01),
        Portrait("Волосы 02", R.drawable.hair_02),
        Portrait("Волосы 03", R.drawable.hair_03)
    )
    val hairState = remember { mutableStateOf(hair[0]) }

    val eyes = listOf(
        Portrait("Глаза 01", R.drawable.eyes_01)
    )
    val eyesState = remember { mutableStateOf(eyes[0]) }

    val nose = listOf(
        Portrait("Нос 01", R.drawable.nose_01)
    )
    val noseState = remember { mutableStateOf(nose[0]) }

    val lips = listOf(
        Portrait("Губы 01", R.drawable.lips_01)
    )
    val lipsState = remember { mutableStateOf(lips[0]) }

    Scaffold(
        topBar = {
            TopBar {
                faceState.value = face[0]
                hairState.value = hair[0]
                eyesState.value = eyes[0]
                noseState.value = nose[0]
                lipsState.value = lips[0]
            }
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.base),
                    contentDescription = "base",
                    modifier = Modifier.size(250.dp)
                )
                Image(
                    painter = painterResource(faceState.value.image),
                    contentDescription = "face",
                    modifier = Modifier.size(250.dp)
                )
                Image(
                    painter = painterResource(hairState.value.image),
                    contentDescription = "hair",
                    modifier = Modifier.size(250.dp)
                )
                Image(
                    painter = painterResource(eyesState.value.image),
                    contentDescription = "eyes",
                    modifier = Modifier.size(250.dp)
                )
                Image(
                    painter = painterResource(noseState.value.image),
                    contentDescription = "nose",
                    modifier = Modifier.size(250.dp)
                )
                Image(
                    painter = painterResource(lipsState.value.image),
                    contentDescription = "lips",
                    modifier = Modifier.size(250.dp)
                )
            }
            Spacer(Modifier.height(15.dp))
            DDMenu(face, faceState)
            Spacer(Modifier.height(5.dp))
            DDMenu(hair, hairState)
            Spacer(Modifier.height(5.dp))
            DDMenu(eyes, eyesState)
            Spacer(Modifier.height(5.dp))
            DDMenu(nose, noseState)
            Spacer(Modifier.height(5.dp))
            DDMenu(lips, lipsState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DDMenu(list: List<Portrait>, listState: MutableState<Portrait>) {
    val state = remember { mutableStateOf(listState.value) }
    var listExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = listExpanded,
        onExpandedChange = { listExpanded = !listExpanded }
    ) {
        TextField(
            value = state.value.text,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = listExpanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = listExpanded,
            onDismissRequest = { listExpanded = false }) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.text) },
                    onClick = {
                        state.value = item
                        listState.value = state.value
                        listExpanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(clear: () -> Unit) {
    var menuExpanded by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "Фоторобот") },
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        clear()
                        menuExpanded = false
                    },
                    text = { Text("Очистить") }
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = Color.DarkGray,
            scrolledContainerColor = Color.DarkGray,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.White,
            actionIconContentColor = Color.Black
        )
    )
}

data class Portrait(val text: String, val image: Int)