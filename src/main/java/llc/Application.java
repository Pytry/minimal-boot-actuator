package llc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by Keith Hoopes on 5/19/2015.
 * Copyright Bear River Mutual 2015.
 */

// Uncomment for actuator
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Value("${spring.view.prefix}")
    private String viewPrefix;
    @Value("${spring.view.suffix}")
    private String viewSuffix;


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() throws ClassNotFoundException {

        InternalResourceViewResolver view = new InternalResourceViewResolver();
        view.setViewClass(JstlView.class);
        view.setPrefix(viewPrefix);
        view.setSuffix(viewSuffix);

        return view;
    }
}
