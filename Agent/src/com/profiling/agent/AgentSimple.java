package com.profiling.agent;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Map;

import com.profiling.model.Option;
import com.profiling.ui.TreeReportBuilder;

public class AgentSimple {

	public static void premain(String agentArgument, Instrumentation instrumentation) {

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
		
		instrumentation.addTransformer(new ClassTransformer());

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					// TODO change with option.
					TreeReportBuilder.generateReport("");
				} catch (IOException e) {
					// TODO Auto-generated catch block.
					e.printStackTrace();
				}
			}
		});
		
		

	}
}
