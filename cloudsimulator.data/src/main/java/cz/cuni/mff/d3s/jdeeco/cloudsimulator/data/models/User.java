package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 5067434244457906867L;

	private Integer id;
	private String email;
	private String password;
	private int role;
	private Date registrationDate;
	private Date lastActivityDate;
	private int activationState;
	private Set<UserInfo> userInfos = new HashSet<UserInfo>(0);
	private Set<AuthenticationToken> authenticationTokens = new HashSet<AuthenticationToken>(0);

	private Set<Project> visibleProjects = new HashSet<Project>(0);

	public User() {
	}

	public User(String email, String password, int role, int activationState) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.activationState = activationState;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}
	
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getLastActivityDate() {
		return this.lastActivityDate;
	}

	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public int getActivationState() {
		return this.activationState;
	}

	public void setActivationState(int activationState) {
		this.activationState = activationState;
	}

	public Set<UserInfo> getUserInfos() {
		return this.userInfos;
	}

	public void setUserInfos(Set<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public Set<AuthenticationToken> getAuthenticationTokens() {
		return this.authenticationTokens;
	}

	public void setAuthenticationTokens(Set<AuthenticationToken> authenticationTokens) {
		this.authenticationTokens = authenticationTokens;
	}

	public Set<Project> getVisibleProjects() {
		return this.visibleProjects;
	}

	public void setVisibleProjects(Set<Project> projects) {
		this.visibleProjects = projects;
	}
}
