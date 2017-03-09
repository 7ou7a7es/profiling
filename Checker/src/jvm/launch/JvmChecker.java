package jvm.launch;

public class JvmChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		
		Runtime rt = Runtime.getRuntime();
		long totalMem = rt.totalMemory();
		long maxMem = rt.maxMemory();
		long freeMem = rt.freeMemory();
		long megs = 1048576;
		
		JvmData.printOutData();
		
		System.out.println("Total Memory: " + totalMem + " ("
				+ (totalMem / megs) + " MiB)");
		System.out.println("Max Memory:   " + maxMem + " (" + (maxMem / megs)
				+ " MiB)");
		System.out.println("Free Memory:  " + freeMem + " (" + (freeMem / megs)
				+ " MiB)");
		
		System.out.println("total time :"+(System.currentTimeMillis()-start));
		
//		Thread.currentThread().getStackTrace()
	}

}
