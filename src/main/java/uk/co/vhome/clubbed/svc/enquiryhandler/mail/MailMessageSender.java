package uk.co.vhome.clubbed.svc.enquiryhandler.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class MailMessageSender
{
	private final JavaMailSenderImpl javaMailSender;

	public MailMessageSender(JavaMailSenderImpl javaMailSender)
	{
		this.javaMailSender = javaMailSender;
	}

	public void send(String messageSubject, String messageContent, String toAddress, String fromAddress, String fromName) throws MessagingException, UnsupportedEncodingException
	{
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, fromName);

		helper.addTo(toAddress);

		helper.setSubject(messageSubject);
		helper.setText(messageContent, true);

		javaMailSender.send(message);
	}
}
