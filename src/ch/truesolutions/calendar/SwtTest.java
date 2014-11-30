/*
 * Created on 11.11.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package ch.truesolutions.calendar;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Daniel
 */
public class SwtTest {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell();
		shell.setLayout(new RowLayout());
		shell.setText("SWT Application");
		shell.open();
		shell.pack();
		new EasyDatePickerSWT(shell, null).show();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}
