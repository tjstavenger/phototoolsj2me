/**
 * 
 */
package phototools.view.midlet;

import javax.microedition.lcdui.ChoiceGroup;

import phototools.Aperture;
import phototools.Camera;
import phototools.utility.PhotoToolsRecordStore;

/**
 * @author tstavenger
 * 
 */
public class OptionsForm extends PhotoToolsForm {
	private ChoiceGroup apertureScale;
	private CameraChoiceGroup cameras;
	private ChoiceGroup units;

	/**
	 * @param title
	 */
	public OptionsForm() {
		super("Selct Options");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see phototools.view.midlet.PhotoToolsForm#initialize()
	 */
	protected void initialize() {
		this.apertureScale = new ChoiceGroup("Aperture Scale: ",
				ChoiceGroup.POPUP, new String[] { "Third", "Half", "Full" },
				null);
		apertureScale.setSelectedIndex(PhotoToolsRecordStore
				.getApertureScaleSelectedIndex(), true);
		append(apertureScale);

		this.cameras = new CameraChoiceGroup("Camera: ", ChoiceGroup.POPUP,
				Camera.getCameras(), null);
		cameras.setSelectedIndex(
				PhotoToolsRecordStore.getCameraSelectedIndex(), true);
		append(cameras);

		this.units = new ChoiceGroup("Units: ", ChoiceGroup.POPUP,
				new String[] { "Feet", "Meters" }, null);
		units.setSelectedIndex(PhotoToolsRecordStore.getUnitSelectedIndex(),
				true);
		append(units);
	}

	/**
	 * Get the selected Aperture
	 * 
	 * @return Aperture
	 */
	public Aperture getSelectedAperture() {
		Aperture aperture = new Aperture();

		switch (apertureScale.getSelectedIndex()) {
		case 0:
			aperture.setThirdStop();
			break;
		case 1:
			aperture.setHalfStop();
			break;
		case 2:
			aperture.setFullStop();
			break;
		default:
			aperture.setThirdStop();

		}

		return aperture;
	}

	/**
	 * Get the selected Camera
	 * 
	 * @return Camera
	 */
	public Camera getSelectedCamera() {
		return cameras.getSelectedCamera();
	}

	public boolean isMetric() {
		return units.getSelectedIndex() == 1;
	}

	public boolean isFeet() {
		return units.getSelectedIndex() == 0;
	}

	public void onSwitchAway() {
		PhotoToolsRecordStore.setApertureScaleSelectedIndex(apertureScale
				.getSelectedIndex());
		PhotoToolsRecordStore.setCameraScaleSelectedIndex(cameras
				.getSelectedIndex());
		PhotoToolsRecordStore.setUnitSelectedIndex(units.getSelectedIndex());
	}
}