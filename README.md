# minimal-boot-actuator
A Spring Boot application with minimal boot configuration. 
Shows how to convert an older spring application to spring boot.
Original source: http://www.mkyong.com/spring-mvc/gradle-spring-mvc-web-project-example/

# Conversion Process

- Add a Java Configuration file annotated with @SpringBootApplication

    package llc;
	
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.boot.builder.SpringApplicationBuilder;
    import org.springframework.boot.context.web.SpringBootServletInitializer;

    @SpringBootApplication
    public class Application extends SpringBootServletInitializer {
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(Application.class);
        }
    }

- Add the Application configuration file as a bean to the traditional xml configuration ( added it just after the context scan).

    <bean name="applicationConfiguration" class="llc.Application"/>

- Move view resolvers into Application java configuration.

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() throws ClassNotFoundException {
        InternalResourceViewResolver view = new InternalResourceViewResolver();
        view.setViewClass(JstlView.class);
        view.setPrefix("/WEB-INF/view/jsp/");
        view.setSuffix(".jsp");
        
        return view;
    }

Alternatively, if you can decide add the prefix and suffix to application.properties.
You can then inject them with @Value in your application, or delete it entirely and just use the provided spring boot view resolver.
I went with the former.

- Removed Default context listener from the spring context xml. 

This is important! 
Since spring boot will provide one you will get an "Error lsitener Start" exception if you do not. 

    <!-- This was removed from web.xml
    <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/dispatcher-servlet.xml</param-value>
    </context-param>
    -->

- Add the plugin to your build script dependancies (I was using gradle)

    buildscript{
        repositories {
            mavenLocal()
            mavenCentral()
        }
        dependencies{
            classpath 'org.springframework.boot:spring-boot-gradle-plugin:1.2.3.RELEASE'
        }
    }

- Add a mainClassName property to the build file, and set to an empty String (indicates not to create an executable).
	
    mainClassName=''
	
- Modify dependacies for spring boot actuator
	
    dependencies {
    
        compile("org.springframework:spring-webmvc:4.1.6.RELEASE")
        compile("jstl:jstl:1.2")
        compile("org.springframework.boot:spring-boot-starter-actuator")
    
        //include in compile only, exclude in the war
        providedCompile("javax.servlet:servlet-api:2.5")
        providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    }
