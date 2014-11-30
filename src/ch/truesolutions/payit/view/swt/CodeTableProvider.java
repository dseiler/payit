/*
 * Created on 08.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.view.swt;

import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

import ch.truesolutions.payit.model.*;

public class CodeTableProvider
	implements IStructuredContentProvider, ITableLabelProvider {
	//private PaymentsModel model;

	public CodeTableProvider() {
	}

	public Object[] getElements(Object element) {
		CodeTableModel model = (CodeTableModel) element;
		return model.getTableData().toArray();
	}

	public void dispose() {
	}

	public void inputChanged(
		Viewer viewer,
		Object old_object,
		Object new_object) {
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
//	public Image getColumnImage(Object obj, int row) {
//		Payment p = (Payment) obj;
//		if(row == 0){
//			int type = p.getPaymentType().intValue();
//			switch(type){
//				case Config.PAYMENT_TYPE_RED:
//					return ImageRegistry.getInstance().getImage("esred_icon_table");
//				case Config.PAYMENT_TYPE_BLUE:
//					return ImageRegistry.getInstance().getImage("esblue_icon_table");
//				case Config.PAYMENT_TYPE_BANKCH:
//					return ImageRegistry.getInstance().getImage("esbank_icon_table");
//				case Config.PAYMENT_TYPE_ORANGE:
//					return ImageRegistry.getInstance().getImage("esorange_icon_table");
//				case Config.PAYMENT_TYPE_IPI:
//					return ImageRegistry.getInstance().getImage("ipi_icon_table");
//			}
//		}
//		return null;
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
//	 */
//	public String getColumnText(Object obj, int row) {
//		Payment p = (Payment) obj;
//		switch (row) {
//			case 0 :
//				return "";// in the type column we have the icon
//			case 1 :
//				return p.getExecDate();
//			case 2 :
//				return p.getAmountDisplayString();
//			case 3 :
//				return p.getFromKto();
//			case 4 :
//				return p.getKtoToDisplayString();
//			case 5 :
//				return p.getToDisplayString();
//			case 6 :
//				return p.getComment();
//			default :
//				return "default";
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
//	 */
//	public void addListener(ILabelProviderListener arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
//	 */
//	public boolean isLabelProperty(Object arg0, String arg1) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
//	 */
//	public void removeListener(ILabelProviderListener arg0) {
//		// TODO Auto-generated method stub
//
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		List l = (List)arg0;
		return l.get(arg1).toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub

	}

}
