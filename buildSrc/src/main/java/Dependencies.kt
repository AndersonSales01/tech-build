/**
 * To define plugins
 */
object BuildPlugins {
    val android = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val googleService = "com.google.gms:google-services:${Versions.google_service}"
}

/**
 * To define dependencies
 */
object Libs {
    val materialDesign = "com.google.android.material:material:${Versions.material}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val lottieAnimations = "com.airbnb.android:lottie:${Versions.lottie}"
    val koinAndroid = "io.insert-koin:koin-android:${Versions.koin_version}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val firebase = "com.google.firebase:firebase-bom:${Versions.firebase}"
    val camera = "androidx.camera:camera-camera2:${Versions.camerax_version}"
    val cameraLifeCycle = "androidx.camera:camera-lifecycle:${Versions.camerax_version}"
    val cameraView= "androidx.camera:camera-view:${Versions.camerax_version}"
    val mlKitVersion= "com.google.android.gms:play-services-mlkit-barcode-scanning:${Versions.mlKit_version}"
}