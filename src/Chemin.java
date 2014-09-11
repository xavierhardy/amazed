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

import java.awt.Point;
import java.util.Stack;
import java.util.Vector;

public class Chemin extends CheminAbstrait {
	private Vector<Point> chemin;
	private boolean[][] carte; //Cette case a-t-elle été visitée ? true: visitée
	
	public Chemin (LabyrintheAbstrait laby){
		super(laby);
		int largeur = laby.getLargeur(), hauteur = laby.getHauteur();
		carte = new boolean[largeur][hauteur];
		for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	carte[i][j] = false; 
		    }
	    }
		
	    Point nouveau = laby.getEntree();
	    Stack<Point> suivants = new Stack<Point>();
		while(laby.getSortie().x != nouveau.x || laby.getSortie().y != nouveau.y){
			suivants.push(nouveau);
			carte[nouveau.x][nouveau.y] = true;
			if(nouveau.y>0 && !carte[nouveau.x][nouveau.y-1] && laby.carte[nouveau.x][nouveau.y].getNord()) suivants.push(new Point(nouveau.x,nouveau.y-1)); //peut-on continuer au nord ?
			else if(nouveau.x>0 && !carte[nouveau.x-1][nouveau.y] && laby.carte[nouveau.x][nouveau.y].getOuest()) suivants.push(new Point(nouveau.x-1,nouveau.y));
			else if(nouveau.y<hauteur-1 && !carte[nouveau.x][nouveau.y+1] && laby.carte[nouveau.x][nouveau.y].getSud()) suivants.push(new Point(nouveau.x,nouveau.y+1));
			else if(nouveau.x<largeur-1 && !carte[nouveau.x+1][nouveau.y] && laby.carte[nouveau.x][nouveau.y].getEst()) suivants.push(new Point(nouveau.x+1,nouveau.y));
			else suivants.pop();
			nouveau = suivants.pop();
		}
		
		chemin = new Vector<Point>();
		
		while(!suivants.isEmpty()){
			nouveau = suivants.pop();
			if(nouveau.x != laby.getEntree().x || nouveau.y != laby.getEntree().y){
				chemin.add(nouveau);
			}
		}
	}

	public int size() {
		return chemin.size();
	}

	public Point get(int i) {
		return chemin.get(i);
	}

	public int getPointHasard() {
		//On veut que ce ne soit ni l'entrée ni la sortie
		return (int)((chemin.size()-2)*Math.random())+1;
	}

	public Point getPoint(int i) {
		return chemin.elementAt(i);
	}
}
