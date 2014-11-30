/*
 * Created on 08.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

/**
 * @author Daniel
 */
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

import ch.truesolutions.payit.model.*;

public class MainFrame extends ApplicationWindow {
	
	private MainModel mainModel;
	private PaymentsModel pendingPaymentsModel;
	private PaymentsModel archivedPaymentsModel;
	private Table pendingPaymentsTable;
	private Table archivedPaymentsTable;
	private TabFolder tabFolder;
	private TableViewer pendingPaymentsTbv;
	private TableViewer archivedPaymentsTbv;
	// all the actions
	private Action backupAction = createBackupAction();
	private Action restoreAction = createRestoreAction();
	private Action exitAction = createExitAction();
	private Action dtaExportAction = createDtaExportAction();
	private Action dtaImportAction = createDtaImportAction();
	private Action dtaViewAction = createDtaViewAction();
	private Action deleteAction = createDeleteAction();
	private Action archiveAction = createArchiveAction();
	private Action reactivateAction = createReactivateAction();
	private Action reuseAction = createReuseAction();
	private Action showAccountsAction = createShowAccountsAction();
	private Action showPostToAction = createShowPostToAction();
	private Action showPostForAction = createShowPostForAction();
	private Action showBankForAction = createShowBankForAction();
	private Action showBankListAction = createShowBankListAction();
	private Action showLocationListAction = createShowLocationListAction();
	private Action selectGermanAction = createSelectGermanAction();
	private Action selectEnglishAction = createSelectEnglishAction();
	private Action showAboutAction = createShowAboutAction();
	private Action showRedSlipAction = createShowRedSlipAction();
	private Action showBlueSlipAction = createShowBlueSlipAction();
	private Action showOrangeSlipAction = createShowOrangeSlipAction();
	private Action showBankSlipAction = createShowBankSlipAction();
	private Action showIpiSlipAction = createShowIpiSlipAction();
	
	private PaymentSlipRed paymentSlipRed;
	private PaymentSlipBlueOrange paymentSlipBlue;
	private PaymentSlipBlueOrange paymentSlipOrange;
	private PaymentSlipBank paymentSlipBank;
	private PaymentSlipIpi paymentSlipIpi;
	
	private UserAccountsTablePanel userAccountsTablePanel;
	
	public MainFrame() {
		super(null);
		mainModel = MainModel.getInstance();
		pendingPaymentsModel = mainModel.getPendingPaymentsModel();
		archivedPaymentsModel = mainModel.getArchivedPaymentsModel();
		setBlockOnOpen(true);
		addMenuBar();
		addToolBar(SWT.FLAT | SWT.BORDER);
		addStatusLine();
	}

	protected Control createContents(Composite parent) {
		
		Shell shell = getShell();
		shell.setText("JFace PayIT");
		
		paymentSlipRed = new PaymentSlipRed(shell);
		paymentSlipBlue = new PaymentSlipBlueOrange(shell,false);
		paymentSlipOrange = new PaymentSlipBlueOrange(shell,true);
		paymentSlipBank = new PaymentSlipBank(shell);
		paymentSlipIpi = new PaymentSlipIpi(shell);
			
		userAccountsTablePanel = new UserAccountsTablePanel(shell);
		
		tabFolder = new TabFolder (parent, SWT.BORDER);
		pendingPaymentsTbv = new TableViewer(tabFolder, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		archivedPaymentsTbv = new TableViewer(tabFolder, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		pendingPaymentsTable = pendingPaymentsTbv.getTable();
		archivedPaymentsTable = archivedPaymentsTbv.getTable();
		
		mainModel.addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent event){
				// check if it is from the pending or from the archived payments
				System.out.println("property changes:"+event.getPropertyName());
				if("pending_payments".equals(event.getPropertyName())){
					pendingPaymentsTbv.refresh();
				}
				if("archived_payments".equals(event.getPropertyName())){
					archivedPaymentsTbv.refresh();
				}			
			}
		});
				
		/**
		 * Tab Folder with Tables
		 */				
		TabItem pendingPaymentsTabItem = new TabItem (tabFolder, SWT.NULL);
		pendingPaymentsTabItem.setText (Config.getInstance().getText("main.tab1"));
		TabItem archivedPaymentsTabItem = new TabItem (tabFolder, SWT.NULL);
		archivedPaymentsTabItem.setText (Config.getInstance().getText("main.tab2"));
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				updateStatusBar();	
			}
		});
		
		PaymentsTableProvider pendingPaymentsProvider = new PaymentsTableProvider();
		pendingPaymentsTbv.setContentProvider(pendingPaymentsProvider);
		pendingPaymentsTbv.setLabelProvider(pendingPaymentsProvider);		
		pendingPaymentsTbv.setSorter(new PaymentSorter());
//		pendingPaymentsTbv.addSelectionChangedListener(new ISelectionChangedListener() {
//			public void selectionChanged(SelectionChangedEvent event) {
//				
//			}
//		});
		pendingPaymentsTable.setLinesVisible (true);
		pendingPaymentsTable.setHeaderVisible(true);
		pendingPaymentsTable.addMouseListener(new MouseAdapter(){
			public void mouseDoubleClick(MouseEvent e){
				showSelectedPaymentSlip();
			}
			public void mouseUp(MouseEvent e){
				updateStatusBar();
			}
		});

		PaymentsTableProvider archivedPaymentsProvider = new PaymentsTableProvider();
		archivedPaymentsTbv.setContentProvider(archivedPaymentsProvider);
		archivedPaymentsTbv.setLabelProvider(archivedPaymentsProvider);		
		archivedPaymentsTbv.setSorter(new PaymentSorter());
//		archivedPaymentsTbv.addSelectionChangedListener(new ISelectionChangedListener() {
//			public void selectionChanged(SelectionChangedEvent event) {
//			}
//		});
		archivedPaymentsTable.setLinesVisible (true);
		archivedPaymentsTable.setHeaderVisible(true);
		archivedPaymentsTable.addMouseListener(new MouseAdapter(){
			public void mouseDoubleClick(MouseEvent e){
				showSelectedPaymentSlip();
			}
			public void mouseUp(MouseEvent e){
				updateStatusBar();
			}
		});
		
		String[] titles = {"table.type", "table.execdate", "table.amount", "table.ktofrom", "table.ktoto", "table.to", "table.comment"};
		int[] styles = {SWT.NULL,SWT.NULL,SWT.RIGHT,SWT.NULL,SWT.NULL,SWT.NULL,SWT.NULL};
		for(int i=0; i<titles.length;i++){
			TableColumn pendingColumn = new TableColumn (pendingPaymentsTable, styles[i]);
			pendingColumn.setData(new Integer(i));
			pendingColumn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					TableColumn tc = (TableColumn)e.getSource();
					int selColumnIndex = ((Integer)tc.getData()).intValue();
					((PaymentSorter)pendingPaymentsTbv.getSorter()).setSortedColumnIndex(selColumnIndex);
					pendingPaymentsTbv.refresh();
				}
			});
			TableColumn archivedColumn = new TableColumn (archivedPaymentsTable, styles[i]);
			archivedColumn.setData(new Integer(i));
			archivedColumn.addSelectionListener(new SelectionAdapter(){
				public void widgetSelected(SelectionEvent e){
					TableColumn tc = (TableColumn)e.getSource();
					int selColumnIndex = ((Integer)tc.getData()).intValue();
					((PaymentSorter)archivedPaymentsTbv.getSorter()).setSortedColumnIndex(selColumnIndex);
					archivedPaymentsTbv.refresh();
				}
			});
			pendingColumn.setText(Config.getInstance().getText(titles[i]));
			archivedColumn.setText(Config.getInstance().getText(titles[i]));
		}
	
		// this has to be here!!
		pendingPaymentsTbv.setInput(pendingPaymentsModel);
		archivedPaymentsTbv.setInput(archivedPaymentsModel);

		// now the column are packed to the right size
		for(int i=0; i<titles.length;i++){
			pendingPaymentsTable.getColumn(i).pack();
			archivedPaymentsTable.getColumn(i).pack();
		}
		
		pendingPaymentsTabItem.setControl(pendingPaymentsTable);
		archivedPaymentsTabItem.setControl(archivedPaymentsTable);
				
		return tabFolder;
	}
	
	private void showSelectedPaymentSlip(){
		Table paymentsTable = null;
		if (tabFolder.getSelectionIndex() == 0) {
			paymentsTable = pendingPaymentsTable;
		} else if (tabFolder.getSelectionIndex() == 1) {
			paymentsTable = archivedPaymentsTable;
		}
		if (paymentsTable != null) {
			TableItem[] items = paymentsTable.getSelection();
			if (items.length == 1) {
				Payment p = (Payment) items[0].getData();
				showPaymentSlip(p);
			} else {
				throw new RuntimeException("only one item can be selected if double click on a payment item!");
			}
		} else {
			throw new RuntimeException("no table found !!");
		}
	}
	
	private void showPaymentSlip(Payment p){
		int type = p.getPaymentType().intValue();
		switch (type) {
			case Config.PAYMENT_TYPE_RED :
				paymentSlipRed.fill(p);
				break;
			case Config.PAYMENT_TYPE_BLUE :
				paymentSlipBlue.fill(p);
				break;
			case Config.PAYMENT_TYPE_BANKCH :
				paymentSlipBank.fill(p);
				break;
			case Config.PAYMENT_TYPE_ORANGE :
				paymentSlipOrange.fill(p);
				break;
			case Config.PAYMENT_TYPE_IPI :
				paymentSlipIpi.fill(p);
				break;
		}
	}
	
	private void updateStatusBar(){
		Table paymentsTable = null;
		double selPaymentsSum = 0.0;
		int selPaymentsCount = 0;
		double totalPaymentsSum = 0.0;
		int totalPaymentsCount = 0;

		// pending payments
		if (tabFolder.getSelectionIndex() == 0) {
			paymentsTable = pendingPaymentsTable;
		}
		// archived payments 
		else if (tabFolder.getSelectionIndex() == 1) {
			paymentsTable = archivedPaymentsTable;
		}
		if (paymentsTable == null) {
			throw new RuntimeException("no table found !!");
		}
		// calculate the selected payments
		selPaymentsCount = paymentsTable.getSelectionCount();
		selPaymentsSum = caculatePaymentsSum(paymentsTable.getSelection());		
		// calculate the total
		totalPaymentsCount = paymentsTable.getItemCount();
		totalPaymentsSum = caculatePaymentsSum(paymentsTable.getItems());
		
		BigDecimal roundedSelSum = new BigDecimal(selPaymentsSum);
		roundedSelSum = roundedSelSum.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal roundedTotalSum = new BigDecimal(totalPaymentsSum);
		roundedTotalSum = roundedTotalSum.setScale(2, BigDecimal.ROUND_HALF_UP);

		setStatus("Total Payments :"+totalPaymentsCount+", total sum :"+roundedTotalSum+", selected payments :"+selPaymentsCount+", selected sum :"+roundedSelSum);
	}
	
	private double caculatePaymentsSum(TableItem[] items){
		double sum = 0.0;
		for(int i=0;i<items.length;i++){
			sum+= ((Payment)items[i].getData()).getAmount().doubleValue();
		}
		return sum;
	}
		
	/**
	 * @see org.eclipse.jface.window.ApplicationWindow#createToolBarManager(int)
	 */
	protected ToolBarManager createToolBarManager(int i) {
		ToolBarManager toolBarManager = new ToolBarManager (SWT.FLAT | SWT.WRAP);
		toolBarManager.add(showRedSlipAction);
		toolBarManager.add(showBlueSlipAction);
		toolBarManager.add(showOrangeSlipAction);
		toolBarManager.add(showBankSlipAction);
		toolBarManager.add(showIpiSlipAction);
		return toolBarManager;
	}

	
	/**
	 * @see org.eclipse.jface.window.ApplicationWindow#createMenuManager()
	 */
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.add(createFileMenu());
		menuManager.add(createPaymentsMenu());
		menuManager.add(createDataMenu());
		menuManager.add(createLanguageMenu());
		menuManager.add(createHelpMenu());
		return menuManager;
	}
	
	private MenuManager createFileMenu() {
		MenuManager menu = new MenuManager(Config.getInstance().getText("menu.file"), "menu.file");
		menu.add(backupAction);
		menu.add(restoreAction);
		menu.add(new Separator());
		menu.add(exitAction);
		return menu;
	}
	
	private MenuManager createPaymentsMenu() {
		MenuManager menu = new MenuManager(Config.getInstance().getText("menu.payments"), "menu.payments");
		menu.add(dtaExportAction);
		menu.add(dtaImportAction);
		menu.add(dtaViewAction);
		menu.add(new Separator());
		menu.add(deleteAction);
		menu.add(archiveAction);
		menu.add(reactivateAction);
		menu.add(reuseAction);
		return menu;
	}

	private MenuManager createDataMenu() {
		MenuManager menu = new MenuManager(Config.getInstance().getText("menu.data"), "menu.data");
		menu.add(showAccountsAction);
		menu.add(new Separator());
		menu.add(showPostToAction);
		menu.add(showPostForAction);
		menu.add(showBankForAction);
		menu.add(showBankListAction);
		menu.add(showLocationListAction);
		return menu;
	}

	private MenuManager createLanguageMenu() {
		MenuManager menu = new MenuManager(Config.getInstance().getText("menu.language"), "menu.language");
		menu.add(selectGermanAction);
		menu.add(selectEnglishAction);
		return menu;
	}

	private MenuManager createHelpMenu() {
		MenuManager menu = new MenuManager(Config.getInstance().getText("menu.help"), "menu.help");
		menu.add(showAboutAction);
		return menu;
	}
	
	private Action createBackupAction() {
		 final ApplicationWindow appli = this;
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.file.backup");
			 }
			 public void run() {
				 appli.setStatus("Essai status line");
				 String[] tab = { "OK!" };
				 MessageDialog dialog =
					 new MessageDialog(
						 getShell(),
						 "Essai JFace",
						 null,
						 "Ceci est une fenètre d''information",
						 MessageDialog.INFORMATION,
						 tab,
						 0);
				 dialog.open();
			 }
			 public void runWithEvent(Event event) {
				 System.out.println(event.toString());
				 run();
			 }
		 };
		 action.setAccelerator('A');
		 return action;
	 }

	/**
	 * Action Definitiona
	 * @return
	 */
	private Action createRestoreAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.file.restore");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }

	private Action createExitAction() {
		final ApplicationWindow appli = this;
		Action action = new Action() {
			public String getToolTipText() {
				 return "ToolTipText";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.file.exit");
			 }
			 public void run() {
			 	appli.close();
			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
		 };
		 return action;
	 }

	private Action createDtaExportAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.payments.dta");
			 }
			 public void run() {
				if(tabFolder.getSelectionIndex() == 0){
					TableItem[] items = pendingPaymentsTable.getSelection();
					if(items.length > 0){
						String [] names = new String [] {"DTA Files", "All Files (*.*)"};
						String [] extensions = new String [] {"*.dta", "*.*"};
						File f = showFileDialog(true,extensions,names,"dta");
						if(f != null){
							
							java.util.List payments = new ArrayList();
							for(int i=0;i<items.length;i++){
								payments.add((Payment)items[i].getData());
							}
							// create the dta file and save it...
							DataConverter.exportDtaFile(f,payments);
						}
					} else {
						showWarningDialog("you didn't select any payments for export!","Warning");
					}
				} else {
					showWarningDialog("exporting of payments to a dta file not possible from the archive!","Warning");
				}			 
			 }
			 
			 public void runWithEvent(Event event) {
			 	run();
			 }
		 };
		 return action;
	 }

	private Action createDtaImportAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.payments.dtaimport");
			 }
			 public void run() {
				if(tabFolder.getSelectionIndex() == 0){
					String [] names = new String [] {"DTA Files", "All Files (*.*)"};
					String [] extensions = new String [] {"*.dta", "*.*"};
					File f = showFileDialog(false,extensions,names,null);
					if(f != null){
						DataConverter.importDtaFile(f);
					}
				} else {
					throw new RuntimeException("importing of payments from a dta file not possible from the archive!");
				}
			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
		 };
		 return action;
	 }

	private Action createDtaViewAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.payments.dtaview");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }

	private Action createDeleteAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.payments.delete");
			 }
			 public void run() {

				if(tabFolder.getSelectionIndex() == 0){
					if(showConfirmationDialog("question_msg.delete","delete_msg.title") == 0){
						TableItem[] items = pendingPaymentsTable.getSelection();
						//delete all the selected items
						java.util.List payments = new ArrayList();
						for(int i=0;i<items.length;i++){
							payments.add((Payment)items[i].getData());
						}
						mainModel.removePayments(payments);
					}
				} else {
					throw new RuntimeException("deleting of payments not possible from the archive!");
				}
			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
		 };
		 return action;
	 }

	private Action createArchiveAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.payments.archive");
			 }
			 public void run() {				
				if(tabFolder.getSelectionIndex() == 0){
					if(showConfirmationDialog("question_msg.archive","archive_msg.title") == 0){
						TableItem[] items = pendingPaymentsTable.getSelection();
						//archive all the selected items
						ArrayList payments = new ArrayList();
						for(int i=0;i<items.length;i++){
							payments.add((Payment)items[i].getData());
						}
						mainModel.archivePayments(payments);
					}
				} else {
					throw new RuntimeException("archiving of payments is only possible for pending payments!");
				}			 	

			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
		 };
		 return action;
	 }

	private Action createReactivateAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.payments.reactivate");
			 }
			 public void run() {
				if(tabFolder.getSelectionIndex() == 1){
					if(showConfirmationDialog("question_msg.reactivate","reactivate_msg.title") == 0){
						TableItem[] items = archivedPaymentsTable.getSelection();
						//delete all the selected items
						ArrayList payments = new ArrayList();
						for(int i=0;i<items.length;i++){
							payments.add((Payment)items[i].getData());
						}
						mainModel.reactivatePayments(payments);
					}
				} else {
					throw new RuntimeException("reactivating of payments is only possible from the archive!");
				}			 	
			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
		 };
		 return action;
	 }

	private Action createReuseAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.payments.reuse");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }

	private Action createShowAccountsAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.data.ktfrom");
			 }
			 public void run() {
			 	userAccountsTablePanel.open();
			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
		 };
		 return action;
	 }

	private Action createShowPostToAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.data.postdirect");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }
	 
	private Action createShowPostForAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.data.postindirect");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }

	private Action createShowBankForAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.data.bank");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }

	private Action createShowBankListAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.data.bkstam");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }

	private Action createShowLocationListAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.data.zipcity");
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }

	private Action createSelectGermanAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.language.german");
			 }
			 public void run() {
				Config.getInstance().setLanguage(Locale.GERMAN.getISO3Language());
				selectEnglishAction.setChecked(false);
			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
			 public int getStyle(){
			 	return AS_CHECK_BOX;
			 }
		 };
		 return action;
	 }

	private Action createSelectEnglishAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "";
			 }
			 public String getText() {
				 return Config.getInstance().getText("menu.language.english");
			 }
			 public void run() {
				Config.getInstance().setLanguage(Locale.ENGLISH.getISO3Language());
				selectGermanAction.setChecked(false);
			 }
			 public void runWithEvent(Event event) {
			 	run();
			 }
			 public int getStyle(){
			   	return AS_CHECK_BOX;
			 }
		 };
		 return action;
	 }

	private Action createShowAboutAction() {
		 Action action = new Action() {
			 public String getToolTipText() {
				 return "ToolTipText";
			 }
			 public String getText() {
				 return "&About...";
			 }
			 public void run() {}
			 public void runWithEvent(Event event) {}
		 };
		 return action;
	 }
	 
	private Action createShowRedSlipAction() {
		Action action = new Action() {
			
			public ImageDescriptor getImageDescriptor() {				
				return ImageRegistry.getInstance().getImageDescriptor("esred_icon");
			}
			public ImageDescriptor getHoverImageDescriptor() {
				return ImageRegistry.getInstance().getImageDescriptor("esred_icon");
			}
			public String getText() {
				return "Red Slip";
			}
			public String getToolTipText() {
				return "Show Red Slip";
			}
			public void run() {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.getInstance().PAYMENT_TYPE_RED));
				showPaymentSlip(p);
			}
			public void runWithEvent(Event event) {
				run();
			}
		};
		action.setAccelerator('Q');
		return action;
	}

	private Action createShowBlueSlipAction() {
		Action action = new Action() {
			
			public ImageDescriptor getImageDescriptor() {				
				return ImageRegistry.getInstance().getImageDescriptor("esblue_icon");
			}
			public ImageDescriptor getHoverImageDescriptor() {
				return ImageRegistry.getInstance().getImageDescriptor("esblue_icon");
			}
			public String getText() {
				return "Blue Slip";
			}
			public String getToolTipText() {
				return "Show Blue Slip";
			}
			public void run() {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.getInstance().PAYMENT_TYPE_BLUE));
				showPaymentSlip(p);
			}
			public void runWithEvent(Event event) {
				run();
			}
		};
		action.setAccelerator('Q');
		return action;
	}

	private Action createShowOrangeSlipAction() {
		Action action = new Action() {
			
			public ImageDescriptor getImageDescriptor() {				
				return ImageRegistry.getInstance().getImageDescriptor("esorange_icon");
			}
			public ImageDescriptor getHoverImageDescriptor() {
				return ImageRegistry.getInstance().getImageDescriptor("esorange_icon");
			}
			public String getText() {
				return "Red Slip";
			}
			public String getToolTipText() {
				return "Show Red Slip";
			}
			public void run() {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.getInstance().PAYMENT_TYPE_ORANGE));
				showPaymentSlip(p);
			}
			public void runWithEvent(Event event) {
				run();
			}
		};
		action.setAccelerator('Q');
		return action;
	}

	private Action createShowBankSlipAction() {
		Action action = new Action() {
			
			public ImageDescriptor getImageDescriptor() {				
				return ImageRegistry.getInstance().getImageDescriptor("esbank_icon");
			}
			public ImageDescriptor getHoverImageDescriptor() {
				return ImageRegistry.getInstance().getImageDescriptor("esbank_icon");
			}
			public String getText() {
				return "Red Slip";
			}
			public String getToolTipText() {
				return "Show Red Slip";
			}
			public void run() {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.getInstance().PAYMENT_TYPE_BANKCH));
				showPaymentSlip(p);
			}
			public void runWithEvent(Event event) {
				run();
			}
		};
		action.setAccelerator('Q');
		return action;
	}

	private Action createShowIpiSlipAction() {
		Action action = new Action() {
			
			public ImageDescriptor getImageDescriptor() {				
				return ImageRegistry.getInstance().getImageDescriptor("ipi_icon");
			}
			public ImageDescriptor getHoverImageDescriptor() {
				return ImageRegistry.getInstance().getImageDescriptor("ipi_icon");
			}
			public String getText() {
				return "Red Slip";
			}
			public String getToolTipText() {
				return "Show Red Slip";
			}
			public void run() {
				Payment p = new Payment();
				p.setPaymentType(new Integer(Config.getInstance().PAYMENT_TYPE_IPI));
				showPaymentSlip(p);
			}
			public void runWithEvent(Event event) {
				run();
			}
		};
		action.setAccelerator('Q');
		return action;
	}
	
	/**
	 * helper methods for displaying different dialog boxes
	 */
	
	/**
	 * Dialog Box for requesting a confirmation from the user
	 * @param msg the message this dialog should display
	 * @return an integer indicating the choice of the user 0: ok, 1: cancel
	 */
	private int showConfirmationDialog(String msg, String title){
		String[] tab = { Config.getInstance().getText("button.ok"), Config.getInstance().getText("button.cancel") };
		MessageDialog dialog =
			new MessageDialog(
				getShell(),
				Config.getInstance().getText(title),
				null,
				Config.getInstance().getText(msg),
				MessageDialog.QUESTION,
				tab,
				0);
		return dialog.open();

	}
	
	/**
	 * Dialog Box for displaying a warning message to the user
	 * @param msg the message this dialog should display
	 */
	private void showWarningDialog(String msg, String title){
		String[] tab = { Config.getInstance().getText("button.ok")};
		MessageDialog dialog =
			new MessageDialog(
				getShell(),
				Config.getInstance().getText(title),
				null,
				Config.getInstance().getText(msg),
				MessageDialog.WARNING,
				tab,
				0);
		dialog.open();
	}

	
	private File showFileDialog(boolean save, String[] filterExtensions, String[] filterNames, String defaultExtension){
		int style = save ? SWT.SAVE : SWT.OPEN;
		File f = null;
		FileDialog dialog = new FileDialog(this.getShell(),style);
		dialog.setFilterExtensions(filterExtensions);
		dialog.setFilterNames(filterNames);
		String fileName = dialog.open();
		if(fileName != null){
			if(defaultExtension != null){
				// TODO add .defaultExtension to the filename if needed
			}
			f = new File(fileName);
		}
		return f;
	}
		
	private File showFileDialog(boolean save){
		return showFileDialog(save,null,null,null);
	}
	
	public static void main(String[] args) {
		MainFrame w = new MainFrame();
		w.open();
		Display.getCurrent().dispose();
	}
}
