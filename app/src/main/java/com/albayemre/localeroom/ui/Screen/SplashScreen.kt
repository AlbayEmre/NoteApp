package com.albayemre.localeroom.ui.Screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Animasyon için scale değeri
    var startAnimation by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f, // 0'dan 1'e büyüme
        animationSpec = tween(
            durationMillis = 1500, // animasyon süresi
            easing = {
                OvershootInterpolator(2f).getInterpolation(it) // esnek yay gibi etki
            }
        ), label = "scaleAnim"
    )

    // LaunchedEffect → ilk açılışta animasyon başlat + yönlendirme
    LaunchedEffect(true) {
        startAnimation = true
        delay(2500) // splash ekranda kalma süresi
        navController.popBackStack()
        navController.navigate(AppScreen.NOTE_LIST.name) // Anasayfa
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Logo animasyonlu
            Icon(
                imageVector = Icons.Default.NoteAdd,
                contentDescription = "Logo",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale) // animasyonlu scale
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App ismi
            Text(
                text = "My Notes",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
