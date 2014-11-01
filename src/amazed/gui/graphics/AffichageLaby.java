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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import amazed.game.users.UtilisateurAbstrait;
import amazed.maze.Case;
import amazed.maze.Chemin;
import amazed.maze.Labyrinthe;


public class AffichageLaby implements InterfaceAffichageLaby {

	private int tailleCase;
	private BufferedImage imgLaby; //On sauvegarde l'image du labyrinthe pour pouvoir l'afficher tr�s rapidement
	
	public AffichageLaby(){
		tailleCase = 35;
	}
	
	public void dessineUtilisateur(Graphics g, UtilisateurAbstrait u) {
		g.setColor(u.getCouleur());
		g.fillOval(tailleCase*u.getX(),tailleCase*u.getY(),tailleCase,tailleCase);
	}
	
	public int getTailleCase(){
		return tailleCase;
	}
	
	public void setTailleCase(int tailleCase){
		this.tailleCase = tailleCase;
	}

	public int getLargeur(int largeur) {
		return largeur*tailleCase+1;
	}
	
	public int getHauteur(int hauteur) {
		return hauteur*tailleCase+1;
	}

	public void dessineLabyrinthe(Graphics g) {
		g.drawImage(imgLaby, 0, 0, null);
	}
	
	public void sauverTampon(Labyrinthe laby) {
		int x = 0,y = 0;
		
		imgLaby = new BufferedImage(laby.getLargeur()*tailleCase+1, laby.getHauteur()*tailleCase+1, BufferedImage.TYPE_INT_ARGB);
    	Graphics g = imgLaby.createGraphics();
    	g.setColor(Color.white);
    	g.fillRect(0, 0, imgLaby.getWidth(), imgLaby.getHeight());
    
    	g.setColor(Color.cyan);
    	g.fillRect(laby.getEntree().x*tailleCase, laby.getEntree().y*tailleCase, tailleCase, tailleCase);
    	g.setColor(Color.green);
    	g.fillRect(laby.getSortie().x*tailleCase, laby.getSortie().y*tailleCase, tailleCase, tailleCase);
    	for(x=0; x < laby.getLargeur(); x++){
    		for(y=0; y < laby.getHauteur(); y++){
    			dessineCase(g,laby, x,y);
    		}
    	}
    }
	

	public BufferedImage getLabyTampon() {
		return imgLaby;
	}
	
	public void dessineSolution(Graphics g, Chemin chemin, Labyrinthe laby) {
		int n = chemin.size();
		Point nouveau;
		for(int i = 0; i<n;i++){
			nouveau = chemin.get(i);
			g.setColor(Color.red);
			g.fillRect(tailleCase*nouveau.x, tailleCase*nouveau.y, tailleCase+1, tailleCase+1);
			dessineCase(g, laby, nouveau.x, nouveau.y);
		}
	}
	
	public void dessineCase(Graphics g, Labyrinthe laby, int x, int y){
		Case caseXY =  laby.getCase(x,y);
		int xEcran = x*tailleCase, yEcran = y*tailleCase;
		if(caseXY.dansLaby()){
			g.setColor(Color.black); //Couleur du pinceau pour dessiner les cases
			
			if(!caseXY.getNord()) g.drawLine(xEcran, yEcran, xEcran+tailleCase,yEcran);
			
			if(!caseXY.getSud()) g.drawLine(xEcran, yEcran+tailleCase, xEcran+tailleCase, yEcran+tailleCase);
			
			if(!caseXY.getEst()) g.drawLine(xEcran+tailleCase, yEcran, xEcran+tailleCase, yEcran+tailleCase);
			
			if(!caseXY.getOuest()) g.drawLine(xEcran, yEcran, xEcran, yEcran+tailleCase);
		}
	}

	public void dessineAide(Graphics g, Chemin chemin, int i, Color couleur) {
		Point aide = chemin.getPoint(i);
		int x = aide.x * tailleCase, y = aide.y * tailleCase;
		g.setColor(couleur);
		int[] xpoints = {x,x+tailleCase/2,x+tailleCase,x+tailleCase/2};
		int[] ypoints = {y+tailleCase/2,y,y+tailleCase/2,y+tailleCase};
		g.fillPolygon(new Polygon(xpoints,ypoints,4));
	}
}


