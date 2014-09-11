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

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class Forme extends FormeAbstraite {
	public boolean[][] carte;
	
	public Forme(int largeur, int hauteur){ //Construit une forme pleine, un rectangle de dimensions largeur x hauteur
		super(largeur,hauteur);
		carte = new boolean[largeur][hauteur];
	    for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	carte[i][j] = false;
		    }
	    }
	}
	
	public Forme(BufferedImage img){ //Construit une forme à partir d'une image, couleur transparente: le blanc
		super(img.getWidth(),img.getHeight());
		carte = new boolean[largeur][hauteur];
		boolean[][] carteVisitee = new boolean[largeur][hauteur]; //Sert à vérifier la connexité
		int nombreCases = 0;
		boolean caseEnDehors;
	    for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	caseEnDehors = img.getRGB(i, j) == Color.white.getRGB();
		    	carte[i][j] = caseEnDehors;
		    	carteVisitee[i][j] = caseEnDehors;
		    	if(!caseEnDehors) nombreCases++;
		    }
	    }

	    
	    //Cette forme est-elle connexe ?
	    int x=0,y=0;
	    while(carteVisitee[x][y] == true){ //Tant qu'on ne se trouve pas dans la forme
	    	x = (int)(largeur*Math.random());
		    y = (int)(hauteur*Math.random());
	    }
	    
	    Stack<Point> parcours = new Stack<Point>();
	    parcours.add(new Point(x,y));
	    Point suivant;
	    while(!parcours.isEmpty()){
	    	suivant = parcours.pop();
	    	x = suivant.x;
	    	y = suivant.y;
	    	if(!carteVisitee[x][y]){
	    		nombreCases--;
		    	carteVisitee[x][y] = true;
	    	}
			if(y>0 && !carteVisitee[x][y-1]) parcours.add(new Point(x,y-1)); //doit-on continuer au nord ?
			if(x>0 && !carteVisitee[x-1][y]) parcours.add(new Point(x-1,y));
			if(y<hauteur-1 && !carteVisitee[x][y+1]) parcours.add(new Point(x,y+1));
			if(x<largeur-1 && !carteVisitee[x+1][y]) parcours.add(new Point(x+1,y));
	    }
	    
	    //Quand l'algorithme s'arrête si le nombre de cases est nul alors la forme est connexe, sinon elle ne l'est pas
	    if(nombreCases > 0){
	    	System.out.println("Forme non connexe, utilisation de la forme pleine à la place");
	    	for(int i=0;i<largeur;i++){
			    for(int j=0;j<hauteur;j++){
			    	carte[i][j] = false;
			    }
		    }
	    }
	}
	

	
}