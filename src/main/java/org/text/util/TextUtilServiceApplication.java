package org.text.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import lombok.extern.slf4j.Slf4j;

/**
 * Main application class which starts the embedded tomcat container and
 * initializes spring
 */
@SpringBootApplication
@Slf4j
public class TextUtilServiceApplication {

    public static void main(String[] args) throws Exception {
        log.debug("Application Starting");
        ApplicationContext applicationContext = SpringApplication.run(TextUtilServiceApplication.class, args);
        DispatcherServlet dispatcherServlet = (DispatcherServlet) applicationContext.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }
}