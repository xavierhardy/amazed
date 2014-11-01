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
package amazed.gui.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Vector;

import amazed.Control;
import amazed.game.users.UtilisateurAbstrait;
import amazed.gui.Fenetre;
import amazed.maze.Chemin;
import amazed.maze.CheminAbstrait;
import amazed.maze.Labyrinthe;
import amazed.maze.LabyrintheAbstrait;

public class Dessin implements InterfaceDessin{
	private Fenetre fenetre;
	private Control control;
	private InterfaceAffichageLaby affichage;
	
	public Dessin(){
		control = new Control(this);
		affichage = new AffichageLaby();
		fenetre = new Fenetre(control);
	}
	
	public void afficherMessage(String message) {
		fenetre.afficherMessage(message);
	}
	
    public void interdireDessin(){
    	fenetre.getPanneau().interdireDessin();
    }
    
    public void autoriserDessin(){
    	fenetre.getPanneau().autoriserDessin();
    }

	public void afficherTemps(int secondes) {
		fenetre.afficherTemps(secondes);
	}

	public int getHauteurDessin() {
		return fenetre.getHauteurPanneau() - 60;
	}
	
	public void initTampon(){
		fenetre.initTampon();
	}

	public void dessineUtilisateur(Graphics g, UtilisateurAbstrait u) {
		affichage.dessineUtilisateur(g,u);
	}
	
	public int getTailleCase() {
		return affichage.getTailleCase();
	}
	
	public void setTailleCase(int tailleCase) {
		affichage.setTailleCase(tailleCase);
	}

	public int getLargeurAffichage(int largeur) {
		return affichage.getLargeur(largeur);
	}
	
	public int getHauteurAffichage(int hauteur) {
		return affichage.getHauteur(hauteur);
	}

	public void dessineLabyrinthe(Graphics g) {
		affichage.dessineLabyrinthe(g);
	}

	public void sauverTampon(LabyrintheAbstrait laby) {
		affichage.sauverTampon((Labyrinthe)laby);
	}

	public BufferedImage getLabyTampon() {
		return affichage.getLabyTampon();
	}

	public void dessine(Graphics g, int temps, Vector<UtilisateurAbstrait> utilisateurs, CheminAbstrait chemin) {
		Iterator<UtilisateurAbstrait> iter = utilisateurs.iterator();
		UtilisateurAbstrait next;
		while(iter.hasNext()){
			next = iter.next();
			if(!next.estArrive()){
				next.dessine(g);
				if(next.aDemandeAide()){
					affichage.dessineAide(g, (Chemin)chemin, next.indexAide(), next.getCouleur());
				}
			}
		}
	}
	
	public void dessineSolution(Graphics g, CheminAbstrait chemin, LabyrintheAbstrait laby) {
		affichage.dessineSolution(g, (Chemin)chemin, (Labyrinthe)laby);
	}
}
