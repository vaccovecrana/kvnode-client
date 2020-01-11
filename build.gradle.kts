plugins {
  `java-library`
  `maven-publish`
  jacoco
}

group = "io.vacco.summitdb"
version = "0.8.0"

java {
  withJavadocJar()
  withSourcesJar()
  sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11
  targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
}

repositories { jcenter() }

dependencies {
  api("com.github.chrisvest:stormpot:3.0")
  testImplementation("io.github.j8spec:j8spec:3.0.0")
}

object Publishing {
  const val gitUrl = "https://github.com/vaccovecrana/jtinn.git"
  const val siteUrl = "https://github.com/vaccovecrana/jtinn"
  const val libraryDesc = "SummitDB Java client"
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      versionMapping {
        usage("java-api") { fromResolutionOf("runtimeClasspath") }
        usage("java-runtime") { fromResolutionResult() }
      }
      pom {
        name.set(project.name)
        description.set(Publishing.libraryDesc)
        url.set(Publishing.siteUrl)
        licenses {
          license {
            name.set("The MIT License")
            url.set("https://opensource.org/licenses/MIT")
          }
        }
        developers {
          developer {
            id.set("jjzazuet")
            name.set("Jesus Zazueta")
            email.set("jjzazuet@vacco.io")
          }
        }
        scm {
          connection.set(Publishing.gitUrl)
          developerConnection.set(Publishing.gitUrl)
          url.set(Publishing.siteUrl)
        }
      }
    }
  }
  repositories {
    maven {
      name = "bintray"
      val bintrayUser = System.getenv("BINTRAY_USER")
      setUrl("https://api.bintray.com/maven/vaccovecrana/vacco-oss/${project.name}")
      credentials {
        username = bintrayUser
        password = System.getenv("BINTRAY_KEY")
      }
    }
  }
}
