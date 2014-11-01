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
package amazed.game.users;

import java.awt.Color;
import java.awt.Point;
import java.util.Stack;

import amazed.Control;
import amazed.maze.LabyrintheAbstrait;


public class UtilisateurOrdinateur extends UtilisateurAbstrait {


	private boolean[][] carte;
	private int largeur, hauteur;
	private Stack<Point> parcours;

	public UtilisateurOrdinateur(int x, int y, Control control, String nom) {
		super(x, y, control, nom);
		LabyrintheAbstrait laby = control.getLaby();
		largeur = laby.getLargeur();
		hauteur = laby.getHauteur();
		carte = new boolean[largeur][hauteur];
		for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	carte[i][j] = false; 
		    }
	    }
	    parcours = new Stack<Point>();
		parcours.push(laby.getEntree());
	}
	
	public UtilisateurOrdinateur(int x, int y, Control control, Color couleur, String nom) {
		super(x, y, control, couleur, nom);
		LabyrintheAbstrait laby = control.getLaby();
		largeur = laby.getLargeur();
		hauteur = laby.getHauteur();
		carte = new boolean[largeur][hauteur];
		for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	carte[i][j] = false; 
		    }
	    }
	    Point nouveau = laby.getEntree();
	    parcours = new Stack<Point>();
	    parcours.push(nouveau);
	}
	
	public void avanceUtilisateur(){
	LabyrintheAbstrait laby = control.getLaby();
	Point nouveau = parcours.pop();
	x = nouveau.x;
	y = nouveau.y;
    if(!laby.getSortie().equals(nouveau)){
		parcours.push(nouveau);

		carte[nouveau.x][nouveau.y] = true;
		if(nouveau.y>0 && !carte[nouveau.x][nouveau.y-1] && laby.carte[nouveau.x][nouveau.y].getNord()) parcours.push(new Point(nouveau.x,nouveau.y-1)); //peut-on continuer au nord ?
		else if(nouveau.x>0 && !carte[nouveau.x-1][nouveau.y] && laby.carte[nouveau.x][nouveau.y].getOuest()) parcours.push(new Point(nouveau.x-1,nouveau.y));
		else if(nouveau.y<hauteur-1 && !carte[nouveau.x][nouveau.y+1] && laby.carte[nouveau.x][nouveau.y].getSud()) parcours.push(new Point(nouveau.x,nouveau.y+1));
		else if(nouveau.x<largeur-1 && !carte[nouveau.x+1][nouveau.y] && laby.carte[nouveau.x][nouveau.y].getEst()) parcours.push(new Point(nouveau.x+1,nouveau.y));
		else parcours.pop();
	}
	}

}