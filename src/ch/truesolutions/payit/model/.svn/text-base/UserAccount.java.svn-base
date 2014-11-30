/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.model;

import java.util.List;

import ch.truesolutions.payit.exceptions.InvalidDataException;

import net.n3.nanoxml.XMLElement;

public class UserAccount {
	private String accountNr = "";
	private String newAccountNr = "";
	private String clearing = "";
	private String iban = "";
	private String bankName = "";
	private String currency = "CHF";
	private String description = "";
	private String line1 = "";
	private String line2 = "";
	private String line3 = "";
	private String zip = "";
	private String city = "";

	public UserAccount() {
	}

	public UserAccount(List al) {
		if (!al.isEmpty()) {
			accountNr = (al.get(0) != null) ? (String) (al.get(0)) : "";
			clearing = (al.get(1) != null) ? (String) (al.get(1)) : "";
			iban = (al.get(2) != null) ? (String) (al.get(2)) : "";
			bankName = (al.get(3) != null) ? (String) (al.get(3)) : "";
			currency = (al.get(4) != null) ? (String) (al.get(4)) : "";
			description = (al.get(5) != null) ? (String) (al.get(5)) : "";
			line1 = (al.get(6) != null) ? (String) (al.get(6)) : "";
			line2 = (al.get(7) != null) ? (String) (al.get(7)) : "";
			line3 = (al.get(8) != null) ? (String) (al.get(8)) : "";
			zip = (al.get(9) != null) ? (String) (al.get(9)) : "";
			city = (al.get(10) != null) ? (String) (al.get(10)) : "";
		}
	}

	public UserAccount(XMLElement elt) {
		newAccountNr = elt.getAttribute("NUMBER", "");
		accountNr = newAccountNr;
		clearing = elt.getAttribute("CLEARING", "");
		iban = elt.getAttribute("IBAN", "");
		bankName = "";
		currency = elt.getAttribute("CURRENCY", "");
		description = elt.getAttribute("DESCRIPTION", "");
		line1 = elt.getAttribute("LINE1", "");
		line2 = elt.getAttribute("LINE2", "");
		line3 = elt.getAttribute("LINE3", "");
		zip = elt.getAttribute("ZIP", "");
		city = elt.getAttribute("CITY", "");
	}

	public void insert() {
		DAO.getInstance().executeUpdate(
			"INSERT INTO UserAccounts (accountNr,clearing,iban,currency,description,line1,line2,line3,zip,city) VALUES ("
				+ "'"
				+ newAccountNr
				+ "','"
				+ clearing
				+ "','"
				+ iban
				+ "','"
				+ currency
				+ "','"
				+ description
				+ "','"
				+ line1
				+ "','"
				+ line2
				+ "','"
				+ line3
				+ "','"
				+ zip
				+ "','"
				+ city
				+ "')");
	}

	public void update() {
		DAO.getInstance().executeUpdate(
			"UPDATE UserAccounts SET accountNr='"
				+ newAccountNr
				+ "',clearing='"
				+ clearing
				+ "',iban='"
				+ iban
				+ "',currency='"
				+ currency
				+ "',description='"
				+ description
				+ "',line1='"
				+ line1
				+ "',line2='"
				+ line2
				+ "',line3='"
				+ line3
				+ "',zip='"
				+ zip
				+ "',city='"
				+ city
				+ "' WHERE accountNr='"
				+ accountNr
				+ "'");
	}

	public void delete() {
		DAO.getInstance().executeUpdate(
			"DELETE FROM UserAccounts WHERE accountNr='" + accountNr + "'");
	}

	public String toString() {
		return accountNr;
	}

	public String toXmlString() {
		// with nanoxml
		nanoxml.XMLElement elt = new nanoxml.XMLElement();
		elt.setName("useraccount");

		elt.setAttribute("number", accountNr);
		elt.setAttribute("clearing", clearing);
		elt.setAttribute("iban", iban);
		elt.setAttribute("currency", currency);
		elt.setAttribute("description", description);
		elt.setAttribute("line1", line1);
		elt.setAttribute("line2", line2);
		elt.setAttribute("line3", line3);
		elt.setAttribute("zip", zip);
		elt.setAttribute("city", city);

		return elt.toString();
	}
	
	public void validate() throws InvalidDataException {
		//check if enterd user account nr is not empty
		if (getNewAccountNr().trim().length() == 0) {
			throw new InvalidDataException(Config.getInstance().getText("validate_msg.accountmissing"));
		}
		// validate the IBAN
		if (getIban().trim().length() > 0
			&& !Utils.validateIbanCtrlNumberMod9710(getIban())) {
			throw new InvalidDataException(Config.getInstance().getText("validate_msg.ibanwrong"));			
		}

	}

	public String getAccountNr() {
		return accountNr;
	}
	public String getNewAccountNr() {
		return newAccountNr;
	}
	public void setAccountNr(String anr) {
		accountNr = anr;
	}
	public void setNewAccountNr(String anr) {
		newAccountNr = anr;
	}
	public String getClearing() {
		return clearing;
	}
	public void setClearing(String c) {
		clearing = c;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBankName() {
		return bankName;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String cur) {
		currency = cur;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String desc) {
		description = desc;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line) {
		line1 = line;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line) {
		line2 = line;
	}
	public String getLine3() {
		return line3;
	}
	public void setLine3(String line) {
		line3 = line;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String z) {
		zip = z;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String c) {
		city = c;
	}
}