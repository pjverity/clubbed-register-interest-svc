package uk.co.vhome.clubbed.svc.enquiryhandler;

import org.axonframework.commandhandling.CommandBus;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class TestConfiguration
{
	@Bean
	CommandBus commandBus()
	{
		return Mockito.mock(CommandBus.class);
	}

	@Bean
	JavaMailSender javaMailSender()
	{
		return Mockito.mock(JavaMailSender.class);
	}
}
