package de.text2sarff;

import java.util.List;
import java.util.Map;

import de.text2sarff.bookWord.BookWordCounter;
import de.text2sarff.bookWord.BookWordListCreator;
import de.text2sarff.bookWord.BookWordListReducer;
import de.text2sarff.sarff.ARFFDataCreator;
import de.text2sarff.sarff.ARFFHeaderCreator;

public class Controller {

	public void go(int aNumberOfAttributes) {
		BookWordListCreator bookWordListCreator = new BookWordListCreator();
		List<String> bookWordList = bookWordListCreator.createBookWordList();
		BookWordCounter bookWordCounter = new BookWordCounter(bookWordList);
		bookWordCounter.countBookWords();
		Map<String, Integer> bookWordCountMap = bookWordCounter
				.getWordWordCountMap();
		BookWordListReducer bookWordListReducer = new BookWordListReducer();
		List<String> reducedBookWordList = bookWordListReducer
				.reduceBookWordList(aNumberOfAttributes, bookWordCountMap);
		ARFFHeaderCreator arffHeader = new ARFFHeaderCreator();
		arffHeader.createARFFHeader(reducedBookWordList);
		ARFFDataCreator arffData = new ARFFDataCreator(reducedBookWordList);
		arffData.createARFFData();
	}

}
