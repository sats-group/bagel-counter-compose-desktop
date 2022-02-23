import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val appVersion = "1.0.0"

plugins {
  kotlin("jvm") version "1.5.31"

  id("org.jetbrains.compose") version "1.0.0"
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

  implementation("org.jetbrains.compose.material:material-icons-extended-desktop:1.0.0")
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
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
