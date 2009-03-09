/**
 * 
 */
package phototools.view.midlet;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author tstavenger
 * 
 */
public class PhotoToolsMIDlet extends MIDlet {
	PhotoToolsCanvas photoToolsCanvas;

	public PhotoToolsMIDlet() {
		this.photoToolsCanvas = new PhotoToolsCanvas(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {

		Display display = Display.getDisplay(this);

		display.setCurrent(photoToolsCanvas);

		photoToolsCanvas.repaint();
	}

}
