package hu.cdogbot.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookEntry {
	private String id;
	private Long time;
	private List<FacebookMessaging> messaging;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public List<FacebookMessaging> getMessaging() {
		return messaging;
	}

	public void setMessaging(List<FacebookMessaging> messaging) {
		this.messaging = messaging;
	}

	@Override
	public String toString() {
		return "FacebookEntry [id=" + id + ", time=" + time + ", messaging=" + messaging + "]";
	}

}
