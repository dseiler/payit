/**
 * Title:        PayIT<p>
 * Description:  Software for processing e-banking tasks offline<p>
 * Copyright:    Copyright (c) Daniel Seiler<p>
 * Company:      Truesolutions<p>
 * @author Daniel Seiler
 * @version 1.0
 */

package ch.truesolutions.payit.view.swing;

import java.io.File;
import javax.swing.filechooser.*;

import ch.truesolutions.payit.model.Config;

public class DtaFileFilter extends FileFilter {
    
    // Accept only .dta and directories files
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        // get file extension
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        
	if (ext != null) {
            if (ext.equals("dta")) {
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }
    
    // The description of this filter
    public String getDescription() {
        return Config.getInstance().getText("filechooser.dtafilefilter.text");
    }
}
