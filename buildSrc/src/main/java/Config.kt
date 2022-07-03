import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 21
    const val compileSdk = 32
    const val targetSdk = 32
    val javaVersion = JavaVersion.VERSION_11
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmTarget = "11"
    const val applicationId = "com.tech.building"
}