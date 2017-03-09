package com.profiling.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.profiling.model.StackTraceNode;

public class TreeReportBuilder {
	
	private static final String TITLE = "Application Stack";
	private static final String HTML_FILENAME = "tree-stack.html";
	private static final TreeReportBuilder htmlFacto = new TreeReportBuilder();
	private static String pathDir;
	private static FileOutputStream writer;
		

	private TreeReportBuilder() {
	}

	public static void generateReport(String outputDir) throws IOException {
		pathDir = outputDir;
		generateHtml();
	}

	public static void generateHtml() throws IOException {
		
		// Init writer with path directory and 
		writer = new FileOutputStream(new File(pathDir + HTML_FILENAME));
		writer.write("<!DOCTYPE html>".getBytes());
		writer.write("<html>".getBytes());
		htmlFacto.generateHtmlHeader();
		htmlFacto.generateHtmlBody();
		writer.write("</html>".getBytes());
		writer.close();
	}

	private void generateHtmlHeader() throws IOException {
		
		// Let the JVM optimized.
		String result = "";
		
		// Init Header tags. 
		result +="<head>";
		result +="<title>"+TITLE+"</title>";
		result +="<style type=\"text/css\">";
		
		// CSS functional
		result +="input {display: none;}";
		result +="input ~ ul {display: none;}";
		result +="input:checked ~ ul {display: block;}";
		result +="input ~ .minus {display: none;}";
		result +="input:checked ~ .plus {display: none;}";
		result +="input:checked ~ .minus {display: inline;}";
		
		// CSS decorator.
		result +="li {";
		result +="display: block;";
		result +="font-family: 'Arial';";
		result +="font-size: 15px;";
		result +="padding: 0.2em;";
		result +="border: 1px solid transparent;";
		result +="}";
		result +="li:hover {";
		result +="border: 1px solid grey;";
		result +="border-radius: 3px;";
		result +="background-color: lightgrey;";
		result +="}";
		result +="icon {";
		result +="font-weight: bold;";
		result +="}";
		result +="line {";
		result +="color: red;";
		result +="font-style: italic;";
		result +="}";
		result +="count {";
		result +="color: green;";
		result +="font-style: italic;";
		result +="}";
		result +="time {";
		result +="color: blue;";
		result +="font-style: italic;";
		result +="}";
		result +="package {";
		result +="display: none;";
		result +="}";
		result +=".totaltime {";
		result +="color: blue;";
		result +="font-weight: bold;";
		result +="}";
		
		// Close opened tags.
		result +="</style>";
		result +="</head>";
		
		// Write in file.
		writer.write(result.getBytes());
	}
	
	private void generateHtmlBody() throws IOException {
		String result = "<body>";
		
		result += StackTraceNode.ROOT.toHtml().replaceAll("<init>", "{init}").replaceAll("<clinit>", "{clinit}");
		result += "</body>";
		
		// Write in file.
		writer.write(result.getBytes());
	}

}
