plugins {
    id("convention-android-library")
}

android {
    namespace = "com.neophron.network"

    buildTypes.forEach {
        it.buildConfigField("String", "BEARER_TOKEN", project.properties["bearer_token"].toString())
    }

}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.moshi)
    api(libs.retrofit)
}
