import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val appVersion = "1.1.0"

plugins {
  kotlin("jvm") version "1.9.21"

  id("org.jetbrains.compose") version "1.5.11"
}

group = "com.sats.bagels"
version = appVersion

repositories {
  google()
  mavenCentral()

  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
  implementation(compose.desktop.currentOs)

  testImplementation(kotlin("test"))

  implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.5.11")

  implementation("com.microsoft.signalr:signalr:8.0.0")
}

java {
  sourceCompatibility = JavaVersion.VERSION_16
  targetCompatibility = JavaVersion.VERSION_16
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "16"

    freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      modules("java.sql")

      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

      packageName = "bagels"
      packageVersion = appVersion

      val iconsRoot = project.file("src/main/resources")

      macOS {
        iconFile.set(iconsRoot.resolve("icon_macos.icns"))
      }
    }
  }
}
