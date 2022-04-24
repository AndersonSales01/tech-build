import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 16
    const val compileSdk = 31
    const val targetSdk = 30
    val javaVersion = JavaVersion.VERSION_11
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmTarget = "11"
    const val applicationId = "com.tech.building"
}