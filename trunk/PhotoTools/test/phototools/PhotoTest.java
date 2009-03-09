/**
 * 
 */
package phototools;

import phototools.Camera;
import phototools.Photo;
import junit.framework.TestCase;

/**
 * @author tstavenger
 * 
 */
public class PhotoTest extends TestCase {
	private Photo photo;
	
	/**
	 * Instantiate the Photo object to test
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		photo = new Photo(new Camera(), 50, 5.6, 10);
	}

	public void testCalculateNearDepthOfField() {
		double near = photo.calculateDepthOfFieldNearLimit();
		
		assertEquals(8.86, near, .1);
	}

	public void testCalculateFarDepthOfField() {
		double far = photo.calculateDepthOfFieldFarLimit();
		
		assertEquals(11.5, far, .1);
	}

	public void testCalculateHyperfocalDistance() {
		double hyperfocalDistance = photo.calculateHyperfocalDistance();
		
		assertEquals(77.25, hyperfocalDistance, .1);
	}
}