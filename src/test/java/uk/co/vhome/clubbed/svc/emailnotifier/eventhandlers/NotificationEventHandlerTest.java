package uk.co.vhome.clubbed.svc.emailnotifier.eventhandlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.vhome.clubbed.svc.emailnotifier.mail.AdminNotificationMailMessageBuilder;
import uk.co.vhome.clubbed.svc.emailnotifier.mail.EnquiryResponseMailMessageBuilder;
import uk.co.vhome.clubbed.svc.emailnotifier.mail.MailMessageSender;
import uk.co.vhome.clubbed.svc.common.events.ClubEnquiryCreatedEvent;
import uk.co.vhome.clubbed.svc.common.events.FreeTokenAcceptedEvent;

import java.io.IOException;

import static org.mockito.Mockito.*;

class NotificationEventHandlerTest
{

	private static final String DOMAIN_EMAIL_ADDRESS = "admin@domain.co.uk";

	private static final String DOMAIN_EMAIL_NAME = "Admin";

	@Mock
	private MailMessageSender mockMailMessageSender;

	@Mock
	private EnquiryResponseMailMessageBuilder mockEnquiryResponseMailMessageBuilder;

	@Mock
	private AdminNotificationMailMessageBuilder mockAdminNotificationMailMessageBuilder;

	private NotificationEventHandler notificationEventHandler;

	private ClubEnquiryCreatedEvent enquiryCreatedEvent;

	private FreeTokenAcceptedEvent freeTokenAcceptedEvent;

	@BeforeEach
	void setUp()
	{
		MockitoAnnotations.initMocks(this);
		notificationEventHandler = new NotificationEventHandler(DOMAIN_EMAIL_ADDRESS, DOMAIN_EMAIL_NAME);

		enquiryCreatedEvent = new ClubEnquiryCreatedEvent("a.runner@home.com",
		                                                  "Amy",
		                                                  "Runner");

		freeTokenAcceptedEvent = new FreeTokenAcceptedEvent("a.runner@home.com");
	}

	@Test
	void noMailSentWhen_newRegistration_messageBuildingFails() throws Exception
	{
		when(mockEnquiryResponseMailMessageBuilder.build("club_enquiry", "a.runner@home.com", "Amy")).thenThrow(new IOException());

		notificationEventHandler.on(enquiryCreatedEvent,
		                            mockEnquiryResponseMailMessageBuilder,
		                            mockAdminNotificationMailMessageBuilder,
		                            mockMailMessageSender);

		verifyZeroInteractions(mockMailMessageSender);
	}

	@Test
	void mailSentWhen_newRegistration_messageBuiltOk() throws Exception
	{
		when(mockEnquiryResponseMailMessageBuilder.build("club_enquiry",
		                                                 "a.runner@home.com",
		                                                 "Amy")).thenReturn("Message Content");

		notificationEventHandler.on(enquiryCreatedEvent,
		                            mockEnquiryResponseMailMessageBuilder,
		                            mockAdminNotificationMailMessageBuilder,
		                            mockMailMessageSender);

		verify(mockMailMessageSender).send("Thanks for your enquiry",
		                                   "Message Content",
		                                   "a.runner@home.com",
		                                   DOMAIN_EMAIL_ADDRESS,
		                                   DOMAIN_EMAIL_NAME);
	}

	@Test
	void noMailSentWhen_freeTokenClaimed_messageBuildingFails() throws Exception
	{
		when(mockAdminNotificationMailMessageBuilder.build("admin_notification",
		                                                   "Free Token Claimed",
		                                                   "a.runner@home.com",
		                                                   null,
		                                                   null,
		                                                   null)).thenThrow(new IOException());

		notificationEventHandler.on(freeTokenAcceptedEvent,
		                            mockAdminNotificationMailMessageBuilder,
		                            mockMailMessageSender);

		verifyZeroInteractions(mockMailMessageSender);
	}

	@Test
	void mailSentWhen_freeTokenClaimed_messageBuiltOk() throws Exception
	{
		when(mockAdminNotificationMailMessageBuilder.build("admin_notification",
				                                           "Free Token Claimed",
				                                           "a.runner@home.com",
				                                           null,
				                                           null,
				                                           null)).thenReturn("Message Content");

		notificationEventHandler.on(freeTokenAcceptedEvent,
		                            mockAdminNotificationMailMessageBuilder,
		                            mockMailMessageSender);

		verify(mockMailMessageSender).send("Free token claimed",
		                                   "Message Content",
		                                   DOMAIN_EMAIL_ADDRESS,
		                                   DOMAIN_EMAIL_ADDRESS,
		                                   DOMAIN_EMAIL_NAME);
	}
}