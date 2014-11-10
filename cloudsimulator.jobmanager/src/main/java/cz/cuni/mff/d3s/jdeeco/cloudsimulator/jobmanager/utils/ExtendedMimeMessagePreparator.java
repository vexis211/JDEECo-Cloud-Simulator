package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils;

import java.io.File;
import java.util.Hashtable;
import java.util.Map.Entry;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class ExtendedMimeMessagePreparator implements MimeMessagePreparator {

	private Hashtable<String, Resource> inlines = new Hashtable<String, Resource>();
	private Hashtable<String, File> attachments = new Hashtable<String, File>();

	private String from;
	private String to;
	private String fromPersonalName;
	private String replyTo;

	private String subject;
	private String html;
	private String plainText;

	public ExtendedMimeMessagePreparator(String from, String to, String fromPersonalName, String replyTo) {
		this.from = from;
		this.to = to;
		this.fromPersonalName = fromPersonalName;
		this.replyTo = replyTo;
	}

	public void AddInline(String id, Resource inline) {
		inlines.put(id, inline);
	}

	public void AddAttachment(String name, File attachment) {
		attachments.put(name, attachment);
	}

	public void SetMessageData(String subject, String plainText, String html) {
		this.subject = subject;
		this.plainText = plainText;
		this.html = html;
	}

	@Override
	public final void prepare(final MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.getMimeMessage().addHeader("Precedence", "bulk");
		helper.setTo(to);
		helper.setFrom(from, fromPersonalName);
		if (replyTo != null)
			helper.setReplyTo(replyTo);
		helper.setSubject(subject);
		helper.setText(plainText, html);

		for (Entry<String, Resource> inlineDef : inlines.entrySet()) {
			helper.addInline(inlineDef.getKey(), inlineDef.getValue());
		}

		for (Entry<String, File> attachmentDef : attachments.entrySet()) {
			helper.addAttachment(attachmentDef.getKey(), attachmentDef.getValue());
		}
	}
}
