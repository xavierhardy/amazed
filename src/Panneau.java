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

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Panneau extends Canvas implements MouseListener, KeyListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Control control;
    private boolean autoriserADessiner = true;
    private int attente = 33;
    private BufferedImage tamponAffichage;
    private Graphics affichageSurTampon;
    private int xImage = 0;
    private int yImage = 0;    
	    
    public Panneau(Control control){
    	this.control = control;
    	tamponAffichage = new BufferedImage(200,200, BufferedImage.TYPE_INT_ARGB);
    	affichageSurTampon = tamponAffichage.createGraphics();
    }
    
	    public void update(Graphics g) {
	    	paint(g);
	    }
	    
	    public void initTampon(){
	    	InterfaceDessin dessin = control.getDessin();
	    	LabyrintheAbstrait laby = control.getLaby();
	    	tamponAffichage = new BufferedImage(dessin.getLargeurAffichage(laby.getLargeur()), dessin.getHauteurAffichage(laby.getHauteur()), BufferedImage.TYPE_INT_ARGB);
	    	affichageSurTampon = tamponAffichage.createGraphics();
	    }
	    
	    public void paint(Graphics g) {
	    	if(autoriserADessiner) {//On veut éviter de dessiner et lire sur un même tampon en même temps
	    		long time = System.currentTimeMillis();
	    		InterfaceDessin dessin = control.getDessin();
	    		
		    	xImage = (this.getWidth()-tamponAffichage.getWidth())/2;
		    	yImage = (this.getHeight()-tamponAffichage.getHeight())/2;
		    	
		    	int x = MouseInfo.getPointerInfo().getLocation().x -  getLocationOnScreen().x - xImage;
		    	int y = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y - yImage;
		    	
		    	control.calcul(x,y);
		    	
		    	dessin.dessineLabyrinthe(affichageSurTampon);
				if(control.estAfficheeSolution()){
					dessin.dessineSolution(affichageSurTampon, control.getChemin(), control.getLaby());
				}
		    	dessin.dessine(affichageSurTampon,control.getTemps(), control.getUtilisateurs(), control.getChemin());
				g.drawImage(tamponAffichage, xImage, yImage, null);
		    	while(System.currentTimeMillis() - time < attente){}
				repaint();
	    	}
	    }	

	    
	    
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	
		public void mousePressed(MouseEvent e) {
			control.souris(e.getButton());
		}
	
		public void mouseReleased(MouseEvent e) {
			control.lacheSouris(e.getButton());
		}
		
		public void keyPressed(KeyEvent e)
		{  
			int key= e.getKeyCode() ;
		    control.clavier(key);
		}
		    
		public void keyReleased(KeyEvent e) {
		    int key= e.getKeyCode() ;
		    control.lacheClavier(key);
		}
		
		public void keyTyped(KeyEvent e) {}

		public void autoriserDessin() {
			autoriserADessiner = true;
		}

		public void interdireDessin() {
			autoriserADessiner = false;
		}
		
		public Component fenetreMere(){
			return getParent().getParent().getParent().getParent();
		}
}
