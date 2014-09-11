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
import java.awt.event.KeyEvent;


public class UtilisateurClavier extends UtilisateurAbstrait {
	private int toucheHaut = KeyEvent.VK_UP;
	private int toucheBas = KeyEvent.VK_DOWN;
	private int toucheGauche = KeyEvent.VK_LEFT;
	private int toucheDroite = KeyEvent.VK_RIGHT;
	private int toucheTriche = KeyEvent.VK_NUMPAD0;
	
	public UtilisateurClavier(int x, int y, Control control, String nom) {
		super(x, y, control, nom);
	}

	public UtilisateurClavier(int x, int y, Control control, Color couleur, String nom) {
		super(x, y, control,couleur, nom);
	}

	public UtilisateurClavier(int x, int y, Control control, Color couleur, int toucheHaut, int toucheBas, int toucheGauche, int toucheDroite, int toucheTriche, String nom) {
		super(x, y, control,couleur, nom);
		this.toucheHaut = toucheHaut;
		this.toucheBas = toucheBas;
		this.toucheDroite = toucheDroite;
		this.toucheGauche = toucheGauche;
		this.toucheTriche = toucheTriche;
	}
	
	public void clavier(int key){
		if(key == toucheDroite){ //Else if imbriqué, c'est moche mais touche*** ne sont pas constants ce qui empêche switch/case
			xc = x+1; yc = y; avance = true;
		} else if(key == toucheGauche){
				xc = x-1; yc = y; avance = true;
		} else if(key == toucheHaut){
				 xc = x; yc = y-1; avance = true;
		} else if(key == toucheBas){
				 xc = x; yc = y+1; avance = true;
		} else if(key == toucheTriche){
			demanderAide();
		}
	}

	public void lacheClavier(int key){
		if(key == toucheDroite || key == toucheGauche || key == toucheHaut || key == toucheBas){
			avance = false;
		}		
	}
	
	public boolean estJoueur(){
		return true;
	}

}
