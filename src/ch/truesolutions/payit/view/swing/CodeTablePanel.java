/*
 * CodeTablePanel.java
 *
 * Created on 15. April 2002, 08:24
 */

package ch.truesolutions.payit.view.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableModel;

import ch.truesolutions.payit.model.*;
import ch.truesolutions.payit.model.CodeTableListener;
import ch.truesolutions.payit.model.CodeTableModel;

import java.util.List;

/**
 *
 * @author  dseiler
 * @version
 */
public class CodeTablePanel
	extends JDialog
	implements MouseListener, CodeTableListener {

	/**
	 * codetable panel is always shown
	 */
	public static final int SHOW_ALWAYS = 0;
	/**
	 * DEFAULT
	 * codetable panel is only shown if the table
	 * data contains not exactly one row. Otherwise
	 * the data is written directly in the ArrayList
	 */
	public static final int SHOW_SOMETIMES = 1;
	/**
	 * codetable panel is never shown. If the table
	 * data contains exactly one row, the values are
	 * propageted to the listeners.
	 */
	public static final int SHOW_NEVER = 2;

	// panels
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel commandPanel;

	// center objects
	JTable codeTable = new JTable();
	JScrollPane jScrollPane = new JScrollPane();
	CodeTableListener codeTableListener;
	JButton newBtn;
	JButton editBtn;
	JButton deleteBtn;
	JButton cancelBtn;
	private CodeTableModel model;
	private int[] columnStyles;
	private int width;
	private int height;

	Window parentWindow;

	private boolean closePanelAfterDblClick = true;

	public CodeTablePanel(
			JDialog parentDialog,
			CodeTableListener codeTableListener,
			CodeTableModel model,
			int[] columnStyles,
			int width,
			int height) {
		super(parentDialog);

		this.parentWindow = parentDialog;
		this.codeTableListener = codeTableListener;
		this.model = model;
		this.columnStyles = columnStyles;
		this.width = width;
		this.height = height;
		init();
	}

	public CodeTablePanel(
			JFrame parentFrame,
			CodeTableListener codeTableListener,
			CodeTableModel model,
			int[] columnStyles,
			int width,
			int height) {

		super(parentFrame);

		this.parentWindow = parentFrame;
		this.codeTableListener = codeTableListener;
		this.model = model;
		this.columnStyles = columnStyles;
		this.width = width;
		this.height = height;
		init();
	}

	private void init() {

		codeTable.addMouseListener(this);

		// initialize Bkstamcodetablepanel
		this.setTitle(model.getTitle());
		//this.setSize(640,200);
        
		// create the sorted table model
		TableSorter sortedTableModel = new TableSorter(new CodeTableModelSwing(model));
		codeTable.setModel(sortedTableModel);
		sortedTableModel.addMouseListenerToHeaderInTable(codeTable);
		// initialize Bkstamcodetable
		codeTable.setSelectionMode(0);
		codeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i=0;i<columnStyles.length;i++) {
			codeTable.getColumnModel().getColumn(i).setPreferredWidth(columnStyles[i]);
		}
		
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(true);
		setSize(width,height);
		setVisible(false);
		jScrollPane.getViewport().add(codeTable, null);
		mainPanel.add(jScrollPane);
		// Adding the panel to the frame
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				setVisible(false);
				dispose();
			}
		});

		// if codeTableListener is null we want a standalone
		// codetable with editing functionality, and we play
		// the role as codetable listener ourselfes.
		if (this.codeTableListener == null) {
			closePanelAfterDblClick = false;
			// command objects
			commandPanel = new JPanel();
			commandPanel.setPreferredSize(new Dimension(0, 30));
			// the width is automatic
			commandPanel.setLayout(null);

			newBtn = new JButton();
			editBtn = new JButton();
			deleteBtn = new JButton();
			cancelBtn = new JButton();

			// initialize the comp on the command panel
			//newBtn.setFont(labelFont);
			newBtn.setSize(80, 20);
			newBtn.setLocation(5, 5);
			newBtn.setMargin(new Insets(0,0,0,0));
			newBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handleNewBtn();
				}
			});
			//editBtn.setFont(labelFont);
			editBtn.setSize(80, 20);
			editBtn.setLocation(90, 5);
			editBtn.setMargin(new Insets(0,0,0,0));
			editBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handleEditBtn();
				}
			});
			//deleteBtn.setFont(labelFont);
			deleteBtn.setSize(80, 20);
			deleteBtn.setLocation(175, 5);
			deleteBtn.setMargin(new Insets(0,0,0,0));
			deleteBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handleDeleteBtn();
				}
			});
			//cancelBtn.setFont(labelFont);
			cancelBtn.setSize(80, 20);
			cancelBtn.setLocation(280, 5);
			cancelBtn.setMargin(new Insets(0,0,0,0));
			cancelBtn.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					handleCancelBtn();
				}
			});

			commandPanel.add(newBtn);
			commandPanel.add(editBtn);
			commandPanel.add(deleteBtn);
			commandPanel.add(cancelBtn);

			getContentPane().add(commandPanel, BorderLayout.SOUTH);
			applyLanguageChange();
			
			this.codeTableListener = this;
		}

	}

	public void setClosePanelAfterDblClick(boolean b) {
		this.closePanelAfterDblClick = b;
	}

	public void tryToShowCodeTable() {
		tryToShowCodeTable(SHOW_SOMETIMES);
	}
	
	public void tryToShowCodeTable(int mode) {
		// the codetable panel is shown according to the mode flag
		int rowCount = codeTable.getModel().getRowCount();
		boolean hasMoreThanOneRow = false;
		if (rowCount == 0) {
			((CodeTableModelInterface) (codeTable.getModel())).noCondition();
			rowCount = codeTable.getModel().getRowCount();
			if (rowCount > 0) { // exception for this case
				hasMoreThanOneRow = true;
			}
		} else if ((rowCount == 1) && !(mode == SHOW_ALWAYS)) {
			List al = model.getTableDataAt(0);
			codeTableListener.handleDoubleClick(al);
		} else {
			hasMoreThanOneRow = true;
		}

		// determine if the panel has to be shown
		if ((mode == SHOW_ALWAYS)
			|| ((mode == SHOW_SOMETIMES) && hasMoreThanOneRow)) {
			showPanel();
		}
	}

	public void showPanel() {
		// clear the previous selections
		codeTable.clearSelection();
		int xoff = (parentWindow.getWidth() - this.getWidth()) / 2;
		xoff = (xoff > 0) ? xoff : 0;
		int yoff = (parentWindow.getHeight() - this.getHeight()) / 2;
		yoff = (yoff > 0) ? yoff : 0;
		this.setLocation(
			parentWindow.getX() + xoff,
			parentWindow.getY() + yoff);
		this.setVisible(true);
	}
	/**
	 * Method of the MouseListener
	 */
	public void mousePressed(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
			if (e.getClickCount() == 2) {
				int selRow = codeTable.getSelectedRow();
				List l = ((CodeTableModelInterface)codeTable.getModel()).getTableDataAt(selRow);
				codeTableListener.handleDoubleClick(l);
				this.setVisible(closePanelAfterDblClick ? false : true);
			}
		}
	}

	void handleNewBtn() {
	}
	void handleEditBtn() {
	}
	void handleDeleteBtn() {
	}
	void handleCancelBtn() {
		this.setVisible(false);
	}
	
	void showDialog(JDialog dialog) {
        int xoff = (this.getWidth() - dialog.getWidth())/2;
        xoff = (xoff > 0) ? xoff : 0;
        int yoff = (this.getHeight() - dialog.getHeight())/2;
        yoff = (yoff > 0) ? yoff : 0;
        dialog.setLocation(this.getX()+xoff,this.getY()+yoff);
        dialog.setVisible(true);
    }

	/**
	 * Methods of the ConfigChangeListenerInterface
	 */

	public void applyLanguageChange() {
		/*
		setTitle(ConfigObject.getResourceBundle().getString("useraccounttablepanel.title"));
		 */
		if (this.codeTableListener == null) {
			newBtn.setText(
				Config.getInstance().getText("button.new"));
			editBtn.setText(
				Config.getInstance().getText("button.edit"));
			deleteBtn.setText(
				Config.getInstance().getText("button.delete"));
			cancelBtn.setText(
				Config.getInstance().getText("button.cancel"));
		}

	}

	public void setLookAndFeel(String lnfName) {
		try {
			UIManager.setLookAndFeel(lnfName);
			SwingUtilities.updateComponentTreeUI(this);
			//this.pack();
		} catch (Exception e) {
		}
	}

	/**
	 * Methods of the CodeTableModelInterface
	 */
	public List getTableDataAt(int index) {
		return (
			(CodeTableModelInterface) (codeTable.getModel())).getTableDataAt(
			index);
	}

	public void setCondition(String field, String value) {
		((CodeTableModelInterface) (codeTable.getModel())).setCondition(
			field,
			value);
	}

	public void noCondition() {
		((CodeTableModelInterface) (codeTable.getModel())).noCondition();
	}

	/**
	 * @see ts.ch.payit.CodeTableListener#handleDoubleClick(ArrayList)
	 */
	public void handleDoubleClick(List record) {
		handleEditBtn();
	}

}