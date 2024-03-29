package de.text2sarff.sarff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.List;

import de.text2sarff.LogUtil;

public class ARFFDataCreator {

	private ARFFVector arffVector = new ARFFVector("OutputFile/sparseArff.txt");
	private List<String> reducedBookWordList;
	private BufferedReader in;
	private BreakIterator iter = BreakIterator.getWordInstance();
	private String word;

	public ARFFDataCreator(final List<String> aReducedBookWordList) {
		this.reducedBookWordList = aReducedBookWordList;
		try {
			in = new BufferedReader(new FileReader(
					"InputFile\\AltesTestament.txt"));
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	public void createARFFData() {
		String line = null;
		long i = 0;
		try {
			while ((line = in.readLine()) != null) {
				arffVector.init(reducedBookWordList.size());
				parseLine(line);
				arffVector.write();
				System.out.println(i++);
			}
			arffVector.close();
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	private void parseLine(final String aLine) {
		iter.setText(aLine);
		for (int last = iter.first(), next = iter.next(); next != BreakIterator.DONE; last = next, next = iter
				.next()) {
			CharSequence part = aLine.subSequence(last, next);
			if (Character.isLetterOrDigit(part.charAt(0))) {
				word = String.valueOf(part);
				word = word.replaceAll("[^\\w ]", "");
				add1IfWordIsInList();
			}
		}
	}
	
	private void add1IfWordIsInList(){
		int pos = reducedBookWordList.indexOf(word);
		if (pos != -1) {
			arffVector.add1(pos);
		}
	}
}
