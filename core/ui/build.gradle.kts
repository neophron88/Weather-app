plugins {
    id("convention-android-library")
}

android {
    namespace = "com.neophron.ui"
}

dependencies {
    compileOnly(project(":library"))
}
