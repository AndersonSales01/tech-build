/**
 * To define plugins
 */
object BuildPlugins {
    val android = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
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

}