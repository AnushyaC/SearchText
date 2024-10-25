package com.main.textsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Aggregator {
	private final Map<String, List<Location>> aggregatedResults = new HashMap<>();

	public void aggregate(Map<String, List<Location>> results) {
		for (Map.Entry<String, List<Location>> entry : results.entrySet()) {
			aggregatedResults.merge(entry.getKey(), entry.getValue(), (oldList, newList) -> {
				oldList.addAll(newList);
				return oldList;
			});
		}
	}

	public void printResults() {
		for (Map.Entry<String, List<Location>> entry : aggregatedResults.entrySet()) {
			System.out.println(entry.getKey() + " --> " + entry.getValue());
		}
	}
}