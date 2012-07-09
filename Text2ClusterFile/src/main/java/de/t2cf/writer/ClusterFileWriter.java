package de.t2cf.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import de.t2cf.LogUtil;

public class ClusterFileWriter {

	private Map<Integer, Integer> lcMap;
	private Map<Integer, String> lvMap;
	private List<BufferedWriter> writer;
	private Path file;
	private Charset charset;

	public ClusterFileWriter(Map<Integer, Integer> aLcMap,
			Map<Integer, String> aLvMap) {
		this.lcMap = aLcMap;
		this.lvMap = aLvMap;
		writer = new ArrayList<BufferedWriter>();
	}

	public void writeVerses() {
		int numberOfFiles = getNumberOfFiles();
		initWriters(numberOfFiles);
		writeVerses2ClusterFiles();
		close(numberOfFiles);
	}

	private int getNumberOfFiles() {
		SortedSet<Map.Entry<Integer, Integer>> lcMapSorted = entriesSortedByValues(lcMap);
		int numberOfFiles = lcMapSorted.last().getValue();
		return numberOfFiles;
	}

	private void initWriters(int numberOfFiles) {
		for (int i = 0; i <= numberOfFiles; i++) {
			initWriter("OutputFile/sparseArff", i);
		}
	}

	private void writeVerses2ClusterFiles() {
		Set<Map.Entry<Integer, String>> lvMapSorted = lvMap.entrySet();
		Iterator<Map.Entry<Integer, String>> it = lvMapSorted.iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> me = (Map.Entry<Integer, String>) it
					.next();
			Integer line = me.getKey();
			Integer cluster = lcMap.get(line);
			try {
				writer.get(cluster).write(me.getValue() + "\n");
			} catch (IOException e) {
				LogUtil.getLogger().error(e);
			}
		}
	}

	private void initWriter(final String aFilePath, int cluster) {
		file = Paths.get(aFilePath + cluster + ".txt");
		charset = Charset.forName("UTF-8");
		try {
			writer.add(cluster, Files.newBufferedWriter(file, charset,
					new OpenOption[] { StandardOpenOption.CREATE }));
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	private void close(int cluster) {
		try {
			for (int i = 0; i <= cluster; i++) {
				writer.get(i).flush();
				writer.get(i).close();
			}
		} catch (IOException e) {
			LogUtil.getLogger().error(e);
		}
	}

	private static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(
			Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
				new Comparator<Map.Entry<K, V>>() {
					@Override
					public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
						int res = e1.getValue().compareTo(e2.getValue());
						return res != 0 ? res : 1;
					}
				});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

}
