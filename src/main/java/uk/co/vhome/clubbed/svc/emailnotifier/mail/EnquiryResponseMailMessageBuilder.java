package uk.co.vhome.clubbed.svc.emailnotifier.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties("freemarker.enquiry")
public class EnquiryResponseMailMessageBuilder
{
	private final Configuration freemarkerConfiguration;

	private final Map<String, String> parameters = new HashMap<>();

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	public EnquiryResponseMailMessageBuilder(Configuration freemarkerConfigurationBean)
	{
		this.freemarkerConfiguration = freemarkerConfigurationBean;
	}

	public String build(String responseTemplatePath, String enquirersEmailAddress, String enquirersName) throws IOException, TemplateException
	{
		Map<String, Object> templateModel = new HashMap<>(parameters);

		templateModel.put("emailAddress", enquirersEmailAddress);
		templateModel.put("firstName", enquirersName);

		Template template = freemarkerConfiguration.getTemplate(responseTemplatePath);

		return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateModel);
	}
}
