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
import java.awt.image.BufferedImage;

import amazed.game.users.UtilisateurAbstrait;
import amazed.maze.Chemin;
import amazed.maze.Labyrinthe;


public interface InterfaceAffichageLaby {
	abstract public void dessineUtilisateur(Graphics g, UtilisateurAbstrait u);
	abstract public int getTailleCase();
	abstract public void setTailleCase(int tailleCase);
	abstract public int getLargeur(int largeur);
	abstract public int getHauteur(int hauteur);
	abstract public void dessineLabyrinthe(Graphics g);
	abstract public void sauverTampon(Labyrinthe laby);
	abstract public BufferedImage getLabyTampon();
	abstract public void dessineSolution(Graphics g, Chemin chemin, Labyrinthe laby);
	abstract public void dessineCase(Graphics g, Labyrinthe laby, int x, int y);
	abstract public void dessineAide(Graphics g, Chemin chemin, int i, Color couleur);
}
