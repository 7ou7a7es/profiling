package jvm.launch;


public class JvmData {

	public static void printOutData() {
		System.out.println("java.library.path : " + System.getProperty("java.library.path"));
		System.out.println("java.version : " + System.getProperty("java.version"));
		System.out.println("sun.arch.data.model : " + System.getProperty("sun.arch.data.model"));
		
		try {
			Thread.currentThread().sleep(60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		JvmArgs.printOutArgs();
	}
}
