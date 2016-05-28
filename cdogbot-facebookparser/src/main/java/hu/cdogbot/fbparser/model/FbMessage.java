package hu.cdogbot.fbparser.model;

import java.time.LocalDateTime;

public class FbMessage {
	private final String sender;
	private final LocalDateTime timestamp;
	private final String message;

	public FbMessage(String sender, LocalDateTime timestamp, String message) {
		this.sender = sender;
		this.timestamp = timestamp;
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

}
