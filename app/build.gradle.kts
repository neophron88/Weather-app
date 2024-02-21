
plugins {
    id("convention-android-application")
}

android {
    namespace = "com.neophron.weather"
    defaultConfig {
        applicationId = "com.neophron.weather"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            resValue("string", "app_name", "@string/application_name")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        debug {
            resValue("string", "app_name", "Debug Weathep app")
            applicationIdSuffix = ".debug"
        }
    }

}

dependencies {
    implementation(project(":library"))
    implementation(project(":core:ui"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:contract"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":features:main"))

}
