package hu.cdogbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookUser {
	private String id;

    public FacebookUser() {}

    public FacebookUser(String id) {
        this.id = id;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FacebookUser [id=" + id + "]";
	}

}
