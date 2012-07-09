package de.text2sarff;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numberOfAttributes = Integer.parseInt(args[0]);
		int maxOccurOfAnAttribute = Integer.parseInt(args[1]);
		Controller controller = new Controller();
		controller.go(numberOfAttributes, maxOccurOfAnAttribute);
	}
}
