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
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.*;

import ch.truesolutions.payit.model.*;

/**
 * @author Daniel
 */
public class PaymentSlipBlueOrange extends PaymentSlip {

	private Text toLine1Text;
	private Text toLine2Text;
	private Text toLine3Text;
	private Text zipToText;
	private Text cityToText;
	private Text forLine1Text;
	private Text forLine2Text;
	private Text forLine3Text;
	private Text zipForText;
	private Text cityForText;
	private Text ktoPostText;
	private Text amount1Text;
	private Text amount2Text;
	private Text refNrText;

	private Button zipCityToBtn;
	private Button zipCityForBtn;
	private Button postForBtn;
	private Button postTo1Btn;
	private Button postTo2Btn;

	// code table panels
	private CodeTablePanel zipCityToCodeTablePanel;
	private CodeTablePanel zipCityForCodeTablePanel;
	private CodeTablePanel postToHistoryCodeTablePanel;
	private CodeTablePanel postForHistoryCodeTablePanel;

	private boolean isOrange = false;

	/**
	 * @param parent
	 */
	public PaymentSlipBlueOrange(Shell parent,boolean isOrange) {
		super(parent);
		this.isOrange = isOrange;
		this.create();
	}
	
	protected Control createContents(Composite parent) {
		Control ctrl = super.createContents(parent);
		Canvas c = mainCanvas;
		FormAttachment left1 = new FormAttachment(0,5);
		FormAttachment right1 = new FormAttachment(0,178);
				
		// toLine1
		toLine1Text = new Text(c,TEXT_FIELD_STYLE);
		FormData toLine1TextFormData = new FormData();
		toLine1TextFormData.top = new FormAttachment(0,48);
		toLine1TextFormData.left = left1;
		toLine1TextFormData.right = right1;
		toLine1Text.setLayoutData(toLine1TextFormData);
		toLine1Text.setTextLimit(24);
		toLine1Text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getPostToHistoryCodeTableModel().setCondition(
						"line1",
						toLine1Text.getText());
					postToHistoryCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// toLine2
		toLine2Text = new Text(c,TEXT_FIELD_STYLE);
		FormData toLine2TextFormData = new FormData();
		toLine2TextFormData.top = new FormAttachment(0,68);
		toLine2TextFormData.left = left1;
		toLine2TextFormData.right = right1;
		toLine2Text.setLayoutData(toLine2TextFormData);
		toLine2Text.setTextLimit(24);
		// toLine3
		toLine3Text = new Text(c,TEXT_FIELD_STYLE);
		FormData toLine3TextFormData = new FormData();
		toLine3TextFormData.top = new FormAttachment(0,88);
		toLine3TextFormData.left = left1;
		toLine3TextFormData.right = right1;
		toLine3Text.setLayoutData(toLine3TextFormData);
		toLine3Text.setTextLimit(24);
		// zipTo
		zipToText = new Text(c,TEXT_FIELD_STYLE);
		FormData zipToTextFormData = new FormData();
		zipToTextFormData.top = new FormAttachment(0,108);
		zipToTextFormData.left = left1;
		zipToTextFormData.right = new FormAttachment(0,45);
		zipToText.setLayoutData(zipToTextFormData);
		zipToText.setTextLimit(5);
		zipToText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getZipCityCodeTableModel().setCondition(
						"zip",
						zipToText.getText());
					zipCityToCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// cityTo
		cityToText = new Text(c,TEXT_FIELD_STYLE);
		FormData cityToTextFormData = new FormData();
		cityToTextFormData.top = new FormAttachment(0,108);
		cityToTextFormData.left = new FormAttachment(0,48);
		cityToTextFormData.right = right1;
		cityToText.setLayoutData(cityToTextFormData);
		cityToText.setTextLimit(18);
		cityToText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getZipCityCodeTableModel().setCondition(
						"city",
						cityToText.getText());
					zipCityToCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// forLine1
		forLine1Text = new Text(c,TEXT_FIELD_STYLE);
		FormData forLine1TextFormData = new FormData();
		forLine1TextFormData.top = new FormAttachment(0,151);
		forLine1TextFormData.left = left1;
		forLine1TextFormData.right = right1;
		forLine1Text.setLayoutData(forLine1TextFormData);
		forLine1Text.setTextLimit(24);
		forLine1Text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getPostForHistoryCodeTableModel().setCondition(
						"postforhistory.line1",
						forLine1Text.getText());
					postForHistoryCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// forLine2	
		forLine2Text = new Text(c,TEXT_FIELD_STYLE);
		FormData forLine2TextFormData = new FormData();
		forLine2TextFormData.top = new FormAttachment(0,171);
		forLine2TextFormData.left = left1;
		forLine2TextFormData.right = right1;
		forLine2Text.setLayoutData(forLine2TextFormData);
		forLine2Text.setTextLimit(24);
		// forLine3
		forLine3Text = new Text(c,TEXT_FIELD_STYLE);
		FormData forLine3TextFormData = new FormData();
		forLine3TextFormData.top = new FormAttachment(0,191);
		forLine3TextFormData.left = left1;
		forLine3TextFormData.right = right1;
		forLine3Text.setLayoutData(forLine3TextFormData);
		forLine3Text.setTextLimit(24);
		// zipFor
		zipForText = new Text(c,TEXT_FIELD_STYLE);
		FormData zipForTextFormData = new FormData();
		zipForTextFormData.top = new FormAttachment(0,211);
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
		cityForTextFormData.top = new FormAttachment(0,211);
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

		// ktoPost
		ktoPostText = new Text(c,TEXT_FIELD_STYLE | SWT.RIGHT);
		FormData ktoPostTextFormData = new FormData();
		ktoPostTextFormData.top = new FormAttachment(0,235);
		ktoPostTextFormData.left = new FormAttachment(0,100);
		ktoPostTextFormData.right = right1;
		ktoPostText.setLayoutData(ktoPostTextFormData);
		ktoPostText.setTextLimit(11);
		ktoPostText.addFocusListener(new FocusAdapter(){
			public void focusLost(FocusEvent e){
				String postKto = ktoPostText.getText();
				ktoPostText.setText(Utils.formatPostKtoNr(postKto,true));
			}
		});
		ktoPostText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// only if the enter key was pressed
				if(e.keyCode == 13){
					MainModel.getInstance().getPostToHistoryCodeTableModel().setCondition(
						"ktopost",
						ktoPostText.getText());
					postToHistoryCodeTablePanel.tryToShowCodeTable();
				}
			}
		});

		// amount1
		amount1Text = new Text(c,TEXT_FIELD_STYLE | SWT.RIGHT);
		FormData amount1TextFormData = new FormData();
		amount1TextFormData.top = new FormAttachment(0,277);
		amount1TextFormData.left = new FormAttachment(0,17);
		amount1TextFormData.right = new FormAttachment(0,127);
		amount1TextFormData.bottom = new FormAttachment(0,302);
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
		amount2TextFormData.top = new FormAttachment(0,277);
		amount2TextFormData.left = new FormAttachment(0,137);
		amount2TextFormData.right = new FormAttachment(0,169);
		amount2TextFormData.bottom = new FormAttachment(0,302);
		amount2Text.setLayoutData(amount2TextFormData);
		amount2Text.setTextLimit(2);
		amount2Text.setFont(newAmountFont);
		// refNr
		refNrText = new Text(c,TEXT_FIELD_STYLE);
		FormData refNrTextFormData = new FormData();
		refNrTextFormData.top = new FormAttachment(0,176);
		refNrTextFormData.left = new FormAttachment(0,248);
		refNrTextFormData.right = new FormAttachment(0,458);
		refNrText.setLayoutData(refNrTextFormData);
		refNrText.setTextLimit(32);
		// zipCityToBtn
		zipCityToBtn = new Button(c,SWT.PUSH);
		FormData zipCityToBtnData = new FormData();
		zipCityToBtnData.top = new FormAttachment(0,108);
		zipCityToBtnData.left = new FormAttachment(0,182);
		zipCityToBtnData.right = new FormAttachment(0,194);
		zipCityToBtnData.bottom = new FormAttachment(0,122);
		zipCityToBtn.setLayoutData(zipCityToBtnData);
		zipCityToBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getZipCityCodeTableModel().noCondition();
				zipCityToCodeTablePanel.tryToShowCodeTable();
			}		
		});

		// zipCityForBtn
		zipCityForBtn = new Button(c,SWT.PUSH);
		FormData zipCityForBtnData = new FormData();
		zipCityForBtnData.top = new FormAttachment(0,211);
		zipCityForBtnData.left = new FormAttachment(0,182);
		zipCityForBtnData.right = new FormAttachment(0,194);
		zipCityForBtnData.bottom = new FormAttachment(0,225);
		zipCityForBtn.setLayoutData(zipCityForBtnData);
		zipCityForBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getZipCityCodeTableModel().noCondition();
				zipCityForCodeTablePanel.tryToShowCodeTable();
			}		
		});

		// postForBtn
		postForBtn = new Button(c,SWT.PUSH);
		FormData postForBtnData = new FormData();
		postForBtnData.top = new FormAttachment(0,151);
		postForBtnData.left = new FormAttachment(0,182);
		postForBtnData.right = new FormAttachment(0,194);
		postForBtnData.bottom = new FormAttachment(0,165);
		postForBtn.setLayoutData(postForBtnData);
		postForBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getPostForHistoryCodeTableModel().noCondition();
				postForHistoryCodeTablePanel.tryToShowCodeTable();
			}		
		});

		// postTo1Btn
		postTo1Btn = new Button(c,SWT.PUSH);
		FormData postTo1BtnData = new FormData();
		postTo1BtnData.top = new FormAttachment(0,48);
		postTo1BtnData.left = new FormAttachment(0,182);
		postTo1BtnData.right = new FormAttachment(0,194);
		postTo1BtnData.bottom = new FormAttachment(0,62);
		postTo1Btn.setLayoutData(postTo1BtnData);
		postTo1Btn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getPostToHistoryCodeTableModel().noCondition();
				postToHistoryCodeTablePanel.tryToShowCodeTable();
			}		
		});

		// postTo2Btn
		postTo2Btn = new Button(c,SWT.PUSH);
		FormData postTo2BtnData = new FormData();
		postTo2BtnData.top = new FormAttachment(0,235);
		postTo2BtnData.left = new FormAttachment(0,182);
		postTo2BtnData.right = new FormAttachment(0,194);
		postTo2BtnData.bottom = new FormAttachment(0,249);
		postTo2Btn.setLayoutData(postTo2BtnData);
		postTo2Btn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				MainModel.getInstance().getPostToHistoryCodeTableModel().noCondition();
				postToHistoryCodeTablePanel.tryToShowCodeTable();
			}		
		});
				
		// initialize the codetables
		zipCityToCodeTablePanel =
			new CodeTablePanel(this.getShell(), new CodeTableListener() {
			public void handleDoubleClick(List record) {
				setZipCityTo(record);
			}
		},
			MainModel.getInstance().getZipCityCodeTableModel(),
			new int[] { SWT.NULL, SWT.NULL, SWT.RIGHT });

		zipCityForCodeTablePanel =
			new CodeTablePanel(this.getShell(), new CodeTableListener() {
			public void handleDoubleClick(List record) {
				setZipCityFor(record);
			}
		},
			MainModel.getInstance().getZipCityCodeTableModel(),
			new int[] { SWT.NULL, SWT.NULL, SWT.RIGHT });


		postToHistoryCodeTablePanel =
			new CodeTablePanel(this.getShell(), new CodeTableListener() {
			public void handleDoubleClick(List record) {
				setPostToHistoryFields(record);
			}
		},
			MainModel.getInstance().getPostToHistoryCodeTableModel(),
			new int[] {
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL });

		postForHistoryCodeTablePanel =
			new CodeTablePanel(this.getShell(), new CodeTableListener() {
			public void handleDoubleClick(List record) {
				setPostForHistoryFields(record);
			}
		},
			MainModel.getInstance().getPostForHistoryCodeTableModel(),
			new int[] {
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
				SWT.NULL,
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
		toLine1Text.setText(payment.getToLine1());
		toLine2Text.setText(payment.getToLine2());
		toLine3Text.setText(payment.getToLine3());
		zipToText.setText(payment.getToZip());
		cityToText.setText(payment.getToCity());
		forLine1Text.setText(payment.getForLine1());
		forLine2Text.setText(payment.getForLine2());
		forLine3Text.setText(payment.getForLine3());
		zipForText.setText(payment.getForZip());
		cityForText.setText(payment.getForCity());
		ktoPostText.setText(payment.getToKtoPost());
		refNrText.setText(payment.getRefNr());		
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

	
	Image getSlipImage(){
		Image image = null;
		if(isOrange){
			image = ImageRegistry.getInstance().getImage("esorange_image");
			GC gc = new GC (image);
			gc.dispose ();
		} else {
			image = ImageRegistry.getInstance().getImage("esblue_image");
			GC gc = new GC (image);
			gc.dispose ();

		}
		return image;
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
		tmpPayment.setToLine1(toLine1Text.getText().trim());
		tmpPayment.setToLine2(toLine2Text.getText().trim());
		tmpPayment.setToLine3(toLine3Text.getText().trim());
		tmpPayment.setToZip(zipToText.getText().trim());
		tmpPayment.setToCity(cityToText.getText().trim());
		tmpPayment.setToKtoPost(ktoPostText.getText().trim());
		tmpPayment.setForLine1(forLine1Text.getText().trim());
		tmpPayment.setForLine2(forLine2Text.getText().trim());
		tmpPayment.setForLine3(forLine3Text.getText().trim());
		tmpPayment.setForZip(zipForText.getText().trim());
		tmpPayment.setForCity(cityForText.getText().trim());
		tmpPayment.setRefNr(refNrText.getText().trim());
		// this call to the super class fills in the general fields
		// validates and saves the payment
		super.handleSaveBtn(tmpPayment);
	}

	/**
	 * All the methods for setting the data from the codetables
	 * @param l List containing all the record data
	 */
	private void setZipCityTo(List l) {
		String zip = l.get(0) != null ? (String)l.get(0) : "";
		String city = l.get(1) != null ? (String)l.get(1) : "";
		zipToText.setText(zip);
		cityToText.setText(city);
	}
	
	private void setZipCityFor(List l) {
		String zip = l.get(0) != null ? (String)l.get(0) : "";
		String city = l.get(1) != null ? (String)l.get(1) : "";
		zipForText.setText(zip);
		cityForText.setText(city);
	}

	private void setPostToHistoryFields(List l) {
		String ktopost = l.get(0) != null ? (String)l.get(0) : "";
		String line1To = l.get(1) != null ? (String)l.get(1) : "";
		String line2To = l.get(2) != null ? (String)l.get(2) : "";
		String line3To = l.get(3) != null ? (String)l.get(3) : "";
		String zipTo = l.get(4) != null ? (String)l.get(4) : "";
		String cityTo = l.get(5) != null ? (String)l.get(5) : "";

		ktoPostText.setText(ktopost);
		toLine1Text.setText(line1To);
		toLine2Text.setText(line2To);
		toLine3Text.setText(line3To);
		zipToText.setText(zipTo);
		cityToText.setText(cityTo);
	}

	private void setPostForHistoryFields(List l) {
		String ktopost = l.get(0) != null ? (String)l.get(0) : "";
		String line1For = l.get(1) != null ? (String)l.get(1) : "";
		String line2For = l.get(2) != null ? (String)l.get(2) : "";
		String line3For = l.get(3) != null ? (String)l.get(3) : "";
		String zipFor = l.get(4) != null ? (String)l.get(4) : "";
		String cityFor = l.get(5) != null ? (String)l.get(5) : "";
		String line1To = l.get(6) != null ? (String)l.get(6) : "";
		String line2To = l.get(7) != null ? (String)l.get(7) : "";
		String line3To = l.get(8) != null ? (String)l.get(8) : "";
		String zipTo = l.get(9) != null ? (String)l.get(9) : "";
		String cityTo = l.get(10) != null ? (String)l.get(10) : "";

		ktoPostText.setText(ktopost);
		forLine1Text.setText(line1For);
		forLine2Text.setText(line2For);
		forLine3Text.setText(line3For);
		zipForText.setText(zipFor);
		cityForText.setText(cityFor);
		toLine1Text.setText(line1To);
		toLine2Text.setText(line2To);
		toLine3Text.setText(line3To);
		zipToText.setText(zipTo);
		cityToText.setText(cityTo);
	}

	/**
	 *  (non-Javadoc)
	 * @see ch.truesolutions.payit.view.swt.PaymentSlip#drawUserAccountData(org.eclipse.swt.graphics.GC)
	 */
	void drawUserAccountData(GC gc) {
		if(selectedUserAccount != null){
			gc.drawText(selectedUserAccount.getLine1(),260,223,true);
			gc.drawText(selectedUserAccount.getLine2(),260,243,true);
			gc.drawText(selectedUserAccount.getLine3(),260,263,true);
			gc.drawText(selectedUserAccount.getZip(),260,283,true);
			gc.drawText(selectedUserAccount.getCity(),300,283,true);
		}
	}
}
