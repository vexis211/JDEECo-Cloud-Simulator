package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

public class AppContextImpl implements AppContext {
	
	private long appVersionNumber;
	private String siteRoot;

	@Override
	public long getAppVersionNumber(){
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
}
