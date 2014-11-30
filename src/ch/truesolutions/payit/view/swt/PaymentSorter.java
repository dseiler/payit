package ch.truesolutions.payit.view.swt;

/**
 * @author Daniel
 */

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import ch.truesolutions.payit.model.Config;
import ch.truesolutions.payit.model.Payment;

public class PaymentSorter extends ViewerSorter {
	
	private int sortedColumnIndex = -1;
	private boolean[] ascSortOrders = new boolean[7];
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public int compare(Viewer viewer, Object arg1, Object arg2) {
		Payment p1 = (Payment)arg1;
		Payment p2 = (Payment)arg2;
		if(sortedColumnIndex > -1){
			if(!ascSortOrders[sortedColumnIndex]){
				p2 = (Payment)arg1;
				p1 = (Payment)arg2;			
			}
		}

		switch(sortedColumnIndex){
			case 0:
				return p1.getPaymentType().compareTo(p2.getPaymentType());
			case 1:
				// date
				java.util.Date d1 = Config.getInstance().getDateFromDisplayDateString(p1.getExecDate());
				java.util.Date d2 = Config.getInstance().getDateFromDisplayDateString(p2.getExecDate());
				return d1.compareTo(d2);
			case 2:
				return p1.getAmount().compareTo(p2.getAmount());
			case 3:
				return p1.getFromKto().compareTo(p2.getFromKto());
			case 4:
				return p1.getKtoToDisplayString().compareTo(p2.getKtoToDisplayString());
			case 5:
				return p1.getToDisplayString().compareTo(p2.getToDisplayString());
			case 6:
				return p1.getComment().compareTo(p2.getComment());
			default:
				return p1.compareTo(p2);
		}
	}

	public void setSortedColumnIndex(int i){
		this.sortedColumnIndex = i;
		ascSortOrders[i] = ascSortOrders[i] ? false : true;
	}

}
