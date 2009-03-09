/**
 * Copyright 2008, Timothy J. Stavenger
 */
package phototools.view.midlet;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Image;

/**
 * Convert the array of int to an array of String.
 */
public class IntegerChoiceGroup extends ChoiceGroup {

	/**
	 * Convert the array of int to an array of String.
	 * 
	 * @param label
	 *            choice group label
	 * @param choiceType
	 *            choice type
	 * @param integerElements
	 *            array of int
	 * @param imageElements
	 *            array of images
	 */
	public IntegerChoiceGroup(String label, int choiceType,
			int[] integerElements, Image[] imageElements) {
		super(label, choiceType, toStringElements(integerElements),
				imageElements);
	}

	/**
	 * Convert an array of int to an array of Strings.
	 * 
	 * @param integerElements
	 *            array of int
	 * @return array of String
	 */
	private static String[] toStringElements(int[] integerElements) {
		String[] strings = new String[integerElements.length];

		for (int i = 0; i < integerElements.length; i++) {
			strings[i] = Integer.toString(integerElements[i]);
		}

		return strings;
	}

	/**
	 * Convert the String at the given element number index into a int.
	 * 
	 * @param elementNum
	 *            index
	 * @return int converted from String
	 */
	public int getInt(int elementNum) {
		return Integer.parseInt(getString(elementNum));
	}
}
