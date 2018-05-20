package uk.co.vhome.clubbed.svc.enquiryhandler.eventhandlers;

import freemarker.template.TemplateException;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import uk.co.vhome.clubbed.svc.enquiryhandler.mail.AdminNotificationMailMessageBuilder;
import uk.co.vhome.clubbed.svc.enquiryhandler.mail.EnquiryResponseMailMessageBuilder;
import uk.co.vhome.clubbed.svc.enquiryhandler.mail.MailMessageSender;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.events.ClubEnquiryCreatedEvent;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.events.FreeTokenAcceptedEvent;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
@ProcessingGroup("enquiry")
public class NotificationEventHandler
{
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationEventHandler.class);

	private static final String CLUB_ENQUIRY = "club_enquiry";

	private static final String ADMIN_NOTIFICATION = "admin_notification";

	private final String domainEmailAddress;

	private final String domainEmailName;

	public NotificationEventHandler(@Value("${email-notifier.domainEmailAddress}") String domainEmailAddress,
	                                @Value("${email-notifier.domainEmailName}") String domainEmailName)
	{
		this.domainEmailAddress = domainEmailAddress;
		this.domainEmailName = domainEmailName;
	}

	@EventHandler
	void on(ClubEnquiryCreatedEvent clubEnquiryCreatedEvent,
	        EnquiryResponseMailMessageBuilder enquiryResponseMailMessageBuilder,
	        AdminNotificationMailMessageBuilder adminNotificationMailMessageBuilder,
	        MailMessageSender mailMessageSender)
	{
		try
		{
			String messageContent = enquiryResponseMailMessageBuilder.build(CLUB_ENQUIRY,
			                                                                clubEnquiryCreatedEvent.getEmailAddress(),
			                                                                clubEnquiryCreatedEvent.getFirstName());

			mailMessageSender.send("Thanks for your enquiry",
			                       messageContent,
			                       clubEnquiryCreatedEvent.getEmailAddress(),
			                       domainEmailAddress, domainEmailName);

			LOGGER.info("Notified: " + clubEnquiryCreatedEvent.getEmailAddress());

			messageContent = adminNotificationMailMessageBuilder.build(ADMIN_NOTIFICATION,
			                                                           "New Registration",
			                                                           clubEnquiryCreatedEvent.getEmailAddress(),
			                                                           clubEnquiryCreatedEvent.getFirstName(),
			                                                           clubEnquiryCreatedEvent.getLastName(),
			                                                           clubEnquiryCreatedEvent.getPhoneNumber());

			mailMessageSender.send("New Registration", messageContent, domainEmailAddress, domainEmailAddress, domainEmailName);

		}
		catch (TemplateException | IOException e)
		{
			LOGGER.error("Failed to generate message content for email notification", e);
		}
		catch (MessagingException | MailException e)
		{
			LOGGER.error("Failed to send mail to " + clubEnquiryCreatedEvent.getEmailAddress(), e);
		}
	}

	@EventHandler
	void on(FreeTokenAcceptedEvent freeTokenAcceptedEvent,
	        AdminNotificationMailMessageBuilder adminNotificationMailMessageBuilder,
	        MailMessageSender mailMessageSender)
	{
		LOGGER.info("Notified: " + domainEmailAddress + " free token claimed");

		try
		{
			String messageContent = adminNotificationMailMessageBuilder.build(ADMIN_NOTIFICATION,
			                                                           "Free Token Claimed",
			                                                           freeTokenAcceptedEvent.getEmailAddress(),
			                                                           null,
			                                                           null,
			                                                           null
			);

			mailMessageSender.send("Free token claimed", messageContent,
			                       domainEmailAddress,
			                       domainEmailAddress, domainEmailName);

		}
		catch (TemplateException | IOException e)
		{
			LOGGER.error("Failed to generate message content for email notification", e);
		}
		catch (MessagingException | MailException e)
		{
			LOGGER.error("Failed to send mail to " + freeTokenAcceptedEvent.getEmailAddress(), e);
		}
	}
}
