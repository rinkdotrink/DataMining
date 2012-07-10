package de.t2cf.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.t2cf.LogUtil;

public class ARFFReader {

	private Map<Integer, Integer> lineNumberClusterNumber;
	private boolean dataSection = false;
	private Pattern p;

	public ARFFReader() {
		lineNumberClusterNumber = new TreeMap<Integer, Integer>();
		p = Pattern.compile(".*cluster.*");
	}

	public Map<Integer, Integer> createLineNumberClusterNumberMap() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"InputFile\\sparseArffC.arff"));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (dataSection) {
					int lineNumber = getLineNumber(line);
					int clusterNumber = getClusterNumber(line);
					lineNumberClusterNumber.put(lineNumber, clusterNumber);
				}
				if (line.startsWith("@data", 0)) {
					dataSection = true;
				}
			}
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
		return lineNumberClusterNumber;
	}

	public Integer getClusterNumber(final String aLine) {
		String line = aLine;
		Integer clusterNumber = -1;
		Matcher m = p.matcher(line);
		boolean b = m.matches();
		if (b) {
			int lastIndex = line.lastIndexOf("cluster");
			int length = line.length();
			String clusterNumberStr = line.substring(lastIndex + 7, length);
			clusterNumber = Integer.parseInt(clusterNumberStr);
		}
		return clusterNumber;
	}

	public Integer getLineNumber(final String aLine) {
		String line = aLine;
		Integer lineNumber = -1;
		int lastIndex = line.indexOf(",");
		String lineNumberStr = line.substring(0, lastIndex);
		lineNumber = Integer.parseInt(lineNumberStr);
		return lineNumber;
	}

}
