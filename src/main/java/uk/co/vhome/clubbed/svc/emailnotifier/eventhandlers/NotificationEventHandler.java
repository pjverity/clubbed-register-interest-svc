package uk.co.vhome.clubbed.svc.emailnotifier.eventhandlers;

import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import uk.co.vhome.clubbed.svc.common.events.ClubEnquiryCreatedEvent;
import uk.co.vhome.clubbed.svc.common.events.FreeTokenAcceptedEvent;
import uk.co.vhome.clubbed.svc.emailnotifier.mail.AdminNotificationMailMessageBuilder;
import uk.co.vhome.clubbed.svc.emailnotifier.mail.EnquiryResponseMailMessageBuilder;
import uk.co.vhome.clubbed.svc.emailnotifier.mail.MailMessageSender;

import javax.mail.MessagingException;
import java.io.IOException;

import static uk.co.vhome.clubbed.svc.common.events.MetaDataKey.SMOKE_TEST;

@Component
@ProcessingGroup("enquiry")
public class NotificationEventHandler
{
	private static final Logger LOGGER = LogManager.getLogger();

	private static final String CLUB_ENQUIRY = "club_enquiry";

	private static final String ADMIN_NOTIFICATION = "admin_notification";

	private final String domainEmailAddress;

	private final String domainEmailName;

	private final String smokeTestEmailAddress;

	public NotificationEventHandler(@Value("${email-notifier.domainEmailAddress}") String domainEmailAddress,
	                                @Value("${email-notifier.domainEmailName}") String domainEmailName,
	                                @Value("${email-notifier.smokeTestEmailAddress}") String smokeTestEmailAddress)
	{
		this.domainEmailAddress = domainEmailAddress;
		this.domainEmailName = domainEmailName;
		this.smokeTestEmailAddress = smokeTestEmailAddress;
	}

	@EventHandler
	void on(ClubEnquiryCreatedEvent clubEnquiryCreatedEvent,
	        EnquiryResponseMailMessageBuilder enquiryResponseMailMessageBuilder,
	        AdminNotificationMailMessageBuilder adminNotificationMailMessageBuilder,
	        MailMessageSender mailMessageSender,
	        MetaData metaData)
	{
		try
		{
			Boolean isSmokeTest = (Boolean) metaData.getOrDefault(SMOKE_TEST.name(), Boolean.FALSE);

			String fromAddress = isSmokeTest ? smokeTestEmailAddress : domainEmailAddress;
			String fromName = isSmokeTest ? domainEmailName + " (Smoke Test)" : domainEmailName;

			sendVisitorConfirmationMail(enquiryResponseMailMessageBuilder,
			                            mailMessageSender,
			                            clubEnquiryCreatedEvent.getEmailAddress(),
			                            clubEnquiryCreatedEvent.getFirstName(),
			                            fromAddress,
			                            fromName);

			sendAdminNotificationMail(adminNotificationMailMessageBuilder,
			                          mailMessageSender,
			                          "New Registration",
			                          "New Registration",
			                          clubEnquiryCreatedEvent.getEmailAddress(),
			                          clubEnquiryCreatedEvent.getFirstName(),
			                          clubEnquiryCreatedEvent.getLastName(),
			                          clubEnquiryCreatedEvent.getPhoneNumber(),
			                          fromAddress,
			                          fromName);

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
	        MailMessageSender mailMessageSender,
	        MetaData metaData)
	{
		LOGGER.info("Notified: {} free token claimed", domainEmailAddress);

		try
		{
			Boolean isSmokeTest = (Boolean) metaData.getOrDefault(SMOKE_TEST.name(), Boolean.FALSE);

			String fromAddress = isSmokeTest ? smokeTestEmailAddress : domainEmailAddress;
			String fromName = isSmokeTest ? domainEmailName + " (Smoke Test)" : domainEmailName;

			sendAdminNotificationMail(adminNotificationMailMessageBuilder,
			                          mailMessageSender,
			                          "Free Token Claimed",
			                          "Free Token Claimed",
			                          freeTokenAcceptedEvent.getEmailAddress(),
			                          null,
			                          null,
			                          null,
			                          fromAddress,
			                          fromName);
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

	private void sendVisitorConfirmationMail(EnquiryResponseMailMessageBuilder messageBuilder,
	                                         MailMessageSender mailMessageSender,
	                                         String toAddress,
	                                         String toName,
	                                         String fromAddress,
	                                         String fromName) throws IOException, TemplateException, MessagingException
	{
		String messageContent = messageBuilder.build(CLUB_ENQUIRY,
		                                             toAddress,
		                                             toName);

		mailMessageSender.send("Thanks for your enquiry",
		                       messageContent,
		                       toAddress,
		                       fromAddress,
		                       fromName);

		LOGGER.info("Notified: {}", toAddress);
	}

	private void sendAdminNotificationMail(AdminNotificationMailMessageBuilder messageBuilder,
	                                       MailMessageSender mailMessageSender,
	                                       String notificationCategory,
	                                       String subject,
	                                       String visitorEmailAddress,
	                                       String visitorFirstName,
	                                       String visitorLastName,
	                                       String visitorPhoneNumber,
	                                       String fromAddress,
	                                       String fromName) throws IOException, TemplateException, MessagingException
	{
		String messageContent = messageBuilder.build(ADMIN_NOTIFICATION,
		                                             notificationCategory,
		                                             visitorEmailAddress,
		                                             visitorFirstName,
		                                             visitorLastName,
		                                             visitorPhoneNumber);

		mailMessageSender.send(subject,
		                       messageContent,
		                       fromAddress, // We send to ourselves, so to == from
		                       fromAddress,
		                       fromName);
	}
}
