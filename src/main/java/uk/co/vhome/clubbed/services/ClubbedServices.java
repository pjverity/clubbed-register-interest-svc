package uk.co.vhome.clubbed.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "uk.co.vhome.clubbed")
@EnableAutoConfiguration
public class ClubbedServices extends SpringBootServletInitializer
{
	private static Class<ClubbedServices> applicationClass = ClubbedServices.class;

	public static void main(String[] args) {
		SpringApplication.run(ClubbedServices.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}
}
