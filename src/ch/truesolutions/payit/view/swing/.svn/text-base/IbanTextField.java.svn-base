package ch.truesolutions.payit.view.swing;

public class IbanTextField extends LimitedTextField {
	public IbanTextField(int maxSigns) {
		super(maxSigns);
	}

	protected boolean validateChar(char c) {
		int k = c;
		if (/*a-z*/((k >= 97) && (k <= 122)) || /*A-Z*/((k >= 65) && (k <= 90)) || /*0-9*/((k >= 48) && (k <= 57))) {
			return true;
		} else {
			return false;
		}
	}
	
	protected char modifyChar(char c){
		String s = String.valueOf(c);
		s = s.toUpperCase();
		return s.charAt(0);
	}
}
