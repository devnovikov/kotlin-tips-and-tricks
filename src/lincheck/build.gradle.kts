import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.22"
}

group = "devnovikov"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	testImplementation("org.jetbrains.kotlinx:lincheck:2.17")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
	jvmArgs("--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED", "--add-exports=java.base/jdk.internal.util=ALL-UNNAMED")
}
