package hu.cdogbot.fbparser;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);

		new FbMessagesParser();
	}

}
