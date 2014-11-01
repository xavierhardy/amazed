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

import java.awt.Point;


public abstract class LabyrintheAbstrait {
	//Coordonn�es de l'entr�e
	protected int entreeX = 0;
	protected int entreeY = 0;
	//Coordonn�es de la sortie
	protected int sortieX = 1;
	protected int sortieY = 1;
	protected int largeur;
	protected int hauteur;
	public Case[][] carte;

	
    public LabyrintheAbstrait(int largeur, int hauteur, Case[][] carte) {
	    this.largeur = largeur ;
	    this.hauteur = hauteur ;
	    this.carte = new Case[largeur][hauteur];
	    for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	this.carte[i][j] = carte[i][j].clone();
		    }
	    }
		setEntreeHasard();
		setSortieHasard();
    }
    
	
    public LabyrintheAbstrait(int largeur, int hauteur) {
	    this.largeur = largeur ;
	    this.hauteur = hauteur ;
    }

    public Point getEntree(){
    	return new Point(entreeX,entreeY);
    }

    public Point getSortie(){
    	return new Point(sortieX,sortieY);
    }

    public void setEntree(int x, int y){
      	entreeX = x;
    	entreeY = y;
    }

    public void setSortie(int x, int y){
    	sortieX = x;
    	sortieY = y;
    }

    public void initEntreeSortie(){
    	int x = 0,y = 0;
		
		//INITIALISATION DE L'ENTREE ET DE LA SORTIE DU LABYRINTHE
		
		while(!carte[x][y].dansLaby() && x < largeur && y < hauteur){
			x++;
			y++;
			System.out.println(x + " " + y);
		}
		if(x == largeur || y == hauteur){ //Si on sort du labyrinthe, c'est que la diagonale n'appartient pas au labyrinthe
			setEntreeHasard();
		}
		else {
			setEntree(x,y);
		}
		
		x = largeur - 1;
		y = hauteur - 1;
		while(!carte[x][y].dansLaby() && x >= 0 && y >= 0){
			x--;
			y--;
		}
		if(x < 0 || y < 0 || (x == getEntree().x && y == getEntree().y)){
			//Si on est sorti par la gauche ou le haut OU
			//Si il n'y a qu'un point sur la diagonale, entr�e et sortie sont confondues
			setSortieHasard();
		}
		else{
			setSortie(x,y);
		}
    }
    
    public void setEntreeHasard(){
	    entreeX = (int)(largeur*Math.random());
	    entreeY = (int)(hauteur*Math.random());
      	while((entreeX == sortieX && entreeY == sortieY) || !carte[entreeX][entreeY].dansLaby()){
    	    entreeX = (int)(largeur*Math.random());
    	    entreeY = (int)(hauteur*Math.random());
      	}
    }

    public void setSortieHasard(){
	    sortieX = (int)(largeur*Math.random());
	    sortieY = (int)(hauteur*Math.random());
      	while((entreeX == sortieX && entreeY == sortieY) || !carte[sortieX][sortieY].dansLaby()){
    	    sortieX = (int)(largeur*Math.random());
    	    sortieY = (int)(hauteur*Math.random());
      	}
    }
    
    
	public int getHauteur() {
		return hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}

	public boolean isEntree(int x, int y){
		return entreeX == x && entreeY == y;
	}

	public boolean isSortie(int x, int y){
		return sortieX == x && sortieY == y;
	}
		
	abstract public void creuser(int x, int y, int dir);

	abstract public CaseAbstraite getCase(int x, int y);
}
