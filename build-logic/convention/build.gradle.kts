plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`

}
dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation("com.android.tools.build:gradle:8.1.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")

}
