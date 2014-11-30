/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ch.truesolutions.payit.exceptions.InvalidDataException;

import net.n3.nanoxml.XMLElement;

public class Payment implements Comparable{
	// Define a static category variable so that it references the
	// Category instance named "ts.ch.payit.PayIT".
	static Logger logger = Logger.getLogger(ch.truesolutions.payit.model.Payment.class);

	public static final int POSTTO_HISTORY_UPDATE = 0;
	public static final int BANK_HISTORY_UPDATE = 1;
	public static final int POSTTO_HISTORY_AND_BANKHISTORY_UPDATE = 2;
	public static final int POSTTO_HISTORY_AND_POSTFOR_HISTORY_UPDATE = 3;
	
	private Integer id = null;
	private Integer paymentType = null;
	private Boolean isPending = new Boolean(true);
	private String execDate = new ch.truesolutions.calendar.EasyDatePicker().getNextSelectableDateAsString();
	private Double amount = null;
	private String fromKto = "";
	private String comment = "";
	private String forLine1 = "";
	private String forLine2 = "";
	private String forLine3 = "";
	private String forZip = "";
	private String forCity = "";
	private String forKtoBank = "";
	private String forClearing = "";
	private String toLine1 = "";
	private String toLine2 = "";
	private String toLine3 = "";
	private String toZip = "";
	private String toCity = "";
	private String toKtoPost = "";
	private String refNr = "";
	private String reason = "";
	private Boolean isSalary = new Boolean(false);
	private String currency = "CHF";
	private String blz = "";
	private String swift = "";
	private String instruction = "";
	private String costs = "";
	private String form = "01"; // default is unstructured text

	public Payment() {
	}

	public Payment(ArrayList al) {
		fill(al);
	}

	public Payment(XMLElement elt) {
		fill(elt);
	}
	
	public void fill(Payment p){
		id = p.getId();
		paymentType = p.getPaymentType();
		isPending = p.getIsPending();
		execDate = p.getExecDate();
		amount = p.getAmount();
		fromKto = p.getFromKto();
		comment = p.getComment();
		forLine1 = p.getForLine1();
		forLine2 = p.getForLine2();
		forLine3 = p.getForLine3();
		forZip = p.getForZip();
		forCity = p.getForCity();
		forKtoBank = p.getForKtoBank();
		forClearing = p.getForClearing();
		toLine1 = p.getToLine1();
		toLine2 = p.getToLine2();
		toLine3 = p.getToLine3();
		toZip = p.getToZip();
		toCity = p.getToCity();
		toKtoPost = p.getToKtoPost();
		refNr = p.getRefNr();
		reason = p.getReason();
		isSalary = p.getIsSalary();
		currency = p.getCurrency();
		blz = p.getBlz();
		swift = p.getSwift();
		instruction = p.getInstruction();
		costs = p.getCosts();
		form = p.getForm();	
	}

	public void fill(ArrayList al) {
		// fill the content of the ArrayList into this payment
		id = (Integer) (al.get(0));
		paymentType = (Integer) (al.get(1));
		isPending = (Boolean) (al.get(2));
		execDate = Config.getInstance().getDisplayDateString((Date) (al.get(3)));
		amount = (Double) (al.get(4));
		fromKto = (al.get(5) != null) ? (String) (al.get(5)) : "";
		comment = (al.get(6) != null) ? (String) (al.get(6)) : "";
		forLine1 = (al.get(7) != null) ? (String) (al.get(7)) : "";
		forLine2 = (al.get(8) != null) ? (String) (al.get(8)) : "";
		forLine3 = (al.get(9) != null) ? (String) (al.get(9)) : "";
		forZip = (al.get(10) != null) ? (String) (al.get(10)) : "";
		forCity = (al.get(11) != null) ? (String) (al.get(11)) : "";
		forKtoBank = (al.get(12) != null) ? (String) (al.get(12)) : "";
		forClearing = (al.get(13) != null) ? (String) (al.get(13)) : "";
		toLine1 = (al.get(14) != null) ? (String) (al.get(14)) : "";
		toLine2 = (al.get(15) != null) ? (String) (al.get(15)) : "";
		toLine3 = (al.get(16) != null) ? (String) (al.get(16)) : "";
		toZip = (al.get(17) != null) ? (String) (al.get(17)) : "";
		toCity = (al.get(18) != null) ? (String) (al.get(18)) : "";
		toKtoPost = (al.get(19) != null) ? (String) (al.get(19)) : "";
		refNr = (al.get(20) != null) ? (String) (al.get(20)) : "";
		reason = (al.get(21) != null) ? (String) (al.get(21)) : "";
		isSalary = (Boolean) (al.get(22));
		currency = (al.get(23) != null) ? (String) (al.get(23)) : "";
		blz = (al.get(24) != null) ? (String) (al.get(24)) : "";
		swift = (al.get(25) != null) ? (String) (al.get(25)) : "";
		instruction = (al.get(26) != null) ? (String) (al.get(26)) : "";
		costs = (al.get(27) != null) ? (String) (al.get(27)) : "";
		form = (al.get(28) != null) ? (String) (al.get(28)) : "";
	}

	public void fill(XMLElement elt) {
		// fill the content of the payment xml element into the attributes
		try {
			paymentType = new Integer(elt.getAttribute("TYPE", ""));
		} catch (NumberFormatException e) {
			logger.error(e);
		}
		isPending = new Boolean(elt.getAttribute("PENDING", "true"));
		execDate =
			Config.getInstance().convertToDisplayDateString(
				elt.getAttribute("DATE", ""));
		try {
			amount = new Double(elt.getAttribute("AMOUNT", ""));
		} catch (NumberFormatException e) {
			logger.error(e);
		}
		fromKto = elt.getAttribute("FROMACCOUNT", "");
		comment = elt.getAttribute("COMMENT", "");
		forLine1 = elt.getAttribute("FORLINE1", "");
		forLine2 = elt.getAttribute("FORLINE2", "");
		forLine3 = elt.getAttribute("FORLINE3", "");
		forZip = elt.getAttribute("FORZIP", "");
		forCity = elt.getAttribute("FORCITY", "");
		forKtoBank = elt.getAttribute("FORBANCACCOUNT", "");
		forClearing = elt.getAttribute("FORCLEARING", "");
		toLine1 = elt.getAttribute("TOLINE1", "");
		toLine2 = elt.getAttribute("TOLINE2", "");
		toLine3 = elt.getAttribute("TOLINE3", "");
		toZip = elt.getAttribute("TOZIP", "");
		toCity = elt.getAttribute("TOCITY", "");
		toKtoPost = elt.getAttribute("TOPOSTACCOUNT", "");
		refNr = elt.getAttribute("REFNR", "");
		reason = elt.getAttribute("REASON", "");
		isSalary = new Boolean(elt.getAttribute("SALARY", "false"));
		currency = elt.getAttribute("CURRENCY", "");
		blz = elt.getAttribute("BLZ", "");
		swift = elt.getAttribute("SWIFT", "");
		instruction = elt.getAttribute("INSTRUCTION", "");
		costs = elt.getAttribute("COSTS", "");
		form = elt.getAttribute("FORM", "");
	}

	void insert() {
		DAO.getInstance().executeUpdate(createInsertSqlString());
	}

	private String createInsertSqlString() {
		String is = isSalary != null ? isSalary.toString() : "null";
		String ip = isPending != null ? isPending.toString() : "true";
		String pt = paymentType != null ? paymentType.toString() : "null";
		String d = "null";
		if (!execDate.equals(""))
			d = "'" + Config.getInstance().convertToDbDateString(execDate) + "'";

		// get the new id
		id = new Integer(DAO.getInstance().getNextId("Payments", "id"));

		StringBuffer sb =
			new StringBuffer("INSERT INTO Payments (id,paymentType,isPending,execDate,amount,fromKto,comment,forLine1,forLine2,forLine3,forZip,forCity,forKtoBank,forClearing,toLine1,toLine2,toLine3,toZip,toCity,toKtoPost,refNr,reason,isSalary,currency,blz,swift,instruction,costs,form) VALUES (");
		sb.append(id);
		sb.append(",");
		sb.append(pt);
		sb.append(",");
		sb.append(ip);
		sb.append(",");
		sb.append(d);
		sb.append(",");
		sb.append(amount);
		sb.append(",'");
		sb.append(fromKto);
		sb.append("','");
		sb.append(comment);
		sb.append("','");
		sb.append(forLine1);
		sb.append("','");
		sb.append(forLine2);
		sb.append("','");
		sb.append(forLine3);
		sb.append("','");
		sb.append(forZip);
		sb.append("','");
		sb.append(forCity);
		sb.append("','");
		sb.append(forKtoBank);
		sb.append("','");
		sb.append(forClearing);
		sb.append("','");
		sb.append(toLine1);
		sb.append("','");
		sb.append(toLine2);
		sb.append("','");
		sb.append(toLine3);
		sb.append("','");
		sb.append(toZip);
		sb.append("','");
		sb.append(toCity);
		sb.append("','");
		sb.append(toKtoPost);
		sb.append("','");
		sb.append(refNr);
		sb.append("','");
		sb.append(reason);
		sb.append("',");
		sb.append(is);
		sb.append(",'");
		sb.append(currency);
		sb.append("','");
		sb.append(blz);
		sb.append("','");
		sb.append(swift);
		sb.append("','");
		sb.append(instruction);
		sb.append("','");
		sb.append(costs);
		sb.append("','");
		sb.append(form);
		sb.append("')");

		return sb.toString();
	}

	void update() {
		String is = isSalary != null ? isSalary.toString() : "null";
		String d = "null";
		if (!execDate.equals(""))
			d = "'" + Config.getInstance().convertToDbDateString(execDate) + "'";

		DAO.getInstance()
			.executeUpdate(
				"UPDATE Payments SET isPending="
					+ isPending.booleanValue()
					+ ",execDate="
					+ d
					+ ",amount="
					+ amount
					+ ",fromKto='"
					+ fromKto
					+ "',comment='"
					+ comment
					+ "',forLine1='"
					+ forLine1
					+ "',forLine2='"
					+ forLine2
					+ "',forLine3='"
					+ forLine3
					+ "',forZip='"
					+ forZip
					+ "',forCity='"
					+ forCity
					+ "',forKtoBank='"
					+ forKtoBank
					+ "',forClearing='"
					+ forClearing
					+ "',toLine1='"
					+ toLine1
					+ "',toLine2='"
					+ toLine2
					+ "',toLine3='"
					+ toLine3
					+ "',toZip='"
					+ toZip
					+ "',toCity='"
					+ toCity
					+ "',toKtoPost='"
					+ toKtoPost
					+ "',refNr='"
					+ refNr
					+ "',reason='"
					+ reason
					+ "',isSalary="
					+ is
					+ ",currency='"
					+ currency
					+ "',blz='"
					+ blz
					+ "',swift='"
					+ swift
					+ "',instruction='"
					+ instruction
					+ "',costs='"
					+ costs
					+ "',form='"
					+ form
					+ "' WHERE id="
					+ id);
	}

	void delete() {
		DAO.getInstance()
			.executeUpdate("DELETE FROM Payments WHERE id=" + id);
	}

	public int updateHistory(boolean doUpdate) {
		// depending on the updated history tables the update type
		// is set and returned
		int updateType = -1;
		// according to the payment type different history tables
		// have to be updated
		int type = paymentType != null ? paymentType.intValue() : -1;
		switch (type) {
			// red and red with iban payment slip
			case Config.PAYMENT_TYPE_RED :
			case Config.PAYMENT_TYPE_RED_IBAN :
				updatePostToHistoryTable(doUpdate);
				updateType = POSTTO_HISTORY_UPDATE;
				if (!"".equals(forKtoBank))
					updateBankHistoryTable(doUpdate);
					updateType = POSTTO_HISTORY_AND_BANKHISTORY_UPDATE;
				break;
				// blue or orange payment slip
			case Config.PAYMENT_TYPE_BLUE :
			case Config.PAYMENT_TYPE_ORANGE :
				updatePostToHistoryTable(doUpdate);
				updateType = POSTTO_HISTORY_UPDATE;
				if (!"".equals(forLine1)) {
					updatePostForHistoryTable(doUpdate);
					updateType = POSTTO_HISTORY_AND_POSTFOR_HISTORY_UPDATE;
				}
				break;
				// bank payment
			case Config.PAYMENT_TYPE_BANKCH :
				updateBankHistoryTable(doUpdate);
				updateType = BANK_HISTORY_UPDATE;
				break;
			default :
				logger.warn("there is no history table for this payment type ("+type+") to update.");
		}
		return updateType;
	}

	private void updatePostToHistoryTable(boolean doUpdate) {
		// determine if we have an insert or an update
		List al =
			DAO.getInstance().executeQuery(
				"SELECT ktoPost FROM PostToHistory WHERE ktoPost = '"
					+ toKtoPost
					+ "'");
		if (al == null || al.size() == 0) // it's an insert
			{
			String sql =
				"INSERT INTO PostToHistory (ktoPost,line1,line2,line3,zip,city) "
					+ "VALUES ('"
					+ toKtoPost
					+ "','"
					+ toLine1
					+ "','"
					+ toLine2
					+ "','"
					+ toLine3
					+ "','"
					+ toZip
					+ "','"
					+ toCity
					+ "')";

			DAO.getInstance().executeUpdate(sql);
		} else if (doUpdate) {
			// do update
			logger.info("updating record in PostToHistory");
			DAO.getInstance()
				.executeUpdate(
					"UPDATE PostToHistory SET "
						+ "ktoPost='"
						+ toKtoPost
						+ "',line1='"
						+ toLine1
						+ "',line2='"
						+ toLine2
						+ "',line3='"
						+ toLine3
						+ "',zip='"
						+ toZip
						+ "',city='"
						+ toCity
						+ "' WHERE ktoPost='"
						+ toKtoPost
						+ "'");
		}
	}

	// for payment slip blue/orange fields below
	private void updatePostForHistoryTable(boolean doUpdate) {
		// determine if we have an insert or an update
		List al =
			DAO.getInstance().executeQuery(
				"SELECT ktoPost FROM PostForHistory "
					+ "WHERE ktoPost = '"
					+ toKtoPost
					+ "' AND line1='"
					+ forLine1
					+ "'");
		if (al == null || al.size() == 0) // it's an insert
			{
			logger.info("inserting record into PostForHistory Table");
			DAO.getInstance()
				.executeUpdate(
					"INSERT INTO PostForHistory (ktoPost,line1,line2,line3,zip,city) "
						+ "VALUES ('"
						+ toKtoPost
						+ "','"
						+ forLine1
						+ "','"
						+ forLine2
						+ "','"
						+ forLine3
						+ "','"
						+ forZip
						+ "','"
						+ forCity
						+ "')");
		} else if (doUpdate) {
			// do update
			logger.info("updating record in PostForHistory");
			DAO.getInstance()
				.executeUpdate(
					"UPDATE PostForHistory SET "
						+ "ktoPost='"
						+ toKtoPost
						+ "',line1='"
						+ forLine1
						+ "',line2='"
						+ forLine2
						+ "',line3='"
						+ forLine3
						+ "',zip='"
						+ forZip
						+ "',city='"
						+ forCity
						+ "' WHERE ktoPost='"
						+ toKtoPost
						+ "' AND line1='"
						+ forLine1
						+ "'");
		}
	}

	private void updateBankHistoryTable(boolean doUpdate) {
		// determine if we have an insert or an update
		List al =
			DAO.getInstance().executeQuery(
				"SELECT ktoBank FROM BankHistory WHERE ktoBank = '"
					+ forKtoBank
					+ "'");
		if (al == null || al.size() == 0) // it's an insert
			{
			logger.info("inserting record into BankHistoryTable");
			DAO.getInstance()
				.executeUpdate(
					"INSERT INTO BankHistory (ktoBank,clearing,line1,line2,line3,zip,city,ktoPost) "
						+ "VALUES ('"
						+ forKtoBank
						+ "','"
						+ forClearing
						+ "','"
						+ forLine1
						+ "','"
						+ forLine2
						+ "','"
						+ forLine3
						+ "','"
						+ forZip
						+ "','"
						+ forCity
						+ "','"
						+ toKtoPost
						+ "')");
		} else if (doUpdate) {
			// do update
			logger.info("updating record in BankHistoryTable");
			DAO.getInstance()
				.executeUpdate(
					"UPDATE BankHistory SET "
						+ "clearing='"
						+ forClearing
						+ "',line1='"
						+ forLine1
						+ "',line2='"
						+ forLine2
						+ "',line3='"
						+ forLine3
						+ "',zip='"
						+ forZip
						+ "',city='"
						+ forCity
						+ "',ktoPost='"
						+ toKtoPost
						+ "' WHERE ktoBank='"
						+ forKtoBank
						+ "'");
		}
	}

	public String toXmlString() {
		// with nanoxml
		nanoxml.XMLElement elt = new nanoxml.XMLElement();
		elt.setName("payment");

		elt.setAttribute("type", paymentType.toString());
		elt.setAttribute("pending", isPending.toString());
		elt.setAttribute("date", Config.getInstance().convertToDbDateString(execDate));
		elt.setAttribute("amount", amount.toString());
		elt.setAttribute("fromaccount", fromKto);
		elt.setAttribute("comment", comment);
		elt.setAttribute("forline1", forLine1);
		elt.setAttribute("forline2", forLine2);
		elt.setAttribute("forline3", forLine3);
		elt.setAttribute("forzip", forZip);
		elt.setAttribute("forcity", forCity);
		elt.setAttribute("forbancaccount", forKtoBank);
		elt.setAttribute("forclearing", forClearing);
		elt.setAttribute("toline1", toLine1);
		elt.setAttribute("toline2", toLine2);
		elt.setAttribute("toline3", toLine3);
		elt.setAttribute("tozip", toZip);
		elt.setAttribute("tocity", toCity);
		elt.setAttribute("topostaccount", toKtoPost);
		elt.setAttribute("refnr", refNr);
		elt.setAttribute("reason", reason);
		elt.setAttribute("salary", isSalary.toString());
		elt.setAttribute("currency", currency);
		elt.setAttribute("blz", blz);
		elt.setAttribute("swift", swift);
		elt.setAttribute("instruction", instruction);
		elt.setAttribute("costs", costs);
		elt.setAttribute("form", form);

		return elt.toString();
	}

	public String toHtmlString() {
		StringBuffer sb = new StringBuffer("<tr bgcolor=\"??row??\"><td>");
		// ??row?? can be replaced by a color
		if (paymentType != null) {
			switch (paymentType.intValue()) {
				case Config.PAYMENT_TYPE_RED :
					sb.append("Rot");
					break;
				case Config.PAYMENT_TYPE_BLUE :
					sb.append("Blau");
					break;
				case Config.PAYMENT_TYPE_ORANGE :
					sb.append("Orange");
					break;
				case Config.PAYMENT_TYPE_BANKCH :
					sb.append("Bank CH");
					break;
				case Config.PAYMENT_TYPE_IPI :
					sb.append("IPI");
					break;
			}
		}
		sb.append("</td><td>");
		sb.append(execDate);
		sb.append("</td><td>");
		sb.append(fromKto);
		sb.append("</td><td>");
		if (forLine1.equals("")
			&& forLine1.equals("")
			&& forLine1.equals("")) {
			sb.append(toLine1);
			sb.append(",");
			sb.append(toLine2);
			sb.append(",");
			sb.append(toLine3);
			sb.append(",");
			sb.append(toZip);
			sb.append(" ");
			sb.append(toCity);
		} else {
			sb.append(forLine1);
			sb.append(",");
			sb.append(forLine2);
			sb.append(",");
			sb.append(forLine3);
			sb.append(",");
			sb.append(forZip);
			sb.append(" ");
			sb.append(forCity);
		}
		sb.append("</td><td><b>");
		if (amount != null) {
			sb.append(amount.toString());
		}
		sb.append("</b></td></tr>");
		return sb.toString();
	}

	public Integer getId() {
		return id;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public Boolean getIsPending() {
		return isPending;
	}
	public String getExecDate() {
		return execDate;
	}
	public String getCurrency() {
		return currency;
	}
	public Double getAmount() {
		return amount;
	}
	public String getFromKto() {
		return fromKto;
	}
	public String getComment() {
		return comment;
	}
	public String getForLine1() {
		return forLine1;
	}
	public String getForLine2() {
		return forLine2;
	}
	public String getForLine3() {
		return forLine3;
	}
	public String getForZip() {
		return forZip;
	}
	public String getForCity() {
		return forCity;
	}
	public String getForKtoBank() {
		return forKtoBank;
	}
	public String getForClearing() {
		return forClearing;
	}
	public String getToLine1() {
		return toLine1;
	}
	public String getToLine2() {
		return toLine2;
	}
	public String getToLine3() {
		return toLine3;
	}
	public String getToZip() {
		return toZip;
	}
	public String getToCity() {
		return toCity;
	}
	public String getToKtoPost() {
		return toKtoPost;
	}
	public String getRefNr() {
		return refNr;
	}
	public String getReason() {
		return reason;
	}
	public Boolean getIsSalary() {
		return isSalary != null ? isSalary : new Boolean(false);
	}
	public String getSwift(){
		return swift;
	}
	public String getCosts(){
		return costs;
	}
	public String getForm(){
		return form;
	}
	/**
	 * @param double1
	 */
	public void setAmount(Double double1) {
		amount = double1;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @param string
	 */
	public void setCosts(String string) {
		costs = string;
	}

	/**
	 * @param string
	 */
	public void setCurrency(String string) {
		currency = string;
	}

	/**
	 * @param string
	 */
	public void setExecDate(String string) {
		execDate = string;
	}

	/**
	 * @param string
	 */
	public void setForCity(String string) {
		forCity = string;
	}

	/**
	 * @param string
	 */
	public void setForClearing(String string) {
		forClearing = string;
	}

	/**
	 * @param string
	 */
	public void setForKtoBank(String string) {
		forKtoBank = string;
	}

	/**
	 * @param string
	 */
	public void setForLine1(String string) {
		forLine1 = string;
	}

	/**
	 * @param string
	 */
	public void setForLine2(String string) {
		forLine2 = string;
	}

	/**
	 * @param string
	 */
	public void setForLine3(String string) {
		forLine3 = string;
	}

	/**
	 * @param string
	 */
	public void setForm(String string) {
		form = string;
	}

	/**
	 * @param string
	 */
	public void setForZip(String string) {
		forZip = string;
	}

	/**
	 * @param string
	 */
	public void setFromKto(String string) {
		fromKto = string;
	}

	/**
	 * @param integer
	 */
	public void setId(Integer integer) {
		id = integer;
	}

	/**
	 * @param boolean1
	 */
	public void setIsPending(Boolean boolean1) {
		isPending = boolean1;
	}

	/**
	 * @param boolean1
	 */
	public void setIsSalary(Boolean boolean1) {
		isSalary = boolean1;
	}

	/**
	 * @param integer
	 */
	public void setPaymentType(Integer integer) {
		paymentType = integer;
	}

	/**
	 * @param string
	 */
	public void setReason(String string) {
		reason = string;
	}

	/**
	 * @param string
	 */
	public void setRefNr(String string) {
		refNr = string;
	}

	/**
	 * @param string
	 */
	public void setSwift(String string) {
		swift = string;
	}

	/**
	 * @param string
	 */
	public void setToCity(String string) {
		toCity = string;
	}

	/**
	 * @param string
	 */
	public void setToKtoPost(String string) {
		toKtoPost = string;
	}

	/**
	 * @param string
	 */
	public void setToLine1(String string) {
		toLine1 = string;
	}

	/**
	 * @param string
	 */
	public void setToLine2(String string) {
		toLine2 = string;
	}

	/**
	 * @param string
	 */
	public void setToLine3(String string) {
		toLine3 = string;
	}

	/**
	 * @param string
	 */
	public void setToZip(String string) {
		toZip = string;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		int result = 0;
		if(o instanceof Payment){
			Payment p = (Payment)o;
			java.util.Date thisDate = Config.getInstance().getDateFromDisplayDateString(this.getExecDate());
			java.util.Date pDate = Config.getInstance().getDateFromDisplayDateString(p.getExecDate());
			if(thisDate.before(pDate)){
				result = -1;
			} else if(thisDate.equals(pDate)){
				if(this.getAmount().doubleValue() < p.getAmount().doubleValue()){
					result = -1;
				} else if(this.getAmount().doubleValue() > p.getAmount().doubleValue()){
					result = 1;
				}
			} else {
				result = 1;
			}
		} else {
			throw new RuntimeException("cannot compare Payment object with object of type :"+o.getClass().getName());
		}
		return result;
	}
	
	public String getToDisplayString(){
		String to = forLine1;
		if(paymentType.intValue() != Config.PAYMENT_TYPE_IPI){
			// test if there is an entry in the for fields
			if ((forLine1 == null || forLine1.equals(""))
				&& (forLine2 == null || forLine2.equals(""))
				&& (forLine3 == null || forLine3.equals(""))) {
				if (toLine1 != null && !toLine1.equals("")) to = toLine1;
				if (toLine2 != null && !toLine2.equals("")) to = to + "," + toLine2;
				if (toLine3 != null && !toLine3.equals("")) to = to + "," + toLine3;
				if (toZip != null) to = to + "," + toZip;
				if (toCity != null && !toCity.equals("")) to = to + " " + toCity; 
			} else {
				if (forLine1 != null && !forLine1.equals("")) to = forLine1;
				if (forLine2 != null && !forLine2.equals("")) to = to + "," + forLine2;
				if (forLine3 != null && !forLine3.equals("")) to = to + "," + forLine3;
				if (forZip != null) to = to + "," + forZip;
				if (forCity != null && !forCity.equals("")) to = to + " " + forCity; 
			}
		}
		return to;
	}
	
	public String getKtoToDisplayString(){
		// if there is a bankaccount we display it
		String kto = forKtoBank;
		if (kto == null || kto.equals(""))
			kto = toKtoPost;
		return kto;		
	}
	
	public String getAmountDisplayString(){
		if(amount != null){
			BigDecimal a = new BigDecimal(((Double) amount).doubleValue());
			a = a.setScale(2, BigDecimal.ROUND_HALF_UP);
			return a.toString();
		} else {
			return "";
		}
	}
	
	public void validate() throws InvalidDataException {
		boolean validateRefNr = true;
		// only pending payments should be validated
		if (isPending.booleanValue() == false) throw new InvalidDataException(
			Config.getInstance().getText("validate_msg.notpending"));
		// general validation
		if (fromKto.equals("")) throw new InvalidDataException(
			Config.getInstance().getText("validate_msg.accountmissing"));
		// validate the date
		if(execDate.equals("")) throw new InvalidDataException(
			Config.getInstance().getText("validate_msg.emptydate"));
		// TODO could do more date validation
		// validate the amount		
		if(amount == null || amount.doubleValue() <= 0) throw new InvalidDataException(
			Config.getInstance().getText("validate_msg.amount"));

		if(paymentType.intValue() == Config.PAYMENT_TYPE_RED ||
			paymentType.intValue() == Config.PAYMENT_TYPE_BLUE ||
			paymentType.intValue() == Config.PAYMENT_TYPE_ORANGE ||
			paymentType.intValue() == Config.PAYMENT_TYPE_RED_IBAN){
				// check if there is a post kto nr
				if (toKtoPost.equals("")) throw new InvalidDataException(
					Config.getInstance().getText("validate_msg.postktomissing"));
				// validate the format of the post kto nr
				int i = toKtoPost.indexOf('-');
				if(i == -1){
					if(toKtoPost.length() != 5) throw new InvalidDataException(
						Config.getInstance().getText("validate_msg.postktolength"));
					else validateRefNr = false;
				} else {
					i = toKtoPost.indexOf('-', i + 1);
					if(i == -1) throw new InvalidDataException(
						Config.getInstance().getText("validate_msg.postktoformat"));
					else {
						i = toKtoPost.indexOf('-', i + 1);
						if(i == -1 && toKtoPost.length() != 11) throw new InvalidDataException(
							Config.getInstance().getText("validate_msg.postktolength"));
						if(i > -1) throw new InvalidDataException(
							Config.getInstance().getText("validate_msg.postktoformat"));	
						// validate the Pruefziffer
						String ktoNrStr = Utils.deleteChars(toKtoPost, '-');
						int ctrlNr = ktoNrStr.charAt(ktoNrStr.length() - 1) - 48;
						logger.info("ctrlNr = " + ctrlNr);
						char[] ktoNrCharArray =
							(ktoNrStr.substring(0, ktoNrStr.length() - 1).toCharArray());
						int[] ktoNrIntArray = new int[ktoNrCharArray.length];
						for (int j = 0; j < ktoNrCharArray.length; j++)
							ktoNrIntArray[j] = ktoNrCharArray[j] - 48;
						int calcCtrlNr = Utils.calculateCtrlNumberMod10Rek(ktoNrIntArray);
						logger.info("calculated ctrlNr = " + calcCtrlNr);
						if (ctrlNr != calcCtrlNr) throw new InvalidDataException(
							Config.getInstance().getText("validate_msg.ctrlnrpostkto"));
					}
				}
				// validate the postkto adresse
				if ((toLine1.equals("")
					&& toLine2.equals("")
					&& toLine3.equals(""))
					|| toZip.equals("")
					|| toCity.equals(""))
					throw new InvalidDataException(
						Config.getInstance().getText("validate_msg.postktoowner"));				
		}	
		
		if ((paymentType.intValue() == Config.PAYMENT_TYPE_RED)) {
			//if something is entered into the indirekt beguenstigten feld
			if (!forLine1.equals("")
				|| !forLine2.equals("")
				|| !forLine3.equals("")
				|| !forZip.equals("")
				|| !forCity.equals("")
				|| !forKtoBank.equals("")
				|| !forClearing.equals("")) {
				if ((forLine1.equals("")
					&& forLine2.equals("")
					&& forLine3.equals(""))
					|| forZip.equals("")
					|| forCity.equals("")
					|| forKtoBank.equals("")
					|| forClearing.equals(""))
					throw new InvalidDataException(
						Config.getInstance().getText("validate_msg.formissing"));
			}
		}
		
		if(paymentType.intValue() == Config.PAYMENT_TYPE_BLUE ||
			paymentType.intValue() == Config.PAYMENT_TYPE_ORANGE){
			//if something is entered into the indirekt beg�nstigten feld
			if (!forLine1.equals("")
				|| !forLine2.equals("")
				|| !forLine3.equals("")
				|| !forZip.equals("")
				|| !forCity.equals("")) {
				if ((forLine1.equals("")
					&& forLine2.equals("")
					&& forLine3.equals(""))
					|| forZip.equals("")
					|| forCity.equals(""))
					throw new InvalidDataException(
						Config.getInstance().getText("validate_msg.formissing"));
			}
			// validate the ref nr pruefziffer
			if(validateRefNr){
				String refNrStr = Utils.deleteChars(refNr, ' ');
				if (refNrStr.length() == 0)throw new InvalidDataException(
					Config.getInstance().getText("validate_msg.refnrmissing"));
				int refCtrlNr = refNrStr.charAt(refNrStr.length() - 1) - 48;
				logger.info("refCtrlNr = " + refCtrlNr);
				char[] refNrCharArray =
					(refNrStr
						.substring(0, refNrStr.length() - 1)
						.toCharArray());
				int[] refNrIntArray = new int[refNrCharArray.length];
				for (int j = 0; j < refNrCharArray.length; j++)
					refNrIntArray[j] = refNrCharArray[j] - 48;
				int calcRefCtrlNr =
					Utils.calculateCtrlNumberMod10Rek(refNrIntArray);
				logger.info("calculated RefCtrlNr = " + calcRefCtrlNr);
				if (refCtrlNr != calcRefCtrlNr) throw new InvalidDataException(
					Config.getInstance().getText("validate_msg.refnrinvalid"));
			}
		}
		
		if(paymentType.intValue() == Config.PAYMENT_TYPE_RED_IBAN)
		{
			if(forKtoBank.equals("")) {
				throw new InvalidDataException(
						Config.getInstance().getText("validate_msg.ibanwrong"));
			}
			// Validate the IBAN Number
			if (!Utils.validateIbanCtrlNumberMod9710(forKtoBank)) throw new InvalidDataException(
					Config.getInstance().getText("validate_msg.ibanwrong"));
		}

	}
	/**
	 * @return
	 */
	public String getBlz() {
		return blz;
	}

	/**
	 * @return
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * @param string
	 */
	public void setBlz(String string) {
		blz = string;
	}

	/**
	 * @param string
	 */
	public void setInstruction(String string) {
		instruction = string;
	}

}
