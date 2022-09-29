import org.bouncycastle.asn1.iana.IANAObjectIdentifiers.experimental

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "org.flepper.currencyconvertor.android"
    compileSdk = 32
    defaultConfig {
        applicationId = "org.flepper.currencyconvertor.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }
    buildFeatures {
        viewBinding = true
    }
    lint {
        abortOnError = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets {
        getByName("main") {
            java {
                srcDirs("src/main/java", "src/test")
            }
        }
    }

}

dependencies {
    val nav_version = "2.5.0"
    val compose_version = "1.2.1"

    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("androidx.activity:activity-ktx:1.5.1")

    // Material Components
    implementation("com.google.android.material:material:1.6.1")

    //Constraint Layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha01")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-beta02")


    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Integration with activities
    implementation("androidx.activity:activity-compose:1.5.1")

    // Compose Material Design
    implementation("androidx.compose.material:material:$compose_version")
    // Animations
    implementation("androidx.compose.animation:animation:$compose_version")
    // Tooling support (Previews, etc.)
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")    // Integration with ViewModels
    implementation("androidx.compose.ui:ui-viewbinding:$compose_version")    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")


    with(Deps.Koin) {
        implementation(core)
        implementation(android)
    }


    //Coil
    implementation("io.coil-kt:coil-compose:2.1.0")
    api("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    api("io.ktor:ktor-client-logging:1.6.6")


    implementation("androidx.compose.ui:ui-util:$compose_version")


    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1") {
        // https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-debug#debug-agent-and-android
        exclude(group = "org.jetbrains.kotlinx",module = "kotlinx-coroutines-debug")
    }
    testImplementation("app.cash.turbine:turbine:0.11.0")
    api("app.cash.turbine:turbine:0.11.0")

    implementation("androidx.fragment:fragment:1.5.3")
    implementation("androidx.lifecycle:lifecycle-process:2.5.1")
    implementation("androidx.lifecycle:lifecycle-service:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.1")
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.5.1")



}

