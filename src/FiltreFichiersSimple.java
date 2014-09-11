/*
The MIT License (MIT)

Copyright (c) 2014 Xavier Hardy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltreFichiersSimple extends FileFilter { //Filtre pour un type de fichier avec au max 2 extensions différentes
	private String extension1;
	private String extension2 = "";
	private boolean extensionUnique = true;
	private String descriptionType;
	private String description;
	
	public FiltreFichiersSimple (String extension, String descriptionType, String description){
		super();
		extension1 = extension;
		this.descriptionType = descriptionType;
		this.description = description;
	}
	
	public FiltreFichiersSimple (String extension1, String extension2, String descriptionType, String description){
		super();
		extensionUnique = false;
		this.extension1 = extension1;
		this.extension2 = extension2;
		this.descriptionType = descriptionType;
		this.description = description;
	}
	
	private boolean isValidExtension(String extension){
		return extension != null && (extension.equals(extension1) || (!extensionUnique && extension.equals(extension2)));
	}
	
	public boolean accept(File f) {
	    if (f.isDirectory()) {
	    	return true;
	    }

	    String extension = Outils.getExtension(f);
		if (isValidExtension(extension)) {
		        return true;
		}
		
	    return false;
	}

	public String getTypeDescription(File f) {
	    String extension = Outils.getExtension(f);

	    if (isValidExtension(extension)){
	    	return descriptionType;
	    }
	    return null;
	}

	public String getDescription() {
        return description;
	}
}
