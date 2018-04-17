## Configuring DispatcherServlet in Java

DispatcherServlet is the centerpiece of Spring MVC . It’s where the request first hits
the framework, and it’s responsible for routing the request through all the other
components.

Instead of a web.xml file, you’re going to use Java to configure DispatcherServlet
in the servlet container. The following listing shows the Java class you’ll need.

Create Java file in `src/main/java/com.afikur.config/AppConfig.java`

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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.afikur"})
public class WebConfig extends WebMvcConfigurerAdapter {
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
This controller is fairly simple. We created a Simple Java Class named `HomeController` and annoted it with `@Controller` so that spring can understand it's a Controller. Next we created a method named `welcome`. The return type of the method is `String`. Finally we returned a String literal `"welcome"`. We annotated the method with `@RequestMapping("/home")`. So when user type `localhost:8080/welcome` this method will hit.

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

Now `run` the project. and go to [http://localhost:8080/welcome](http://localhost:8080/welcome). If everything goes well, you'll see the heading text `Welcome` .