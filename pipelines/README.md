## Maven

OSWAP
```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <version>7.0.4</version>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <format>HTML</format>
        <outputDirectory>target/dependency-check-report</outputDirectory>
    </configuration>
</plugin>
```

```bash
mvn checkstyle:check
```

Para pushear a Nexus con un settings.xml
```xml
<settings>
    <servers>
        <server>
            <id>nexus-releases</id> <!-- Debe coincidir con el id en el POM -->
            <username>admin</username>
            <password>1234</password>
        </server>
    </servers>
</settings>
```

Y esto otro para referenciar al Nexus en el pom.xm
```xml
    <distributionManagement>
        <repository>
            <!-- ID del repositorio en Nexus -->
            <id>nexus-releases</id>
            <!-- URL del repositorio Nexus para releases -->
            <url>http://autojenkins-nexus.autojenkins.svc.cluster.local:8081/repository/autojenkins/</url>
        </repository>
    </distributionManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.valentiasoft.backapislackconnector.BackApiSlackConnectorApplication.groovy</mainClass> <!-- Clase principal -->
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
