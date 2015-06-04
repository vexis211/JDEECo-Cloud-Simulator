package cz.cuni.mff.d3s.jdeeco.cloudsimulator.simulation.settings;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Profile")
public class AssertSettingsProfile {

	@XStreamAsAttribute
	@XStreamAlias("Id")
	private final String id;

	@XStreamAlias("Default")
	private final DefaultAssertSetting defaultAssertSetting;

	@XStreamImplicit(itemFieldName = "Import")
	private final ArrayList<SettingsProfileImport> profileImports;

	@XStreamImplicit(itemFieldName = "Assert")
	private final ArrayList<AssertGroupSetting> assertGroupSettings;
	
	public AssertSettingsProfile(String id, DefaultAssertSetting defaultAssertSetting,
			List<SettingsProfileImport> profileImports, List<AssertGroupSetting> assertGroupSettings) {
		this.id = id;
		this.defaultAssertSetting = defaultAssertSetting;
		this.profileImports = profileImports != null ? new ArrayList<SettingsProfileImport>(profileImports) : null;
		this.assertGroupSettings = assertGroupSettings != null ? new ArrayList<AssertGroupSetting>(assertGroupSettings) : null;
	}

	public String getId() {
		return id;
	}

	public DefaultAssertSetting getDefaultAssertSettings() {
		return defaultAssertSetting;
	}

	public List<SettingsProfileImport> getProfileImports() {
		return profileImports;
	}

	public List<AssertGroupSetting> getAssertGroupSettings() {
		return assertGroupSettings;
	}
}
