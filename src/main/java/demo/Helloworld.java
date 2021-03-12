package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Helloworld {
	private static final Logger logger = LoggerFactory.getLogger(Helloworld.class);
	public static boolean main(final String[] args) {
		logger.log("Hello, World!!");
		return true;
	}

}
