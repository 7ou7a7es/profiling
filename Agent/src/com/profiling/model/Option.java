package com.profiling.model;

import java.util.HashMap;
import java.util.Map;

public enum Option {
	file;
	private static final String OPTION_SEPARATOR = ",";
	private static final String VALUE_SEPARATOR = "=";

	public static Map<Option, String> parse(String arg) {
		Map<Option, String> optionMap = new HashMap<Option, String>();
		if (arg != null) {
			String[] options = arg.split(OPTION_SEPARATOR);
			for (String option : options) {
				int indexValue = option.indexOf(VALUE_SEPARATOR);
				if (indexValue < 1 || indexValue + 1 > option.length()) {
					optionMap.put(Option.valueOf(option), null);
				} else {
					optionMap.put(Option.valueOf(option.substring(0, indexValue)), option.substring(indexValue + 1));
				}
			}
		}
		return optionMap;
	}
}
