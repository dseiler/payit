/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.model;

import org.apache.log4j.Logger;

public class Utils {
	static Logger logger = Logger.getLogger(ch.truesolutions.payit.model.Payment.class);

	public static StringBuffer fillStringBuffer(
		String s,
		int newLength,
		char fillSign,
		boolean isLeftOriented) {
		String source = (s == null) ? "" : s;
		int l = source.length();

		StringBuffer dest = new StringBuffer();

		while (dest.length() < (newLength - l)) {
			dest.append(fillSign);
		}

		if (isLeftOriented) {
			dest.insert(0, source);
		} else {
			dest.append(source);
		}

		dest.setLength(newLength);
		return dest;
	}

	public static String deleteChars(String source, int c) {
		int i = source.indexOf(c);
		StringBuffer sb = new StringBuffer(source);
		while (i != -1) {
			sb = sb.deleteCharAt(i);
			i = sb.toString().indexOf(c);
		}
		return sb.toString();
	}

	public static String deleteSubstring(String source, String substr) {
		int i = source.indexOf(substr);
		int l = substr.length();
		StringBuffer sb = new StringBuffer(source);
		while (i != -1) {
			sb = sb.delete(i, i + l);
			i = sb.toString().indexOf(substr);
		}
		return sb.toString();
	}

	public static String formatPostKtoNr(String pcnr, boolean isBlue) {
		String newPcnr = null;
		if (pcnr != null) {
			int i = pcnr.indexOf('-');
			if (i == -1
				&& (pcnr.length() <= 5)
				&& isBlue) // only for the blue slip
				{
				newPcnr = fillStringBuffer(pcnr, 5, '0', false).toString();
				//source.setText(newPcnr);
			} else {
				newPcnr = deleteChars(pcnr, '-');
				if (newPcnr.length() > 3) {
					String a = newPcnr.substring(0, 2);
					String b = newPcnr.substring(2, newPcnr.length() - 1);
					String c = newPcnr.substring(newPcnr.length() - 1);
					b = fillStringBuffer(b, 6, '0', false).toString();
					newPcnr = a + "-" + b + "-" + c;
					//source.setText(newPcnr);
				}
			}
		}
		return newPcnr;
	}

	public static String addSuffix(String source, String suf) {
		String result = "";
		int ind = source.indexOf('.');
		if (ind == -1) {
			result = source + "." + suf;
		} else {
			result = source.substring(0, ind + 1) + suf;
		}
		return result;
	}
	
	/**
	 * method for separating a number into two strings, one before
	 * the comma and one after the comma
	 * @param nr
	 * @return
	 */
	public static String[] separateNumberByComma(Double nr) {
		String[] result = {"",""};
		
		if (nr != null) {
			double d = nr.doubleValue();
			String dStr = Double.toString(d);
			int i = dStr.indexOf('.');
			String beforeComma;
			String afterComma;
			if (i != -1) {
				beforeComma = dStr.substring(0, i);
				afterComma =
					(fillStringBuffer(dStr.substring(i + 1),2,'0',true)).toString();
			} else {
				beforeComma = dStr;
				afterComma = "00";
			}
			result[0] = beforeComma;
			result[1] = afterComma;
		}
		return result;
		
		/* alternative
		 	double d = nr.doubleValue();
			int beforeComma = (int) d;
			int afterComma = (int) ((d * 100) % (beforeComma * 100));
			result[0] = Integer.toString(beforeComma);
			result[1] = Integer.toString(afterComma);

		 */
	}

	/**
	 * Method for calculating the control number accourding to the Modulo 10 Rekursiv rule
	 */
	public static int calculateCtrlNumberMod10Rek(int[] nr) {
		// building the calculation matrix and the ctrl number array

		/*0 1 2 3 4 5 6 7 8 9*/
		int[][] matrix = { /*0*/ { 0, 9, 4, 6, 8, 2, 7, 1, 3, 5 },
			/*1*/ {
				9, 4, 6, 8, 2, 7, 1, 3, 5, 0 },
			/*2*/ {
				4, 6, 8, 2, 7, 1, 3, 5, 0, 9 },
			/*3*/ {
				6, 8, 2, 7, 1, 3, 5, 0, 9, 4 },
			/*4*/ {
				8, 2, 7, 1, 3, 5, 0, 9, 4, 6 },
			/*5*/ {
				2, 7, 1, 3, 5, 0, 9, 4, 6, 8 },
			/*6*/ {
				7, 1, 3, 5, 0, 9, 4, 6, 8, 2 },
			/*7*/ {
				1, 3, 5, 0, 9, 4, 6, 8, 2, 7 },
			/*8*/ {
				3, 5, 0, 9, 4, 6, 8, 2, 7, 1 },
			/*9*/ {
				5, 0, 9, 4, 6, 8, 2, 7, 1, 3 }
		};

		int[] ctrlNr = { 0, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

		// go through all digits of the number an calulate the next row in the matrix
		int row = 0;
		for (int i = 0; i < nr.length; i++) {
			row = matrix[row][nr[i]];
		}

		// with the last value of the row we can determine the ctrl number
		return ctrlNr[row];
	}

	public static boolean validateIbanCtrlNumberMod9710(String nr) {
		if(nr == null || "".equals(nr)){
			return false;
		}
		// get the ctrl number
		String pzString = nr.substring(2, 4);
		int pz = 0;
		try {
			pz = new Integer(pzString).intValue();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		// compare the number with the calculated number
		if (pz == calculateIbanCtrlNumberMod9710(nr)) {
			return true;
		} else {
			return false;
		}
	}

	public static int calculateIbanCtrlNumberMod9710(String nr) {
		// move the 4 first characters at the end of the iban
		String pzString = nr.substring(2, 4);
		int pz = 0;
		try {
			pz = new Integer(pzString).intValue();
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
		logger.info("read ctrl number=" + pz);
		String tmp = nr.substring(4) + nr.substring(0, 4);
		char[] charArray = tmp.toCharArray();
		int alphaCount = 0;
		for (int i = 0; i < charArray.length; i++) {
			int k = ((int) (charArray[i]));
			// check if we have an alphanumeric value (A-Z = 65-90)
			if ((k >= 65) && (k <= 90)) {
				k = k - 55; // A-Z = 10 - 35
				int end = i + alphaCount;
				int start = end + 1;
				tmp =
					tmp.substring(0, end)
						+ Integer.toString(k)
						+ tmp.substring(start);
				alphaCount++;
			} else if (!((k >= 48) && (k <= 57))) {
				logger.error("illegal character");
				return -1;
			}
		}

		// try to convert this string to a long
		int result = 0;
		try {
			java.math.BigInteger bigInt = new java.math.BigInteger(tmp);
			result = bigInt.mod(java.math.BigInteger.valueOf(97)).intValue();
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
		if (result == 1) {
			logger.info("given ctrl number is correct=" + pz);
			return pz; // ctrl number was correct
		} else {
			// we have to calculate the correct ctrl number
			// replace the wrong ctrl number with 00
			String tmp2 = tmp.substring(0, tmp.length() - 2) + "00";
			try {
				java.math.BigInteger bigInt = new java.math.BigInteger(tmp2);
				result =
					98
						- (bigInt
							.mod(java.math.BigInteger.valueOf(97))
							.intValue());
			} catch (Exception e) {
				logger.error(e);
				return -1;
			}
			logger.info("calculated ctrl number=" + result);
			return result;
		}
	}

	public static boolean validateReasonCtrlNumberMod9710(String nr) {
		if(nr == null || "".equals(nr)){
			return false;
		}
		// get the ctrl number
		String pzString = nr.substring(0, 2);
		int pz = 0;
		try {
			pz = new Integer(pzString).intValue();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		// compare the number with the calculated number
		if (pz == calculateReasonCtrlNumberMod9710(nr)) {
			return true;
		} else {
			return false;
		}
	}

	public static int calculateReasonCtrlNumberMod9710(String nr) {
		// first we delete all the spaces
		nr = deleteChars(nr, ' ');
		// we have to chop of the ctrl number
		nr = nr.substring(2);

		// we convert all the non numerical characters to numeric values
		char[] charArray = nr.toCharArray();
		int alphaCount = 0;
		for (int i = 0; i < charArray.length; i++) {
			int k = ((int) (charArray[i]));
			// check if we have an alphanumeric value (A-Z = 65-90)
			if ((k >= 65) && (k <= 90)) {
				k = k - 55; // A-Z = 10 - 35
				int end = i + alphaCount;
				int start = end + 1;
				nr =
					nr.substring(0, end)
						+ Integer.toString(k)
						+ nr.substring(start);
				alphaCount++;
			} else if (!((k >= 48) && (k <= 57))) {
				logger.error("illegal character");
				return -1;
			}
		}
		// we add to zeros
		nr += "00";

		// try to convert this string to a long
		int result = 0;
		try {
			java.math.BigInteger bigInt = new java.math.BigInteger(nr);
			result =
				98 - (bigInt.mod(java.math.BigInteger.valueOf(97)).intValue());
		} catch (Exception e) {
			logger.error(e);
			return -1;
		}
		logger.info("calculated ctrl number=" + result);
		return result;
	}
/*
	public static void main(String[] args) {
		System.out.println(
			"ctrl nr of 5400 0005 6781 2F48 K012:"
				+ Utils.calculateReasonCtrlNumberMod9710(
					"5400 0005 6781 2F48 K012"));
		if (Utils
			.validateReasonCtrlNumberMod9710("5400 0005 6781 2F48 K012")) {
			System.out.println("ctrl number is correct!");
		} else {
			System.out.println("ctrl nr is not correct !");
		}
		
		Utils.calculateIbanCtrlNumberMod9710("CH6500700113700041646");
		Utils.calculateIbanCtrlNumberMod9710("CH11002300A1023502601");
		Utils.calculateIbanCtrlNumberMod9710("AZ09");
		
	}
*/
}