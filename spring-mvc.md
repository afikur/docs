## Spring for Absolute Beginners
The prerequisite of this doc is `Maven Funamentals`. If you don't know about maven, take a look at [Maven Fundamentals](). In this doc we are using `Intellij Idea Ultimate 18.1`. If you don't know how to create a maven project in Intellij Idea follow this doc [Create Maven Project]().

## Dependencies
In `pom.xml` add following dependency.

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.0.5.RELEASE</version>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.0</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>
```
So, our `pom.xml` file now looks like this
<details>
	<summary>Click here to see entire pom.xml file</summary>
	<?xml version="1.0" encoding="UTF-8"?>

	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	    <modelVersion>4.0.0</modelVersion>

	    <groupId>com.afikur</groupId>
	    <artifactId>SpringIntro</artifactId>
	    <version>1.0-SNAPSHOT</version>
	    <packaging>war</packaging>

	    <name>SpringIntro Maven Webapp</name>
	    <!-- FIXME change it to the project's website -->
	    <url>http://www.example.com</url>

	    <properties>
	        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	        <maven.compiler.source>1.7</maven.compiler.source>
	        <maven.compiler.target>1.7</maven.compiler.target>
	    </properties>

	    <dependencies>
	        <dependency>
	            <groupId>junit</groupId>
	            <artifactId>junit</artifactId>
	            <version>4.11</version>
	            <scope>test</scope>
	        </dependency>
	        <dependency>
	            <groupId>org.springframework</groupId>
	            <artifactId>spring-webmvc</artifactId>
	            <version>5.0.5.RELEASE</version>
	        </dependency>
	        <dependency>
	            <groupId>javax.servlet</groupId>
	            <artifactId>javax.servlet-api</artifactId>
	            <version>4.0.0</version>
	            <scope>provided</scope>
	        </dependency>
	        <dependency>
	            <groupId>javax.servlet</groupId>
	            <artifactId>jstl</artifactId>
	            <version>1.2</version>
	        </dependency>
	    </dependencies>

	    <build>
	        <finalName>SpringIntro</finalName>
	        <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
	            <plugins>
	                <plugin>
	                    <artifactId>maven-clean-plugin</artifactId>
	                    <version>3.0.0</version>
	                </plugin>
	                <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
	                <plugin>
	                    <artifactId>maven-resources-plugin</artifactId>
	                    <version>3.0.2</version>
	                </plugin>
	                <plugin>
	                    <artifactId>maven-compiler-plugin</artifactId>
	                    <version>3.7.0</version>
	                </plugin>
	                <plugin>
	                    <artifactId>maven-surefire-plugin</artifactId>
	                    <version>2.20.1</version>
	                </plugin>
	                <plugin>
	                    <artifactId>maven-war-plugin</artifactId>
	                    <version>3.2.0</version>
	                </plugin>
	                <plugin>
	                    <artifactId>maven-install-plugin</artifactId>
	                    <version>2.5.2</version>
	                </plugin>
	                <plugin>
	                    <artifactId>maven-deploy-plugin</artifactId>
	                    <version>2.8.2</version>
	                </plugin>
	            </plugins>
	        </pluginManagement>
	    </build>
	</project>
</details>

## Configuring DispatcherServlet in Java

DispatcherServlet is the centerpiece of Spring MVC . It’s where the request first hits
the framework, and it’s responsible for routing the request through all the other
components.

Instead of a web.xml file, you’re going to use Java to configure DispatcherServlet
in the servlet container. The following listing shows the Java class you’ll need.

Create a package `com.afikur.config`

Create a Java file `src/main/java/com.afikur.config/AppConfig.java`

```java
package com.afikur.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@ComponentScan(basePackages = {"com.afikur"})
public class AppConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/"};
    }
}
```

It will show Errors. Because `RootConfig.class` and `WebConfig.class` cannot be found. So create Both Classes.

Create java file in `src/main/java/com.afikur.config/WebConfig.java`

```java
package com.afikur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.afikur"})
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
```
In Spring 5.x.x `WebMvcConfigurerAdapter` has been deprecated. Instead of extending `WebMvcConfigurerAdapter` we now implements `WebMvcConfigurer` interface. For 4.x.x extend `WebMvcConfigurerAdapter` class.

Now create java file
`src/main/java/com.afikur.config/RootConfig.java`

```java
package com.afikur.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"com.afikur"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        })
public class RootConfig {
}
```

Now Our Configuration is done. Let's write our first Controller


## Controller
Create a package called `com.afikur.controller`

Create a Java file `src/main/java/com.afikur.controller/HomeController.java`

```java
package com.afikur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String welcome() {
        return "welcome";
    }
}
```
This controller is fairly simple. We created a Simple Java Class named `HomeController` and annoted it with `@Controller` so that spring can understand it's a Controller. Next we created a method named `welcome`. The return type of the method is `String`. Finally we returned a String literal `"welcome"`. We annotated the method with `@RequestMapping("/home")`. So when user type `localhost:8080/home` this method will hit.

In `com.afikur.config` package we created a file named `WebConfig.java` where we declared a `Bean` named `resourceViewResolver` which returns a `viewResolver`. We added the prefix `"/WEB-INF/views/"` and suffix `".jsp"`. That's why, when we returning a String literal from controller it's resolving the view as "/WEB-INF/views/welcome.jsp"

So, now we have to create a `jsp` file. First create a `views` folder in `webapp/WEB-INF`. Then create a file
`webapp/WEB-INF/views/welcome.jsp`

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Welcome</h1>
</body>
</html>
```

Now `run` the project. and go to [http://localhost:8080/home](http://localhost:8080/home). If everything goes well, you'll see the heading text `Welcome` .