package hu.cdogbot.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookMessaging {
	private FacebookUser sender;
	private FacebookUser recipient;
	private Long timestamp;
	private FacebookMessage message;

	public FacebookUser getSender() {
		return sender;
	}

	public void setSender(FacebookUser sender) {
		this.sender = sender;
	}

	public FacebookUser getRecipient() {
		return recipient;
	}

	public void setRecipient(FacebookUser recipient) {
		this.recipient = recipient;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public LocalDateTime timestamp() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
	}

	public FacebookMessage getMessage() {
		return message;
	}

	public void setMessage(FacebookMessage message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "FacebookMessaging [sender=" + sender + ", recipient=" + recipient + ", timestamp=" + timestamp
				+ ", message=" + message + "]";
	}

}
