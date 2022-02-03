plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 31
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        // testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
        externalNativeBuild {
            cmake {
                cppFlags("-std=c++17")
            }
        }
    }

    buildTypes {
        release {
            // minifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
        }
    }
    buildFeatures {
        viewBinding = true
    }

    // Start of product flavor definition
    // Expected native abi for flavors:
    // GreenApple       =>  "x86_64" and "x86"
    // YellowPineapple  =>  "arm64-v8a"
    defaultConfig {
        ndk {
            abiFilters.clear()
        }
    }

    flavorDimensions += "Colors"
    flavorDimensions += "Fruits"

    productFlavors {
        create("Apple") {
            dimension = "Fruits"
            ndk {
                abiFilters.clear()
                abiFilters += "x86_64"
                abiFilters += "x86"
            }
        }
        create("Pineapple") {
            dimension = "Fruits"
            ndk {
                abiFilters.clear()
                abiFilters += "arm64-v8a"
            }
        }
        create("Yellow") { dimension = "Colors" }
        create("Green") { dimension = "Colors" }
    }

    variantFilter {
        this.ignore = !(name.startsWith("GreenApple") || name.startsWith("YellowPineapple"))
    }

    // End of product flavor definition
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}