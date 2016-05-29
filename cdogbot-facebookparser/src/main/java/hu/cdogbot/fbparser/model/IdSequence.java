package hu.cdogbot.fbparser.model;

public class IdSequence {

	private long id = 1;
	
	public long next() {
		return id++;
	}

	@Override
	public String toString() {
		return "IdSequence [id=" + id + "]";
	}
}
