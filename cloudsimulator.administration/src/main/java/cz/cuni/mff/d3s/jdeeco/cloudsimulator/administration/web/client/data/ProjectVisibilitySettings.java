package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data;

public class ProjectVisibilitySettings {

	private int projectId;
	private String projectName;
	private boolean visible;

	public ProjectVisibilitySettings() {
	}

	public ProjectVisibilitySettings(int projectId, String projectName, boolean visible) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.visible = visible;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
