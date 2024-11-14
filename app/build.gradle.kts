
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

android {
    namespace = "com.example.githookcheck"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.githookcheck"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
    buildFeatures {
        viewBinding = true
    }
}
dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:12.1.1")
}
tasks.register<Copy>("installGitHook") {
    // Define the hook name you want to install, e.g., "pre-commit"
    val hookName = "pre-commit"

    // Specify the source path of your hook script
    from("$rootDir/scripts/$hookName") // Assuming your script is in "scripts" directory at project root

    // Specify the destination path of the Git hooks directory
    into("$rootDir/.git/hooks/")

    // Rename the file to the required hook name (e.g., "pre-commit")
    rename { hookName }

    // Make the file executable
    doLast {
        file("$rootDir/.git/hooks/$hookName").setExecutable(true)
    }
}

// Optionally, run the installGitHook task automatically before each build
tasks.named("build") {
    dependsOn("installGitHook")
}
