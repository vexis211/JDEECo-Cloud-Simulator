package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.utils;

import java.util.Map;

import javax.annotation.Resource;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;

public class EmailPreparatorImpl implements EmailPreparator {

	private final String HTML_TEMPLATE_NAME_PREFIX = "emails/html/";
	private final String PLAINTEXT_TEMPLATE_NAME_PREFIX = "emails/plaintext/";

	@Resource
	private AppContext appContext;
	
	@Resource
	private ViewRenderer viewRenderer;
	private String from;
	private String fromPersonalName;
	
	public EmailPreparatorImpl(String from, String fromPersonalName) {
		this.from = from;
		this.fromPersonalName = fromPersonalName;
	}
	
	@Override
	public ExtendedMimeMessagePreparator prepareMessage(String to, String subject, String templateIdent,
			Map<String, Object> params) throws Exception {
		return prepareMessage(to, subject, templateIdent, params, null);
	}

	@Override
	public ExtendedMimeMessagePreparator prepareMessage(String to, String subject, String templateIdent,
			Map<String, Object> params, String replyTo) throws Exception {

		String mailReplyTo = replyTo != null ? replyTo : from;
		
		final String htmlVersion = viewRenderer.render(GetHtmlTemplateName(templateIdent), params);
		final String plaintextVersion = viewRenderer.render(GetPlainTextTemplateName(templateIdent), params);

		ExtendedMimeMessagePreparator msg = new ExtendedMimeMessagePreparator(from, to, fromPersonalName, mailReplyTo);
		msg.SetMessageData(subject, plaintextVersion, htmlVersion);

		return msg;
	}
	
	private String GetHtmlTemplateName(String templateIdent) {
		return HTML_TEMPLATE_NAME_PREFIX + templateIdent;
	}

	private String GetPlainTextTemplateName(String templateIdent) {
		return PLAINTEXT_TEMPLATE_NAME_PREFIX + templateIdent;
	}
}
