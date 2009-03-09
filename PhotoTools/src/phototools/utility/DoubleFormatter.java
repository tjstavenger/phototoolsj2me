/**
 * 
 */
package phototools.utility;

/**
 * @author tstavenger
 * 
 */
public class DoubleFormatter {

	/**
	 * Format the double into a String with the given digits of precision.
	 * 
	 * @param value
	 *            double
	 * @return String
	 */
	public static String round(double value, int precision) {
		double rounded = value + ((1D / MathUtility.pow(10D, precision)) / 2D);
		String string = String.valueOf(rounded);

		String result;

		if (precision == 0) {
			result = string.substring(0, string.indexOf("."));
		} else {
			result = string.substring(0, string.indexOf(".") + (precision + 1));
		}

		return result;
	}

	public static String roundDepthOfField(double value, int precision) {
		String result;

		if (value >= 0) {
			result = round(value, precision);
		} else {
			result = "\u221e";
		}

		return result;
	}

	public static String truncate(double value, int precision) {
		String string = String.valueOf(value);
		String result;

		if (precision <= 0) {
			result = string.substring(0, string.indexOf("."));
		} else {
			result = string.substring(0, string.indexOf(".") + (precision + 1));
		}

		return result;
	}

	/**
	 * Cannot instantiate, use static methods
	 */
	private DoubleFormatter() {
		super();
	}
}
