plugins {
    id("convention-android-library")
}


android {
    namespace = "com.neophron88.library"
}


dependencies {
    implementation(libs.recyclerview)
    implementation(libs.lifecycle.runtime)
    implementation(libs.fragment.ktx)
    compileOnly(libs.viewBinding)
}