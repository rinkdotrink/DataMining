package de.text2sarff.bookWord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class BookWordListReducer {

	public List<String> reduceBookWordList(final int aNumberOfattributes,
			final Map<String, Integer> aBookWordCountMap) {
		SortedSet<Map.Entry<String, Integer>> wordCountMapSorted = entriesSortedByValues(aBookWordCountMap);
		List<String> wordListReduced = new ArrayList<String>();
		Iterator<Map.Entry<String, Integer>> it = wordCountMapSorted.iterator();
		int m = 0;
		while (it.hasNext()) {
			Map.Entry<String, Integer> me = (Map.Entry<String, Integer>) it
					.next();
			if (m < aNumberOfattributes) {
				wordListReduced.add((String) me.getKey());
			}
			m++;
		}
		return wordListReduced;
	}

	private static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(
			Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
				new Comparator<Map.Entry<K, V>>() {
					@Override
					public int compare(Map.Entry<K, V> e2, Map.Entry<K, V> e1) {
						int res = e1.getValue().compareTo(e2.getValue());
						return res != 0 ? res : 1;
					}
				});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

}
