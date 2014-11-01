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

import amazed.Control;


public class UtilisateurSouris extends UtilisateurAbstrait {
	
	public UtilisateurSouris(int x, int y, Control control, String nom) {
		super(x, y, control, nom);
	}
	
	public UtilisateurSouris(int x, int y, Control control, Color couleur, String nom) {
		super(x, y, control, couleur, nom);
	}
	
	public void souris(int bouton){
		if(bouton == 1)	avance = true;
		else demanderAide();
	}
	
	public void lacheSouris(int bouton){
		if(bouton == 1)	avance = false;
	}
	
	public void coordonneesCurseur(int xc, int yc){
		this.xc = xc;
		this.yc = yc;
	}
	
	public boolean estJoueur(){
		return true;
	}
}
