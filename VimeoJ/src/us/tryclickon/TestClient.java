package us.tryclickon;

import java.util.Scanner;

import us.tryclickon.vimeoResponse.User;

public class TestClient {
	public static void main(String[] args) {
		VimeoWrapper vimeoWrapper = new VimeoWrapper("", "");
		User user = vimeoWrapper.getUser("coen");
		System.out.println(user.toString());
		String link = vimeoWrapper.generateLoginLink();
		System.out.println(link);
		System.out.print("Visit the link, and press enter");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		System.out.println(vimeoWrapper.getToken());
//	
	}
}
