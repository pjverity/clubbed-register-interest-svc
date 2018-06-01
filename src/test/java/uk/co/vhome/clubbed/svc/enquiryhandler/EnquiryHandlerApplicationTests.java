package uk.co.vhome.clubbed.svc.enquiryhandler;

import org.axonframework.commandhandling.CommandBus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.vhome.clubbed.svc.EnquiryHandlerApplication;
import uk.co.vhome.clubbed.svc.enquiryhandler.config.WebConfiguration;
import uk.co.vhome.clubbed.svc.enquiryhandler.model.commands.NewClubEnquiryCommand;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EnquiryHandlerApplication.class, WebConfiguration.class, TestConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EnquiryHandlerApplicationTests
{
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CommandBus mockCommandBus;

	@Test
	void contextLoads()
	{
	}

	@Test
	void handlerDispatchesNewClubEnquiryCommand() throws Exception
	{
		mockMvc.perform(post("/v2/club-enquiry/emails/amy.user@home.co.uk")
				                .contentType("application/x-www-form-urlencoded")
				                .content("&firstName=Amy&lastName=User&phone=+44 (0)207 555 1234"));

		NewClubEnquiryCommand newClubEnquiryCommand = new NewClubEnquiryCommand("amy.user@home.co.uk",
		                                                                        "Amy",
		                                                                        "User",
		                                                                        "+44 (0)207 555 1234");

		verify(mockCommandBus).dispatch(argThat(cmdMsg -> cmdMsg.getPayload().equals(newClubEnquiryCommand)), any());
	}
}
