plugins {
    id("java")
    `java-library-conventions`
    `junit4-compatibility`
    `testing-conventions`
}

//group = "com.ynan"
group = "junit5"
version = "5.7.2"


dependencies {
    internal(platform(project(":dependencies")))
    compile(project(":junit-jupiter-api"))
    compile(project(":junit-jupiter-engine"))
    compile(project(":junit-jupiter-params"))
    compile(project(":junit-platform-launcher"))
    //compile(project(":junit-bom"))
    compile(project(":junit-vintage-engine"))
    //compile("org.junit.vintage:junit-vintage-engine:5.7.2")
    compile("org.apiguardian:apiguardian-api:1.1.0")
    compile("org.opentest4j:opentest4j:1.2.0")

    //testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    //testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")
    //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

tasks.test {
    useJUnitPlatform()
}