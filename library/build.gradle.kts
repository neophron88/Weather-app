plugins {
    id("convention-android-library")
}


android {
    namespace = "com.neophron88.library"
}


dependencies {
    implementation(libs.recyclerview)
    compileOnly(libs.lifecycle.runtime)
    compileOnly(libs.viewBinding)
}