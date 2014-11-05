package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager;

public class AppContextImpl implements AppContext {
	
	private long appVersionNumber;

	public long getAppVersionNumber(){
		return appVersionNumber;
	}

	public void setAppVersionNumber(long appVersionNumber) {
		this.appVersionNumber = appVersionNumber;
	}
}
