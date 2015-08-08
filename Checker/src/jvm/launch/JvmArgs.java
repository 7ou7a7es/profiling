package jvm.launch;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class JvmArgs {

	public static int argNb = 0;

	public static void printOutArgs() {
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		for (String arg : runtimeMxBean.getInputArguments()) {
			System.out.println("JVM arg " + argNb++ + " : " + arg);
		}
	}
}
