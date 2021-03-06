package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.AssertSettingsProfile;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.DefaultAssertSetting;

@XStreamAlias("Asserts")
public class AssertsSettings {

	@XStreamAlias("DefaultAssert")
	private final DefaultAssertSetting defaultSetting;

	@XStreamAlias("AssertProfiles")
	private final ArrayList<AssertSettingsProfile> profiles;

	public AssertsSettings(DefaultAssertSetting defaultSetting, List<AssertSettingsProfile> profiles) {
		this.defaultSetting = defaultSetting;
		this.profiles = profiles != null ? new ArrayList<AssertSettingsProfile>(profiles) : null;
	}

	public DefaultAssertSetting getDefaultSetting() {
		return defaultSetting;
	}

	public List<AssertSettingsProfile> getProfiles() {
		return profiles;
	}
	
	private Object readResolve() throws IOException {
		if (this.defaultSetting == null) {
			throw new IOException("Cannot read object: Default element is compulsory.");
		}
		return this;
	}
}
