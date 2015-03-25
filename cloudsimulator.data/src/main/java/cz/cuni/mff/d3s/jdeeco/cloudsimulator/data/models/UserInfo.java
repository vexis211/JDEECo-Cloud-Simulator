package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

public class UserInfo implements java.io.Serializable {

	private static final long serialVersionUID = 7283168303649597284L;

	private Integer id;
	private User user;
	private String name;
	private String value;

	public UserInfo() {
	}

	public UserInfo(User user, String name, String value) {
		this.user = user;
		this.name = name;
		this.value = value;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
