package uk.co.vhome.clubbed.svc.enquiryhandler.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminNotificationMailMessageBuilder
{
	private final Configuration freemarkerConfiguration;

	public AdminNotificationMailMessageBuilder(Configuration freemarkerConfigurationBean)
	{
		this.freemarkerConfiguration = freemarkerConfigurationBean;
	}

	public String build(String responseTemplatePath,
	                    String notificationCategory,
	                    String enquirersEmailAddress,
	                    String enquirersFirstName,
	                    String enquirersLastName,
	                    String phoneNumber) throws IOException, TemplateException
	{
		Map<String, Object> templateModel = new HashMap<>();

		templateModel.put("notificationCategory", notificationCategory);
		templateModel.put("emailAddress", enquirersEmailAddress);
		templateModel.put("firstName", enquirersFirstName);
		templateModel.put("lastName", enquirersLastName);
		templateModel.put("phoneNumber", phoneNumber);

		Template template = freemarkerConfiguration.getTemplate(responseTemplatePath);

		return FreeMarkerTemplateUtils.processTemplateIntoString(template, templateModel);
	}
}
