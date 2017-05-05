package de.ros.fux.connectivity;

import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;

public class Connect {

	
	private Socket socket;
	
	
	public Connect(int port, InetAddress address){
		
		try {
			socket = new Socket(address, port);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
