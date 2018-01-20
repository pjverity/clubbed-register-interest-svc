package uk.co.vhome.clubbed.svc.enquiryhandler;

import org.axonframework.commandhandling.CommandBus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.vhome.clubbed.svc.enquiryhandler.config.WebConfiguration;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.NewClubEnquiryCommand;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ClubbedSvcEnquiryHandlerApplication.class, WebConfiguration.class, TestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClubbedSvcEnquiryHandlerApplicationTests
{
	@Inject
	private MockMvc mockMvc;

	@Inject
	private CommandBus mockCommandBus;

	@Test
	void contextLoads()
	{
	}

	@Test
	void handlerDispatchesNewClubEnquiryCommand() throws Exception
	{
		mockMvc.perform(post("/v1/enquiries/club-enquiry/emails/amy.user@home.co.uk?firstName=Amy&lastName=User"));

		NewClubEnquiryCommand newClubEnquiryCommand = new NewClubEnquiryCommand("amy.user@home.co.uk", "Amy", "User");

		verify(mockCommandBus).dispatch(argThat(cmdMsg -> cmdMsg.getPayload().equals(newClubEnquiryCommand)));
	}
}
