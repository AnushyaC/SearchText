package com.main.textsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

class Matcher implements Callable<Map<String, List<Location>>> {
	private final String text;
	private final int addLineCount;

	public Matcher(String text, int addLineCount) {
		this.text = text;
		this.addLineCount = addLineCount;
	}

	@Override
	public Map<String, List<Location>> call() {
		Map<String, List<Location>> result = null;
		String[] lines = null;
		result = new HashMap<>();
		lines = text.split("\n");

		for (int i = 0; i < lines.length; i++) {
			for (String name : TextSearch.NAMES_TO_FIND) {
				Pattern pattern = null;
				java.util.regex.Matcher matcher = null;
				pattern = Pattern.compile("\\b" + Pattern.quote(name.toLowerCase()) + "\\b");
				matcher = pattern.matcher(lines[i].toLowerCase());
				while (matcher.find()) {
					int charOffset = matcher.start();
					result.computeIfAbsent(name, k -> new ArrayList<>())
							.add(new Location(i + addLineCount + 1, charOffset + 1));

				}
			}
		}
		return result;
	}
}