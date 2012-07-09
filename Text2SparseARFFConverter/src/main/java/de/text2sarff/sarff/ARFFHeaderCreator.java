package de.text2sarff.sarff;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import de.text2sarff.LogUtil;

public class ARFFHeaderCreator {

	private BufferedWriter writer;
	private Path file;
	private Charset charset;
	private List<String> reducedBookWordList;
	
	public void createARFFHeader(final List<String> aWordList) {
		initWriter("OutputFile/sparseArff.txt");
		this.reducedBookWordList = aWordList;		
		try {
			writer.write("% 1. Title: AT \n");
			writer.write("@RELATION AT \n");
			writer.write("\n");			
			for (int i = 0; i < reducedBookWordList.size(); i++) {
				String next = reducedBookWordList.get(i);
				writer.write("@ATTRIBUTE " + next + " NUMERIC \n");
			}
			writer.write("\n");
			writer.write("@DATA");
			writer.write("\n");
			close();
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	private void initWriter(final String aFilePath) {
		file = Paths.get(aFilePath);
		charset = Charset.forName("UTF-8");
		try {
			writer = Files.newBufferedWriter(file, charset,
					new OpenOption[] { StandardOpenOption.CREATE });
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	private void close() {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}
}
