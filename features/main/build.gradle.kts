@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("convention-android-feature")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.neophron.main"
}

dependencies {
    implementation(project(":library"))
    implementation(project(":core:ui"))
    implementation(project(":core:contract"))
    implementation(project(":domain"))

}
