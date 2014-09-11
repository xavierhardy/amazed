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

public class GenerateurTaupe extends GenerateurAbstrait{
	private Labyrinthe laby;
	private int hauteur;
	private int largeur;
	private int[][] carte; //soit -2: n'appartient pas à la forme; -1: départ; 0: pas visitée; 1,2,3,4 : ma case précédente est dans la direction O,N,E,S
	
	public GenerateurTaupe(){}
	
	private void initCases(Forme forme){ //S'occupe d'initialiser le tableau des cases visités
		largeur = forme.getLargeur();
		hauteur = forme.getHauteur();
		//System.out.println("Forme: dimensions "+largeur+"x"+hauteur);
	    carte = new int[largeur][hauteur];
		for(int i=0;i<largeur;i++){
		    for(int j=0;j<hauteur;j++){
		    	if(forme.carte[i][j]) carte[i][j] = -2; //si cette case n'appartient pas au labyrinthe, on lui donne la valeur -2
		    }
	    }
	}
	
	public Labyrinthe genere(Forme forme){
	//Largeur, Hauteur
	int largeur = forme.getLargeur(), hauteur = forme.getHauteur();
	
	//Genere un labyrinthe plein dans la forme
	laby = new Labyrinthe(forme);
	initCases(forme);
	
	//Point de départ
	int xi = (int)(Math.random()*largeur);
	int yi = (int)(Math.random()*hauteur); 

	while(carte[xi][yi] == -2){ //Le soucis, c'est qu'on peut avoir un point de départ à l'extérieur de la forme, donc on s'assure que ça n'arrive pas
		xi = (int)(Math.random()*largeur);
		yi = (int)(Math.random()*hauteur); 
	}

	int x = xi, y = yi; //Position de la "taupe"
	int dir;
	int done;

	int precedent = -1; //direction pour tomber sur le précédent
	while(carte[x][y] != -1) //Tant que la taupe en est au premier pas, ou tant qu'elle n'est pas au départ
	{
		done = 0;
		int[] directionsVisitees = new int[4];
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
				case 0: if(x>0 && carte[x-1][y] == 0){ //Peut-on creuser dans cette direction ?
					laby.creuser(x,y,dir); //On brise la cloison dans Labyrinthe
					if(carte[x][y] == 0) { //Si on n'est pas sur une case visitée, on met à jour le précédent
						carte[x][y] = precedent; //On se met dans l'état visité en mettant la direction du précédent ou -1 pour le départ
					}
					x--; //On met à jour la position
					precedent = 3; //On indique notre position au suivant

					done = 5;
					} 
				else {
					done++;
				}
				break;
				case 1:	if(y>0 && carte[x][y-1] == 0){
					laby.creuser(x,y,dir);
					if(carte[x][y] == 0) { 
						carte[x][y] = precedent;
					}
					y--;
					precedent = 4;
					done = 5;
					} 
				else {
					done++;
				}
				break;
				case 2:	if(x<largeur-1 && carte[x+1][y] == 0){
					laby.creuser(x,y,dir);
					if(carte[x][y] == 0) { 
						carte[x][y] = precedent;
					}
					x++;
					precedent = 1;
					done = 5;
					} 
				else {
					done++;
				}
				break;
				default:
				if(y<hauteur-1 && carte[x][y+1] == 0){
					laby.creuser(x,y,dir);
					if(carte[x][y] == 0) { 
						carte[x][y] = precedent;
					}
					y++;
					precedent = 2;
					done = 5;
					} 
				else {
					done++;
				}
				break;
			}
		
			if(done == 4){ //Si le tour ne s'est pas terminé avec succès, done = 4 sinon 5
						   //Ici on ne peut pas creusé ou avancé, on en revient en arrière
				if(carte[x][y] == 0) carte[x][y] = precedent; //On met à jour avant de partir
				switch(carte[x][y]){
					case 1: x--; break;
					case 2: y--; break;
					case 3: x++; break;
					case 4: y++; break;
				}	
			}
		}
	}
	return laby;
	}

	public String getNom() {
		return "Exploration exhaustive";
	}
	
	/*public Labyrinthe genere(Forme forme) {
	//Largeur, Hauteur
	int largeur = forme.getLargeur(), hauteur = forme.getHauteur();
	
	//Genere un labyrinthe plein dans la forme
	laby = new Labyrinthe(forme);
	initCases(forme);
	
	//Point de départ
	int xi = (int)(Math.random()*largeur);
	int yi = (int)(Math.random()*hauteur); 

	while(carte[xi][yi] == -2){ //Le soucis, c'est qu'on peut avoir un point de départ à l'extérieur de la forme, donc on s'assure que ça n'arrive pas
		xi = (int)(Math.random()*largeur);
		yi = (int)(Math.random()*hauteur); 
	}
	System.out.println("Point de départ: "+xi+","+yi);
	int x = xi, y = yi; //Position de la "taupe"
	int dir;
	int done;
	//boolean depart = true; //Premier pas si vrai sinon faux
	int precedent = -1; //direction pour tomber sur le précédent
	while(carte[x][y] != -1) //Tant que la taupe en est au premier pas, ou tant qu'elle n'est pas au départ
	{
		dir = (int)(4*Math.random());
		//System.out.println("Point de départ: "+xi+","+yi+"  "+x+","+y+"  "+dir);
		done = 0;
		while(done < 4){ //Tant qu'on a pas vérifié toutes les directions
			switch(dir){ //Détaillons la première direction, les autres sont identiques
				case 0: if(x>0 && carte[x-1][y] == 0){ //Peut-on creuser dans cette direction ?
					laby.creuser(x,y,dir); //On brise la cloison dans Labyrinthe
					if(carte[x][y] == 0) { //Si on n'est pas sur une case visitée, on met à jour le précédent
						carte[x][y] = precedent; //On se met dans l'état visité en mettant la direction du précédent ou -1 pour le départ
					}
					x--; //On met à jour la position
					precedent = 3; //On indique notre position au suivant
					//System.out.println("ouest");
					done = 5;
					} 
				else {
					done++;
					dir++; 
				}
				break;
				case 1:	if(y>0 && carte[x][y-1] == 0){
					laby.creuser(x,y,dir);
					if(carte[x][y] == 0) { 
						carte[x][y] = precedent;
					}
					y--;
					precedent = 4;
					//System.out.println("nord");
					done = 5;
					} 
				else {
					done++;
					dir++; 
				}
				break;
				case 2:	if(x<largeur-1 && carte[x+1][y] == 0){
					laby.creuser(x,y,dir);
					if(carte[x][y] == 0) { 
						carte[x][y] = precedent;
					}
					x++;
					precedent = 1;
					//System.out.println("est");
					done = 5;
					} 
				else {
					done++;
					dir++; 
				}
				break;
				default:
				if(y<hauteur-1 && carte[x][y+1] == 0){
					laby.creuser(x,y,dir);
					if(carte[x][y] == 0) { 
						carte[x][y] = precedent;
					}
					y++;
					precedent = 2;
					//System.out.println("sud");
					done = 5;
					} 
				else {
					done++;
					dir=0; 
				}
				break;
			}
		
			if(done == 4){ //Si le tour ne s'est pas terminé avec succès, done = 4 sinon 5
						   //Ici on ne peut pas creusé ou avancé, on en revient en arrière
				if(carte[x][y] == 0) carte[x][y] = precedent; //On met à jour avant de partir
				switch(carte[x][y]){
					case 1: x--; break;//System.out.println("retour ouest"); break;
					case 2: y--; break;//System.out.println("retour nord"); break;
					case 3: x++; break;//System.out.println("retour est"); break;
					case 4: y++; break;//System.out.println("retour sud"); break;
				}	
			}
		}
	}
	return laby;
	}*/
}