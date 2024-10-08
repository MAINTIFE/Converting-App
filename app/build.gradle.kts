import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
}

android {

    namespace = "com.example.aconverterapp"
    compileSdk = 34

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
            // You can add other files to exclude if necessary, like:
             excludes += "META-INF/LICENSE"
             excludes += "META-INF/LICENSE.txt"
             excludes += "META-INF/NOTICE"
             excludes += "META-INF/NOTICE.txt"
        }
    }
    defaultConfig {
        applicationId = "com.example.aconverterapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.json:json:20210307")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")

}