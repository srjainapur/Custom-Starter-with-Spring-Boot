Custom Starter with Spring Boot

1. Locating Auto Configuration Classes
On starting our application, Spring Boot checks for a specific file named as spring.factories. This file is located in the META-INF directory. Here is an entry from the spring-boot-autoconfigure.

# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.zensar.config.HelloServiceAutoConfiguration


All auto configuration classes should list under EnableAutoConfiguration key in the spring.factories property file.Let’s pay our attention to few key points in the auto-configuration file entry.
	Based on the configuration file, Spring Boot will try to run all these configurations.
	Actual class configuration load will depend upon the classes on the classpath (e.g. if Spring find JPA in classpath, it will load JPA configuration class)
	
1.2 Conditional Annotation
Spring Boot use annotations to determine if an autoconfiguration class needs to be configured or not.The @ConditionalOnClass and @ConditionalOnMissingClass 
annotations help Spring Boot to determine if an auto-configuration class needs to be included or not.In the similar fashion @ConditionalOnBean and 
@ConditionalOnMissingBean are used for spring bean level autoconfiguration.

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zensar.service.HelloService;
import com.zensar.service.HelloServiceImpl;

@Configuration
@ConditionalOnClass(HelloService.class)
public class HelloServiceAutoConfiguration {
	...
	...
}

Conditional Annotation
Spring Boot use default values for the beans initialization. These defaults are based on the Spring environment properties. @EnableConfigurationProperties is declared with HelloProperties class.
Here is the code snippet for this class

@ConfigurationProperties(prefix = "spring.hello")
public class HelloProperties {
   private static final Charset DEFAULT_CHARSET = 
   StandardCharsets.UTF_8;
   
   private String message; 
}

Properties defined in the HelloProperties file are the default properties for HelloServiceAutoConfiguration class while initializing beans. Spring Boot allows us to override these 
configuration properties using application.properties file. To override default port, we need to add the following entry in our application.properties file.

spring.hello.message=Welcome to Custom Spring Starter .  (prefix+property name)

2. Custom Starter with Spring Boot
    the hello-service-spring-boot-starter with HelloService which takes the name as input to say hello.
    HelloService will use the default configuration for the default name.
    We will create Spring Boot demo application for using our hello-service-starter.
	
2.1 The Auto-Configure Module
The hello-service-spring-boot-starter will have the following classes and configurations

    HelloServiveProperties file for default name.
    HelloService interface and HelloServiceImpl class.
    HelloServiceAutoConfiguration to create HelloService Bean.
    The pom.xml file for bringing required dependencies to our custom starter. 
	
	The final piece of our auto-configuration is the addition of this class in the spring.factories property file located in the /src/main/resources/META-INF.
	org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.zensar.config.HelloServiceAutoConfiguration
	
On application startup
    HelloServiceAutoConfiguration will run if HelloService class is available in the classpath. ( @ConditionOnClass annotation).
    HelloService Bean will be created by Spring Boot if it is not available (@ConditionalOnMissingBean).
    If developer defines their own HelloService bean, our customer starter will not create HelloService Bean.

2.2 The pom.xml
The last part of the custom starter is the pom.xml to bring in all the required dependencies.

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.zensar</groupId>
	<artifactId>hello-service-spring-boot-starter</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>hello-service-spring-boot-starter</name>
	<description>Demo project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-autoconfigure</artifactId>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>


3. Using the custom starter
Let’s create a sample Spring Boot application to use our custom starter. Once We create starter app, add the custom starter as a dependency in pom.xml.

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.zensar.custom</groupId>
	<artifactId>custom-starter-demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>custom-starter-demo</name>
	<description>Demo project for Spring Boot</description>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>
		
		<dependency>
			<groupId>com.zensar</groupId>
			<artifactId>hello-service-spring-boot-starter</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

If we run our application, you will see following output in the console

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.3.RELEASE)

2019-03-20 14:37:24.946  INFO 17188 --- [           main] c.z.custom.CustomStarterDemoApplication  : Starting CustomStarterDemoApplication on DLXW51S0140 with PID 17188 (D:\Microservices\Secured_MS_WS\custom-starter-demo\target\classes started by SJ45506 in D:\Microservices\Secured_MS_WS\custom-starter-demo)
2019-03-20 14:37:24.952  INFO 17188 --- [           main] c.z.custom.CustomStarterDemoApplication  : No active profile set, falling back to default profiles: default
2019-03-20 14:37:25.384  INFO 17188 --- [           main] c.z.custom.CustomStarterDemoApplication  : Started CustomStarterDemoApplication in 0.7 seconds (JVM running for 1.227)
Hello from the default starter


We have defined no HelloService is our demo application. When Spring Boot started, auto-configuration did not find any custom bean definition. 
Our custom starter auto configuration class created default “HelloService” bean. (as visible from the output).To understand Spring Boot auto-configuration logic and 
functionality, let’s create a custom HelloService bean in our sample application


  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.3.RELEASE)

2019-03-20 14:38:17.862  INFO 15332 --- [           main] c.z.custom.CustomStarterDemoApplication  : Starting CustomStarterDemoApplication on DLXW51S0140 with PID 15332 (D:\Microservices\Secured_MS_WS\custom-starter-demo\target\classes started by SJ45506 in D:\Microservices\Secured_MS_WS\custom-starter-demo)
2019-03-20 14:38:17.865  INFO 15332 --- [           main] c.z.custom.CustomStarterDemoApplication  : No active profile set, falling back to default profiles: default
2019-03-20 14:38:18.280  INFO 15332 --- [           main] c.z.custom.CustomStarterDemoApplication  : Started CustomStarterDemoApplication in 0.689 seconds (JVM running for 1.216)
We are overriding our custom Hello Service

When we defined our custom bean, Spring Boot default HelloService is no longer available. This enables developers to completely override default bean definition by creating/ providing their own bean definition

