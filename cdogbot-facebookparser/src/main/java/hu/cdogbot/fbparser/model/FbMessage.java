package hu.cdogbot.fbparser.model;

public class FbMessage {
	private final String sender;
	private final String timestamp;
	private final String message;

	public FbMessage(String sender, String timestamp, String message) {
		this.sender = sender;
		this.timestamp = timestamp;
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

}
