/**
 * Copyright 2008, Timothy J. Stavenger
 */
package phototools.view.midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

/**
 * {@link FormCanvas} implementation for the Photo Tools application, which
 * calculates depth of field, field of view, angle of view, and hyperfocal
 * distance.
 */
public class PhotoToolsCanvas extends FormCanvas {
	private PhotoForm photoForm;
	private OptionsForm optionsForm;

	private Command optionsCommand = new Command("Options", "Set Options",
			Command.HELP, Integer.MAX_VALUE);

	/**
	 * Initialize the view.
	 * 
	 * @parent MIDlet running this canvas
	 */
	public PhotoToolsCanvas(MIDlet parent) {
		super(parent);

		this.optionsForm = new OptionsForm();

		this.photoForm = new PhotoForm(optionsForm.getSelectedAperture(),
				optionsForm.getSelectedCamera(), optionsForm.isMetric());
		photoForm.repaint();

		addCommands();

		setForm(photoForm);
	}

	/**
	 * Recalculate the numbers each time the screen is painted.
	 * 
	 * @see phototools.view.midlet.FormCanvas#paintForm(javax.microedition.lcdui.Graphics)
	 */
	protected void onPaintForm(Graphics graphics) {
		photoForm.repaint();
	}

	/**
	 * 
	 * @see phototools.view.midlet.FormCanvas#onCommandAction(javax.microedition.lcdui.Command,
	 *      javax.microedition.lcdui.Displayable)
	 */
	public void onCommandAction(Command command, Displayable displayable) {
		photoForm.commandAction(command, displayable);

		if (optionsCommand.equals(command)) {
			switchToSubForm(optionsForm);
		}
	}

	/**
	 * 
	 * @see phototools.view.midlet.FormCanvas#onSwitchFormBack(javax.microedition.lcdui.Form)
	 */
	protected void onSwitchFormBack(Form subForm) {
		if (optionsForm.equals(subForm)) {
			photoForm.setCamera(optionsForm.getSelectedCamera());
			photoForm.setAperture(optionsForm.getSelectedAperture());
			photoForm.setMetric(optionsForm.isMetric());
		}
	}

	protected void addCommands() {
		addCommand(optionsCommand);
	}

	protected void removeCommands() {
		removeCommand(optionsCommand);
	}
}