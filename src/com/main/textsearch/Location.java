package com.main.textsearch;

class Location {
	private final int lineOffset;
	private final int charOffset;

	public Location(int lineOffset, int charOffset) {
		this.lineOffset = lineOffset;
		this.charOffset = charOffset;
	}

	@Override
	public String toString() {
		return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
	}
}