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

public class GenerateurFusion extends GenerateurAbstrait{

	private Labyrinthe laby;
	private int largeur;
	private int hauteur;
	private int nbsections; //nbsections va nous permettre de tester rapidement si on a terminé.
	private int[][] carte; //Chaque case correspond à une case du labyrinthe et lui donne sa section, avec la valeur -1, si c'est à l'extérieur du labyrinthe
	private int sectionEnConversion; //On conserve la valeur de la section en traitement pour réduire l'usage mémoire lors des appels récursifs de convertir
	private int sectionNouvelle;
	
	public GenerateurFusion(){}
	
	private void initCases(Forme forme){ //S'occupe d'initialiser le tableau des sections
		largeur = forme.getLargeur();
		hauteur = forme.getHauteur();

	    int k = 0;
	    carte = new int[largeur][hauteur];
		for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	if(forme.carte[i][j]) carte[i][j] = -1; //sinon, on lui donne la valeur -1
		    	else carte[i][j] = k++ ; //si cette case appartient au labyrinthe, on lui donne une valeur positive et on incrémente
		    }
	    }
		nbsections = k;
	}
	
	/*
	//Il faut convertir toute une section en une autre, on utilise la connexité pour éviter de parcourir toute la matrice carte
	//Cette solution a l'avantage de la rapidité, mais est très gourmande en mémoire...
	private void convertir(int x, int y){ 
		carte[x][y] = sectionNouvelle;

		if(y>0 && carte[x][y-1] == sectionEnConversion) convertir(x,y-1); //doit-on continuer au nord ?
		if(x>0 && carte[x-1][y] == sectionEnConversion) convertir(x-1,y);
		if(y<hauteur-1 && carte[x][y+1] == sectionEnConversion) convertir(x,y+1);
		if(x<largeur-1 && carte[x+1][y] == sectionEnConversion) convertir(x+1,y);
	}
	
	//On parcourt toute la matrice
	//Solution naive et lente, mais utile pour éviter des problèmes de mémoire car il n'y a pas de récursivités
	//En effet, on peut faire des matrices énormes sans problème, mais ça prend du temps à générer
	private void convertir(int x, int y){ 
		carte[x][y] = sectionNouvelle;
		for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	if(carte[i][j] == sectionEnConversion) carte[i][j] = sectionNouvelle;
		    }
	    }
	}*/
	
	//On adapte la solution récursive en solution itérative
	//Objectif: optimiser vitesse et mémoire
	//On utilise une seule pile, et on ne fait pas de récursivité, on finit un couloir avant de passer à un autre
	//Ca marche, c'est instantané sur des matrice de la taille de l'écran
	private void convertir(int x, int y){ 
		Stack<Point> suivants = new Stack<Point>();
		int xi = x, yi = y;
		suivants.push(new Point(x,y));
		while(!suivants.isEmpty()){
			Point nouveau = suivants.pop();
			xi = nouveau.x;
			yi = nouveau.y;
			carte[xi][yi] = sectionNouvelle;

			if(yi>0 && carte[xi][yi-1] == sectionEnConversion) suivants.push(new Point(xi,yi-1)); //doit-on continuer au nord ?
			if(xi>0 && carte[xi-1][yi] == sectionEnConversion) suivants.push(new Point(xi-1,yi));
			if(yi<hauteur-1 && carte[xi][yi+1] == sectionEnConversion) suivants.push(new Point(xi,yi+1));
			if(xi<largeur-1 && carte[xi+1][yi] == sectionEnConversion) suivants.push(new Point(xi+1,yi));
		}
	}
	
	public Labyrinthe genere(Forme forme) {
		initCases(forme);
		
		//Genere un labyrinthe plein dans la forme
		laby = new Labyrinthe(forme);
		int[] directionsVisitees = new int[4];
		int x, y, dir,done; //Coordonnées du point qui va voir un de ses murs détruits et direction dans laquelle on creuse
		while(nbsections > 1)
		{
			x = (int)(largeur*Math.random());
			y = (int)(hauteur*Math.random());
			
			done = 0;
			if(carte[x][y] != -1) { //Si ce point appartient au labyrinthe
				for(int i = 0; i<4; i++){
					directionsVisitees[i] = -1;
				}
				while(done < 4){ //Tant qu'on a pas vérifié toutes les directions
					dir = (int)(4*Math.random());
					while(contient(dir,directionsVisitees)){
						dir = (int)(4*Math.random());
					}
				directionsVisitees[done] = dir;
					switch(dir){ //Détaillons la première direction, les autres sont identiques
						case 0: if(x>0 && carte[x-1][y] != -1 && carte[x-1][y] != carte[x][y]){ //Peut-on creuser dans cette direction ?
							laby.creuser(x,y,dir); //On brise la cloison dans Labyrinthe
							if(carte[x-1][y] < carte[x][y]){ //On transforme toujours la section d'indice la plus grande, de façon à ce que plus la section à indice faible, plus elle a de chance d'être grande
								sectionEnConversion = carte[x][y]; // on indique que l'on va convertir la section correspondant à x-1,y
								sectionNouvelle = carte[x-1][y]; //on indique la nouvelle valeur de cette section
								convertir(x,y); //on la convertit de façon récursive
							}
							else {
								sectionEnConversion = carte[x-1][y];
								sectionNouvelle = carte[x][y];
								convertir(x-1,y);
							}
							nbsections--;
							done = 4; //on indique qu'on a fini notre tour
							} 
						else {
							done++;
						}
						break;
						case 1: if(y>0 && carte[x][y-1] != -1 && carte[x][y-1] != carte[x][y]){
							laby.creuser(x,y,dir);
							if(carte[x][y-1] < carte[x][y]){
								sectionEnConversion = carte[x][y];
								sectionNouvelle = carte[x][y-1];
								convertir(x,y);
							}
							else {
								sectionEnConversion = carte[x][y-1];
								sectionNouvelle = carte[x][y];
								convertir(x,y-1);
							}
							nbsections--;
							done = 4;
							} 
						else {
								done++;
							} 
						break;
						case 2: if(x<largeur-1 && carte[x+1][y] != -1 && carte[x+1][y] != carte[x][y]){
							laby.creuser(x,y,dir);
							if(carte[x+1][y] < carte[x][y]){
								sectionEnConversion = carte[x][y];
								sectionNouvelle = carte[x+1][y];
								convertir(x,y);
							}
							else {
								sectionEnConversion = carte[x+1][y];
								sectionNouvelle = carte[x][y];
								convertir(x+1,y);
							}
							nbsections--;
							done = 4;
							}
						else {
							done++;
							}
						break;
						default: if(y<hauteur-1 && carte[x][y+1] != -1 && carte[x][y+1] != carte[x][y]){
							laby.creuser(x,y,dir);
							if(carte[x][y+1] < carte[x][y]){
								sectionEnConversion = carte[x][y];
								sectionNouvelle = carte[x][y+1];
								convertir(x,y);
							}
							else {
								sectionEnConversion = carte[x][y+1];
								sectionNouvelle = carte[x][y];
								convertir(x,y+1);
							}
							nbsections--;
							done = 4;
							}
						else {
							done++;
							}
						break;
					}
				}
			}
		}
			
		
			return laby;
		}
	
	/*public Labyrinthe genere(Forme forme) {
	initCases(forme);
	
	//Genere un labyrinthe plein dans la forme
	laby = new Labyrinthe(forme);
	int x, y, dir,done; //Coordonnées du point qui va voir un de ses murs détruits et direction dans laquelle on creuse
	while(nbsections > 1)
	{
		x = (int)(largeur*Math.random());
		y = (int)(hauteur*Math.random());
		
		dir = (int)(4*Math.random()); //On laisse de plus de chance au hasard en ne laissant pas

		done = 0;
		if(carte[x][y] != -1) { //Si ce point appartient au labyrinthe
			while (done < 4){//Pour éviter de sortir du tableau par la gauche
				switch(dir){ //Détaillons la première direction, les autres sont identiques
					case 0: if(x>0 && carte[x-1][y] != -1 && carte[x-1][y] != carte[x][y]){ //Peut-on creuser dans cette direction ?
						laby.creuser(x,y,dir); //On brise la cloison dans Labyrinthe
						if(carte[x-1][y] < carte[x][y]){ //On transforme toujours la section d'indice la plus grande, de façon à ce que plus la section à indice faible, plus elle a de chance d'être grande
							sectionEnConversion = carte[x][y]; // on indique que l'on va convertir la section correspondant à x-1,y
							sectionNouvelle = carte[x-1][y]; //on indique la nouvelle valeur de cette section
							convertir(x,y); //on la convertit de façon récursive
						}
						else {
							sectionEnConversion = carte[x-1][y];
							sectionNouvelle = carte[x][y];
							convertir(x-1,y);
						}
						nbsections--;
						done = 4; //on indique qu'on a fini notre tour
						} 
					else {
						done++;
						dir++; 
					}
					break;
					case 1: if(y>0 && carte[x][y-1] != -1 && carte[x][y-1] != carte[x][y]){
						laby.creuser(x,y,dir);
						if(carte[x][y-1] < carte[x][y]){
							sectionEnConversion = carte[x][y];
							sectionNouvelle = carte[x][y-1];
							convertir(x,y);
						}
						else {
							sectionEnConversion = carte[x][y-1];
							sectionNouvelle = carte[x][y];
							convertir(x,y-1);
						}
						nbsections--;
						done = 4;
						} 
					else {
							dir++;
							done++;
						} 
					break;
					case 2: if(x<largeur-1 && carte[x+1][y] != -1 && carte[x+1][y] != carte[x][y]){
						laby.creuser(x,y,dir);
						if(carte[x+1][y] < carte[x][y]){
							sectionEnConversion = carte[x][y];
							sectionNouvelle = carte[x+1][y];
							convertir(x,y);
						}
						else {
							sectionEnConversion = carte[x+1][y];
							sectionNouvelle = carte[x][y];
							convertir(x+1,y);
						}
						nbsections--;
						done = 4;
						}
					else {
						dir++;
						done++;
						}
					break;
					default: if(y<hauteur-1 && carte[x][y+1] != -1 && carte[x][y+1] != carte[x][y]){
						laby.creuser(x,y,dir);
						if(carte[x][y+1] < carte[x][y]){
							sectionEnConversion = carte[x][y];
							sectionNouvelle = carte[x][y+1];
							convertir(x,y);
						}
						else {
							sectionEnConversion = carte[x][y+1];
							sectionNouvelle = carte[x][y];
							convertir(x,y+1);
						}
						nbsections--;
						done = 4;
						}
					else {
						dir = 0;
						done++;
						}
					break;
				}
			}
		}
	}
	
	
		return laby;
	}*/

	public String getNom() {
		return "Fusion aléatoire de chemins";
	}	
	
}
