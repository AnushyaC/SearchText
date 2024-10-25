package com.main.textsearch;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class TextSearch {
	public static final String[] NAMES_TO_FIND = { "Timothy" };

	private static final int PART_SIZE = 1000;
	private static final ExecutorService executor = Executors.newFixedThreadPool(130);

	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader reader = null;
		StringBuilder partBuilder = null;
		String line = null;
		List<Future<Map<String, List<Location>>>> futures = null;
		Aggregator aggregator = null;

		try {
			File file = null;
			InputStream inputStream = null;
			file = new File("resources/big.txt");
			inputStream = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(inputStream));
			partBuilder = new StringBuilder();
			futures = new ArrayList<>();

			int addLineCount = 0;
			boolean isFirstTime = true;

			while ((line = reader.readLine()) != null) {
				partBuilder.append(line).append("\n");
				if (partBuilder.toString().split("\n").length >= PART_SIZE) {
					if (!isFirstTime) {
						addLineCount = addLineCount + 1000;
					} else {
						isFirstTime = false;
					}
					futures.add(executor.submit(new Matcher(partBuilder.toString(), addLineCount)));
					partBuilder.setLength(0);
				}
			}
			
			if (partBuilder.length() > 0) {
				futures.add(executor.submit(new Matcher(partBuilder.toString(), addLineCount)));
			}
			aggregator = new Aggregator();
			
			for (Future<Map<String, List<Location>>> future : futures) {
				aggregator.aggregate(future.get());
			}
			aggregator.printResults();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
			executor.shutdown();
		}
	}
}
