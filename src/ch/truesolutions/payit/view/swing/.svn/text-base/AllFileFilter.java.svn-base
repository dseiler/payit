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

public class AllFileFilter extends FileFilter {
    
    // Accept everything
    public boolean accept(File f) {
        return true;
    }
    
    // The description of this filter
    public String getDescription() {
        return Config.getInstance().getText("filechooser.allfilefilter.text");
    }
}
