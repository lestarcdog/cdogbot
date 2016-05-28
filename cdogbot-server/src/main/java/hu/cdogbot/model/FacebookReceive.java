package hu.cdogbot.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookReceive {

	private String object;
	private List<FacebookEntry> entry;

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public List<FacebookEntry> getEntry() {
		return entry;
	}

	public void setEntry(List<FacebookEntry> entry) {
		this.entry = entry;
	}

	@Override
	public String toString() {
		return "FacebookReceive [object=" + object + ", entry=" + entry + "]";
	}
}
