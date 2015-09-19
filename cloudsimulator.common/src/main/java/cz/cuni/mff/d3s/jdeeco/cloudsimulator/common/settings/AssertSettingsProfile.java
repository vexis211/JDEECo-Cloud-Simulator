package cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.AssertGroupSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.DefaultAssertSetting;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.common.settings.SettingsProfileImport;

@XStreamAlias("AssertProfile")
public class AssertSettingsProfile {

	@XStreamAsAttribute
	@XStreamAlias("Id")
	private final String id;

	@XStreamAlias("DefaultAssert")
	private final DefaultAssertSetting defaultAssertSetting;

	@XStreamImplicit(itemFieldName = "ProfileImport")
	private final ArrayList<SettingsProfileImport> profileImports;

	@XStreamImplicit(itemFieldName = "AssertGroup")
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
