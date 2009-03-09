/**
 * 
 */
package phototools.view.midlet;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Image;

import phototools.Aperture;

/**
 *  
 */
public class ApertureChoiceGroup extends ChoiceGroup {
	private Aperture aperture;

	/**
	 * Convert the Aperture to an array of String.
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
	public ApertureChoiceGroup(String label, int choiceType, Aperture aperture,
			Image[] imageElements) {
		super(label, choiceType, toStringElements(aperture), imageElements);

		this.aperture = aperture;
	}

	/**
	 * Convert an array of double to an array of Strings.
	 * 
	 * @param doubleElements
	 *            array of double
	 * @return array of String
	 */
	private static String[] toStringElements(Aperture aperture) {
		double[] apertures = aperture.getFormattedApertures();
		String[] strings = new String[apertures.length];

		for (int i = 0; i < apertures.length; i++) {
			strings[i] = Double.toString(apertures[i]);
		}

		return strings;
	}

	public void resetAperture(Aperture aperture, Image[] imageElements) {
		if (!this.aperture.equals(aperture)) {
			this.aperture = aperture;

			deleteAll();

			String[] apertures = toStringElements(aperture);

			for (int i = 0; i < apertures.length; i++) {
				if (imageElements == null) {
					append(apertures[i], null);
				} else {
					append(apertures[i], imageElements[i]);
				}
			}
		}
	}

	/**
	 * Get the selected Camera.
	 * 
	 * @return Camera
	 */
	public double getSelectedAperture() {
		return aperture.calculateAperture(getSelectedIndex());
	}
}
