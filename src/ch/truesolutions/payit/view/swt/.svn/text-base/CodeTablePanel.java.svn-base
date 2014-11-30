/*
 * CodeTablePanel.java
 *
 * Created on 15. April 2002, 08:24
 */

package ch.truesolutions.payit.view.swt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import ch.truesolutions.payit.model.*;
import ch.truesolutions.payit.model.CodeTableListener;
import ch.truesolutions.payit.model.CodeTableModel;

/**
 *
 * @author  dseiler
 * @version
 */
public class CodeTablePanel
	extends Dialog
	implements CodeTableListener, PropertyChangeListener {

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

	// center objects
	private Canvas mainCanvas;
	TableViewer codeTableTbv;
	private CodeTableListener codeTableListener;
	
	
	// command panel
	Canvas commandCanvas;	
	Button newBtn;
	Button editBtn;
	Button deleteBtn;
	Button cancelBtn;

	Shell shell;
	Table codeTable;
	CodeTableModel model;
	int[] styles;

	private boolean closePanelAfterDblClick = true;

	public CodeTablePanel(
		Shell parent,
		CodeTableListener codeTableListener,
		CodeTableModel model,
		int[] styles) {
		super(parent);
		this.codeTableListener = codeTableListener;
		this.model = model;
		this.model.addPropertyChangeListener(this);
		this.styles = styles;
		create();
	}

	protected Control createContents(Composite parent) {		
		shell = getShell();
		shell.setText(Config.getInstance().getText(model.getTitle()));
		parent.setLayout(new FormLayout());
		shell.setVisible(false);
		mainCanvas = new Canvas(parent,SWT.NONE);
		mainCanvas.setLayout(new FillLayout());
		FormData mainCanvasFormData = new FormData();
		mainCanvasFormData.height = 300;
		mainCanvasFormData.top = new FormAttachment(0,0);
		//mainCanvasFormData.left = left1;
		//mainCanvasFormData.right = right1;
		mainCanvasFormData.bottom = new FormAttachment(100,-30);
		mainCanvas.setLayoutData(mainCanvasFormData);

		codeTableTbv = new TableViewer(mainCanvas, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		codeTable = codeTableTbv.getTable();
		codeTable.setLinesVisible (true);
		codeTable.setHeaderVisible(true);
		codeTable.addMouseListener(new MouseAdapter(){
			public void mouseDoubleClick(MouseEvent e){
				TableItem[] items = codeTable.getSelection();
				if (items.length == 1) {
					List l = (List) items[0].getData();
					codeTableListener.handleDoubleClick(l);
					if(closePanelAfterDblClick){
						handleCancelBtn();
					}
				} else {
					throw new RuntimeException("only one item can be selected if double click on a code table item!");
				}
			}		
		});
		
		commandCanvas = new Canvas(parent,SWT.NULL);
		commandCanvas.setLayout(new FormLayout());
		FormData commandCanvasFormData = new FormData();
		//commandCanvasFormData.height = 300;
		commandCanvasFormData.top = new FormAttachment(100,-30);
		commandCanvasFormData.left = new FormAttachment(0,0);
		commandCanvasFormData.right = new FormAttachment(100,0);
		commandCanvasFormData.bottom = new FormAttachment(100,0);
		commandCanvas.setLayoutData(commandCanvasFormData);

		cancelBtn = new Button(commandCanvas,SWT.PUSH);
		FormData cancelBtnFormData = new FormData();
		cancelBtnFormData.top = new FormAttachment(100,-25);
		cancelBtnFormData.left = new FormAttachment(100,-65);
		cancelBtnFormData.right = new FormAttachment(100,-5);
		cancelBtnFormData.bottom = new FormAttachment(100,-5);
		cancelBtn.setLayoutData(cancelBtnFormData);
		cancelBtn.setText(Config.getInstance().getText("button.cancel"));
		cancelBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				handleCancelBtn();			}		
		});
				
		// if codeTableListener is null we want a standalone
		// codetable with editing functionality, and we play
		// the role as codetable listener ourselfes.
		if (this.codeTableListener == null) {
			closePanelAfterDblClick = false;
			// command objects

			newBtn = new Button(commandCanvas,SWT.PUSH);
			editBtn = new Button(commandCanvas,SWT.PUSH);
			deleteBtn = new Button(commandCanvas,SWT.PUSH);
			

			// initialize the comp on the command panel
			newBtn.addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent e){
					System.out.println("opening new record...");
					//handleNewBtn();
				}		
			});
			
			editBtn.addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent e){
					System.out.println("editing code table record");
					//handleEditBtn();
				}		
			});

			deleteBtn.addMouseListener(new MouseAdapter() {
				public void mouseUp(MouseEvent e){
					System.out.println("deleting code table record");
					//handleDeleteBtn();
				}		
			});
						
			// if no code table listener was specified
			// we act our self as the codetable listener
			// for handling doubleClicks on record items			
			this.codeTableListener = this;
		}
		String[] titles = model.getTitles();
		
		CodeTableProvider codeTableProvider = new CodeTableProvider();
		codeTableTbv.setContentProvider(codeTableProvider);
		codeTableTbv.setLabelProvider(codeTableProvider);		
		codeTableTbv.setSorter(new CodeTableSorter(titles.length));

		for(int i=0; i<titles.length;i++){
			TableColumn column = new TableColumn (codeTable, styles[i]);
			column.setData(new Integer(i));
			column.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					TableColumn tc = (TableColumn)e.getSource();
					int selColumnIndex = ((Integer)tc.getData()).intValue();
					((CodeTableSorter)codeTableTbv.getSorter()).setSortedColumnIndex(selColumnIndex);
					codeTableTbv.refresh();
				}
			});
			column.setText(Config.getInstance().getText(titles[i]));
		}
	
		// this has to be here!!
		codeTableTbv.setInput(model);

		// now the column are packed to the right size
		for(int i=0; i<titles.length;i++){
			codeTable.getColumn(i).pack();
		}
		
		shell.pack();

		return shell;
	}

	public void setClosePanelAfterDblClick(boolean b) {
		this.closePanelAfterDblClick = b;
	}

	public void tryToShowCodeTable() {
		tryToShowCodeTable(SHOW_SOMETIMES);
	}
	
	public void tryToShowCodeTable(int mode) {
		// the codetable panel is shown according to the mode flag
		int rowCount = model.getTableData().size();
		boolean hasMoreThanOneRow = false;
		if (rowCount == 0) {
			model.noCondition();
			rowCount = model.getTableData().size();
			if (rowCount > 0) { // exception for this case
				hasMoreThanOneRow = true;
			}
		} else if ((rowCount == 1) && !(mode == SHOW_ALWAYS)) {
			codeTableListener.handleDoubleClick(model.getTableDataAt(0));
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
			if(shell == null){
				this.open();
			} else {
				shell.setVisible(true);
			}
			shell.setFocus();
	}

	void handleNewBtn() {
	}
	void handleEditBtn() {
	}
	void handleDeleteBtn() {
	}
	void handleCancelBtn() {
		shell.setVisible(false);
	}
	
	public void handleDoubleClick(List record) {
		handleEditBtn();
	}

	/**
	 * 	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 */
	protected void setShellStyle(int arg0) {
		super.setShellStyle(SWT.TITLE | SWT.RESIZE /*| SWT.BORDER | SWT.PRIMARY_MODAL*/);
	}
	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		// this event should alway come from a code table refresh
		codeTableTbv.refresh();
	}

}