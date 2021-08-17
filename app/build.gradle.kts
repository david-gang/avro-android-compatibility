plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }
    sourceSets {
        getByName("main") {
            java.srcDirs("build/avro")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled =false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

tasks.register("generateAvro"){
    doLast {
        val schemaFile = File("${project.projectDir.absolutePath}/schemas/example.avsc")
        val schema = org.apache.avro.Schema.Parser().parse(schemaFile)
        val compiler = org.apache.avro.compiler.specific.SpecificCompiler(schema)
        compiler.compileToDestination(schemaFile, File("${project.projectDir.absolutePath}/build/avro"))
    }
}

dependencies {
    implementation("org.apache.avro:avro:1.10.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
}