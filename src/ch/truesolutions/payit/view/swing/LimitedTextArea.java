package ch.truesolutions.payit.view.swing;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class LimitedTextArea extends JTextArea implements DocumentListener,FocusListener
{
	private int maxRows;
	private int maxColumns;
	private String[] lines;
	
	public LimitedTextArea(int maxRows, int maxColumns)
	{
		super();
		
		lines = new String[maxRows];
		for(int i=0;i<maxRows;i++)
			lines[i] = "";
			
		this.maxRows = maxRows;
		this.maxColumns = maxColumns;
		
		this.addFocusListener(this);
		
		Document doc = new TextAreaDocument(maxRows,maxColumns);
		doc.addDocumentListener(this);
		setDocument(doc);
		
		setBorder(new JTextField().getBorder());
		//setBorder(BasicBorders.getTextFieldBorder());
		setLineWrap(true);
		setWrapStyleWord(false);
	}
	
	public String getLine(int lineNr)
	{
		if(lineNr < maxRows)
			return lines[lineNr];
		else
			return "";
	}
	
	public void setText(String text)
	{
		super.setText(text);
		super.setText(fitText());
	}	
	
	public boolean isManagingFocus()
	{
		return false;
	}
	
	// Methods from the DocumentListener Interface
	public void removeUpdate(DocumentEvent e)
	{
		if(e instanceof TextAreaDocument.MyEvent)
			transferFocus();
	}
	
	public void insertUpdate(DocumentEvent e){}
	public void changedUpdate(DocumentEvent e){}
	public void focusGained(FocusEvent e){}

	public void focusLost(FocusEvent e)
	{
		//System.out.println("Original:\n"+getText());
		//System.out.println("Modified:\n"+fitText());
		super.setText(fitText());
		/*
		for(int i = 0;i<maxRows;i++)
			System.out.println("line "+i+":"+getLine(i));
		*/
	}
	
	private String fitText()
	{
		String text = this.getText();
		String tmp = "";
		int currentPos = 0;
          	int sectionLength = 0;
		int numLines = 0;
		int x = 0;
		int i = 0;
		int j = 0;
          	
          	while(x != -1)
          	{
          		x = text.indexOf('\n',currentPos);
          		if(x == -1)
          			sectionLength = text.length()-1 - currentPos;
          		else
          			sectionLength = x - currentPos;
          		j=0;
          		for(i=sectionLength;i > maxColumns;i=i-maxColumns)
          		{
          			tmp = tmp + text.substring(j*maxColumns,j*maxColumns+maxColumns) + "\n";
          			j++;
          		}
          		currentPos = x+1;
          		x = text.indexOf('\n',currentPos);
          	}
          	tmp = tmp + text.substring(j*maxColumns,text.length());
          	// get all the generated lines
          	currentPos = 0;
          	x = tmp.indexOf('\n',currentPos);
          	while(x!=-1)
          	{
          		if(numLines < maxRows)
          			lines[numLines] = tmp.substring(currentPos,x);
          		numLines++;
          		currentPos = x + 1;
          		x = tmp.indexOf('\n',currentPos);
          	}
          	if(numLines < maxRows)
          			lines[numLines] = tmp.substring(currentPos,tmp.length());
          	// clean the remaining lines
          	for(int k = numLines+1;k<maxRows;k++)
          		lines[k] = "";
          	return tmp;
         }
}

class TextAreaDocument extends PlainDocument
{	
	private int maxRows;
        private int maxColumns;
	
	public TextAreaDocument(int maxRows,int maxColumns)
	{
		super();
		this.maxRows = maxRows;
		this.maxColumns = maxColumns;
	}
	
	public void insertString(int offset, 
       		String str, AttributeSet a)
        	throws BadLocationException {
          
          int numLines = 0;
          int currentPos = 0;
          int sectionLength = 0;
          
          super.insertString(offset, str, a);
          char[] insertChars = str.toCharArray();
          
          String text = getText(0,getLength());
          
          if(insertChars.length > 0)
          {
          	int x = 0;
          	while(x != -1)
          	{
          		x = text.indexOf('\n',currentPos);
          		if(x == -1)
          		{
          			sectionLength = text.length()-1 - currentPos;
          			numLines = numLines + (sectionLength / maxColumns);
          		}
          		else
          		{
          			numLines++;
          			sectionLength = x - currentPos;
          			if(sectionLength != maxColumns)
          				numLines = numLines + (sectionLength / maxColumns);
          		}
          		currentPos = x+1;
          	}
          	if(numLines >= maxRows)
          	{
          		super.remove(offset, 1);
          		if(insertChars[0] == '\n')
          		{
          			// if it was the return button we move the focus to the next component
          			fireRemoveUpdate(new MyEvent(offset,insertChars.length,DocumentEvent.EventType.REMOVE));
          		}
          	}
          }
	}		
	
	class MyEvent extends AbstractDocument.DefaultDocumentEvent
	{
		public MyEvent(int offs, int len, DocumentEvent.EventType type)
		{
			super(offs,len,type);
		}	
	}
		
}