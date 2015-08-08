package com.profiling.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackTraceNode {

	public static final StackTraceNode ROOT = new StackTraceNode();

	public List<StackTraceNode> children = new ArrayList<StackTraceNode>();

	public String name;

	public String nodeLongName;

	public String keyName;

	public int count = 0;

	public long time = 0;

	public int line;

	public boolean isNative = false;

	public boolean isAbstract = false;

	private StackTraceNode() {
		name = "root";
	}

	public StackTraceNode(StackTraceElement stackTraceElement) {
		keyName = stackTraceElement.toString();
		name = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
		line = (stackTraceElement.getLineNumber() > 0 ? stackTraceElement.getLineNumber() : -1);
		isNative = stackTraceElement.isNativeMethod();
	}
	
	public StackTraceNode(StackTraceElement stackTraceElement, long ptime) {
		this(stackTraceElement);
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

	public boolean addNodes(StackTraceElement[] stackElement2Store, long pTime) {

		if (stackElement2Store!=null && stackElement2Store.length > 0) {

			int lastIndex = stackElement2Store.length - 1;

			StackTraceNode node = new StackTraceNode(stackElement2Store[lastIndex], pTime);

			int childIndex = children.indexOf(node);

			if (childIndex < 0) {
				childIndex = children.size();
				children.add(node);
			}

			if (lastIndex > 0) {
				children.get(childIndex).addNodes(Arrays.copyOfRange(stackElement2Store, 1, lastIndex));
			}
		}

		return true;
	}

	public boolean addNodes(StackTraceElement[] stackElement2Store) {

		if (stackElement2Store == null) {

			System.out.println("***ERRRRRRRRRRRRRRRRRRRRRRORRRRRRR***");
			return false;
		}

		if (stackElement2Store.length > 0) {

			int lastIndex = stackElement2Store.length - 1;

			StackTraceNode node = new StackTraceNode(stackElement2Store[lastIndex]);

			int childIndex = children.indexOf(node);

			if (childIndex < 0) {
				childIndex = children.size();
				children.add(node);
			} else {
				count++;
			}

			if (lastIndex > 0) {
				children.get(childIndex).addNodes(Arrays.copyOf(stackElement2Store, lastIndex));
			}

		}

		return true;
	}

	@Override
	public String toString() {
		String result = "";
		result += "<child>";
		// result += (keyName != null) ? ("<keyName>" + keyName.replaceAll("<", "{").replaceAll(">", "}") +
		// "</keyName>") : "";
		// result += (name != null) ? ("<name>" + name.replaceAll("<", "{").replaceAll(">", "}") + "</name>") : "";
		// result += (nodeLongName != null) ? ("<nodeLongName>" + nodeLongName.replaceAll("<", "{").replaceAll(">", "}")
		// + "</nodeLongName>") : "";
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
}
