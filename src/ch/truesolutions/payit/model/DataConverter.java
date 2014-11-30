/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.model;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.log4j.Logger;

import ch.truesolutions.payit.exceptions.InvalidDataException;

public class DataConverter {
	static Logger logger = Logger.getLogger(ch.truesolutions.payit.model.DataConverter.class);

	static int lengthBlue = 384; // length of the type 826
	static int lengthRed = 512;
	static int lengthIpi = 640;
	// length of the type 827 Achtung falls Zahlungsgrund fehlt kann die Laenge auch 384 sein !!
	static int lengthTail = 128; // length of the type 890

	// header
	static int start01 = 0; // 2
	static int startExecDate = 2; // 6
	static int startClearingTo = 8; // 12
	static int startSequenceOut = 20; // 5
	static int startCreationDate = 25; // 6
	static int startClearingFrom = 31;
	// 7 the Byte nr. 31 (from 0) is the start of the Clearing Number
	static int startAuthorId = 38; // 5
	static int startSequenceIn = 43; // 5
	static int startTransactionType = 48;
	// 3 the Byte nr. 48 (from 0) is the start of the Transaction Type
	static int startPaymentType = 51; // 1
	static int startActionFlag = 52; // 1
	static int startRefNrId = 53; // 5
	static int startRefNrTrNr = 58; // 11
	static int startKtoFrom = 69;
	// 24 the Byte nr. 69 (from 0) is the start of the Konto Number
	static int startAmountValuta = 93; // 6 Blanks
	static int startAmountCurrency = 99; // 3
	static int startAmountValue = 102; // 12
	static int start02 = 128; // 2
	static int startBlueFromLine1 = 130; // 20
	static int startRedFromLine1 = 130; // 24
	static int startIpiFromLine1 = 142; // 35
	static int startBlueFromLine2 = 150; // 20
	static int startRedFromLine2 = 154; // 24
	static int startBlueFromLine3 = 170; // 20
	static int startIpiFromLine2 = 177; // 35
	static int startRedFromLine3 = 178; // 24
	static int startBlueFromZip = 190; // 5
	static int startBlueFromCity = 195; // 15
	static int startRedFromZip = 202; // 5
	static int startRedFromCity = 207; // 19
	static int startIpiFromZip = 212; // 5
	static int startIpiFromCity = 217; // 30

	static int start03 = 256; // 03/C/
	static int startToKto = 261; // 9 for 826, 27 for 827 bank, 12 for 827 post
	static int startBlueForLine1 = 270;
	// 20 falls zugunsten von leer ist wird einzahlung fuer genommen
	static int startRedForLine1 = 288;
	// 24 falls zugunsten von leer ist wird einzahlung fuer genommen
	static int startBlueForLine2 = 290; // 20
	static int startRedForLine2 = 312; // 24
	static int startBlueForLine3 = 310; // 20
	static int startToKtoIpi = 329; // 34
	static int startRedForLine3 = 336; // 24
	static int startBlueForZip = 330; // 5
	static int startSwift = 259; // 35
	static int startRedForZip = 360; // 5
	static int startBlueForCity = 335; // 15
	static int startRedForCity = 365; // 15
	static int startBlueRefNr = 350;
	// TODO max 34 wahrscheinlich 27 rechtsbuendig
	static int start04 = 384; // 2
	static int startReason = 386; // max 4*32 gewaehlt 4*24, UBS 4*28
	static int startIpiForLine1 = 386;
	static int startIpiReasonType = 514; // 1 U: unstructured, I: structured
	static int startIpiReason = 515; // 35 or 20
	static int clearingToLength = 7;
	static int transTypeLength = 3;

	public static String readDtaFileIntoHtmlString(File dtaFile) {
		return importDtaFile(dtaFile, true);
	}

	public static void importDtaFile(File dtaFile) {
		importDtaFile(dtaFile, false);
	}

	public static String importDtaFile(File dtaFile, boolean getHtmlString) {

		StringBuffer dta = new StringBuffer();
		StringBuffer html = new StringBuffer();

		try {
			// Read the file
			// TODO reading of the file doesnt match the written one if payments contains IPI - fix this
			FileReader fr = new FileReader(dtaFile);
			BufferedReader br = new BufferedReader(fr);
			while (br.ready()) {
				dta.append(br.readLine());
			}
			fr.close();
			br.close();
		} catch (EOFException e) {
			logger.error("unexpected end of file reached");
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException("IOException: "+e);
		}

		int currentRecord = 0; // beginning of the current DTA-Record
		int nextRecord = 0;
		int fileLength = dta.length();
		if (fileLength < lengthRed) {
			throw new RuntimeException("Illegal file length: "+fileLength);
		}
			

		while (currentRecord < fileLength) {
			Payment payment = new Payment();
			boolean isValid = false;
			String clearingTo = "";
			String transactionType =
				dta.substring(
					currentRecord + startTransactionType,
					currentRecord + startTransactionType + transTypeLength);

			// end of loop
			/**
			* ES Type: BLUE
			*/
			if (transactionType.equals("826")) {
				payment.setPaymentType(
					new Integer(Config.PAYMENT_TYPE_BLUE));
				// to Kto
				String tmp =
					dta.substring(
							currentRecord + startToKto,
							currentRecord + startToKto + 9).trim();
				if (tmp.substring(0, 4).equals("0000")) {
					payment.setToKtoPost(tmp.substring(4));
				} else {
					payment.setToKtoPost(tmp);
				}

				// format the post kto nr
				payment.setToKtoPost(
					Utils.formatPostKtoNr(payment.getToKtoPost(), true));
				// receiver address

				payment.setToLine1(
					dta.substring(
							currentRecord + startBlueForLine1,
							currentRecord + startBlueForLine1 + 20).trim());
				payment.setToLine2(
					dta.substring(
							currentRecord + startBlueForLine2,
							currentRecord + startBlueForLine2 + 20).trim());
				payment.setToLine3(
					dta.substring(
							currentRecord + startBlueForLine3,
							currentRecord + startBlueForLine3 + 20).trim());
				payment.setToZip(
					dta.substring(
							currentRecord + startBlueForZip,
							currentRecord + startBlueForZip + 5).trim());
				payment.setToCity(
					dta.substring(
							currentRecord + startBlueForCity,
							currentRecord + startBlueForCity + 15).trim());
				// ref nr
				payment.setRefNr(
					dta.substring(
							currentRecord + startBlueRefNr,
							currentRecord + startBlueRefNr + 27).trim());

				nextRecord = currentRecord + lengthBlue;
				isValid = true;
			}
			/**
			* ES Type: RED or BANK
			*/
			else if (transactionType.equals("827")) {
				clearingTo =
					dta.substring(
							currentRecord + startClearingTo,
							currentRecord + startClearingTo + clearingToLength)
						.trim();
				String paymentType =
					dta.substring(
							currentRecord + startPaymentType,
							currentRecord + startPaymentType + 1)
						.trim();
				payment.setIsSalary(
					new Boolean(paymentType.equals("1") ? true : false));

				// red payment slip (post)
				if (clearingTo.equals("")) {
					payment.setPaymentType(
						new Integer(Config.PAYMENT_TYPE_RED));

					// to Kto
					payment.setToKtoPost(
						Utils.formatPostKtoNr(
							dta.substring(
									currentRecord + startToKto,
									currentRecord + startToKto + 12)
								.trim(),
							false));

					// receiver
					payment.setToLine1(
						dta.substring(
								currentRecord + startRedForLine1,
								currentRecord + startRedForLine1 + 24)
							.trim());
					payment.setToLine2(
						dta.substring(
								currentRecord + startRedForLine2,
								currentRecord + startRedForLine2 + 24)
							.trim());
					payment.setToLine3(
						dta.substring(
								currentRecord + startRedForLine3,
								currentRecord + startRedForLine3 + 24)
							.trim());
					payment.setToZip(
						dta.substring(
								currentRecord + startRedForZip,
								currentRecord + startRedForZip + 5)
							.trim());
					payment.setToCity(
						dta.substring(
								currentRecord + startRedForCity,
								currentRecord + startRedForCity + 19)
							.trim());
				}
				// bank payment slip
				else {
					payment.setPaymentType(
						new Integer(Config.PAYMENT_TYPE_BANKCH));

					// to Kto
					payment.setForKtoBank(
						dta.substring(
								currentRecord + startToKto,
								currentRecord + startToKto + 27)
							.trim());

					// receiver
					payment.setForLine1(
						dta.substring(
								currentRecord + startRedForLine1,
								currentRecord + startRedForLine1 + 24)
							.trim());
					payment.setForLine2(
						dta.substring(
								currentRecord + startRedForLine2,
								currentRecord + startRedForLine2 + 24)
							.trim());
					payment.setForLine3(
						dta.substring(
								currentRecord + startRedForLine3,
								currentRecord + startRedForLine3 + 24)
							.trim());
					payment.setForZip(
						dta.substring(
								currentRecord + startRedForZip,
								currentRecord + startRedForZip + 5)
							.trim());
					payment.setForCity(
						dta.substring(
								currentRecord + startRedForCity,
								currentRecord + startRedForCity + 19)
							.trim());

					// fill in the clearing number of the receiver
					payment.setForClearing(clearingTo);
				}

				if (dta.substring(
						currentRecord + start04,
						currentRecord + start04 + 2)
					.equals("04")) {
					// we have a reson field

					payment.setReason(
						dta.substring(
								currentRecord + startReason,
								currentRecord + startReason + 96)
							.trim());

					nextRecord = currentRecord + lengthRed;
				} else // we don't have a reson field
					{
					nextRecord = currentRecord + lengthBlue;
				}
				isValid = true;
			}
			/**
			* ES Type: IPI
			*/
			else if (transactionType.equals("836")) {
				payment.setPaymentType(
					new Integer(Config.PAYMENT_TYPE_IPI));

				String paymentType =
					dta.substring(
							currentRecord + startPaymentType,
							currentRecord + startPaymentType + 1)
						.trim();
				payment.setIsSalary(
					new Boolean(paymentType.equals("1") ? true : false));

				// to Kto
				payment.setForKtoBank(
					dta.substring(
							currentRecord + startToKtoIpi,
							currentRecord + startToKtoIpi + 34)
						.trim());

				// receiver
				payment.setForLine1(
					dta.substring(
							currentRecord + startIpiForLine1,
							currentRecord + startIpiForLine1 + 35)
						.trim());
						
				// swift
				payment.setSwift(
					dta.substring(
							currentRecord + startSwift,
							currentRecord + startSwift + 35)
						.trim());
						
				// reason type
				String reasonType =
					dta.substring(
							currentRecord + startIpiReasonType,
							currentRecord + startIpiReasonType + 1);
				int reasonLength = 0;
				// reason type: I:structured --> form = 00, U:unstructured--> form=01
				if("I".equals(reasonType)){
					payment.setForm("00");
					reasonLength = 20;
				} else {
					payment.setForm("01");
					reasonLength = 35;
				}
				
				// reason
				payment.setReason(
					dta.substring(
							currentRecord + startIpiReason,
							currentRecord + startIpiReason + reasonLength)
						.trim());

				nextRecord = currentRecord + lengthIpi;
				isValid = true;
			}

			/**
			* ES Type: TAIL (TOTAL)
			*/
			else if (transactionType.equals("890")) {
				String total = dta.substring(currentRecord + 53).trim();
				// 53 is the length of the header
				total = total.replace(',', '.');
				html.append(
					"<tr bgcolor=\"??total??\"><td colspan=\"4\"><b>Total</b></td><td><b>");
				html.append(total);
				html.append("</b></td></tr>");
				nextRecord = currentRecord + lengthTail;
			} else
				nextRecord = fileLength;

			if (isValid) {
				// read the date
				int startDate = startExecDate;
				if(payment.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI){
					startDate = startAmountValuta;
				}
				String month =
					dta.substring(
						currentRecord + startDate + 2,
						currentRecord + startDate + 4);
				String day =
					dta.substring(
						currentRecord + startDate + 4,
						currentRecord + startDate + 6);
				String year =
					"20"
						+ dta.substring(
							currentRecord + startDate,
							currentRecord + startDate + 2);
				payment.setExecDate(
						Config.getInstance().convertToDisplayDateString(
						year + "-" + month + "-" + day));
				
				// read the currency value
				payment.setCurrency(
					dta.substring(
							currentRecord + startAmountCurrency,
							currentRecord + startAmountCurrency + 3)
						.trim());
				
				
				// read the amount value
				String amountStr =
					dta.substring(
							currentRecord + startAmountValue,
							currentRecord + startAmountValue + 12)
						.trim();
				amountStr = amountStr.replace(',', '.');
				try {
					payment.setAmount(new Double(amountStr));
				} catch (NumberFormatException e) {
					logger.error(e);
				}
				
				// read the Kto From
				payment.setFromKto(
					dta.substring(
							currentRecord + startKtoFrom,
							currentRecord + startKtoFrom + 24)
						.trim());

				// if the from kto nr is unknown we insert it as a new user account

				// determine if it is a new user account or an existing one
				if (!payment.getFromKto().equals("") && !getHtmlString) {
					UserAccount userAccount = new UserAccount();
					userAccount.setAccountNr(payment.getFromKto());
					// detect if the new user account isn't allready used
					List al = 
						DAO.getInstance().executeQuery(
							"SELECT accountNr FROM UserAccounts "
								+ "WHERE accountNr='"
								+ userAccount.getNewAccountNr()
								+ "'");
					if ((al == null || al.size() == 0)
						&& Utils.validateIbanCtrlNumberMod9710(
							userAccount.getNewAccountNr())) {
						// we didn't find a kto nr and the kto nr is actually a iban nr
						// let's try to look up the iban nr
						userAccount.setIban(userAccount.getNewAccountNr());
						al =
							DAO.getInstance().executeQuery(
								"SELECT accountNr FROM UserAccounts "
									+ "WHERE iban='"
									+ userAccount.getIban()
									+ "'");
						// if we have found and iban we take the corepsonding account nr and put it into the payment
						if (al != null && al.size() > 0) {
							payment.setFromKto(
								(String) ((ArrayList) (al.get(0))).get(0));
						}
					}
					if (al == null || al.size() == 0) {
						boolean insertAccount = true;
						String clearingFrom =
							dta.substring(
									currentRecord + startClearingFrom,
									currentRecord + startClearingFrom + 7)
								.trim();
						String line1From = "";
						String line2From = "";
						String line3From = "";
						String zipFrom = "";
						String cityFrom = "";
						if (transactionType.equals("826")) {
							line1From =
								dta.substring(
										currentRecord + startBlueFromLine1,
										currentRecord
											+ startBlueFromLine1
											+ 20)
									.trim();
							line2From =
								dta.substring(
										currentRecord + startBlueFromLine2,
										currentRecord
											+ startBlueFromLine2
											+ 20)
									.trim();
							line3From =
								dta.substring(
										currentRecord + startBlueFromLine3,
										currentRecord
											+ startBlueFromLine3
											+ 20)
									.trim();
							zipFrom =
								dta.substring(
										currentRecord + startBlueFromZip,
										currentRecord + startBlueFromZip + 5)
									.trim();
							cityFrom =
								dta.substring(
										currentRecord + startBlueFromCity,
										currentRecord + startBlueFromCity + 15)
									.trim();
						} else if (transactionType.equals("827")) {
							line1From =
								dta.substring(
										currentRecord + startRedFromLine1,
										currentRecord + startRedFromLine1 + 24)
									.trim();
							line2From =
								dta.substring(
										currentRecord + startRedFromLine2,
										currentRecord + startRedFromLine2 + 24)
									.trim();
							line3From =
								dta.substring(
										currentRecord + startRedFromLine3,
										currentRecord + startRedFromLine3 + 24)
									.trim();
							zipFrom =
								dta.substring(
										currentRecord + startRedFromZip,
										currentRecord + startRedFromZip + 5)
									.trim();
							cityFrom =
								dta.substring(
										currentRecord + startRedFromCity,
										currentRecord + startRedFromCity + 19)
									.trim();
						// IPI
						} else if (transactionType.equals("836")) {
							line1From =
								dta.substring(
										currentRecord + startIpiFromLine1,
										currentRecord + startIpiFromLine1 + 35)
									.trim();
							line2From =
								dta.substring(
										currentRecord + startIpiFromLine2,
										currentRecord + startIpiFromLine2 + 35)
									.trim();
							zipFrom =
								dta.substring(
										currentRecord + startIpiFromZip,
										currentRecord + startIpiFromZip + 5)
									.trim();
							cityFrom =
								dta.substring(
										currentRecord + startIpiFromCity,
										currentRecord + startIpiFromCity + 30)
									.trim();
						} else {
							insertAccount = false;
						}
						if (insertAccount) {
							userAccount.setClearing(clearingFrom);
							userAccount.setDescription("");
							userAccount.setLine1(line1From);
							userAccount.setLine2(line2From);
							userAccount.setLine3(line3From);
							userAccount.setZip(zipFrom);
							userAccount.setCity(cityFrom);

							try {
								MainModel.getInstance().getUserAccountsModel().addUserAccount(userAccount);
							} catch (InvalidDataException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}

				// inserting the payment
				if (!getHtmlString) {
					MainModel.getInstance().addPayment(payment);
				} else {
					html.append(payment.toHtmlString());
				}
			}

			currentRecord = nextRecord;
			logger.info("transaction type=" + transactionType);
		}
		return html.toString();
	}

	public static void exportDtaFile(File dtaFile, List payments) {
		StringBuffer dtaStringBuffer = new StringBuffer();
		StringBuffer tmp;
		int paymentCount = 0;
		int lineLength = 0;
		double sum = 0;
		List userAccounts =
			MainModel.getInstance().getUserAccountsModel().getUserAccounts();
		boolean found;
		for (int i = 0; i < payments.size(); i++) {
			paymentCount++;
			UserAccount account = null;
			Payment p = (Payment) (payments.get(i));
			//loop through the user accounts vector and search for the matching kto number
			Iterator it = userAccounts.iterator();
			found = false;
			while (it.hasNext() && !found) {
				account = (UserAccount) (it.next());
				if (account.getAccountNr().equals(p.getFromKto()))
					found = true;
			}
			if (!found) {
				logger.error("user account not found!");
				throw new RuntimeException("user account not found !");
			}

			/* adding the payement header */
			tmp = makeHeader(p, account, paymentCount);
			if (tmp != null) {
				dtaStringBuffer.append(tmp);
			} else {
				logger.error("making header failed");
				throw new RuntimeException("making header failed !");
			}
			/* adding ref number (5 postitions id = PAYIT and 11 positions Transaction nr) */
			dtaStringBuffer.append("PAYIT");
			dtaStringBuffer.append(
				Utils.fillStringBuffer("" + paymentCount, 11, '0', false));
			/* adding the kto nr of the bank from, found will be true ! */
			/* if there is an IBAN we take it */
			if (account.getIban().equals("")) {
				dtaStringBuffer.append(
					Utils.fillStringBuffer(p.getFromKto(), 24, ' ', true));
			} else {
				logger.info("taking IBAN :" + account.getIban());
				dtaStringBuffer.append(
					Utils.fillStringBuffer(account.getIban(), 24, ' ', true));
			}
			/* adding the amount with the currency */
			if ((p.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI) || (p.getPaymentType().intValue() == Config.PAYMENT_TYPE_RED_IBAN)) {
				/* adding the date */
				String d = Config.getInstance().convertToDbDateString(p.getExecDate());
				if (d.equals(""))
					d = "0000-00-00";
				dtaStringBuffer.append(d.substring(2, 4));
				dtaStringBuffer.append(d.substring(5, 7));
				dtaStringBuffer.append(d.substring(8));
			} else {
				// 6 blancs
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 6, ' ', true));
			}
			dtaStringBuffer.append(p.getCurrency());
			sum =
				sum + (p.getAmount() != null ? p.getAmount().doubleValue() : 0);
			if ((p.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI) || (p.getPaymentType().intValue() == Config.PAYMENT_TYPE_RED_IBAN)) {
				dtaStringBuffer.append(
					Utils.fillStringBuffer(
						p.getAmount().toString().replace('.', ','),
						15,
						' ',
						true));
				/* adding 11 blanks */
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 11, ' ', true));
			} else {
				dtaStringBuffer.append(
					Utils.fillStringBuffer(
						p.getAmount().toString().replace('.', ','),
						12,
						' ',
						true));
				/* adding 14 blanks */
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 14, ' ', true));
			}
			/* adding 02 */
			dtaStringBuffer.append("02");

			if ((p.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI) || (p.getPaymentType().intValue() == Config.PAYMENT_TYPE_RED_IBAN)) {
				// adding conversion rate (so far blank)
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 12, ' ', true));
				// adding address from 3 x 35
				dtaStringBuffer.append(
					Utils.fillStringBuffer(account.getLine1(), 35, ' ', true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(account.getLine2(), 35, ' ', true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(account.getZip(), 5, ' ', true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(account.getCity(), 30, ' ', true));
				// adding 9 spares
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 9, ' ', true));
			} else {
				/* adding address from */
				if ((p.getPaymentType().intValue()
					== Config.PAYMENT_TYPE_RED)
					|| (p.getPaymentType().intValue()
						== Config.PAYMENT_TYPE_BANKCH)) {
					lineLength = 24; // red payment and bank payment
				} else if (
					(p.getPaymentType().intValue()
						== Config.PAYMENT_TYPE_BLUE)
						|| (p.getPaymentType().intValue()
							== Config.PAYMENT_TYPE_ORANGE)) {
					lineLength = 20; // blue payment and orange payment
				}

				dtaStringBuffer.append(
					Utils.fillStringBuffer(
						account.getLine1(),
						lineLength,
						' ',
						true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(
						account.getLine2(),
						lineLength,
						' ',
						true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(
						account.getLine3(),
						lineLength,
						' ',
						true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(account.getZip(), 5, ' ', true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(
						account.getCity(),
						lineLength - 5,
						' ',
						true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer(
						"",
						126 - 4 * lineLength,
						' ',
						true));
			}
			/* adding 03,04 and 05 */
			if ((p.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI) || (p.getPaymentType().intValue() == Config.PAYMENT_TYPE_RED_IBAN)) {
				// 03
				dtaStringBuffer.append("03");
				// determine the bank adress type
				String swift = Utils.deleteChars(p.getSwift(), ' ');
				if (swift.length() == 8 || swift.length() == 11) {
					// it's a swift adress
					dtaStringBuffer.append("A");
					dtaStringBuffer.append(
						Utils.fillStringBuffer(swift, 35, ' ', true));
				} else {
					dtaStringBuffer.append("D");
					dtaStringBuffer.append(
						Utils.fillStringBuffer(p.getSwift(), 35, ' ', true));
				}
				// second line of the bank address is empty
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 35, ' ', true));
				// adding IBAN
				String iban = Utils.deleteChars(p.getForKtoBank(), ' ');
				dtaStringBuffer.append(
					Utils.fillStringBuffer(iban, 34, ' ', true));
				// spars
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 21, ' ', true));

				// 04
				dtaStringBuffer.append("04");
				// for line 1 (only one line is used in my case)
				dtaStringBuffer.append(
					Utils.fillStringBuffer(p.getForLine1(), 35, ' ', true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 35, ' ', true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 35, ' ', true));
				// spar
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 21, ' ', true));

				// 05
				dtaStringBuffer.append("05");
				// do we have a structured reason ?
				if ("00".equals(p.getForm())
					|| "02".equals(p.getForm())
					|| "04".equals(p.getForm())) {
					dtaStringBuffer.append("I");
					String reason = Utils.deleteChars(p.getReason(), ' ');
					if (reason.length() == 20) {
						dtaStringBuffer.append(reason);
					} else {
						logger.warn(
							"illegal length of structured reason string. cuttin it down");
						dtaStringBuffer.append(
							Utils.fillStringBuffer(reason, 20, ' ', true));
					}
					dtaStringBuffer.append(
						Utils.fillStringBuffer("", 15, ' ', true));
				} else {
					dtaStringBuffer.append("U");
					dtaStringBuffer.append(
						Utils.fillStringBuffer(p.getReason(), 35, ' ', true));
				}
				// unused lines
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 35, ' ', true));
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 35, ' ', true));
				// spesenregelung
				if ("OUR".equals(p.getCosts())) {
					dtaStringBuffer.append("0");
				} else if ("BEN".equals(p.getCosts())) {
					dtaStringBuffer.append("1");
				} else if ("SHA".equals(p.getCosts())) {
					dtaStringBuffer.append("2");
				} else {
					logger.warn(
						"unknown spesenregelung:"
							+ p.getCosts()
							+ ",setting it to shared");
					dtaStringBuffer.append("1");
				}
				// spar
				dtaStringBuffer.append(
					Utils.fillStringBuffer("", 19, ' ', true));
			} else {
				dtaStringBuffer.append("03/C/");

				// determine which address to take
				String fl1 = p.getForLine1();
				String fl2 = p.getForLine1();
				String fl3 = p.getForLine1();
				boolean isFor = true;

				if ((fl1 == null || fl1.equals(""))
					&& (fl2 == null || fl2.equals(""))
					&& (fl3 == null || fl3.equals(""))) {
					isFor = false;
				} else
					isFor = true;

				String refNr = "";
				/* adding kto for */
				if ((p.getPaymentType().intValue()
					== Config.PAYMENT_TYPE_RED)
					|| (p.getPaymentType().intValue()
						== Config.PAYMENT_TYPE_BANKCH))
					// red or bank payment slip
					{
					if (isFor) // bank payment 
						dtaStringBuffer.append(
							Utils.fillStringBuffer(
								p.getForKtoBank(),
								27,
								' ',
								true));
					else // post payment
						dtaStringBuffer.append(
							Utils.fillStringBuffer(
								p.getToKtoPost(),
								27,
								' ',
								true));
				} else if (
					(p.getPaymentType().intValue()
						== Config.PAYMENT_TYPE_BLUE)
						|| (p.getPaymentType().intValue()
							== Config.PAYMENT_TYPE_ORANGE))
					// blue or orange payment slip
					{
					// konvert the kto nr to a valid post kto nr
					// String kto = getBluePaymentPostKtoNr(p.getToKtoPost());
					// remove the '-' from the kto nr
					String kto = Utils.deleteChars(p.getToKtoPost(), '-');
					// remove the whitespaces from the refnr
					refNr = Utils.deleteChars(p.getRefNr(), ' ');
					// the length of the kto determines the length of the refNr
					if (kto.length() == 5) // the refnr can be 15 long
						{
						refNr =
							Utils
								.fillStringBuffer(refNr, 15, '0', false)
								.toString();
					} else if (kto.length() == 9) // the refnr can be 27 long
						{
						refNr =
							Utils
								.fillStringBuffer(refNr, 27, '0', false)
								.toString();
					} else {
						logger.error(
							"invalid length of the kto nr: " + kto.length());
							throw new RuntimeException("invalid length of the kto nr: " + kto.length());
					}

					dtaStringBuffer.append(
						Utils.fillStringBuffer(kto, 9, '0', false));
				}

				/* adding address for */
				if (isFor) {
					// we take the date from the for- fields
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getForLine1(),
							lineLength,
							' ',
							true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getForLine2(),
							lineLength,
							' ',
							true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getForLine3(),
							lineLength,
							' ',
							true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(p.getForZip(), 5, ' ', true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getForCity(),
							lineLength - 5,
							' ',
							true));
				} else {
					// we take the date from the to- fields
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getToLine1(),
							lineLength,
							' ',
							true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getToLine2(),
							lineLength,
							' ',
							true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getToLine3(),
							lineLength,
							' ',
							true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(p.getToZip(), 5, ' ', true));
					dtaStringBuffer.append(
						Utils.fillStringBuffer(
							p.getToCity(),
							lineLength - 5,
							' ',
							true));
				}

				/* adding the refNr (blue) or the payment reason (red/bank) */
				if ((p.getPaymentType().intValue()
					== Config.PAYMENT_TYPE_RED)
					|| (p.getPaymentType().intValue()
						== Config.PAYMENT_TYPE_BANKCH))
					// red or bank payment slip
					{
					if ((p.getReason() != null)
						&& (!p.getReason().trim().equals(""))) {
						/* adding 04 */
						dtaStringBuffer.append("04");

						// under windows we have to do like this !!
						String reason = Utils.deleteChars(p.getReason(), '\n'
						/*System.getProperty("line.separator")*/
						);
						// perhaps this works for mac and linux
						reason =
							Utils.deleteSubstring(
								reason,
								System.getProperty("line.separator"));

						dtaStringBuffer.append(
							Utils.fillStringBuffer(reason, 126, ' ', true));
					}
				} else if (
					(p.getPaymentType().intValue()
						== Config.PAYMENT_TYPE_BLUE)
						|| (p.getPaymentType().intValue()
							== Config.PAYMENT_TYPE_ORANGE))
					// blue or orange payment slip
					{
					dtaStringBuffer.append(
						Utils.fillStringBuffer(refNr, 34, ' ', true));
				}
			}
		}

		// writing the total of all payments (type 890)
		Payment p = new Payment();
		p.setPaymentType(new Integer(0));
		UserAccount account = new UserAccount();
		paymentCount++;
		/* adding the payement header of the total*/
		tmp = makeHeader(p, account, paymentCount);
		if (tmp != null)
			dtaStringBuffer.append(tmp);
		else {
			logger.error("making header of the total record failed");
			throw new RuntimeException("making header of the total record failed");
		}
		/* round the sum of all payments to max 3 dezimal and add it to the file*/

		sum =
			(new BigDecimal(sum))
				.setScale(3, BigDecimal.ROUND_HALF_EVEN)
				.doubleValue();
		String total = "" + sum;
		total = total.replace('.', ',');
		dtaStringBuffer.append(Utils.fillStringBuffer(total, 75, ' ', true));

		try {
			FileWriter fw = new FileWriter(dtaFile);
			BufferedWriter bw = new BufferedWriter(fw);
			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,"US-ASCII"));
			bw.write(dtaStringBuffer.toString(), 0, dtaStringBuffer.length());
			bw.close();
			fw.close();
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e.getMessage());
		}
	}

	private static StringBuffer makeHeader(
		Payment p,
		UserAccount account,
		int paymentCount) {
		StringBuffer headerStringBuffer = new StringBuffer();
		/* adding 01 */
		headerStringBuffer.append("01");
		// adding the date, for IPI it has to be blank
		String d = Config.getInstance().convertToDbDateString(p.getExecDate());
		if((p.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI) || (p.getPaymentType().intValue() == Config.PAYMENT_TYPE_RED_IBAN)){
			d = "";
		}
		if (d.equals("")) {
			d = "0000-00-00";
		}
		headerStringBuffer.append(d.substring(2, 4));
		headerStringBuffer.append(d.substring(5, 7));
		headerStringBuffer.append(d.substring(8));
		/* adding the clearing to */
		headerStringBuffer.append(
			Utils.fillStringBuffer(p.getForClearing(), 12, ' ', true));
		/* adding ausgabe sequenz nummer (5 zeros) */
		headerStringBuffer.append(Utils.fillStringBuffer("", 5, '0', true));
		/* adding todays date */
		headerStringBuffer.append(
			("" + Calendar.getInstance().get(Calendar.YEAR)).substring(2));
		headerStringBuffer.append(
			Utils.fillStringBuffer(
				"" + (Calendar.getInstance().get(Calendar.MONTH) + 1),
				2,
				'0',
				false));
		headerStringBuffer.append(
			Utils.fillStringBuffer(
				"" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
				2,
				'0',
				false));
		/* adding clearing from */
		headerStringBuffer.append(
			Utils.fillStringBuffer(account.getClearing(), 7, ' ', true));
		/* adding the id string of the sender */
		headerStringBuffer.append("PAYIT");
		/* adding the payment count */
		headerStringBuffer.append(
			Utils.fillStringBuffer("" + paymentCount, 5, '0', false));
		/* adding the transaction type */
		if (p.getPaymentType().intValue() == 0) {
			headerStringBuffer.append("890"); // end of dta file (type 890)
		} else if (
			(p.getPaymentType().intValue() == Config.PAYMENT_TYPE_RED)
				|| (p.getPaymentType().intValue()
					== Config.PAYMENT_TYPE_BANKCH)) {
			headerStringBuffer.append("827"); // red payment and bank payment
		} else if (
			(p.getPaymentType().intValue() == Config.PAYMENT_TYPE_BLUE)
				|| (p.getPaymentType().intValue()
					== Config.PAYMENT_TYPE_ORANGE)) {
			headerStringBuffer.append("826"); // blue payment
		} else if (
			(p.getPaymentType().intValue() == Config.PAYMENT_TYPE_IPI) || (p.getPaymentType().intValue() == Config.PAYMENT_TYPE_RED_IBAN)) {
			headerStringBuffer.append("836"); // IPI payment or ES with IBAN
		} else {
			logger.error("illegal payment type:" + p.getPaymentType());
			return null;
		}
		/* adding the payment type (salary or not)*/
		if (p.getIsSalary().booleanValue())
			headerStringBuffer.append("1");
		else
			headerStringBuffer.append("0");
		/* adding a internal flag */
		headerStringBuffer.append("0");
		return headerStringBuffer;
	}
}
