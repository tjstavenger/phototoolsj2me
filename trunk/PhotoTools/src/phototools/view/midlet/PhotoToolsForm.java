/**
 * 
 */
package phototools.view.midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;

/**
 * @author tstavenger
 * 
 */
public abstract class PhotoToolsForm extends Form implements CommandListener {
	/**
	 * @param arg0
	 * @param arg1
	 */
	public PhotoToolsForm(String arg0, Item[] arg1) {
		super(arg0, arg1);

		initialize();
		addCommands();

		repaint();
	}

	/**
	 * @param title
	 */
	public PhotoToolsForm(String title) {
		super(title);

		initialize();
		addCommands();

		repaint();
	}

	protected abstract void initialize();

	protected void repaint() {

	}

	public void removeCommands() {

	}

	public void addCommands() {

	}
	
	public void onSwitchAway() {
		
	}
	
	public void onSwitchTo() {
		
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition
	 *      .lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public final void commandAction(Command command, Displayable displayable) {
		onCommandAction(command, displayable);
		repaint();
	}
	
	protected void onCommandAction(Command command, Displayable displayable) {

	}
}
