package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

public class AppContextImpl implements AppContext {

	private long appVersionNumber;
	private String siteRoot;

	private String activateAccountUri;
	private String resetPasswordUri;

	@Override
	public long getAppVersionNumber() {
		return appVersionNumber;
	}

	public void setAppVersionNumber(long appVersionNumber) {
		this.appVersionNumber = appVersionNumber;
	}

	@Override
	public String getSiteRoot() {
		return siteRoot;
	}

	public void setSiteRoot(String siteRoot) {
		this.siteRoot = siteRoot;
	}

	@Override
	public String getActivateAccountUri() {
		return activateAccountUri;
	}

	public void setActivateAccountUri(String activateAccountUri) {
		this.activateAccountUri = activateAccountUri;
	}

	@Override
	public String getResetPasswordUri() {
		return resetPasswordUri;
	}

	public void setResetPasswordUri(String resetPasswordUri) {
		this.resetPasswordUri = resetPasswordUri;
	}
}
