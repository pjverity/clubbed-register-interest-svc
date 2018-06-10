package uk.co.vhome.clubbed.svc.enquiryhandler.model;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.NewClubEnquiryCommand;
import uk.co.vhome.clubbed.svc.common.events.ClubEnquiryCreatedEvent;

import static org.axonframework.test.matchers.Matchers.*;

class EnquiryTest
{
	private AggregateTestFixture<Enquiry> testFixture;

	@BeforeEach
	void setUp()
	{
		testFixture = new AggregateTestFixture<>(Enquiry.class);
	}

	@Test
	void createsNew()
	{
		testFixture.givenNoPriorActivity()
				.when(new NewClubEnquiryCommand("a.runner@home.com", "Amy", "Runner", "+44 (0)207 555 1234", false))
				.expectEventsMatching(
						exactSequenceOf(
								messageWithPayload(equalTo(new ClubEnquiryCreatedEvent("a.runner@home.com", "Amy", "Runner", "+44 (0)207 555 1234"))),
								andNoMore()
						)
				)
				.expectSuccessfulHandlerExecution();
	}
}