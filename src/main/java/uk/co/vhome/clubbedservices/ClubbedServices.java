package uk.co.vhome.clubbedservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.support.SimpleJndiBeanFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Session;

@Configuration
@ComponentScan
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

	@Bean
	public JavaMailSender javaMailSender()
	{
		SimpleJndiBeanFactory locator = new SimpleJndiBeanFactory();
		Session session = ((Session) locator.getBean("mail/Session"));

		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setSession(session);
		javaMailSender.setDefaultEncoding("UTF-8");

		return javaMailSender;
	}

}
