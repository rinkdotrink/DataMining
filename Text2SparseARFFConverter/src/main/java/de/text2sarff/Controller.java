package de.text2sarff;

import java.util.List;
import java.util.Map;

import de.text2sarff.arff.ARFFDataCreator;
import de.text2sarff.arff.ARFFHeaderCreator;
import de.text2sarff.arff.WordCounter;
import de.text2sarff.arff.WordListReducer;
import de.text2sarff.lineparser.LineParser;

public class Controller {
	
	public void go(int numberOfAttributes){
		LineParser lineParser = new LineParser();
		List<String> wordList = lineParser.createWordList();
		WordCounter wordCounter = new WordCounter(wordList);
		wordCounter.countWords();
		Map<String, Integer> wordCountMap = wordCounter
				.getWordWordCountMap();
		WordListReducer wordListReducer = new WordListReducer();
		wordList = wordListReducer.reduceWordList(numberOfAttributes,
				wordCountMap);
		ARFFHeaderCreator arffHeader = new ARFFHeaderCreator();
		arffHeader.createARFFHeader(wordList);
		ARFFDataCreator arffData = new ARFFDataCreator(wordList);
		arffData.createVectors();
		System.out.println("done");
	}

}
