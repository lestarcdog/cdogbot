package hu.cdogbot.fbparser.db;

/**
 * Created by cdog on 2016.07.19..
 */
public class RankedResponse {
    private final Double rank;
    private final Long messageId;
    private final Long responseId;
    private final String response;


    public RankedResponse(Double rank, Long messageId, Long responseId, String response) {
        this.rank = rank;
        this.messageId = messageId;
        this.responseId = responseId;
        this.response = response;
    }

    public Double getRank() {
        return rank;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "RankedResponse{" +
            "rank=" + rank +
            ", messageId=" + messageId +
            ", responseId=" + responseId +
            ", response='" + response + '\'' +
            '}';
    }
}
