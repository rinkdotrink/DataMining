package de.t2cf.controller;

import java.util.Map;

import de.t2cf.reader.ARFFReader;
import de.t2cf.reader.ATReader;
import de.t2cf.writer.ClusterFileWriter;

public class Controller {

	public void go() {
		ARFFReader arffReader = new ARFFReader();
		Map<Integer, Integer> lcMap = arffReader.createLCMap();
		ATReader atReader = new ATReader();
		Map<Integer, String> lvMap = atReader.createLVMap();
		ClusterFileWriter clusterFileWriter = new ClusterFileWriter(lcMap,
				lvMap);
		clusterFileWriter.writeVerses();
		System.out.println("done");
	}
}
