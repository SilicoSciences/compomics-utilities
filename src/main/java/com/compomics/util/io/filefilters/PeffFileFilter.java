package com.compomics.util.io.filefilters;

import java.io.File;
import javax.swing.filechooser.*;

/**
 * File filter for *.peff files.
 *
 * @author  Harald Barsnes
 */
public class PeffFileFilter extends FileFilter {
    
    /**
     * Accept all directories, *.peff files.
     *
     * @param f
     * @return boolean
     */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = FileFilterUtils.getExtension(f);
        if (extension != null) {
            if (extension.equals(FileFilterUtils.peff)
                    || extension.equals(FileFilterUtils.PEFF)){
                return true;
            }
            else {
                return false;
            }
        }        
        return false;
    }
    
    /**
     * The description of this filter
     *
     * @return String
     */
    public java.lang.String getDescription() {
        return "*.peff";
    }
}