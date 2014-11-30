/*
 * Created on 05.07.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import ch.truesolutions.payit.model.*;

/**
 * @author Daniel
 */
public class UserAccountsTablePanel extends Dialog {

	// center objects
	private Canvas mainCanvas;
	TableViewer userAccountsTableTbv;	
	
	// command panel
	Canvas commandCanvas;	
	Button newBtn;
	Button editBtn;
	Button deleteBtn;
	Button cancelBtn;

	Shell shell;
	Table userAccountsTable;
	UserAccountsModel model;

	/**
	 * @param arg0
	 */
	public UserAccountsTablePanel(Shell parent) {
		super(parent);
		model = MainModel.getInstance().getUserAccountsModel();
	}

	protected Control createContents(Composite parent) {		
		shell = getShell();
		shell.setText(Config.getInstance().getText("useraccounttablepanel.title"));
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

		userAccountsTableTbv = new TableViewer(mainCanvas, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		userAccountsTable = userAccountsTableTbv.getTable();
		userAccountsTable.setLinesVisible (true);
		userAccountsTable.setHeaderVisible(true);
		userAccountsTable.addMouseListener(new MouseAdapter(){
			public void mouseDoubleClick(MouseEvent e){
				TableItem[] items = userAccountsTable.getSelection();
				if (items.length == 1) {
					UserAccount account = (UserAccount) items[0].getData();
					new UserAccountPanel(shell,account).open();
				} else {
					throw new RuntimeException("only one item can be selected if double click on a code table item!");
				}
			}		
		});
		model.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent event){
				// check if it is from the pending or from the archived payments
				System.out.println("property changes:"+event.getPropertyName());
				userAccountsTableTbv.refresh();
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
				handleCancelBtn();			
			}		
		});
				
		// initialize the comp on the command panel

		newBtn = new Button(commandCanvas,SWT.PUSH);		
		FormData newBtnFormData = new FormData();
		newBtnFormData.top = new FormAttachment(100,-25);
		newBtnFormData.left = new FormAttachment(0,5);
		newBtnFormData.right = new FormAttachment(0,65);
		newBtnFormData.bottom = new FormAttachment(100,-5);
		newBtn.setLayoutData(newBtnFormData);
		newBtn.setText(Config.getInstance().getText("button.new"));
		newBtn.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e){
				handleNewBtn();
			}		
		});
		
		editBtn = new Button(commandCanvas,SWT.PUSH);
		FormData editBtnFormData = new FormData();
		editBtnFormData.top = new FormAttachment(100,-25);
		editBtnFormData.left = new FormAttachment(0,70);
		editBtnFormData.right = new FormAttachment(0,130);
		editBtnFormData.bottom = new FormAttachment(100,-5);
		editBtn.setLayoutData(editBtnFormData);
		editBtn.setText(Config.getInstance().getText("button.edit"));
		editBtn.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e){
				System.out.println("editing code table record");
				//handleEditBtn();
			}		
		});

		deleteBtn = new Button(commandCanvas,SWT.PUSH);
		FormData deleteBtnFormData = new FormData();
		deleteBtnFormData.top = new FormAttachment(100,-25);
		deleteBtnFormData.left = new FormAttachment(0,135);
		deleteBtnFormData.right = new FormAttachment(0,195);
		deleteBtnFormData.bottom = new FormAttachment(100,-5);
		deleteBtn.setLayoutData(deleteBtnFormData);
		deleteBtn.setText(Config.getInstance().getText("button.delete"));
		deleteBtn.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e){
				System.out.println("deleting code table record");
				//handleDeleteBtn();
			}		
		});
						
		int[] styles = new int[]{SWT.LEFT,SWT.LEFT,SWT.LEFT};
		String[] titles = model.getTitles();
		
		UserAccountsTableProvider userAccountsTableProvider = new UserAccountsTableProvider();
		userAccountsTableTbv.setContentProvider(userAccountsTableProvider);
		userAccountsTableTbv.setLabelProvider(userAccountsTableProvider);		
		//TODO sorter: userAccountsTableTbv.setSorter(new CodeTableSorter(titles.length));

		for(int i=0; i<titles.length;i++){
			TableColumn column = new TableColumn (userAccountsTable, styles[i]);
			column.setData(new Integer(i));
			column.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					TableColumn tc = (TableColumn)e.getSource();
					int selColumnIndex = ((Integer)tc.getData()).intValue();
					//((CodeTableSorter)userAccountsTableTbv.getSorter()).setSortedColumnIndex(selColumnIndex);
					userAccountsTableTbv.refresh();
				}
			});
			column.setText(Config.getInstance().getText(titles[i]));
		}
	
		// this has to be here!!
		userAccountsTableTbv.setInput(model);

		// now the column are packed to the right size
		for(int i=0; i<titles.length;i++){
			userAccountsTable.getColumn(i).pack();
		}
		
		shell.pack();

		return shell;
	}
	
	private void handleCancelBtn(){
		this.close();
	}
	
	private void handleNewBtn(){
		new UserAccountPanel(shell,new UserAccount()).open();
	}
	
	/**
	 * 	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 */
	protected void setShellStyle(int arg0) {
		super.setShellStyle(SWT.TITLE | SWT.RESIZE | SWT.PRIMARY_MODAL/*| SWT.BORDER | SWT.PRIMARY_MODAL*/);
	}


}
