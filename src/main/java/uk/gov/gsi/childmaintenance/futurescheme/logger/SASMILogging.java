package uk.gov.gsi.childmaintenance.futurescheme.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import uk.gov.gsi.childmaintenance.futurescheme.PdfConversion;

/**
 * The Class SASMILogging being used for generating SASMI reports for reconciliation.
 */
public class SASMILogging {
	
	/** The Constant sasMILogger. */
	private static final Logger sasMILogger = Logger.getLogger(SASMILogging.class);
	static {
		final Calendar cal = Calendar.getInstance();
		final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String sasMiPattern = "%m%n";
		PatternLayout sasMiLayout = new PatternLayout(sasMiPattern);
		PdfConversion.filePaths.setSasLog(PdfConversion.filePaths.getSasLog().substring(0,PdfConversion.filePaths.getSasLog().lastIndexOf('/')) +PdfConversion.filePaths.getSasLog().substring(PdfConversion.filePaths.getSasLog().lastIndexOf('/')+1,PdfConversion.filePaths.getSasLog().lastIndexOf('.'))+ "_" + sdf.format(cal.getTime()) + PdfConversion.filePaths.getSasLog().substring( PdfConversion.filePaths.getSasLog().lastIndexOf('.')));
		FileAppender sasMIAppender = null;
		try {
			sasMIAppender = new FileAppender(sasMiLayout, PdfConversion.filePaths.getSasLog());
			sasMIAppender.setImmediateFlush(true);
			sasMIAppender.setAppend(true);
			sasMIAppender.setFile(PdfConversion.filePaths.getSasLog());
			sasMIAppender.setThreshold(Level.INFO);
		} catch (Exception e) {
			LALogging.error("Failed to initialize SASMI Logger");
		}

		sasMILogger.addAppender(sasMIAppender);
	}

	/**
	 * Instantiates a new SASMI logging.
	 */
	private SASMILogging() {
	}

	
	/**
	 * Info.
	 *
	 * @param msg the msg
	 */
	public static void info(String msg) {
		sasMILogger.info(msg);

	}


}
