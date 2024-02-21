plugins {
    id("convention-android-library")
}

android {
    namespace = "com.neophron.data"
}

dependencies {
    implementation(project(":library"))
    implementation(project(":domain"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

}
