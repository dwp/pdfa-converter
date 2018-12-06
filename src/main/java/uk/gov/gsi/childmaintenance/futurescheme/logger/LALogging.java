package uk.gov.gsi.childmaintenance.futurescheme.logger;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import uk.gov.gsi.childmaintenance.futurescheme.PdfConversion;

/**
 * The Class LALogging.
 */
public class LALogging {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(LALogging.class);

	static {

		// setting up a FileAppender dynamically...
		String pattern = "%d{yyyy-MM-dd'T'HH:mm:sszzz} %5p - %m%n";
		PatternLayout layout = new PatternLayout(pattern);
		FileAppender fileAppender;
		try {
			fileAppender = new FileAppender(layout, PdfConversion.filePaths.getLogFile());
			fileAppender.setImmediateFlush(true);
			fileAppender.setAppend(true);

			logger.setLevel(Level.DEBUG);
			logger.addAppender(fileAppender);

		} catch (IOException e) {
			LALogging.error("Failed to initialize Logger");
		}

	}
	
	/**
	 * Instantiates a new LA logging.
	 */
	private LALogging() {
	}
	
	/**
	 * Debug.
	 *
	 * @param msg the msg
	 */
	public static void debug(String msg) {
		logger.debug(msg);

	}
	
	/**
	 * Info.
	 *
	 * @param msg the msg
	 */
	public static void info(String msg) {
		logger.info(msg);

	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 * @param e the e
	 */
	public static void error(String msg, Exception e) {
		logger.error(msg, e);
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 */
	public static void error(String msg) {
		logger.error(msg);
	}

}
