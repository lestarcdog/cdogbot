package hu.cdogbot.fbparser.db;

/**
 * Created by cdog on 2016.07.19..
 */
public class RankedResponse {
    private final Double rank;
    private final Long messageId;
    private final Long responseId;
    private final String utterance;
    private final String response;


    public RankedResponse(Double rank, Long messageId, Long responseId,String utterance, String response) {
        this.rank = rank;
        this.messageId = messageId;
        this.responseId = responseId;
        this.utterance = utterance;
        this.response = response;
    }

    public Double getRank() {
        return rank;
    }

    public String getResponse() {
        return response;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public String getUtterance() {
        return utterance;
    }

    @Override
    public String toString() {
        return "RankedResponse{" +
            "rank=" + rank +
            ", messageId=" + messageId +
            ", responseId=" + responseId +
            ", utterance='" + utterance + '\'' +
            ", response='" + response + '\'' +
            '}';
    }
}
