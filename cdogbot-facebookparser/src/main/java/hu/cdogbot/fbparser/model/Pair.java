package hu.cdogbot.fbparser.model;

public class Pair {
	private final FbMessage previous;
	private final FbMessage current;

	public Pair(FbMessage previous, FbMessage current) {
		this.previous = previous;
		this.current = current;
	}

	public FbMessage getPrevious() {
		return previous;
	}

	public FbMessage getCurrent() {
		return current;
	}
}
