package org.nexusiq.successmetrics.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;

@Component
public class NetworkService {
	
	public boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
	    } 
	    catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } 
	    catch (IOException e) {
	        return false;
	    }
	}

}
