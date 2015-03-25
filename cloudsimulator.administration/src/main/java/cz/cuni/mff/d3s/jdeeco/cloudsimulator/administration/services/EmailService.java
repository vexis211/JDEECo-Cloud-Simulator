package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.data.models.User;

public interface EmailService {

	void sendActivationEmail(User user, String activationCode) throws Exception;

	void sendResetPasswordEmail(User user, String resetPasswordCode) throws Exception;
}
