package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.data.models.User;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils.EmailPreparator;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.utils.ExtendedMimeMessagePreparator;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.web.MappingSettings;

public class EmailServiceImpl implements EmailService {

	private final String ACTIVATEACCOUNT_IDENT = "activateAccount";
	private final String RESETPASSWORD_IDENT = "resetPassword";

	/**
	 * Mail sending.
	 */
	@Resource
	private JavaMailSender mailSender;

	@Resource
	private EmailPreparator emailPreparator;

	@Resource
	private AppContext appContext;

	/**
	 * Resolving template names to views.
	 */
	@Resource
	private UrlBasedViewResolver faceletsViewResolverJsf;

	@Override
	public void sendActivationEmail(User user, String activationCode) throws Exception {

		Map<String, Object> params = prepareGeneralParams();
		params.put("email", user.getEmail());
		params.put(ACTIVATEACCOUNT_IDENT + "Link", createAccountActivationLink(activationCode));
		String subject = "Account activation!"; // TODO improvement - localization

		ExtendedMimeMessagePreparator msg = emailPreparator.prepareMessage(user.getEmail(), subject,
				ACTIVATEACCOUNT_IDENT, params);
		mailSender.send(msg);
	}

	/**
	 * Generates link for logging into unactivated registered account and activating it immediately.
	 *
	 * @param activationCode
	 *            Activation login code.
	 * @return HTTP link.
	 */
	private String createAccountActivationLink(final String activationCode) {
		String activateLink = MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.ACTIVATION, activationCode);
		return activateLink;
	}

	@Override
	public void sendResetPasswordEmail(User user, String resetPasswordCode) throws Exception {

		Map<String, Object> params = prepareGeneralParams();
		params.put("email", user.getEmail());
		params.put(RESETPASSWORD_IDENT + "Link", createResetPasswordLink(resetPasswordCode));
		String subject = "Reset password!"; // TODO improvement - localize

		ExtendedMimeMessagePreparator msg = emailPreparator.prepareMessage(user.getEmail(), subject,
				RESETPASSWORD_IDENT, params);
		mailSender.send(msg);
	}

	/**
	 * Generates link for logging into account and showing change password form immediately.
	 *
	 * @param resetPasswordCode
	 *            Reset password login code.
	 * @return HTTP link.
	 */
	private String createResetPasswordLink(final String resetPasswordCode) {
		String activateLink = MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.RESETPASSWORD, resetPasswordCode);
		return activateLink;
	}

	/**
	 * Get general parameters
	 *
	 * @return Parameter map
	 */
	private Map<String, Object> prepareGeneralParams() {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("siteRoot", appContext.getSiteRoot());

		return params;
	}
}
