plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}


buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.3.15") // ✅ Firebase용
    }
}
