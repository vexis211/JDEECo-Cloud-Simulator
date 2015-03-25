package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

import java.util.ArrayList;
import java.util.List;

public class ProjectListVisibilitySettings {
	private List<ProjectVisibilitySettings> visibilitySettings;

	public ProjectListVisibilitySettings() {
		this.visibilitySettings = new ArrayList<>();
	}

	public ProjectListVisibilitySettings(List<ProjectVisibilitySettings> visibilitySettings) {
		this.visibilitySettings = visibilitySettings;
	}

	public List<ProjectVisibilitySettings> getVisibilitySettings() {
		return visibilitySettings;
	}

	public void setVisibilitySettings(List<ProjectVisibilitySettings> visibilitySettings) {
		this.visibilitySettings = visibilitySettings;
	}
}
