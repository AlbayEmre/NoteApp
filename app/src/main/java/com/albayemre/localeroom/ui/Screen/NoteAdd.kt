package com.albayemre.localeroom.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.albayemre.localeroom.Data.Local.Entity.DataEntity
import com.albayemre.localeroom.ViewModel.NoteViewModel
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddScreen(
    navController: NavController,
    viewModel: NoteViewModel,
    noteId: String? = null
) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val currentNote by viewModel.currentNote.collectAsState()

    // Notu yükle
    LaunchedEffect(noteId) {
        if (noteId != null) {
            viewModel.getNote(noteId)
        } else {
            viewModel.clearCurrentNote()
        }
    }

    // Alanları güncelle
    LaunchedEffect(currentNote) {
        currentNote?.let {
            title = it.noteTitle
            subtitle = it.noteSubtitle
            description = it.noteDescription
            imageUri = if (it.noteImagePath.isNotBlank()) Uri.parse(it.noteImagePath) else null
        } ?: run {
            title = ""
            subtitle = ""
            description = ""
            imageUri = null
        }
    }

    // Galeri açıcı
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Yeni Not" else "Notu Düzenle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isBlank() && subtitle.isBlank() && description.isBlank() && imageUri == null) {
                            scope.launch { snackbarHostState.showSnackbar("Boş not kaydedilemez") }
                            return@IconButton
                        }

                        val noteToSave = (currentNote ?: DataEntity()).copy(
                            noteTitle = title.trim(),
                            noteSubtitle = subtitle.trim(),
                            noteDescription = description.trim(),
                            noteImagePath = imageUri?.toString() ?: ""
                        )

                        if (noteId == null) {
                            viewModel.insertNote(noteToSave)
                        } else {
                            viewModel.updateNote(noteToSave)
                        }

                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Kaydet")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            // Başlık
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Başlık") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Alt Başlık
            OutlinedTextField(
                value = subtitle,
                onValueChange = { if (it.length <= 30) subtitle = it },
                label = { Text("Alt Başlık") },
                singleLine = true,
                supportingText = {
                    Text("${subtitle.length} / 30")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Açıklama
            OutlinedTextField(
                value = description,
                onValueChange = { if (it.length <= 500) description = it },
                label = { Text("Açıklama") },
                supportingText = { Text("${description.length} / 500") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                maxLines = Int.MAX_VALUE
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Görsel
            if (imageUri != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Seçilen Resim",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📷 Galeriden Resim Seç")
            }
        }
    }
}
