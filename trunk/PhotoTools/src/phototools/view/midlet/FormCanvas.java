/**
 * Copyright 2008, Timothy J. Stavenger
 */
package phototools.view.midlet;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

/**
 * Display using a {@link Canvas}, but still provide a generic layout using a
 * {@link Form}.
 */
public class FormCanvas extends Canvas implements CommandListener {
	private MIDlet parent;

	private PhotoToolsForm currentForm;
	private PhotoToolsForm savedForm;
	private Font labelFont;
	private Font textFont;
	private int selectedIndex;
	private int cursorIndex;

	private Command clearCommand = new Command("Clear",
			"Delete previous character", Command.CANCEL, Integer.MIN_VALUE);

	private Command subFormOkCommand = new Command("OK", "OK", Command.OK,
			Integer.MIN_VALUE);

	/**
	 * Set the default label and text {@link Font}.
	 * 
	 * @param parent
	 *            MIDlet running this canvas
	 */
	public FormCanvas(MIDlet parent) {
		setParent(parent);
		setLabelFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,
				Font.SIZE_SMALL));
		setTextFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN,
				Font.SIZE_SMALL));
		setSelectedIndex(0);
		setCursorIndex(-1);

		setCommandListener(this);
	}

	/**
	 * @return {@link #getWidth()} / 2
	 */
	protected final int getCenter() {
		return getWidth() / 2;
	}

	/**
	 * @return the cursorIndex
	 */
	protected final int getCursorIndex() {
		return cursorIndex;
	}

	/**
	 * @return the form
	 */
	protected final PhotoToolsForm getForm() {
		return currentForm;
	}

	/**
	 * @return the labelFont
	 */
	protected final Font getLabelFont() {
		return labelFont;
	}

	/**
	 * @return {@link #getHeight()} / 2
	 */
	protected final int getMiddle() {
		return getHeight() / 2;
	}

	/**
	 * @return the selectedIndex
	 */
	protected final int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * @return the textFont
	 */
	protected final Font getTextFont() {
		return textFont;
	}

	/**
	 * Add the given number represented by keyCode into the currently selected
	 * {@link TextField}. If the currently selected {@link Item} is not a
	 * {@link TextField}, ignore the input.
	 * 
	 * @param keyCode
	 *            int key pressed
	 */
	private void handleNumericKey(int keyCode) {
		Item item = getForm().get(getSelectedIndex());

		if (item instanceof TextField) {
			TextField textField = (TextField) item;

			switch (keyCode) {
			case Canvas.KEY_NUM0:
				textField.insert("0", getCursorIndex());
				break;

			case Canvas.KEY_NUM1:
				textField.insert("1", getCursorIndex());
				break;

			case Canvas.KEY_NUM2:
				textField.insert("2", getCursorIndex());
				break;

			case Canvas.KEY_NUM3:
				textField.insert("3", getCursorIndex());
				break;

			case Canvas.KEY_NUM4:
				textField.insert("4", getCursorIndex());
				break;

			case Canvas.KEY_NUM5:
				textField.insert("5", getCursorIndex());
				break;

			case Canvas.KEY_NUM6:
				textField.insert("6", getCursorIndex());
				break;

			case Canvas.KEY_NUM7:
				textField.insert("7", getCursorIndex());
				break;

			case Canvas.KEY_NUM8:
				textField.insert("8", getCursorIndex());
				break;

			case Canvas.KEY_NUM9:
				textField.insert("9", getCursorIndex());
				break;

			case Canvas.KEY_POUND:
				if (textField.getString().indexOf(".") == -1) {
					textField.insert(".", getCursorIndex());
				}
				break;

			default:
				break;
			}

			setCursorIndex(getCursorIndex() + 1);
		}
	}

	private void clearCharacter() {
		Item item = getForm().get(getSelectedIndex());

		if (item instanceof TextField) {
			int delete = getCursorIndex() - 1;

			if (delete >= 0) {
				((TextField) item).delete(getCursorIndex() - 1, 1);
				setCursorIndex(getCursorIndex() - 1);

				if (getCursorIndex() < 0) {
					setCursorIndex(0);
				}
			}
		}
		
		repaint();
	}

	private boolean isNumericKey(int keyCode) {
		return Canvas.KEY_NUM0 == keyCode || Canvas.KEY_NUM1 == keyCode
				|| Canvas.KEY_NUM2 == keyCode || Canvas.KEY_NUM3 == keyCode
				|| Canvas.KEY_NUM4 == keyCode || Canvas.KEY_NUM5 == keyCode
				|| Canvas.KEY_NUM6 == keyCode || Canvas.KEY_NUM7 == keyCode
				|| Canvas.KEY_NUM8 == keyCode || Canvas.KEY_NUM9 == keyCode
				|| Canvas.KEY_POUND == keyCode;
	}

	/**
	 * @return boolean true if the currently selected {@link Item} is one that
	 *         allows input
	 */
	protected boolean isSelectableItem() {
		boolean selectable = false;
		Item item = getForm().get(getSelectedIndex());

		if (item instanceof DateField || item instanceof TextField
				|| item instanceof ChoiceGroup) {
			selectable = true;
		}

		return selectable;
	}

	/**
	 * Scroll through the editable {@link Form} {@link Item} elements.
	 * 
	 * @see javax.microedition.lcdui.Canvas#keyPressed(int)
	 */
	protected void keyPressed(int keyCode) {
		if (isNumericKey(keyCode)) {
			handleNumericKey(keyCode);
		} else {
			switch (getGameAction(keyCode)) {
			case Canvas.UP:
				previousSelectableItem();
				break;

			case Canvas.DOWN:
				nextSelectableItem();
				break;

			case Canvas.RIGHT:
				nextSelection();
				break;

			case Canvas.LEFT:
				previousSelection();
				break;

			default:
				break;
			}
		}

		repaint();
	}

	/**
	 * Scroll through the editable {@link Form} {@link Item} elements.
	 * 
	 * Processed the key repetition by calling {@link #keyPressed(int)}.
	 * 
	 * @see javax.microedition.lcdui.Canvas#keyRepeated(int)
	 */
	protected void keyRepeated(int keyCode) {
		keyPressed(keyCode);
	}

	/**
	 * Increment the {@link #selectedIndex} until it is on a selectable
	 * {@link Item}
	 * 
	 * @see #isSelectableItem()
	 */
	protected final void nextSelectableItem() {
		do {
			setSelectedIndex(getSelectedIndex() + 1);

			if (getSelectedIndex() >= getForm().size()) {
				setSelectedIndex(0);
			}
		} while (!isSelectableItem());

		resetItemState();
	}

	/**
	 * If the currently selected {@link Item} is a {@link ChoiceGroup},
	 * increment its selected element by 1. If the current selection is the last
	 * one, wrap to the beginning.
	 */
	protected final void nextSelection() {
		Item item = getForm().get(getSelectedIndex());

		if (item instanceof ChoiceGroup) {
			ChoiceGroup choiceGroup = (ChoiceGroup) item;
			int nextChoice = choiceGroup.getSelectedIndex() + 1;

			if (nextChoice >= choiceGroup.size()) {
				nextChoice = 0;
			}

			choiceGroup.setSelectedIndex(nextChoice, true);
		} else if (item instanceof TextField) {
			setCursorIndex(getCursorIndex() + 1);
		}
	}

	/**
	 * Perform some functions prior to the {@link Form} being painted.
	 * 
	 * Default implementation does nothing. Sub classes can override this method
	 * to perform various operations.
	 * 
	 * @param graphics
	 *            Graphics to paint on
	 */
	protected void onPaintForm(Graphics graphics) {

	}

	/**
	 * Place the form on the screen
	 * 
	 * @param graphics
	 *            {@link Graphics} to paint on
	 * @see javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	protected final void paint(Graphics graphics) {
		setTitle(getForm().getTitle());
		setTicker(getForm().getTicker());

		graphics.setColor(0, 0, 0);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		paintForm(graphics);
	}

	/**
	 * Paint a ChoiceGroup. Assumes that only one element can be selected at a
	 * time.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            ChoiceGroup to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of ChoiceGroup (top of next item)
	 */
	protected int paintChoiceGroup(Graphics graphics, ChoiceGroup item,
			int top, boolean selected) {
		drawLabel(graphics, item, top);

		drawInputString(graphics, item.getString(item.getSelectedIndex()), top);

		if (selected) {
			drawActiveRect(graphics, item, top);
			fillSelectionTraingles(graphics, item, top);
		}

		return top + getLabelFont().getHeight();
	}

	protected void drawInputString(Graphics graphics, String input, int top) {
		if (input != null) {
			graphics.setFont(getTextFont());
			graphics.drawString(input, getCenter() + 2, top, Graphics.TOP
					| Graphics.LEFT);
		}
	}

	protected void drawActiveRect(Graphics graphics, Item item, int top) {
		graphics.setColor(105, 105, 105);
		graphics.drawRect(getCenter(), top, getCenter() - 1, getLabelFont()
				.getHeight() - 1);
	}

	protected void fillSelectionTraingles(Graphics graphics, Item item, int top) {
		graphics.setColor(105, 105, 105);
		int triangleTop = top + 2;
		int triangleMiddle = top + (getLabelFont().getHeight() / 2);
		int triangleBottom = top + getLabelFont().getHeight() - 2;
		int triangleRight = getWidth() - 4;
		int triangleLeft = triangleRight - 12;

		// draw left arrow
		graphics.fillTriangle(triangleLeft, triangleMiddle, triangleLeft + 5,
				triangleTop, triangleLeft + 5, triangleBottom);

		// draw right arrow
		graphics.fillTriangle(triangleRight, triangleMiddle, triangleRight - 5,
				triangleTop, triangleRight - 5, triangleBottom);
	}

	/**
	 * Paint a CustomItem. Currently does nothing.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            CustomItem to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of CustomItem (top of next item)
	 */
	protected int paintCustomItem(Graphics graphics, CustomItem item, int top) {
		return top;
	}

	/**
	 * Paint a DateField. Currently does nothing.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            DateField to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of DateField (top of next item)
	 */
	protected int paintDateField(Graphics graphics, DateField item, int top,
			boolean selected) {
		return top;
	}

	/**
	 * Paint the {@link Form} for this {@link FormCanvas}.
	 * 
	 * @param graphics
	 *            {@link Graphics} to paint on
	 */
	protected final void paintForm(Graphics graphics) {
		onPaintForm(graphics);

		int y = 0;

		for (int i = 0; i < getForm().size(); i++) {
			Item item = getForm().get(i);
			boolean selected = i == selectedIndex;

			if (item instanceof StringItem) {
				y = paintStringItem(graphics, (StringItem) item, y);
			} else if (item instanceof DateField) {
				y = paintDateField(graphics, (DateField) item, y, selected);
			} else if (item instanceof TextField) {
				y = paintTextField(graphics, (TextField) item, y, selected);
			} else if (item instanceof ChoiceGroup) {
				y = paintChoiceGroup(graphics, (ChoiceGroup) item, y, selected);
			} else if (item instanceof Spacer) {
				y = paintSpacer(graphics, (Spacer) item, y);
			} else if (item instanceof Gauge) {
				y = paintGauge(graphics, (Gauge) item, y);
			} else if (item instanceof ImageItem) {
				y = paintImageItem(graphics, (ImageItem) item, y);
			} else if (item instanceof CustomItem) {
				y = paintCustomItem(graphics, (CustomItem) item, y);
			}
		}
	}

	/**
	 * Paint a Gauge. Currently does nothing.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            Gauge to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of Gauge (top of next item)
	 */
	protected int paintGauge(Graphics graphics, Gauge item, int top) {
		return top;
	}

	/**
	 * Paint a ImageItem. Currently does nothing.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            ImageItem to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of ImageItem (top of next item)
	 */
	protected int paintImageItem(Graphics graphics, ImageItem item, int top) {
		return top;
	}

	/**
	 * Paint a {@link Spacer} on the screen left aligned startgin at the given
	 * top pixel. The {@link Spacer} is rendered at the
	 * {@link Spacer#getMinimumHeight()} with a horizontal line through the
	 * middle.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            Spacer to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of {@link Spacer} (top of next item)
	 */
	protected int paintSpacer(Graphics graphics, Spacer item, int top) {
		graphics.setColor(190, 190, 190);
		int lineY = top + item.getMinimumHeight() / 2;
		graphics.drawLine(0, lineY, getWidth(), lineY);

		return top + item.getMinimumHeight();
	}

	/**
	 * Paint a StringItem on the screen at left aligned starting at the given
	 * top pixel.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            StringItem to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of StringItem (top of next item)
	 */
	protected int paintStringItem(Graphics graphics, StringItem item, int top) {
		drawLabel(graphics, item, top);
		drawValueString(graphics, item.getText(), top);

		return top + getLabelFont().getHeight();
	}

	protected void drawValueString(Graphics graphics, String value, int top) {
		if (value != null) {
			graphics.setFont(getTextFont());
			graphics.drawString(value, getCenter(), top, Graphics.TOP
					| Graphics.LEFT);
		}
	}

	protected void drawLabel(Graphics graphics, Item item, int top) {
		graphics.setColor(255, 255, 255);
		graphics.setFont(getLabelFont());
		graphics.drawString(item.getLabel(), 0, top, Graphics.TOP
				| Graphics.LEFT);
	}

	/**
	 * Paint a TextField.
	 * 
	 * @param graphics
	 *            Graphics to paint with
	 * @param item
	 *            TextField to paint
	 * @param top
	 *            int top pixel
	 * @return int pixel at bottom of TextField (top of next item)
	 */
	protected int paintTextField(Graphics graphics, TextField item, int top,
			boolean selected) {
		drawLabel(graphics, item, top);
		drawInputString(graphics, item.getString(), top);

		if (selected) {
			drawActiveRect(graphics, item, top);
			drawCursor(graphics, item, top);
			addCommand(clearCommand);
		}

		return top + getLabelFont().getHeight();
	}

	protected void drawCursor(Graphics graphics, TextField item, int top) {
		if (getCursorIndex() == -1) {
			setCursorIndex(item.getString().length());
		} else if (getCursorIndex() > item.getString().length()) {
			setCursorIndex(item.getString().length());
		}

		int cursor = (getCenter() + 2)
				+ getTextFont().substringWidth(item.getString(), 0,
						getCursorIndex()) - 1;
		graphics.drawLine(cursor, top + 2, cursor, top
				+ getTextFont().getHeight() - 3);
	}

	/**
	 * Decrement the {@link #selectedIndex} until it is on a selectable
	 * {@link Item}
	 * 
	 * @see #isSelectableItem()
	 */
	protected final void previousSelectableItem() {
		do {
			setSelectedIndex(getSelectedIndex() - 1);

			if (getSelectedIndex() < 0) {
				setSelectedIndex(getForm().size() - 1);
			}
		} while (!isSelectableItem());

		resetItemState();
	}

	/**
	 * If the currently selected {@link Item} is a {@link ChoiceGroup},
	 * decrement its selected element by 1. If the current selection is the
	 * first one, wrap to the end.
	 */
	protected final void previousSelection() {
		Item item = getForm().get(getSelectedIndex());

		if (item instanceof ChoiceGroup) {
			ChoiceGroup choiceGroup = (ChoiceGroup) item;
			int previousChoice = choiceGroup.getSelectedIndex() - 1;

			if (previousChoice < 0) {
				previousChoice = choiceGroup.size() - 1;
			}

			choiceGroup.setSelectedIndex(previousChoice, true);
		} else if (item instanceof TextField) {
			setCursorIndex(getCursorIndex() - 1);

			if (getCursorIndex() == -1) {
				setCursorIndex(0);
			}
		}
	}

	private void resetItemState() {
		setCursorIndex(-1);
		removeCommand(clearCommand);
	}

	/**
	 * @param cursorIndex
	 *            the cursorIndex to set
	 */
	protected final void setCursorIndex(int cursorIndex) {
		this.cursorIndex = cursorIndex;
	}

	/**
	 * @param form
	 *            the form to set
	 */
	protected final void setForm(PhotoToolsForm form) {
		this.currentForm = form;
	}

	/**
	 * @param labelFont
	 *            the labelFont to set
	 */
	protected final void setLabelFont(Font labelFont) {
		this.labelFont = labelFont;
	}

	/**
	 * @param selectedIndex
	 *            the selectedIndex to set
	 */
	protected final void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * @param textFont
	 *            the textFont to set
	 */
	protected final void setTextFont(Font textFont) {
		this.textFont = textFont;
	}

	public final void commandAction(Command command, Displayable displayable) {
		onCommandAction(command, displayable);

		if (clearCommand.equals(command)) {
			clearCharacter();
		} else if (subFormOkCommand.equals(command)) {
			switchFormBack();
		}
	}

	public void onCommandAction(Command command, Displayable displayable) {

	}

	public void switchToSubForm(PhotoToolsForm subForm) {
		this.savedForm = getForm();		
		savedForm.removeCommands();
		
		savedForm.onSwitchAway();
		subForm.onSwitchTo();
		setForm(subForm);

		removeCommands();
		addCommand(subFormOkCommand);

		setSelectedIndex(-1);
		nextSelectableItem();
		resetItemState();

		repaint();

	}

	public void switchFormBack() {
		onSwitchFormBack(getForm());

		removeCommand(subFormOkCommand);
		addCommands();
		savedForm.addCommands();
		
		getForm().onSwitchAway();
		savedForm.onSwitchTo();
		setForm(savedForm);
		repaint();
	}

	protected void onSwitchFormBack(Form subForm) {

	}

	protected void addCommands() {

	}

	protected void removeCommands() {

	}

	/**
	 * @return the parent
	 */
	protected final MIDlet getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	protected final void setParent(MIDlet parent) {
		this.parent = parent;
	}
}