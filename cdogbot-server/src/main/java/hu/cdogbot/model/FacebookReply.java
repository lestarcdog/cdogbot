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

    private class Recipient {
        private final String id;

        public Recipient(String id) {
            this.id = id;
        }
    }

    private class Message {
        private final String text;

        public Message(String text) {
            this.text = text;
        }
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public Message getMessage() {
        return message;
    }
}
