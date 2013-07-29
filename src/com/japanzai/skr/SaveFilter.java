package com.japanzai.skr;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SaveFilter extends FileFilter {
	
	private final String[] extensions = {".sks"};
	
    public boolean accept(File file) {
    	
        String filename = file.getName();
        
        return (isSupported(filename) || file.isDirectory());
        
    }
    
    private boolean isSupported(String suffix){
    	for (String s : extensions){
    		if (suffix.endsWith(s)){
    			return true;
    		}
    	}
    	return false;
    }
    
    public String getDescription() {
        return "*.sks";
    }
}
