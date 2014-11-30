/*
 * Created on 12.04.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.payit.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Daniel
 */
public class PaymentsModel {

	private List payments;
	private String constraint;
	

	/**
	 * 
	 */
	public PaymentsModel(String constraint) {
		super();
		this.constraint = constraint;
		reload();
		
	}

	/**
	 * @return
	 */
	public List getPayments() {
		return payments;
	}

	/**
	 * @param list
	 */
	public void setPayments(List payments) {
		this.payments = payments;
	}
	
	public void reload() {
		// query the database
		ArrayList al = new ArrayList();
		List l = DAO.getInstance().executeQuery(constraint);
		for(Iterator i = l.iterator();i.hasNext();){
			Payment p = new Payment((ArrayList)i.next());
			// p.register(this);
			al.add(p);
		}
		setPayments(al);
	}

	public Payment getPayment(int row) {
		return (Payment)(payments.get(row));
	}
}
