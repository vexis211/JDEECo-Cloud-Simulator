package cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models;

import java.util.Date;

public class AuthenticationToken implements java.io.Serializable {

	private static final long serialVersionUID = -8621994037021362827L;

	private Integer id;
	private User user;
	private String value;
	private Date created;
	private Date expiry;

	public AuthenticationToken() {
	}

	public AuthenticationToken(User user, String value, Date created, Date expiry) {
		this.user = user;
		this.value = value;
		this.created = created;
		this.expiry = expiry;
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

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpiry() {
		return this.expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

}
