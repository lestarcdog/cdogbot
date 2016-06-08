package hu.cdogbot.fbparser.model;

import hu.cdogbot.fbparser.ModelConfig;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class FbMessage {
	@Override
	public String toString() {
		return "FbMessage [sender=" + sender + ", timestamp=" + timestamp + ", message=" + processedMessage + "]";
	}

	private Long id;
	private final String sender;
	private final boolean senderMe;
	private final LocalDateTime timestamp;
	private final String rawMessage;


	private final String processedMessage;
	private Long nextMessageId = null;
	
	public FbMessage(String sender, LocalDateTime timestamp,String rawMessage, String processedMessage) {
		this.sender = sender;
		this.timestamp = timestamp;
		this.rawMessage = rawMessage;
		this.processedMessage = processedMessage;
        this.senderMe = sender.equals(ModelConfig.ME);
    }

	public boolean isSenderMe() {
		return senderMe;
	}

	public Long getId() {
		return id;
	}
	
	public String getRawMessage() {
		return rawMessage;
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
	
	public long getTimestampAsLong() {
		return timestamp.toInstant(ZoneId.of("Europe/Budapest").getRules().getOffset(timestamp)).toEpochMilli();
	}

	public String getProcessedMessage() {
		return processedMessage;
	}

}
