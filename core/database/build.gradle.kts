plugins {
    id("convention-android-library")
}

android {
    namespace = "com.neophron.database"
    kapt {
        arguments {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(libs.room)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
}
