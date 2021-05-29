plugins { id("io.vacco.oss.gitflow") version "0.9.8" }

group = "io.vacco.kvnode"
version = "0.8.0"

configure<io.vacco.oss.gitflow.GsPluginProfileExtension> {
  addJ8Spec()
  addClasspathHell()
  sharedLibrary(true, false)
}

val api by configurations

dependencies {
  api("com.github.chrisvest:stormpot:3.1")
}
