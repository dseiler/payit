/*
 * Created on 19.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

import java.util.Iterator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import ch.truesolutions.payit.exceptions.InvalidDataException;
import ch.truesolutions.payit.model.*;

/**
 * @author Daniel
 */
public abstract class PaymentSlip extends Dialog {

	static final int TEXT_FIELD_STYLE = SWT.SINGLE | SWT.BORDER;
	static final int TEXT_AREA_STYLE =SWT.MULTI | SWT.WRAP | SWT.BORDER;
	
	Canvas northCanvas;
	Canvas mainCanvas;
	Canvas eastCanvas;
	Canvas southCanvas;

	java.util.List userAccounts;
	UserAccount selectedUserAccount;
	Payment payment;
	
	Button salaryBtn;
	Combo userAccountsCombo;
	Text execDateText;
	Text bookingText;
	
	Shell shell;
	/**
	 * @param arg0
	 */
	
	
	PaymentSlip(Shell parent) {
		super(parent);
	}

	protected Control createContents(Composite parent) {		
		shell = getShell();
		shell.setSize(628,440);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = true;
		rowLayout.pack = true;
		rowLayout.marginLeft = 0;
		rowLayout.marginTop = 0;
		rowLayout.marginRight = 0;
		rowLayout.marginBottom = 0;
		rowLayout.spacing = 0;
		shell.setLayout(rowLayout);
				
		// canvs in the north
		RowLayout northRowLayout = new RowLayout();
		northRowLayout.wrap = true;
		northRowLayout.marginLeft = 10;
		northRowLayout.marginTop = 5;
		northRowLayout.marginRight = 0;
		northRowLayout.marginBottom = 0;
		northRowLayout.spacing = 5;
		northCanvas = new Canvas (parent, SWT.NONE);
		northCanvas.setLayout(northRowLayout);
		northCanvas.setLayoutData(new RowData(620,30));
		Label execDateLabel = new Label(northCanvas,SWT.NONE);
		execDateLabel.setText(Config.getInstance().getText("label.execdate"));
		execDateText = new Text(northCanvas,TEXT_FIELD_STYLE);
		Button calendarBtn = new Button(northCanvas, SWT.PUSH);//12x14
		calendarBtn.setLayoutData(new RowData(12,14));
		Label separator1 = new Label(northCanvas,SWT.NONE);
		separator1.setText("  ");
		Label userAccountLabel = new Label(northCanvas,SWT.NONE);
		userAccountLabel.setText(Config.getInstance().getText("label.ktofrom"));
		userAccountsCombo = new Combo(northCanvas,SWT.DROP_DOWN & SWT.READ_ONLY);
		userAccountsCombo.setLayoutData(new RowData(120,14));
		userAccounts = MainModel.getInstance().getUserAccountsModel().getUserAccounts();
		for(Iterator i=userAccounts.iterator();i.hasNext();){
			UserAccount account = (UserAccount)i.next();
			userAccountsCombo.add(account.getAccountNr());
		}
		userAccountsCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				int index = userAccountsCombo.getSelectionIndex();
				UserAccount ac = (UserAccount)userAccounts.get(index);
				userAccountsCombo.setToolTipText(ac.getBankName()+","+ac.getClearing());
				selectedUserAccountChanged(ac);
			}
		});
		//northCanvas.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_RED));
		
		// main Canvas
		mainCanvas = new Canvas (parent, SWT.NONE);
		mainCanvas.setLayout(new FormLayout());
		mainCanvas.setLayoutData(new RowData(510,350));		
		Font mainCanvasFont = mainCanvas.getFont();
		FontData[] fontData = mainCanvasFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(12);
		}
		Font newMainCanvasFont = new Font(shell.getDisplay(), fontData);
		mainCanvas.setFont(newMainCanvasFont);

		//mainCanvas.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_YELLOW));
		mainCanvas.addPaintListener (new PaintListener () {
			public void paintControl (PaintEvent e) {
				e.gc.drawImage (getSlipImage(), 0, 0);
				drawUserAccountData(e.gc);
			}
		});

		// Canvas in the east
		FormLayout eastCanvasLayout = new FormLayout();
		eastCanvas = new Canvas (parent, SWT.NONE);
		eastCanvas.setLayout(eastCanvasLayout);
		eastCanvas.setLayoutData(new RowData(110,350));
		//eastCanvas.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_BLUE));

		FormAttachment leftBorder = new FormAttachment(0,10);
		FormAttachment rightBorder = new FormAttachment(0,100);
		salaryBtn = new Button (eastCanvas, SWT.CHECK);
		FormData eastCanvasSalaryBtnFormData = new FormData();
		eastCanvasSalaryBtnFormData.bottom = new FormAttachment(100,-100);
		eastCanvasSalaryBtnFormData.left = leftBorder;
		eastCanvasSalaryBtnFormData.right = rightBorder;
		salaryBtn.setLayoutData(eastCanvasSalaryBtnFormData);
		salaryBtn.setText (Config.getInstance().getText("checkbox.salary"));
		salaryBtn.setVisible(false);
		
		Button saveBtn = new Button (eastCanvas, SWT.PUSH);
		FormData eastCanvasSaveBtnFormData = new FormData();
		eastCanvasSaveBtnFormData.bottom = new FormAttachment(100,-30);
		eastCanvasSaveBtnFormData.left = leftBorder;
		eastCanvasSaveBtnFormData.right = rightBorder;
		saveBtn.setLayoutData(eastCanvasSaveBtnFormData);
		saveBtn.setText (Config.getInstance().getText("button.save"));
		saveBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				handleSaveBtn();
			}
		});
		
		Button cancelBtn = new Button (eastCanvas, SWT.PUSH);
		FormData eastCanvasCancelBtnFormData = new FormData();
		eastCanvasCancelBtnFormData.bottom = new FormAttachment(100,0);
		eastCanvasCancelBtnFormData.left = leftBorder;
		eastCanvasCancelBtnFormData.right = rightBorder;
		cancelBtn.setLayoutData(eastCanvasCancelBtnFormData);
		cancelBtn.setText (Config.getInstance().getText("button.cancel"));
		cancelBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				handleCancelBtn();
			}
		});

		// Canvas in the south
		FormLayout southCanvasLayout = new FormLayout();
		southCanvas = new Canvas (parent, SWT.NONE);
		southCanvas.setLayout(southCanvasLayout);
		southCanvas.setLayoutData(new RowData(624,30));
		//southCanvas.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_GREEN));

		Label bookingTextLabel = new Label(southCanvas,SWT.NONE);
		FormData bookingTextLabelFormData = new FormData();
		bookingTextLabelFormData.top = new FormAttachment(0,10);
		bookingTextLabelFormData.left = leftBorder;
		bookingTextLabel.setLayoutData(bookingTextLabelFormData);
		bookingTextLabel.setText(Config.getInstance().getText("label.comment"));

		bookingText = new Text(southCanvas,TEXT_FIELD_STYLE);
		FormData bookingTextFormData = new FormData();
		bookingTextFormData.top = new FormAttachment(0,10);
		bookingTextFormData.left = new FormAttachment(0,100);
		bookingTextFormData.right = new FormAttachment(0,510);
		bookingText.setLayoutData(bookingTextFormData);
		bookingText.setTextLimit(50);

		return shell;
	}
			
	// some methods that should be overriden by the subclass
	abstract Image getSlipImage();
	
	void handleSaveBtn(){}
	
	void handleSaveBtn(Payment tmpPayment){
		// fill the general payments fields
		tmpPayment.setExecDate(execDateText.getText());
		tmpPayment.setFromKto(
			selectedUserAccount != null ? selectedUserAccount.getAccountNr() : "");
		tmpPayment.setComment(bookingText.getText().trim());
		tmpPayment.setIsSalary(new Boolean(salaryBtn.getSelection()));
		try {
			tmpPayment.validate();
			// if the tmpPayment is valid we copy the data into the
			// original payment
			payment.fill(tmpPayment);
			// save or update the payment
			if(payment.getId() == null){
				MainModel.getInstance().addPayment(payment);
			} else {
				MainModel.getInstance().updatePayment(payment);
			}
			shell.setVisible(false);
		} catch (InvalidDataException e) {
			String[] tab = { "OK" };
			MessageDialog dialog =
				new MessageDialog(
					getShell(),
					"...",
					null,
					Config.getInstance().getText(e.getMessage()),
					MessageDialog.ERROR,
					tab,
					0);
			dialog.open();
		}
	}

	void handleCancelBtn(){
		shell.setVisible(false);
	}
	
	void drawUserAccountData(GC gc){
	}
	
	void selectedUserAccountChanged(UserAccount userAccount){
		selectedUserAccount = userAccount;
		mainCanvas.redraw();
	}
	
	void fill() {
		salaryBtn.setSelection(payment.getIsSalary().booleanValue());
		execDateText.setText(payment.getExecDate());
		bookingText.setText(payment.getComment());
		for(int i=0;i<userAccounts.size();i++){
			UserAccount ua = (UserAccount)userAccounts.get(i);
			if(payment.getFromKto().equals(ua.getAccountNr())){
				userAccountsCombo.select(i);
				selectedUserAccountChanged(ua);
				break;
			}
		}
	}
	
	/**
	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 */
	protected void setShellStyle(int arg0) {
		super.setShellStyle(SWT.TITLE /*| SWT.BORDER | SWT.PRIMARY_MODAL*/);
	}

}
