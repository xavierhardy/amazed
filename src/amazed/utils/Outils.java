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
package amazed.utils;

import java.io.File;

public class Outils {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String bmp = "bmp";
    public final static String laby = "laby";

    public static String getExtension(File fichier) {
        String extension = null;
        String nom = fichier.getName();
        int i = nom.lastIndexOf('.');

        if (i > 0 &&  i < nom.length() - 1) {
            extension = nom.substring(i+1).toLowerCase();
        }
        return extension;
    }
    
    public static String presenterTemps(int secondes) { //Pr�sente le temps en secondes en M:SS
    	int nbSecondes = secondes % 60;
    	String chaineSecondes;
    	if(nbSecondes < 10){
    		chaineSecondes = "0" + Integer.toString(nbSecondes);
    	}
    	else {
    		chaineSecondes = Integer.toString(nbSecondes);
    	}
    	
    	int nbMinutes = secondes / 60;
    	String chaineMinutes = Integer.toString(nbMinutes);
    	return chaineMinutes + ":" + chaineSecondes;
    }
}
