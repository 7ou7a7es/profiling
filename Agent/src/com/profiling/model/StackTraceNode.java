package com.profiling.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackTraceNode {
	
	private static final String ICON_OPEN_TAG = "<icon>";
	private static final String ICON_CLOSE_TAG = "</icon>";
	
	private static final String LINE_LABEL = " line : ";
	private static final String LINE_OPEN_TAG = "<line>";
	private static final String LINE_CLOSE_TAG = "</line>";

	private static final String COUNT_LABEL = " count : ";
	private static final String COUNT_OPEN_TAG = "<count>";
	private static final String COUNT_CLOSE_TAG = "</count>";
	
	private static final String PACKAGE_OPEN_TAG = "<package>";
	private static final String PACKAGE_CLOSE_TAG = "</package>";
	
	private static final String TIME_LABEL = " time : ";
	private static final String TIME_UNIT = " ms ";
	private static final String TIME_OPEN_TAG = "<time>";
	private static final String TIME_CLOSE_TAG = "</time>";
	
	public static volatile StackTraceNode ROOT = new StackTraceNode();

	public List<StackTraceNode> children = new ArrayList<StackTraceNode>();

	public String id = Integer.toHexString(super.hashCode());
	
	public String name;

	public String nodeLongName;

	public String keyName;
	
	public int count = 1;

	public long time = 0;

	public int line;

	public boolean isNative = false;

	public boolean isAbstract = false;

	private StackTraceNode() {
		name = "root";
	}

	public StackTraceNode(StackTraceElement stackTraceElement, long ptime) {
		
		System.out.println("************************************************************************");
		System.out.println("stackTraceElement.getFileName() : "+stackTraceElement.getFileName());
		System.out.println("stackTraceElement.getClassName() : "+stackTraceElement.getClassName());
		System.out.println("stackTraceElement.getLineNumber() : "+stackTraceElement.getLineNumber());
		System.out.println("stackTraceElement.getMethodName() : "+stackTraceElement.getMethodName());
		System.out.println("************************************************************************");
		
		keyName = stackTraceElement.toString();
		name = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
		line = (stackTraceElement.getLineNumber() > 0 ? stackTraceElement.getLineNumber() : -1);
		isNative = stackTraceElement.isNativeMethod();
		time = ptime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyName == null) ? 0 : keyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StackTraceNode other = (StackTraceNode) obj;
		if (keyName == null) {
			if (other.keyName != null)
				return false;
		} else if (!keyName.equals(other.keyName))
			return false;
		return true;
	}

	public boolean addInformations(StackTraceElement[] stackElement2Store, long pTime) {
		time += pTime;
		return addNodes(stackElement2Store, pTime);
	}
	
	public boolean addNodes(StackTraceElement[] stackElement2Store, long pTime) {

		if (stackElement2Store!=null && stackElement2Store.length > 1) {

			int lastIndex = stackElement2Store.length - 1;

			StackTraceNode node = new StackTraceNode(stackElement2Store[lastIndex], pTime);

			int childIndex = children.indexOf(node);

			if (childIndex < 0) {
				childIndex = children.size();
				children.add(node);
			} else {
				children.get(childIndex).time += pTime;
				if (lastIndex < 2){
					children.get(childIndex).count++;
				}
			}

			if (lastIndex > 0) {
				children.get(childIndex).addNodes(Arrays.copyOfRange(stackElement2Store, 0, lastIndex), pTime);
			}
		}

		return true;
	}

	@Override
	public String toString() {
		String result = "";
		result += "<child>";
		result += (keyName != null) ? ("<keyName>" + keyName + "</keyName>") : "";
		result += (name != null) ? ("<name>" + name + "</name>") : "";
		result += (nodeLongName != null) ? ("<nodeLongName>" + nodeLongName + "</nodeLongName>") : "";
		result += "<count>" + count + "</count>";
		result += "<time>" + time + "</time>";
		result += (line > 0) ? ("<line>" + line + "</line>") : "";
		result += "<isNative>" + isNative + "</isNative>";
		result += "<isAbstract>" + isAbstract + "</isAbstract>";
		for (StackTraceNode child : children) {
			result += "<element>" + child.toString() + "</element>";
		}
		return result + "</child>";
	}
	
	public String toHtml(){
		String result ="";
		result += "<label class=\"totaltime\">";
		result += "total time : " + time + " ms";
		result += "</label>";
		result += "<ul>";
		for (StackTraceNode child : children) {
			result += child.toHtmlTree();
		}
		result += "</ul>";
		return result;
	}
	
	public String toHtmlTree() {
		String result ="";
		if (children.isEmpty()){
			result += "<li>";
			result += name;
			result += (line> 0) ? LINE_OPEN_TAG + LINE_LABEL + line + LINE_CLOSE_TAG:"";
			result += COUNT_OPEN_TAG + COUNT_LABEL + count + COUNT_CLOSE_TAG;
			result += TIME_OPEN_TAG + TIME_LABEL + time + TIME_UNIT + TIME_CLOSE_TAG;
			result += PACKAGE_OPEN_TAG + name.substring(0, name.lastIndexOf('.')) + PACKAGE_CLOSE_TAG;
			result += "</li>";
		} else {
			result += "<li>";;
			result += "<input type=\"checkbox\" id=\"" + keyName + id + "\" />";
			result += "<label for=\"" + keyName + id + "\" class=\"plus\">" + ICON_OPEN_TAG + "+ " + ICON_CLOSE_TAG + "</label>";
			result += "<label for=\"" + keyName + id + "\" class=\"minus\">" + ICON_OPEN_TAG+"- " + ICON_CLOSE_TAG + "</label>";
			result += "<label for=\"" + keyName + id + "\">";
			result += name;
			result += (line> 0) ? LINE_OPEN_TAG + LINE_LABEL + line + LINE_CLOSE_TAG:"";
			result += COUNT_OPEN_TAG + COUNT_LABEL + count + COUNT_CLOSE_TAG;
			result += TIME_OPEN_TAG + TIME_LABEL + time + TIME_UNIT +  TIME_CLOSE_TAG;
			result += PACKAGE_OPEN_TAG + name.substring(0, name.lastIndexOf('.')) + PACKAGE_CLOSE_TAG;
			result += "</label>";
			result += "<ul>";
			for (StackTraceNode child : children) {
				result += child.toHtmlTree();
			}
			result += "</ul>";
		}
		return result;
	}
}
