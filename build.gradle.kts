import org.gradle.jvm.tasks.Jar

plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "com.stock"
version = "1.0.2"

java {
	sourceCompatibility = JavaVersion.VERSION_19
}

repositories {
	mavenCentral()
}

configurations.all {
    exclude( group = "ch.qos.logback", module = "logback-classic")
}

dependencies {

	implementation ("org.springframework.boot:spring-boot-starter-log4j2")
    modules {
        module("org.springframework.boot:spring-boot-starter-logging") {
            replacedBy("org.springframework.boot:spring-boot-starter-log4j2", "Use Log4j2 instead of Logback")
        }
    }

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-web") {
       exclude( group = "org.springframework.boot", module = "spring-boot-starter-logging" ) 
    }
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    
	implementation("org.springframework.boot:spring-boot-starter-quartz")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("com.google.guava:guava:32.1.1-jre")
}


tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
