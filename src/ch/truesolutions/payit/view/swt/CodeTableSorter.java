package ch.truesolutions.payit.view.swt;

/**
 * @author Daniel
 */

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class CodeTableSorter extends ViewerSorter {
	
	private int sortedColumnIndex = -1;
	private boolean[] ascSortOrders;
	
	public CodeTableSorter(int numbCols){
		ascSortOrders = new boolean[numbCols];
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public int compare(Viewer viewer, Object arg1, Object arg2) {
		List record1 = (List)arg1;
		List record2 = (List)arg2;
		if(sortedColumnIndex > -1){
			if(ascSortOrders[sortedColumnIndex]){
				record2 = (List)arg1;
				record1 = (List)arg2;			
			}
			return ((String)record1.get(sortedColumnIndex)).compareTo((String)record2.get(sortedColumnIndex));
		} else {
			return -1;
		}
	}

	public void setSortedColumnIndex(int i){
		this.sortedColumnIndex = i;
		ascSortOrders[i] = ascSortOrders[i] ? false : true;
	}

}
