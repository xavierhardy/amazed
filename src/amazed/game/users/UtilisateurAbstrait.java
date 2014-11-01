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
import java.awt.Graphics;

import amazed.Control;
import amazed.maze.LabyrintheAbstrait;


public abstract class UtilisateurAbstrait {
	protected int x;
	protected int y;
	protected int xc; //Coordonn�es du curseur (souris, clavier, d�pend de l'utilisateur)
	protected int yc; 
	protected boolean sens = true;  //Si vrai alors le joueur va de l'entr�e � la sortie sinon l'inverse
	protected Control control;
	protected boolean avance = false;
	protected Color couleur = Color.red;
	protected String nom = "";
	protected boolean estArrive = false;
	protected int aDemandeAide = 0;
	
	public UtilisateurAbstrait(int x, int y, Control control, String nom){
		this.x = x;
		this.y = y;
		this.xc = x;
		this.yc = y;
		this.control = control;
		this.nom = nom;
	}
	
	public UtilisateurAbstrait(int x, int y, Control control, Color couleur, String nom){
		this.x = x;
		this.y = y;
		this.xc = x;
		this.yc = y;
		this.couleur = couleur;
		this.control = control;
		this.nom = nom;
	}
	
	public void souris(int bouton){}
	public void lacheSouris(int bouton){}
	public void changeSens(){
		sens = !sens;
	}
	
	public boolean getSens(){
		return sens;
	}
	
	public String getNom(){
		return nom;
	}
	
	public void clavier(int key){}
	public void lacheClavier(int key){}
	
	public void avanceUtilisateur(){
		if(avance){
			LabyrintheAbstrait laby = control.getLaby(); 
			if(xc > x && x < laby.getLargeur() - 1 && laby.carte[x][y].getEst()) x++;
			else if(xc < x && x > 0 && laby.carte[x][y].getOuest()) x--;
			else if(yc > y && y < laby.getHauteur() - 1 && laby.carte[x][y].getSud()) y++;
			else if(yc < y && y > 0 && laby.carte[x][y].getNord()) y--;
		}
	}	
	
	public void dessine(Graphics g){
		control.getDessin().dessineUtilisateur(g,this);
	}
	
	public void coordonneesCurseur(int xc, int yc){}
	
	public boolean estJoueur(){
		return false;
	}
	
	public boolean estArrive(){
		return estArrive;
	}
	
	public void arrive(){
		estArrive = true;
	}

	public Color getCouleur() {
		return couleur;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void demanderAide(){
		if(aDemandeAide == 0){
			aDemandeAide = control.triche();
		}
	}

	public boolean aDemandeAide() {
		return aDemandeAide > 0;
	}

	public int indexAide() {
		return aDemandeAide;
	}
	
}
