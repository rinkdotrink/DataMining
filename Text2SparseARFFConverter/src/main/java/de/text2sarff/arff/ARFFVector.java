package de.text2sarff.arff;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import de.text2sarff.LogUtil;

public class ARFFVector {

	private BufferedWriter writer;
	private Path file;
	private Charset charset;
	private List<Integer> list;

	public ARFFVector() {
	}

	public ARFFVector(final String aFilePath) {
		initWriter(aFilePath);
	}

	public void init(final int aSize) {
		list = new ArrayList<Integer>(aSize);
		for (int i = 0; i < aSize; i++) {
			list.add(i, 0);
		}
	}

	public void add1(final int aPos) {
		int count = list.get(aPos);
		count++;
		list.set(aPos, count);
	}

	public void write() {
		try {
			int firstNon0Pos = 0;
			int maxSize = list.size();
			firstNon0Pos = findFirstNonZeroPosition(firstNon0Pos, maxSize);
			if (firstNon0Pos < maxSize) {
				writePosAndWordCounts(firstNon0Pos);
			} else {
				writer.write("{");
			}
			writer.write("}");
			writer.write("\n");
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}
	
	private int findFirstNonZeroPosition(int k, int maxSize) {
		while ((k < maxSize) && (list.get(k) == 0)) {
			k++;
		}
		return k;
	}

	private void writePosAndWordCounts(int k) {
		try {
			writer.write("{" + k + " " + list.get(k));
			for (int i = k + 1; i < list.size(); i++) {
				if (!(list.get(i) == 0)) {
					writer.write("," + i + " " + list.get(i));
				}
			}
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}

	}

	private void initWriter(final String aFilePath) {
		file = Paths.get(aFilePath);
		charset = Charset.forName("UTF-8");
		try {
			writer = Files.newBufferedWriter(file, charset,
					new OpenOption[] { StandardOpenOption.APPEND });
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	public void close() {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	public List<Integer> getList() {
		return list;
	}

}
