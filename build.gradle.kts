// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    kotlin("kapt") version "1.9.24" apply false   // << kapt eklendi
    id("com.google.dagger.hilt.android") version "2.52" apply false // << Hilt plugin
}