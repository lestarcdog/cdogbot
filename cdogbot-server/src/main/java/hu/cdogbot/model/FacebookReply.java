package hu.cdogbot.model;

/**
 * Created by cdog on 2016.06.09..
 */
public class FacebookReply {
    private final Recipient recipient;
    private final Message message;

    public FacebookReply(String id, String text) {
        this.recipient = new Recipient(id);
        this.message = new Message(text);
    }

    public class Recipient {
        private final String id;

        public Recipient(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public class Message {
        private final String text;

        public Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public Recipient getRecipient() {
        return recipient;

    }

    public Message getMessage() {
        return message;
    }
}
