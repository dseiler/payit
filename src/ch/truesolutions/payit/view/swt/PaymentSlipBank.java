/*
 * Created on 19.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.*;

import ch.truesolutions.payit.model.*;

/**
 * @author Daniel
 */
public class PaymentSlipBank extends PaymentSlip {

	private Text ktoBankText;
	private Text clearingText;
	private Text forLine1Text;
	private Text forLine2Text;
	private Text forLine3Text;
	private Text zipForText;
	private Text cityForText;
	private Text amount1Text;
	private Text amount2Text;
	private Text reasonText;

	private Button zipCityForBtn;
	private Button bankAccount1Btn;
	private Button bankAccount2Btn;
	private Button clearingBtn;

	// code table panels
	private CodeTablePanel zipCityForCodeTablePanel;
	private CodeTablePanel bankHistoryCodeTablePanel;
	private CodeTablePanel bkstamCodeTablePanel;

	/**
	 * @param parent
	 */
	public PaymentSlipBank(Shell parent) {
		super(parent);
		this.create();
	}
	
	protected Control createContents(Composite parent) {
		Control ctrl = super.createContents(parent);
		salaryBtn.setVisible(true);
		Canvas c = mainCanvas;
		FormAttachment left1 = new FormAttachment(0,5);
		FormAttachment right1 = new FormAttachment(0,178);
				
		// ktoBank
		ktoBankText = new Text(c,TEXT_FIELD_STYLE);
		FormData ktoBankTextFormData = new FormData();
		ktoBankTextFormData.top = new FormAttachment(0,50);
		ktoBankTextFormData.left = left1;
		ktoBankTextFormData.right = new FormAttachment(0,115);
		ktoBankText.setLayoutData(ktoBankTextFormData);
		ktoBankText.setTextLimit(27);
		ktoBankText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getBankHistoryCodeTableModel().setCondition(
						"bankhistory.ktoBank",
						ktoBankText.getText());
					bankHistoryCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// clearing
		clearingText = new Text(c,TEXT_FIELD_STYLE);
		FormData clearingTextFormData = new FormData();
		clearingTextFormData.top = new FormAttachment(0,50);
		clearingTextFormData.left = new FormAttachment(0,140);
		clearingTextFormData.right = right1;
		clearingText.setLayoutData(clearingTextFormData);
		clearingText.setTextLimit(5);
		clearingText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getBkstamCodeTableModel().setCondition(
						"clearing",
						clearingText.getText());
					bkstamCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// forLine1
		forLine1Text = new Text(c,TEXT_FIELD_STYLE);
		FormData forLine1TextFormData = new FormData();
		forLine1TextFormData.top = new FormAttachment(0,165);
		forLine1TextFormData.left = left1;
		forLine1TextFormData.right = right1;
		forLine1Text.setLayoutData(forLine1TextFormData);
		forLine1Text.setTextLimit(24);
		forLine1Text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getBankHistoryCodeTableModel().setCondition(
						"bankhistory.line1",
						forLine1Text.getText());
					bankHistoryCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// forLine2	
		forLine2Text = new Text(c,TEXT_FIELD_STYLE);
		FormData forLine2TextFormData = new FormData();
		forLine2TextFormData.top = new FormAttachment(0,185);
		forLine2TextFormData.left = left1;
		forLine2TextFormData.right = right1;
		forLine2Text.setLayoutData(forLine2TextFormData);
		forLine2Text.setTextLimit(24);
		// forLine3
		forLine3Text = new Text(c,TEXT_FIELD_STYLE);
		FormData forLine3TextFormData = new FormData();
		forLine3TextFormData.top = new FormAttachment(0,205);
		forLine3TextFormData.left = left1;
		forLine3TextFormData.right = right1;
		forLine3Text.setLayoutData(forLine3TextFormData);
		forLine3Text.setTextLimit(24);
		// zipFor
		zipForText = new Text(c,TEXT_FIELD_STYLE);
		FormData zipForTextFormData = new FormData();
		zipForTextFormData.top = new FormAttachment(0,225);
		zipForTextFormData.left = left1;
		zipForTextFormData.right = new FormAttachment(0,45);
		zipForText.setLayoutData(zipForTextFormData);
		zipForText.setTextLimit(5);
		zipForText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getZipCityCodeTableModel().setCondition(
						"zip",
						zipForText.getText());
					zipCityForCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// cityFor
		cityForText = new Text(c,TEXT_FIELD_STYLE);
		FormData cityForTextFormData = new FormData();
		cityForTextFormData.top = new FormAttachment(0,225);
		cityForTextFormData.left = new FormAttachment(0,48);
		cityForTextFormData.right = right1;
		cityForText.setLayoutData(cityForTextFormData);
		cityForText.setTextLimit(18);
		cityForText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getZipCityCodeTableModel().setCondition(
						"city",
						cityForText.getText());
					zipCityForCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// amount1
		amount1Text = new Text(c,TEXT_FIELD_STYLE | SWT.RIGHT);
		FormData amount1TextFormData = new FormData();
		amount1TextFormData.top = new FormAttachment(0,297);
		amount1TextFormData.left = new FormAttachment(0,17);
		amount1TextFormData.right = new FormAttachment(0,127);
		amount1TextFormData.bottom = new FormAttachment(0,322);
		amount1Text.setLayoutData(amount1TextFormData);
		amount1Text.setTextLimit(8);
		Font amount1Font = amount1Text.getFont();
		FontData[] fontData = amount1Font.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(12);
		}
		Font newAmountFont = new Font(shell.getDisplay(), fontData);
		amount1Text.setFont(newAmountFont);
		// amount2
		amount2Text = new Text(c,TEXT_FIELD_STYLE | SWT.RIGHT);
		FormData amount2TextFormData = new FormData();
		amount2TextFormData.top = new FormAttachment(0,297);
		amount2TextFormData.left = new FormAttachment(0,146);
		amount2TextFormData.right = right1;
		amount2TextFormData.bottom = new FormAttachment(0,322);
		amount2Text.setLayoutData(amount2TextFormData);
		amount2Text.setTextLimit(2);
		amount2Text.setFont(newAmountFont);
		// reason
		reasonText = new Text(c,TEXT_AREA_STYLE);
		FormData reasonTextFormData = new FormData();
		reasonTextFormData.top = new FormAttachment(0,50);
		reasonTextFormData.left = new FormAttachment(0,215);
		reasonTextFormData.right = new FormAttachment(0,385);
		reasonTextFormData.bottom = new FormAttachment(0,100);
		reasonText.setLayoutData(reasonTextFormData);
		reasonText.setTextLimit(96); // 4 x 24
		// zipCityForBtn
		zipCityForBtn = new Button(c,SWT.PUSH);
		FormData zipCityForBtnData = new FormData();
		zipCityForBtnData.top = new FormAttachment(0,225);
		zipCityForBtnData.left = new FormAttachment(0,182);
		zipCityForBtnData.right = new FormAttachment(0,194);
		zipCityForBtnData.bottom = new FormAttachment(0,239);
		zipCityForBtn.setLayoutData(zipCityForBtnData);
		zipCityForBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getZipCityCodeTableModel().noCondition();
				zipCityForCodeTablePanel.tryToShowCodeTable();
			}		
		});
		// bankAccountBtn1
		bankAccount1Btn = new Button(c,SWT.PUSH);
		FormData bankAccount1BtnData = new FormData();
		bankAccount1BtnData.top = new FormAttachment(0,50);
		bankAccount1BtnData.left = new FormAttachment(0,119);
		bankAccount1BtnData.right = new FormAttachment(0,131);
		bankAccount1BtnData.bottom = new FormAttachment(0,64);
		bankAccount1Btn.setLayoutData(bankAccount1BtnData);
		bankAccount1Btn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getBankHistoryCodeTableModel().noCondition();
				bankHistoryCodeTablePanel.tryToShowCodeTable();
			}		
		});
		// bankAccountBtn2
		bankAccount2Btn = new Button(c,SWT.PUSH);
		FormData bankAccount2BtnData = new FormData();
		bankAccount2BtnData.top = new FormAttachment(0,165);
		bankAccount2BtnData.left = new FormAttachment(0,182);
		bankAccount2BtnData.right = new FormAttachment(0,194);
		bankAccount2BtnData.bottom = new FormAttachment(0,179);
		bankAccount2Btn.setLayoutData(bankAccount2BtnData);
		bankAccount2Btn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getBankHistoryCodeTableModel().noCondition();
				bankHistoryCodeTablePanel.tryToShowCodeTable();
			}		
		});
		// clearing btn
		clearingBtn = new Button(c,SWT.PUSH);
		FormData clearingBtnData = new FormData();
		clearingBtnData.top = new FormAttachment(0,50);
		clearingBtnData.left = new FormAttachment(0,182);
		clearingBtnData.right = new FormAttachment(0,194);
		clearingBtnData.bottom = new FormAttachment(0,64);
		clearingBtn.setLayoutData(clearingBtnData);
		clearingBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getBkstamCodeTableModel().noCondition();
				bkstamCodeTablePanel.tryToShowCodeTable();
			}		
		});
		
		zipCityForCodeTablePanel =
			new CodeTablePanel(this.getShell(), new CodeTableListener() {
			public void handleDoubleClick(List record) {
				setZipCityFor(record);
			}
		},
			MainModel.getInstance().getZipCityCodeTableModel(),
			new int[] { SWT.NULL, SWT.NULL, SWT.RIGHT });

		bankHistoryCodeTablePanel =
			new CodeTablePanel(this.getShell(), new CodeTableListener() {
			public void handleDoubleClick(List record) {
				setBankHistoryFields(record);
			}
		},
			MainModel.getInstance().getBankHistoryCodeTableModel(),
			new int[] {
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL });

		bkstamCodeTablePanel =
			new CodeTablePanel(this.getShell(), new CodeTableListener() {
			public void handleDoubleClick(List record) {
				setBkstamFields(record);
			}
		},
			MainModel.getInstance().getBkstamCodeTableModel(),
			new int[] {
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL });

		
		
		return ctrl;

	}

	public void fill(Payment payment){
		this.payment = payment;
		super.fill();
		// fill PaymentSlipBank
		ktoBankText.setText(payment.getForKtoBank());
		clearingText.setText(payment.getForClearing());

		// TODO query the bkstam for this bank
//		MainModel.getInstance().getBkstamCodeTableModel().setCondition(
//			"clearing",
//		clearingText.getText());
//		bkstamCodeTablePanel.tryToShowCodeTable(CodeTablePanel.SHOW_NEVER);

		forLine1Text.setText(payment.getForLine1());
		forLine2Text.setText(payment.getForLine2());
		forLine3Text.setText(payment.getForLine3());
		zipForText.setText(payment.getForZip());
		cityForText.setText(payment.getForCity());
		reasonText.setText(payment.getReason());
		if (payment.getAmount() != null) {
			double amount = payment.getAmount().doubleValue();
			String amountStr = Double.toString(amount);
			int i = amountStr.indexOf('.');
			String beforeComma;
			String afterComma;
			if (i != -1) {
				beforeComma = amountStr.substring(0, i);
				afterComma =
					(Utils.fillStringBuffer(amountStr.substring(i + 1),2,'0',true)).toString();
			} else {
				beforeComma = amountStr;
				afterComma = "00";
			}
			amount1Text.setText(beforeComma);
			amount2Text.setText(afterComma);
		} else {
			amount1Text.setText("");
			amount2Text.setText("");			
		}

		shell.setVisible(true);
	}
	
	void handleSaveBtn(){
		Payment tmpPayment = new Payment();
		tmpPayment.setPaymentType(payment.getPaymentType());
		tmpPayment.setId(payment.getId());
		tmpPayment.setIsPending(payment.getIsPending());
		try{
			Double amount = Double.valueOf(amount1Text.getText()+"."+amount2Text.getText());
			tmpPayment.setAmount(amount);
		} catch (Exception e){}		
		//fill the payment
		tmpPayment.setForKtoBank(ktoBankText.getText().trim());
		tmpPayment.setForClearing(clearingText.getText().trim());
		tmpPayment.setForLine1(forLine1Text.getText().trim());
		tmpPayment.setForLine2(forLine2Text.getText().trim());
		tmpPayment.setForLine3(forLine3Text.getText().trim());
		tmpPayment.setForZip(zipForText.getText().trim());
		tmpPayment.setForCity(cityForText.getText().trim());
		tmpPayment.setReason(reasonText.getText().trim());
		// this call to the super class fills in the general fields
		// validates and saves the payment
		super.handleSaveBtn(tmpPayment);
	}

		
	Image getSlipImage(){
		Image image = ImageRegistry.getInstance().getImage("esbank_image");
		GC gc = new GC (image);
		gc.dispose ();
		return image;
	}
	
	/**
	 * All the methods for setting the data from the codetables
	 * @param l List containing all the record data
	 */
	private void setZipCityFor(List l) {
		String zip = l.get(0) != null ? (String)l.get(0) : "";
		String city = l.get(1) != null ? (String)l.get(1) : "";
		zipForText.setText(zip);
		cityForText.setText(city);
	}

	private void setBkstamFields(List l) {
		String clearing = l.get(0) != null ? (String)l.get(0) : "";
		String bkname = l.get(1) != null ? (String)l.get(1) : "";
		String adress = l.get(2) != null ? (String)l.get(2) : "";
		String zip = l.get(3) != null ? (String)l.get(3) : "";
		String city = l.get(4) != null ? (String)l.get(4) : "";

		clearingText.setText(clearing);
		//adrBankLine1.setText(bkname);
		//adrBankLine2.setText(adress);
		//adrBankLine3.setText(zip + " " + city);
	}

	private void setBankHistoryFields(List l) {
		String ktobank = l.get(0) != null ? (String)l.get(0) : "";
		String clearing = l.get(1) != null ? (String)l.get(1) : "";
		String line1 = l.get(2) != null ? (String)l.get(2) : "";
		String line2 = l.get(3) != null ? (String)l.get(3) : "";
		String line3 = l.get(4) != null ? (String)l.get(4) : "";
		String zip = l.get(5) != null ? (String)l.get(5) : "";
		String city = l.get(6) != null ? (String)l.get(6) : "";

		ktoBankText.setText(ktobank);
		clearingText.setText(clearing);
		forLine1Text.setText(line1);
		forLine2Text.setText(line2);
		forLine3Text.setText(line3);
		zipForText.setText(zip);
		cityForText.setText(city);
	}

	/**
	 *  (non-Javadoc)
	 * @see ch.truesolutions.payit.view.swt.PaymentSlip#drawUserAccountData(org.eclipse.swt.graphics.GC)
	 */
	void drawUserAccountData(GC gc) {
		if(selectedUserAccount != null){
			gc.drawText(selectedUserAccount.getLine1(),260,203,true);
			gc.drawText(selectedUserAccount.getLine2(),260,223,true);
			gc.drawText(selectedUserAccount.getLine3(),260,243,true);
			gc.drawText(selectedUserAccount.getZip(),260,263,true);
			gc.drawText(selectedUserAccount.getCity(),300,263,true);
		}
	}

}
