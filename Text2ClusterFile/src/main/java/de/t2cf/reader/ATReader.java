package de.t2cf.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import de.t2cf.LogUtil;

public class ATReader {

	private Map<Integer, String> lineVers;
	
	public ATReader() {
		lineVers = new TreeMap<Integer, String>();
	}

	public Map<Integer, String> createLVMap() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"InputFile\\AltesTestament.txt"));
			String line = null;
			int lineNumber = 0;
			while ((line = in.readLine()) != null) {
				lineVers.put(lineNumber, line);
				lineNumber++;
			}
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
		return lineVers;
	}
}
