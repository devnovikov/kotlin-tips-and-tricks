rootProject.name = "kotlin-tips-and-tricks"

file("$rootDir/src").listFiles()?.filter { it.isDirectory }?.forEach {
    include("src:${it.name}")
}
include("src:extensions")
findProject(":src:extensions")?.name = "extensions"