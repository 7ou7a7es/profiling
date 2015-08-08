package com.profiling.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class StackInfoWriter extends PrintWriter {
	
	public static final PrintWriter WRITER = null;

	public StackInfoWriter(File file) throws FileNotFoundException, UnsupportedEncodingException {
		super(file, "UTF-8");
	}

}
