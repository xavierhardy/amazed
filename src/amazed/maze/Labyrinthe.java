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
package amazed.maze;

public class Labyrinthe extends LabyrintheAbstrait {
		
	public Labyrinthe(int largeur, int hauteur, Case[][] carte){
		super(largeur,hauteur,carte);
	}
	
	public Labyrinthe(Forme forme){
		super(forme.getLargeur(),forme.getHauteur());
		carte = new Case[largeur][hauteur];
	    for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	carte[i][j] = new Case(forme.carte[i][j]);
		    }
	    }
		setEntreeHasard();
		setSortieHasard();
	}
	
	public Labyrinthe(int largeur, int hauteur){
		super(largeur,hauteur);
		this.carte = new Case[largeur][hauteur];
	    for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	this.carte[i][j] = new Case(); 
		    }
	    }
		setEntreeHasard();
		setSortieHasard();
	}

	public void creuser(int x, int y, int dir) { //On suppose que l'on essaye pas de creuser l� o� on ne peut pas, pour r�duire le nombre de tests
		switch(dir){
			case 0: carte[x][y].setOuest(true); carte[x-1][y].setEst(true); break;
			case 1: carte[x][y].setNord(true); carte[x][y-1].setSud(true); break;
			case 2: carte[x][y].setEst(true); carte[x+1][y].setOuest(true); break;
			default: carte[x][y].setSud(true); carte[x][y+1].setNord(true); break;
		}
	}

	public Case getCase(int x, int y) {
		return carte[x][y];
	}
}
