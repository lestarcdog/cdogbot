package hu.cdogbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookMessage {
	private String mid;
	private Long seq;
	private String text;

    public FacebookMessage() {

    }

    public FacebookMessage(String mid, Long seq, String text) {
        this.mid = mid;
        this.seq = seq;
        this.text = text;
    }

    public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "FacebookMessage [mid=" + mid + ", seq=" + seq + ", text=" + text + "]";
	}

}
