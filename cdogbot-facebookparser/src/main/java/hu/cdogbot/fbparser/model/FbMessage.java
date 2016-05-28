package hu.cdogbot.fbparser.model;

import java.time.LocalDateTime;

public class FbMessage {
	@Override
	public String toString() {
		return "FbMessage [sender=" + sender + ", timestamp=" + timestamp + ", message=" + message + "]";
	}

	private Long id;
	private final String sender;
	private final LocalDateTime timestamp;
	private final String message;
	private Long nextMessageId;
	
	public FbMessage(String sender, LocalDateTime timestamp, String message) {
		this.sender = sender;
		this.timestamp = timestamp;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNextMessageId() {
		return nextMessageId;
	}

	public void setNextMessageId(Long nextMessageId) {
		this.nextMessageId = nextMessageId;
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
