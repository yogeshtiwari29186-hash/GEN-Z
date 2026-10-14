plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "in.genz.seofixer"
    compileSdk = 35
    defaultConfig {
        applicationId = "in.genz.seofixer"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
