package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.annotation.Resource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Send email in case of errors
 */
public class ErrorMailSender {

	protected String sender;

	// Recipients of error e-mails
	protected String[] recipients;
	
	private static final String SUBJECT_FORMAT = "Error occurred: %s.";
	private static final String TEXT_FORMAT = "%s\n\n%s\n\n%s";

	@Resource
	protected JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public JavaMailSender getMailSender() {
		return this.mailSender;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public void sendAboutError(Exception error, String errorName, String text) {
		// Assemble the text.
		ByteArrayOutputStream sstream = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(sstream);
		error.printStackTrace(ps);
		ps.close();

		// Assemble the message.
		SimpleMailMessage errorMessage = new SimpleMailMessage();
		errorMessage.setSubject(String.format(SUBJECT_FORMAT, errorName));
		errorMessage.setText(String.format(TEXT_FORMAT, error.getMessage(), text, sstream.toString()));
		errorMessage.setTo(getRecipients());
		errorMessage.setFrom(getSender());

		// Send the message.
		mailSender.send(errorMessage);
	}

}
