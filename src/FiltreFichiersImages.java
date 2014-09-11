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

public class FiltreFichiersImages extends FileFilter {

	public boolean accept(File f) {
	    if (f.isDirectory()) {
	    	return true;
	    }

	    String extension = Outils.getExtension(f);
	    if (extension != null) {
		if (extension.equals(Outils.tiff) ||
		    extension.equals(Outils.tif) ||
		    extension.equals(Outils.gif) ||
		    extension.equals(Outils.jpeg) ||
		    extension.equals(Outils.jpg) ||
		    extension.equals(Outils.png)||
		    extension.equals(Outils.bmp)) {
		        return true;
		} else {
		    return false;
		}
	    }
	    return false;
	}

	public String getTypeDescription(File f) {
	    String extension = Outils.getExtension(f);
	    String type = null;

	    if (extension != null) {
	        if (extension.equals(Outils.jpeg) ||
	            extension.equals(Outils.jpg)) {
	            type = "Image JPEG";
	        } else if (extension.equals(Outils.gif)){
	            type = "Image GIF";
	        } else if (extension.equals(Outils.tiff) ||
	                   extension.equals(Outils.tif)) {
	            type = "Image TIFF";
	        } else if (extension.equals(Outils.png)){
	            type = "Image PNG";
	        } else if (extension.equals(Outils.bmp)){
	            type = "Image BMP";}
	    }
	    return type;
	}

	public String getDescription() {
        return "Images (*.jpeg, *.jpg, *.gif, *.tif, *.tiff, *.png, *.bmp)";
	}
}
