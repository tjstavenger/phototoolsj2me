/**
 * 
 */
package phototools.view.midlet;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

import phototools.Aperture;
import phototools.Camera;
import phototools.Photo;
import phototools.utility.DoubleFormatter;
import phototools.utility.PhotoToolsRecordStore;

/**
 *  
 */
public class PhotoForm extends PhotoToolsForm {
	private static final String NEAR_LIMIT_LABEL = "    Near Limit: ";
	private static final String FAR_LIMIT_LABEL = "    Far Limit: ";
	private static final String BEFORE_LABEL = "    Before: ";
	private static final String BEHIND_LABEL = "    Behind: ";
	private static final String HYPERFOCAL_LABEL = "Hyperfocal";
	private static final String FOV_LABEL = "Field of View: ";
	private static final String AOV_LABEL = "Angle of View: ";

	private static final int PRECISION = 2;

	private TextField focalLength;
	private ApertureChoiceGroup aperture;
	private TextField focusDistance;

	private StringItem depthOfFieldBefore;
	private StringItem depthOfFieldBehind;
	private StringItem depthOfFieldNearLimit;
	private StringItem depthOfFieldFarLimit;
	private StringItem hyperfocalLabel;
	private StringItem halfHyperfocalDistance;
	private StringItem hyperfocalDistance;

	private StringItem fieldOfView;
	private StringItem angleOfView;

	private Photo photo;

	/**
	 * 
	 */
	public PhotoForm(Aperture aperture, Camera camera, boolean metric) {
		super("Photo Tools Calculator");

		// temporarily store the saved/default aperture selected index before it
		// gets overwritten by the setAperture() call
		int apertureSelectedIndex = PhotoToolsRecordStore
				.getApertureSelectedIndex();

		setAperture(aperture);
		setCamera(camera);
		setMetric(metric);

		// reset the saved/default aperture as after calling setAperture its
		// selected index is reset to zero
		this.aperture.setSelectedIndex(apertureSelectedIndex, true);
	}

	/**
	 * Add all of the form elements.
	 */
	protected void initialize() {
		this.photo = new Photo();
		photo.setCamera(new Camera());

		this.focalLength = new TextField("Focal Length: ", String
				.valueOf(PhotoToolsRecordStore.getFocalLength()), 10,
				TextField.DECIMAL);
		append(focalLength);

		this.aperture = new ApertureChoiceGroup("Aperture: ",
				ChoiceGroup.POPUP, new Aperture(), null);
		aperture.setSelectedIndex(PhotoToolsRecordStore
				.getApertureSelectedIndex(), true);
		append(aperture);

		this.focusDistance = new TextField("Focus Distance: ", String
				.valueOf(PhotoToolsRecordStore.getFocusDistance()), 10,
				TextField.DECIMAL);
		append(focusDistance);

		append(new Spacer(0, 5));
		StringItem dofLabel = new StringItem("Depth of Field", null);
		append(dofLabel);

		this.depthOfFieldNearLimit = new StringItem(NEAR_LIMIT_LABEL, null);
		append(depthOfFieldNearLimit);

		this.depthOfFieldFarLimit = new StringItem(FAR_LIMIT_LABEL, null);
		append(depthOfFieldFarLimit);

		this.depthOfFieldBefore = new StringItem(BEFORE_LABEL, null);
		append(depthOfFieldBefore);

		this.depthOfFieldBehind = new StringItem(BEHIND_LABEL, null);
		append(depthOfFieldBehind);

		this.hyperfocalLabel = new StringItem(HYPERFOCAL_LABEL, null);
		append(hyperfocalLabel);

		this.halfHyperfocalDistance = new StringItem("    Half: ", null);
		append(halfHyperfocalDistance);

		this.hyperfocalDistance = new StringItem("    Distance:", null);
		append(hyperfocalDistance);

		this.fieldOfView = new StringItem(FOV_LABEL, null);
		append(fieldOfView);

		this.angleOfView = new StringItem(AOV_LABEL, null);
		append(angleOfView);
	}

	/**
	 * Return the feet or meters abbreviation depending on if the Metric or
	 * English system is being used.
	 * 
	 * @return String " (ft)" or " (m)"
	 * 
	 * @see Photo#isMetric()
	 */
	private String feetOrMeters() {
		if (photo.isMetric()) {
			return "m";
		} else {
			return "ft";
		}
	}

	protected void repaint() {
		recalculate();
	}

	public void setCamera(Camera camera) {
		photo.setCamera(camera);
		repaint();
	}
	
	public void setAperture(Aperture aperture) {
		this.aperture.resetAperture(aperture, null);

		repaint();
	}
	
	public void setMetric(boolean metric) {
		photo.setMetric(metric);
	}

	/**
	 * Calculate the depth of field and hyperfocal distance.
	 */
	private void recalculate() {

		try {
			photo.setAperture(aperture.getSelectedAperture());
			PhotoToolsRecordStore.setApertureSelectedIndex(aperture
					.getSelectedIndex());

			photo.setFocalLength(Integer.parseInt(focalLength.getString()));
			PhotoToolsRecordStore.setFocalLength(photo.getFocalLength());

			halfHyperfocalDistance.setText(DoubleFormatter.round(photo
					.calculateHalfHyperfocalDistance(), PRECISION)
					+ " " + feetOrMeters());
			hyperfocalDistance.setText(DoubleFormatter.round(photo
					.calculateHyperfocalDistance(), PRECISION)
					+ " " + feetOrMeters());

			angleOfView.setText(DoubleFormatter.round(photo
					.calculateAngleOfViewHorizontal(), PRECISION)
					+ "\u00B0 x "
					+ DoubleFormatter.round(photo
							.calculateAngleOfViewVertical(), PRECISION)
					+ "\u00B0");
		} catch (NumberFormatException e) {
			halfHyperfocalDistance.setText("");
			hyperfocalDistance.setText("");
			angleOfView.setText("");
		}

		try {
			photo.setFocusDistance(Double
					.parseDouble(focusDistance.getString()));
			PhotoToolsRecordStore.setFocusDistance(photo.getFocusDistance());

			depthOfFieldFarLimit.setText(DoubleFormatter.roundDepthOfField(
					photo.calculateDepthOfFieldFarLimit(), PRECISION)
					+ " " + feetOrMeters());
			depthOfFieldNearLimit.setText(DoubleFormatter.round(photo
					.calculateDepthOfFieldNearLimit(), PRECISION)
					+ " " + feetOrMeters());
			depthOfFieldBefore.setText(DoubleFormatter.round(photo
					.calculateDepthOfFieldBefore(), PRECISION)
					+ " " + feetOrMeters());
			depthOfFieldBehind.setText(DoubleFormatter.roundDepthOfField(photo
					.calculateDepthOfFieldBehind(), PRECISION)
					+ " " + feetOrMeters());

			fieldOfView.setText(DoubleFormatter.round(photo
					.calculateFieldOfViewHorizontal(), PRECISION)
					+ " "
					+ feetOrMeters()
					+ " x "
					+ DoubleFormatter.round(photo
							.calculateFieldOfViewVertical(), PRECISION)
					+ " "
					+ feetOrMeters());
		} catch (NumberFormatException e) {
			depthOfFieldFarLimit.setText("");
			depthOfFieldNearLimit.setText("");
			depthOfFieldBefore.setText("");
			depthOfFieldBehind.setText("");

			fieldOfView.setText("");
		}
	}
}
