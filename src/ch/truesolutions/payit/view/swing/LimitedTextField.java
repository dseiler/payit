package ch.truesolutions.payit.view.swing;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class LimitedTextField
	extends JTextField
	implements KeyListener, FocusListener {
	private int maxSigns;
	public LimitedTextField(int maxSigns) {
		super();
		Document doc = new TextFieldDocument(maxSigns, this);
		setDocument(doc);
		this.maxSigns = maxSigns;
		this.addKeyListener(this);
		this.addFocusListener(this);
		//setBorder(BasicBorders.getTextFieldBorder());
	}

	public void setText(String text) {
		int l = text.length();
		super.setText(text.substring(0, (l < maxSigns) ? l : maxSigns));
	}

	protected boolean validateChar(char c) {
		return true;
	}

	protected void handleEnterKey() {
	}

	public void keyPressed(KeyEvent e) {
	}
	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
		/*if(e.getKeyChar() == '\n')
			handleEnterKey();*/
	}
	public void focusGained(FocusEvent e) {
	}
	public void focusLost(FocusEvent e) {
	}
	
	protected char modifyChar(char c){
		return c;
	}

}

class TextFieldDocument extends PlainDocument {
	private int maxSigns;
	//private boolean onlyDigits;
	private LimitedTextField ltf;

	public TextFieldDocument(int maxSigns, LimitedTextField ltf) {
		super();
		this.maxSigns = maxSigns;
		this.ltf = ltf;
	}

	public void insertString(int offset, String str, AttributeSet a)
		throws BadLocationException {

		char[] insertChars = str.toCharArray();

		if ((insertChars.length > 0)
			&& (insertChars.length + getLength() <= maxSigns)) {
			if (ltf.validateChar(insertChars[0])) {
				//if (Character.isDigit(insertChars[0]))
				insertChars[0] = ltf.modifyChar(insertChars[0]);	
				super.insertString(offset, new String(insertChars), a);
			}
		}
	}
}