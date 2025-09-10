package com.albayemre.localeroom.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.albayemre.localeroom.Data.Local.Entity.DataEntity
import com.albayemre.localeroom.ViewModel.NoteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val noteList by viewModel.noteList.collectAsState()

    var showMenu by remember { mutableStateOf(false) }
    var isSearch by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var showDeleteAllAlert by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearch) {
                        TextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                viewModel.searchNote(it)
                            },
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            ),
                            label = { Text("Ara", color = MaterialTheme.colorScheme.onPrimary) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            maxLines = 1,
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = "Notlar",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isSearch) {
                            searchText = ""
                            viewModel.getAllNoteData()
                        }
                        isSearch = !isSearch
                    }) {
                        Icon(
                            imageVector = if (isSearch) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = if (isSearch) "Kapat" else "Ara",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menü",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Tümünü Sil", fontSize = 15.sp) },
                            onClick = {
                                showMenu = false
                                if (noteList.isNotEmpty()) showDeleteAllAlert = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Tema Değiştir", fontSize = 15.sp) },
                            onClick = {
                                showMenu = false
                                onThemeChange(!isDarkTheme)
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Not Ekle") },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Yeni Not") },
                onClick = { 
                    viewModel.clearCurrentNote()
                    navController.navigate(AppScreen.NOTE_ADD.name)
                }
            )
        }
    ) { innerPadding ->
        if (noteList.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Henüz not yok. Sağ alttan ekleyebilirsin.",
                    modifier = Modifier.padding(24.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = noteList,
                    key = { it.noteID }
                ) { note: DataEntity ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                // notId parametresiyle Add screen’e git
                                navController.navigate(AppScreen.NOTE_ADD.name + "?noteId=${note.noteID}")
                            }
                    ) {
                        Text(
                            text = note.noteTitle,
                            style = MaterialTheme.typography.titleMedium
                        )
                        if (note.noteSubtitle.isNotBlank()) {
                            Text(
                                text = note.noteSubtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                        if (note.noteDescription.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = note.noteDescription,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (note.noteImagePath.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            AsyncImage(
                                model = note.noteImagePath,
                                contentDescription = "Not resmi",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            )
                        }
                    }
                    Divider()
                }
            }

        }

        if (showDeleteAllAlert) {
            AlertDialog(
                onDismissRequest = { showDeleteAllAlert = false },
                title = { Text("Tüm Notları Sil") },
                text = { Text("Tüm notlar silinecek. Emin misiniz?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.deleteAllNotes()
                        showDeleteAllAlert = false
                    }) { Text("Evet") }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteAllAlert = false }) { Text("Hayır") }
                }
            )
        }
    }
}
