package uk.gov.gsi.childmaintenance.futurescheme;

import uk.gov.gsi.childmaintenance.futurescheme.logger.LALogging;

/**
 * The Class LAUtilityException is a custom exception class.
 */
public class LAUtilityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new LA utility exception.
	 *
	 * @param errorMessage the error message
	 */
	public LAUtilityException(String errorMessage) {
		LALogging.error(errorMessage);
	}
}
