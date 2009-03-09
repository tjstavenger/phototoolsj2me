/**
 * 
 */
package phototools.utility;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * @author tstavenger
 * 
 */
public class PhotoToolsRecordStore {
	private static final String RECORD_STORE_NAME = "PhotoToolsOptions";
	private static final String APERTURE_SCALE_KEY = "apertureScale";
	private static final String CAMERA_KEY = "camera";
	private static final String UNIT_KEY = "unit";
	private static final String FOCAL_LENGTH_KEY = "focalLength";
	private static final String APERTURE_KEY = "aperture";
	private static final String FOCUS_DISTANCE_KEY = "focusDistance";
	private static final String DELIMITER = "=";

	private static String load(String key, String defaultValue) {
		RecordStore recordStore = null;
		RecordEnumeration recordEnumeration = null;
		String value = defaultValue;

		try {
			try {
				recordStore = RecordStore.openRecordStore(RECORD_STORE_NAME,
						true);
				recordEnumeration = recordStore.enumerateRecords(null, null,
						false);

				while (recordEnumeration.hasNextElement()) {
					byte[] raw = recordEnumeration.nextRecord();
					String option = new String(raw);

					// Parse out the key/value pair
					int index = option.indexOf(DELIMITER);
					String currentKey = option.substring(0, index);

					if (currentKey.equals(key)) {
						value = option.substring(index + 1);
					}
				}
			} finally {
				if (recordEnumeration != null) {
					recordEnumeration.destroy();
				}

				if (recordStore != null) {
					recordStore.closeRecordStore();
				}
			}
		} catch (RecordStoreException e) {
			// return the default
		}

		return value;

	}

	private static void save(String key, Object value) {
		RecordStore recordStore = null;
		RecordEnumeration recordEnumeration = null;

		try {
			try {
				recordStore = RecordStore.openRecordStore(RECORD_STORE_NAME,
						true);
				recordEnumeration = recordStore.enumerateRecords(null, null,
						false);

				// Delete the current value for this key
				while (recordEnumeration.hasNextElement()) {
					int id = recordEnumeration.nextRecordId();
					byte[] raw = recordStore.getRecord(id);
					String option = new String(raw);

					// Parse out the key/value pair
					int index = option.indexOf(DELIMITER);
					String currentKey = option.substring(0, index);

					if (currentKey.equals(key)) {
						recordStore.deleteRecord(id);
					}
				}

				// Save the new value for this key
				String option = key + DELIMITER + value;
				recordStore.addRecord(option.getBytes(), 0, option.length());
			} finally {
				if (recordEnumeration != null)
					recordEnumeration.destroy();
				if (recordStore != null)
					recordStore.closeRecordStore();
			}
		} catch (RecordStoreException e) {
			// oh well, can't say we didn't try
		}
	}

	public static int getApertureScaleSelectedIndex() {
		return Integer.parseInt(load(APERTURE_SCALE_KEY, "0"));
	}

	public static void setApertureScaleSelectedIndex(int selectedIndex) {
		save(APERTURE_SCALE_KEY, new Integer(selectedIndex));
	}

	public static int getCameraSelectedIndex() {
		return Integer.parseInt(load(CAMERA_KEY, "0"));
	}

	public static void setCameraScaleSelectedIndex(int selectedIndex) {
		save(CAMERA_KEY, new Integer(selectedIndex));
	}

	public static int getUnitSelectedIndex() {
		return Integer.parseInt(load(UNIT_KEY, "0"));
	}

	public static void setUnitSelectedIndex(int selectedIndex) {
		save(UNIT_KEY, new Integer(selectedIndex));
	}

	public static int getFocalLength() {
		return Integer.parseInt(load(FOCAL_LENGTH_KEY, "50"));
	}

	public static void setFocalLength(int focalLength) {
		save(FOCAL_LENGTH_KEY, new Integer(focalLength));
	}

	public static int getApertureSelectedIndex() {
		return Integer.parseInt(load(APERTURE_KEY, "0"));
	}

	public static void setApertureSelectedIndex(int selectedIndex) {
		save(APERTURE_KEY, new Integer(selectedIndex));
	}

	public static double getFocusDistance() {
		return Double.parseDouble(load(FOCUS_DISTANCE_KEY, "10"));
	}

	public static void setFocusDistance(double focusDistance) {
		save(FOCUS_DISTANCE_KEY, new Double(focusDistance));
	}

	private PhotoToolsRecordStore() {
		super();
	}
}