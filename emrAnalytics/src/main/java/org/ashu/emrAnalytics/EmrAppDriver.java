package org.ashu.emrAnalytics;

import static spark.SparkBase.setIpAddress;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

/**
 * @author ashu
 *
 */
public class EmrAppDriver {

	private static final String IP_ADDRESS = "localhost";
	private static final int PORT = 8080;
	private static final String INPUT_FILE = "911.csv";

	public static void main(String[] args) throws Exception {
		setIpAddress(IP_ADDRESS);
		setPort(PORT);
		staticFileLocation("/public");
		new EmrResource(new EmrService(INPUT_FILE));
	}

}
