package de.text2sarff.bookWord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.text2sarff.LogUtil;

public class BookWordListCreator {

	private Set<String> bookWordSet = new HashSet<String>();
	private List<String> bookWordList;
	private BreakIterator iter = BreakIterator.getWordInstance();
	private String word;

	public List<String> createBookWordList() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"InputFile\\AltesTestament.txt"));
			String line = null;
			while ((line = in.readLine()) != null) {
				parseLine(line);
			}
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
		bookWordList = set2List(bookWordSet);
		return bookWordList;
	}
	
	private void parseLine(final String aLine) {
		iter.setText(aLine);
		for (int last = iter.first(), next = iter.next(); next != BreakIterator.DONE; last = next, next = iter
				.next()) {
			CharSequence part = aLine.subSequence(last, next);
			if (Character.isLetterOrDigit(part.charAt(0))) {
				word = String.valueOf(part);
				word = word.replaceAll("[^\\w ]", "");
				bookWordSet.add(word);
			}
		}
	}
	
	private List<String> set2List(final Set<String> aWordSet) {
		bookWordList = new ArrayList<String>(aWordSet);
		return bookWordList;
	}


}
