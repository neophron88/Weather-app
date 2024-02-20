
plugins {
    id("convention-android-library")
}

android {
    namespace = "com.neophron.network"
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.moshi)
}
