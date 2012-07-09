package de.text2sarff.lineparser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LineParser {

	private Set<String> wordSet = new HashSet<String>();
	private List<String> wordList;
	private BreakIterator iter = BreakIterator.getWordInstance();
	private String word;

	private List<String> set2List(final Set<String> aWordSet) {
		wordList = new ArrayList<String>(aWordSet);
		return wordList;
	}

	public List<String> createWordList() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"InputFile\\AltesTestament.txt"));
			String line = null;
			while ((line = in.readLine()) != null) {
				parseLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		wordList = set2List(wordSet);
		return wordList;
	}

	public void parseLine(final String aLine) {
		iter.setText(aLine);
		for (int last = iter.first(), next = iter.next(); next != BreakIterator.DONE; last = next, next = iter
				.next()) {
			CharSequence part = aLine.subSequence(last, next);
			if (Character.isLetterOrDigit(part.charAt(0))) {
				word = String.valueOf(part);
				word = word.replaceAll("[^\\w ]", "");
				wordSet.add(word);
			}
		}
	}

}
