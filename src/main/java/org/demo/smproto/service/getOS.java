package org.demo.smproto.service;

public class getOS {
	
	public String getOS() {
		String os = System.getProperty("os.name");
	    System.out.println("Detected operating system: " + os);
	    return os;
	}
	
}
