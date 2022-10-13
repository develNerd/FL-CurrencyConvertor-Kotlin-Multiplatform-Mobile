plugins {
    kotlin("multiplatform")
    id("com.codingfeline.buildkonfig")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.7.10"
    id("com.rickclephas.kmp.nativecoroutines") version "0.13.0"

}





kotlin {
    android()
    val serializationVersion = "1.4.0"
    val ktorVersion = "2.1.1"
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }



    sourceSets {
        sourceSets["commonTest"].dependencies {
        }
        sourceSets["androidTest"].dependencies {
            implementation("org.jetbrains.kotlin:kotlin-test:1.6.21")
            implementation("junit:junit:4.13")
        }
        val commonMain by getting {
            dependencies{
                implementation("io.ktor:ktor-client-auth:$ktorVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-cio:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                with(Deps.Koin) {
                    api(core)
                    api(test)
                }

                with(Deps.Kermit){
                    implementation(kermitMain)
                }

                with(Deps.JetpackPreviews){
                    implementation(datasore_okio)
                    implementation(datasore)
                }

                api(Deps.multiplatformSettings)
                api(Deps.multiplatformSettingsCoroutines)

                implementation(kotlin("stdlib-common"))
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:1.6.21")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
            }
            tasks{

            }
        }


        val androidMain by getting{
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

    }
}

android {
    namespace = "org.flepper.currencyconvertor"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}


//TODO(Comment out if you need to use Buildkonfig)
/**
 *
In local.properties set

sdk.dir=/Users/d3vnerd/Library/Android/sdk
WEB_CLIENT_API_KEY=API_CLIENT_STRING

val webApiKey:String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
rootDir
).getProperty("WEB_CLIENT_API_KEY")


 */

buildkonfig {
    packageName = "org.flepper.currencyconvertor"
    val webKey = "web_key"

    defaultConfigs {
        buildConfigField(
            com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            webKey,
            "webApiKey" //replace with  webApiKey variable
        )
    }

}
