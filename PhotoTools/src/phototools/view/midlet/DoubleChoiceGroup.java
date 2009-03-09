/**
 * Copyright 2008, Timothy J. Stavenger
 */
package phototools.view.midlet;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Image;

/**
 * Convert an array of double to an array of String for the ChoiceGroup
 */
public class DoubleChoiceGroup extends ChoiceGroup {

	/**
	 * Convert the array of double to an array of String.
	 * 
	 * @param label
	 *            choice group label
	 * @param choiceType
	 *            choice type
	 * @param doubleElements
	 *            array of double
	 * @param imageElements
	 *            array of images
	 */
	public DoubleChoiceGroup(String label, int choiceType,
			double[] doubleElements, Image[] imageElements) {
		super(label, choiceType, toStringElements(doubleElements),
				imageElements);
	}

	/**
	 * Convert an array of double to an array of Strings.
	 * 
	 * @param doubleElements
	 *            array of double
	 * @return array of String
	 */
	private static String[] toStringElements(double[] doubleElements) {
		String[] strings = new String[doubleElements.length];

		for (int i = 0; i < doubleElements.length; i++) {
			strings[i] = Double.toString(doubleElements[i]);
		}

		return strings;
	}

	/**
	 * Convert the String at the given element number index into a double.
	 * 
	 * @param elementNum
	 *            index
	 * @return double converted from String
	 */
	public double getDouble(int elementNum) {
		return Double.parseDouble(getString(elementNum));
	}
}