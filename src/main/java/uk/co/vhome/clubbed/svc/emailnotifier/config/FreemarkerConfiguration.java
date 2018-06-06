package uk.co.vhome.clubbed.svc.emailnotifier.config;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import uk.co.vhome.clubbed.svc.emailnotifier.model.MailTemplate;
import uk.co.vhome.clubbed.svc.emailnotifier.repositories.MailTemplateRepository;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.time.ZoneOffset;

@Component
public class FreemarkerConfiguration
{
	@Bean
	Configuration freemarkerConfigurationBean(MailTemplateRepository mailTemplateRepository)
	{
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

		cfg.setDefaultEncoding("UTF-8");
		cfg.setLogTemplateExceptions(false);
		cfg.setLocalizedLookup(false);

		cfg.setTemplateLoader(repositoryBasedTemplateLoader(mailTemplateRepository));

		return cfg;
	}

	private TemplateLoader repositoryBasedTemplateLoader(MailTemplateRepository mailTemplateRepository)
	{
		return new TemplateLoader()
		{
			@Override
			public Object findTemplateSource(String name) throws IOException
			{
				return mailTemplateRepository.findByName(name);
			}

			@Override
			public long getLastModified(Object templateSource)
			{
				if ( templateSource instanceof MailTemplate )
				{
					return ((MailTemplate) templateSource).getModified().toEpochSecond(ZoneOffset.UTC);
				}

				return -1;
			}

			@Override
			public Reader getReader(Object templateSource, String encoding) throws IOException
			{
				if ( templateSource instanceof MailTemplate )
				{
					return new StringReader(((MailTemplate) templateSource).getTemplate());
				}

				return null;
			}

			@Override
			public void closeTemplateSource(Object templateSource) throws IOException
			{

			}
		};
	}
}
