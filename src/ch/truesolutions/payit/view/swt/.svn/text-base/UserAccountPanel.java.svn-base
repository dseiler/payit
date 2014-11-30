/*
 * Created on 19.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import ch.truesolutions.payit.exceptions.InvalidDataException;
import ch.truesolutions.payit.model.*;
import ch.truesolutions.payit.model.Config;
import ch.truesolutions.payit.model.UserAccount;

/**
 * @author Daniel
 */
public class UserAccountPanel extends Dialog {

	static final int TEXT_FIELD_STYLE = SWT.SINGLE | SWT.BORDER;
	static final int TEXT_AREA_STYLE =SWT.MULTI | SWT.WRAP | SWT.BORDER;
	
	Canvas mainCanvas;
	Canvas southCanvas;
	
	Shell shell;
	UserAccount account;

	private Text line1Text;
	private Text line2Text;
	private Text line3Text;
	private Text zipText;
	private Text cityText;
	private Text descText;//30
	private Text ibanText;//21
	private Text ktoNrText;//24
	private Text clearingText;//5
	
	
	UserAccountPanel(Shell parent) {
		super(parent);
	}
	
	UserAccountPanel(Shell parent, UserAccount account){
		this(parent);
		this.account = account;
	}

	protected Control createContents(Composite parent) {		
		shell = getShell();
		shell.setSize(325,355);
		RowLayout rowLayout = new RowLayout();
		rowLayout.wrap = true;
		rowLayout.pack = true;
		rowLayout.marginLeft = 0;
		rowLayout.marginTop = 0;
		rowLayout.marginRight = 0;
		rowLayout.marginBottom = 0;
		rowLayout.spacing = 0;
		shell.setLayout(rowLayout);
						
		// main Canvas
		mainCanvas = new Canvas (parent, SWT.NONE);
		mainCanvas.setLayout(new FormLayout());
		mainCanvas.setLayoutData(new RowData(320,300));		
		Font mainCanvasFont = mainCanvas.getFont();
		FontData[] fontData = mainCanvasFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(12);
		}
		Font newMainCanvasFont = new Font(shell.getDisplay(), fontData);
		mainCanvas.setFont(newMainCanvasFont);
		//mainCanvas.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_RED));

		// name label
		Label nameLabel = new Label(mainCanvas,SWT.NONE);
		FormData nameLabelFormData = new FormData();
		nameLabelFormData.top = new FormAttachment(0,30);
		nameLabelFormData.left = new FormAttachment(0,15);
		nameLabel.setLayoutData(nameLabelFormData);
		nameLabel.setText(Config.getInstance().getText("label.name"));
		// line1
		line1Text = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData line1TextFormData = new FormData();
		line1TextFormData.top = new FormAttachment(0,30);
		line1TextFormData.left = new FormAttachment(0,100);
		line1TextFormData.right = new FormAttachment(0,300);
		line1Text.setLayoutData(line1TextFormData);
		line1Text.setTextLimit(20);
		// address label
		Label addressLabel = new Label(mainCanvas,SWT.NONE);
		FormData addressLabelFormData = new FormData();
		addressLabelFormData.top = new FormAttachment(0,55);
		addressLabelFormData.left = nameLabelFormData.left;
		addressLabel.setLayoutData(addressLabelFormData);
		addressLabel.setText(Config.getInstance().getText("label.address"));
		// line2
		line2Text = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData line2TextFormData = new FormData();
		line2TextFormData.top = new FormAttachment(0,55);
		line2TextFormData.left = line1TextFormData.left;
		line2TextFormData.right = line1TextFormData.right;
		line2Text.setLayoutData(line2TextFormData);
		line2Text.setTextLimit(20);
		// line3
		line3Text = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData line3TextFormData = new FormData();
		line3TextFormData.top = new FormAttachment(0,80);
		line3TextFormData.left = line1TextFormData.left;
		line3TextFormData.right = line1TextFormData.right;
		line3Text.setLayoutData(line3TextFormData);
		line3Text.setTextLimit(20);
		// zip city label
		Label zipCityLabel = new Label(mainCanvas,SWT.NONE);
		FormData zipCityLabelFormData = new FormData();
		zipCityLabelFormData.top = new FormAttachment(0,105);
		zipCityLabelFormData.left = nameLabelFormData.left;
		zipCityLabel.setLayoutData(zipCityLabelFormData);
		zipCityLabel.setText(Config.getInstance().getText("label.zipcity"));
		// zip
		zipText = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData zipTextFormData = new FormData();
		zipTextFormData.top = new FormAttachment(0,105);
		zipTextFormData.left = line1TextFormData.left;
		zipTextFormData.right = new FormAttachment(0,145);
		zipText.setLayoutData(zipTextFormData);
		zipText.setTextLimit(5);
		// city
		cityText = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData cityTextFormData = new FormData();
		cityTextFormData.top = new FormAttachment(0,105);
		cityTextFormData.left = new FormAttachment(0,150);
		cityTextFormData.right = line1TextFormData.right;
		cityText.setLayoutData(cityTextFormData);
		cityText.setTextLimit(15);
		// description label
		Label descLabel = new Label(mainCanvas,SWT.NONE);
		FormData descLabelFormData = new FormData();
		descLabelFormData.top = new FormAttachment(0,150);
		descLabelFormData.left = nameLabelFormData.left;
		descLabel.setLayoutData(descLabelFormData);
		descLabel.setText(Config.getInstance().getText("label.description"));
		// description
		descText = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData descTextFormData = new FormData();
		descTextFormData.top = new FormAttachment(0,150);
		descTextFormData.left = line1TextFormData.left;
		descTextFormData.right = line1TextFormData.right;
		descText.setLayoutData(descTextFormData);
		descText.setTextLimit(30);
		// iban label
		Label ibanLabel = new Label(mainCanvas,SWT.NONE);
		FormData ibanLabelFormData = new FormData();
		ibanLabelFormData.top = new FormAttachment(0,175);
		ibanLabelFormData.left = nameLabelFormData.left;
		ibanLabel.setLayoutData(ibanLabelFormData);
		ibanLabel.setText(Config.getInstance().getText("label.iban"));
		// iban
		ibanText = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData ibanTextFormData = new FormData();
		ibanTextFormData.top = new FormAttachment(0,175);
		ibanTextFormData.left = line1TextFormData.left;
		ibanTextFormData.right = line1TextFormData.right;
		ibanText.setLayoutData(ibanTextFormData);
		ibanText.setTextLimit(21);
		// ktoNr label
		Label ktoNrLabel = new Label(mainCanvas,SWT.NONE);
		FormData ktoNrLabelFormData = new FormData();
		ktoNrLabelFormData.top = new FormAttachment(0,200);
		ktoNrLabelFormData.left = nameLabelFormData.left;
		ktoNrLabel.setLayoutData(ktoNrLabelFormData);
		ktoNrLabel.setText(Config.getInstance().getText("label.kto"));
		// description
		ktoNrText = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData ktoNrTextFormData = new FormData();
		ktoNrTextFormData.top = new FormAttachment(0,200);
		ktoNrTextFormData.left = line1TextFormData.left;
		ktoNrTextFormData.right = line1TextFormData.right;
		ktoNrText.setLayoutData(ktoNrTextFormData);
		ktoNrText.setTextLimit(24);
		// clearing label
		Label clearingLabel = new Label(mainCanvas,SWT.NONE);
		FormData clearingLabelFormData = new FormData();
		clearingLabelFormData.top = new FormAttachment(0,225);
		clearingLabelFormData.left = nameLabelFormData.left;
		clearingLabel.setLayoutData(clearingLabelFormData);
		clearingLabel.setText(Config.getInstance().getText("label.clearing"));
		// clearing
		clearingText = new Text(mainCanvas,TEXT_FIELD_STYLE);
		FormData clearingTextFormData = new FormData();
		clearingTextFormData.top = new FormAttachment(0,225);
		clearingTextFormData.left = line1TextFormData.left;
		clearingTextFormData.right = new FormAttachment(0,145);
		clearingText.setLayoutData(clearingTextFormData);
		clearingText.setTextLimit(5);
		

/*
		clearingButton.setSize(16, 16);
		clearingButton.setLocation(150, 109);
		clearingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bkstamCodeTablePanel.noCondition();
				bkstamCodeTablePanel.tryToShowCodeTable(
					CodeTablePanel.SHOW_ALWAYS);
			}
		});


		bankLabel.setFont(labelFont);
		bankLabel.setSize(90, 20);
		bankLabel.setLocation(15, 130);

		bankValueLabel.setFont(normalFont);
		bankValueLabel.setSize(200, 20);
		bankValueLabel.setLocation(100, 130);
		bankValueLabel.setOpaque(true);
		bankValueLabel.setBackground(Color.white);

 */

		// Canvas in the south		
		FormLayout southCanvasLayout = new FormLayout();
		southCanvas = new Canvas (parent, SWT.NONE);
		southCanvas.setLayout(southCanvasLayout);
		southCanvas.setLayoutData(new RowData(320,30));
		//southCanvas.setBackground(getShell().getDisplay().getSystemColor (SWT.COLOR_GREEN));

		Button saveBtn = new Button (southCanvas, SWT.PUSH);
		FormData southCanvasSaveBtnFormData = new FormData();
		southCanvasSaveBtnFormData.top = new FormAttachment(0,5);
		southCanvasSaveBtnFormData.bottom = new FormAttachment(100,-5);
		southCanvasSaveBtnFormData.left = new FormAttachment(0,5);
		southCanvasSaveBtnFormData.right = new FormAttachment(0,65);
		saveBtn.setLayoutData(southCanvasSaveBtnFormData);
		saveBtn.setText (Config.getInstance().getText("button.save"));
		saveBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				handleSaveBtn();
			}
		});
		
		Button cancelBtn = new Button (southCanvas, SWT.PUSH);
		FormData southCanvasCancelBtnFormData = new FormData();
		southCanvasCancelBtnFormData.top = new FormAttachment(0,5);
		southCanvasCancelBtnFormData.bottom = new FormAttachment(100,-5);
		southCanvasCancelBtnFormData.left = new FormAttachment(100,-65);
		southCanvasCancelBtnFormData.right = new FormAttachment(100,-5);
		cancelBtn.setLayoutData(southCanvasCancelBtnFormData);
		cancelBtn.setText (Config.getInstance().getText("button.cancel"));
		cancelBtn.addMouseListener(new MouseAdapter(){
			public void mouseUp(MouseEvent e){
				handleCancelBtn();
			}
		});
		
		if(this.account != null){
			fill();
		}

		return shell;
	}
			
	
	void handleSaveBtn(){
		UserAccount newAccount = new UserAccount();
		newAccount.setAccountNr(account.getAccountNr());
		newAccount.setNewAccountNr(ktoNrText.getText());
		newAccount.setClearing(clearingText.getText());
		newAccount.setLine1(line1Text.getText());
		newAccount.setLine2(line2Text.getText());
		newAccount.setLine3(line3Text.getText());
		newAccount.setZip(zipText.getText());
		newAccount.setCity(cityText.getText());
		newAccount.setIban(ibanText.getText());
		newAccount.setDescription(descText.getText());
		
		try{
			// TODO newAccount.validate();
			if(account.getAccountNr().length() == 0){
				newAccount.setAccountNr(newAccount.getNewAccountNr());
				MainModel.getInstance().getUserAccountsModel().addUserAccount(newAccount);
			} else {
				MainModel.getInstance().getUserAccountsModel().updateUserAccount(newAccount);
			}
			shell.close();
		} catch (InvalidDataException e) {
			// TODO show msg box
			System.out.println(e);
		}

	}
	

	void handleCancelBtn(){
		shell.close();
	}
		
	void fill() {
		line1Text.setText(account.getLine1());
		line2Text.setText(account.getLine2());
		line3Text.setText(account.getLine3());
		zipText.setText(account.getZip());
		cityText.setText(account.getCity());
		descText.setText(account.getDescription());
		ibanText.setText(account.getIban());
		ktoNrText.setText(account.getAccountNr());
		clearingText.setText(account.getClearing());
	}
	
	/**
	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 */
//	protected void setShellStyle(int arg0) {
//		super.setShellStyle(SWT.TITLE /*| SWT.BORDER | SWT.PRIMARY_MODAL*/);
//	}

}
