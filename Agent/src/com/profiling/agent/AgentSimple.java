package com.profiling.agent;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.util.Map;

import com.profiling.model.Option;
import com.profiling.model.StackTraceNode;
import com.profiling.ui.ReportBuilder;

public class AgentSimple {

	public static void premain(String agentArgument, Instrumentation instrumentation) {

		// try {
		// URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		// Class<URLClassLoader> classLoaderClass = URLClassLoader.class;
		// Method method = classLoaderClass.getDeclaredMethod("addURL", new Class[] { URL.class });
		// method.setAccessible(true);
		// method.invoke(systemClassLoader, new Object[]
		// {StackTraceNode.class.getResource("com/profiling/model/StackTraceNode.class")});
		// } catch (Throwable t) {
		// t.printStackTrace();
		// System.err.println("Error when adding url to system ClassLoader ");
		// }

		System.out.println("AgentSimplearg : " + agentArgument);

		String fileName = null;
		File file;

		Map<Option, String> options = Option.parse(agentArgument);

		for (Option option : options.keySet()) {
			switch (option) {
			case file:
				fileName=options.get(option);
				break;
			default:// TODO log option not found.
				break;
			}
		}
		
		

		// PrintWriter writer = new PrintWriter(file, "UTF-8");

		instrumentation.addTransformer(new ClassTransformer());

		try {
			ReportBuilder.generateReport("");
		} catch (IOException e) {
			System.err.println("file not found");
		}

		System.err.println("#########################################");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.err.println("************** TREE : ");
				System.err.println(StackTraceNode.ROOT.toString());
				System.err.println("**********************");
			}
		});

	}
}
