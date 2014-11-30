/*
 * Created on 19.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import ch.truesolutions.payit.model.Payment;
import ch.truesolutions.payit.model.UserAccount;

/**
 * @author Daniel
 */
public class PaymentSlipIpi extends PaymentSlip {

	/**
	 * @param parent
	 */
	public PaymentSlipIpi(Shell parent) {
		super(parent);
		this.create();
	}
	
	protected Control createContents(Composite parent) {
		Control ctrl = super.createContents(parent);
		mainCanvas.setLayoutData(new RowData(725,300));
		eastCanvas.setLayoutData(new RowData(110,300));
		shell.setSize(843,390);
		return ctrl;
	}

	public void fill(Payment payment){
		this.payment = payment;
		super.fill();
		// TODO fill PaymentSlipIpi
		shell.setVisible(true);
	}
	
	Image getSlipImage(){
		Image image = ImageRegistry.getInstance().getImage("ipi_image");
		GC gc = new GC (image);
		gc.dispose ();
		return image;
	}

	void selectedUserAccountChanged(UserAccount userAccount) {
		// TODO Auto-generated method stub
	}
}
