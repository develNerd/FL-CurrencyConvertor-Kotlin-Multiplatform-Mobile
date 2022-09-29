plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.4.0-alpha10").apply(false)
    id("com.android.library").version("7.4.0-alpha10").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
    //id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

buildscript {

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.android.tools.build:gradle:7.4.0-beta01")
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.13.3")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {setUrl("https://jitpack.io")}

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
