# Spring Boot Profile
Spring boot with Restful Example with profile Enabled.

Spring Boot supports all the profile configuration outlined so far, with a few additional features.

The initialization parameter spring.profiles.active can be set up as a property in Spring Boot to define currently active profiles. This is a standard property that Spring Boot will pick up automatically:

##### To set the profile from properties file

> spring.profiles.active=dev
    
##### To set profiles programmatically, we can also use the SpringApplication class:

> SpringApplication.setAdditionalProfiles("dev");

##### To set profiles using Maven in Spring Boot, we can specify profile names under spring-boot-maven-plugin in pom.xml:

> <plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <profiles>
                <profile>dev</profile>
            </profiles>
        </configuration>
    </plugin>
    ...
</plugins>

And execute the Spring Boot specific Maven goal:
> mvn spring-boot:run

##### To set profiles and override the profiles using Spring Boot specific Maven goal:
> mvn spring-boot:run -Dspring-boot.run.profiles=uat

##### To set profiles and override the profiles using java command:
> java -jar -Dspring.profiles.active=uat profiles-0.0.1-SNAPSHOT.jar

* Run by opening 3 separate command prompts:

    > java -jar -Dspring.profiles.active=uat profiles-0.0.1-SNAPSHOT.jar
    
    > java -jar -Dspring.profiles.active=dev profiles-0.0.1-SNAPSHOT.jar
    
    > java -jar -Dspring.profiles.active=prod profiles-0.0.1-SNAPSHOT.jar

* Steps to Run using Spring Boot maven commands  :
    ##### For Spring-Boot 1.x
    > mvn spring-boot:run -Dspring.profiles.active=uat

    > mvn spring-boot:run -Dspring.profiles.active=dev

    > mvn spring-boot:run -Dspring.profiles.active=prod

    ##### For Spring-Boot 2.x
    > mvn spring-boot:run -Dspring-boot.run.profiles=uat

    > mvn spring-boot:run -Dspring-boot.run.profiles=dev

    > mvn spring-boot:run -Dspring-boot.run.profiles=prod

**Thanks**