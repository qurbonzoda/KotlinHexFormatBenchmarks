import kotlinx.benchmark.gradle.JvmBenchmarkTarget

plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.8"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.8")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

benchmark {
    configurations {
        register("primitive") {
            include("PrimitiveFormattingBenchmark")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }

        register("byteArray") {
            include("ByteArrayFormattingBenchmark")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }

        register("complexByteArray") {
            include("ComplexByteArrayFormattingBenchmark")
            warmups = 10
            iterations = 10
            iterationTime = 1
            iterationTimeUnit = "s"
        }
    }
    targets {
        register("main")
    }
}