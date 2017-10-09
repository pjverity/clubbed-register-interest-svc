package uk.co.vhome.clubbed.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

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
		return application.sources(applicationClass, WebConfiguration.class);
	}

	@Bean
	ExecutorService executorService()
	{
		// Run synchronously. Could probably be done a little more neatly. Not sure what that
		// way is yet.
		return new AbstractExecutorService()
		{
			private volatile boolean isShutdown = false;

			@Override
			public void execute(Runnable command)
			{
				command.run();
			}

			@Override
			public void shutdown()
			{
				isShutdown = true;
			}

			@Override
			public List<Runnable> shutdownNow()
			{
				return Collections.emptyList();
			}

			@Override
			public boolean isShutdown()
			{
				return isShutdown;
			}

			@Override
			public boolean isTerminated()
			{
				return isShutdown;
			}

			@Override
			public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException
			{
				return isShutdown;
			}
		};
	}
}
