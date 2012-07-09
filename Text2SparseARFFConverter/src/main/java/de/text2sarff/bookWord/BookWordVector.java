package de.text2sarff.bookWord;

import java.util.ArrayList;
import java.util.List;

public class BookWordVector {

	private List<Integer> bookWordList;

	public void init(final int aSize) {
		bookWordList = new ArrayList<Integer>(aSize);
		for (int i = 0; i < aSize; i++) {
			bookWordList.add(i, 0);
		}
	}

	public void add1(final int aPos) {
		int count = bookWordList.get(aPos);
		count++;
		bookWordList.set(aPos, count);
	}

	public List<Integer> getList() {
		return bookWordList;
	}

}
