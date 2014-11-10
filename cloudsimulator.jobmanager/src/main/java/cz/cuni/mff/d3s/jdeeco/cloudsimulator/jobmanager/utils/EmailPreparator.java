package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils;

import java.util.Map;

public interface EmailPreparator {

	/**
	 * Creates message from templates.
	 *
	 * @param to
	 *            To email
	 * @param subject
	 *            Email subject
	 * @param templateIdent
	 *            Template ident.
	 * @param params
	 *            Parameters for template.
	 * @return Email.
	 * @throws Exception
	 *             Error during rendering.
	 */
	ExtendedMimeMessagePreparator prepareMessage(final String to, final String subject,
			final String templateIdent, final Map<String, Object> params) throws Exception;

	/**
	 * Creates message from templates.
	 *
	 * @param to
	 *            To email
	 * @param subject
	 *            Email subject
	 * @param htmlTemplateName
	 *            Html version template name.
	 * @param templateIdent
	 *            Template ident.
	 * @param params
	 *            Parameters for template.
	 * @param replyTo
	 *            Reply to email address
	 * @return Email.
	 * @throws Exception
	 *             Error during rendering.
	 */
	ExtendedMimeMessagePreparator prepareMessage(final String to,
			final String subject, final String templateIdent,
			final Map<String, Object> params, final String replyTo) throws Exception;
}
