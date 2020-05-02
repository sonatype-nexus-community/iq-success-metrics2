package org.demo.smproto;

public class getOS {
		
	public static String getOS() {
		String os = System.getProperty("os.name");
	    System.out.println("Detected operating system: " + os);
	    return os;
	}
	
}
