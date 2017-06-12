package com.company.dataprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * starting point of this spring boot application
 * bootstraps dependencies
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer
{	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class); 
	}
	
	public static void main( String[] args )  throws Exception
    {
      SpringApplication.run(Application.class, args);
    }    
}
