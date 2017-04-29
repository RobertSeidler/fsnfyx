package de.ros.fux.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadIn {

	public static Config readIniFile(){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.ini"));
			String str = "";
			String[] values = new String[Config.params];
			int i=0;
			while((str = br.readLine()) != null){
				
				values[i] = str;
				i++;
			}
			
			Config conf = new Config();
			
			conf.setInput(values);
			
			return conf;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new Config();
	}
	
}
