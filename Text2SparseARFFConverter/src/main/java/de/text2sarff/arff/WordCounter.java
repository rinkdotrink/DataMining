package de.text2sarff.arff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.text2sarff.LogUtil;

public class WordCounter {

	private ARFFVector arffVector = new ARFFVector();
	private List<String> wordList;
	private Map<String, Integer> wordCountMap;
	private BreakIterator iter = BreakIterator.getWordInstance();
	private BufferedReader in;
	private String word;

	public WordCounter(final List<String> aWordList) {
		this.wordList = aWordList;
		try {
			in = new BufferedReader(new FileReader(
					"InputFile\\AltesTestament.txt"));
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	public void countWords() {
		String line = null;
		long i = 0;
		try {
			arffVector.init(wordList.size());
			while ((line = in.readLine()) != null) {
				parseLine(line);
				System.out.println(i++);
			}
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	public Map<String, Integer> getWordWordCountMap(int maxOccurs) {
		wordCountMap = new TreeMap<String, Integer>(newAscIntComparator());
		List<Integer> listInteger = arffVector.getList();
		int listIntegerSize = listInteger.size();
		for (int i = 0; i < listIntegerSize; i++) {
			Integer count = listInteger.get(i);
			String word = wordList.get(i);
			if (count >= maxOccurs) {
				wordCountMap.put(word, count);
			}
			i++;
		}
		return wordCountMap;
	}

	private void parseLine(final String aLine) {
		iter.setText(aLine);
		for (int last = iter.first(), next = iter.next(); next != BreakIterator.DONE; last = next, next = iter
				.next()) {
			CharSequence part = aLine.subSequence(last, next);
			if (Character.isLetterOrDigit(part.charAt(0))) {
				word = String.valueOf(part);
				word = word.replaceAll("[^\\w ]", "");
				int pos = wordList.indexOf(word);
				arffVector.add1(pos);
			}
		}
	}

	private Comparator<String> newAscIntComparator() {
		return new Comparator<String>() {
			@Override
			public int compare(String first, String second) {
				return first.compareTo(second);
			}
		};
	}
}
