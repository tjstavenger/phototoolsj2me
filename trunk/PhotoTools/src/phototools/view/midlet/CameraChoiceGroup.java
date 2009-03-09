/**
 * Copyright 2008, Timothy J. Stavenger
 */
package phototools.view.midlet;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Image;

import phototools.Camera;

/**
 * @author tstavenger
 * 
 */
public class CameraChoiceGroup extends ChoiceGroup {
	
	/**
	 * Convert the array of Camera to an array of String.
	 * 
	 * @param label
	 *            choice group label
	 * @param choiceType
	 *            choice type
	 * @param cameraElements
	 *            array of Camera
	 * @param imageElements
	 *            array of images
	 */
	public CameraChoiceGroup(String label, int choiceType,
			Camera[] cameraElements, Image[] imageElements) {
		super(label, choiceType, toStringElements(cameraElements),
				imageElements);
	}

	/**
	 * Convert an array of Camera to an array of Strings (model names)
	 * 
	 * @param cameraElements
	 *            array of Camera
	 * @return array of String
	 */
	private static String[] toStringElements(Camera[] cameraElements) {
		String[] strings = new String[cameraElements.length];

		for (int i = 0; i < cameraElements.length; i++) {
			strings[i] = cameraElements[i].getModel();
		}

		return strings;
	}

	/**
	 * Get the Camera at the given index
	 * 
	 * @param elementNum
	 *            index
	 * @return double converted from String
	 */
	public Camera getCamera(int elementNum) {
		return Camera.selectCamera(elementNum);
	}
	
	/**
	 * Get the selected Camera.
	 * 
	 * @return Camera
	 */
	public Camera getSelectedCamera() {
		return getCamera(getSelectedIndex());
	}
}
