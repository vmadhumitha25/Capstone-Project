package utilis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import base.ProjectSpecification;

public class ConfigReader extends ProjectSpecification{

	private static Properties properties;
		//Load the config file
	static {
		try {
			FileInputStream file = new FileInputStream("src/test/resources/config.properties");
			properties = new Properties();  //Creating object
			properties.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Failed to Load the config properties file" +e.getMessage());
		}
	}
	//property key to fecth the value and return the correspoding property value
	public static String getProperty(String key) {
	    if (properties == null) {
	        System.out.println("Properties file not loaded!");
	        return null;
	    }
	    return properties.getProperty(key);
	}
}
