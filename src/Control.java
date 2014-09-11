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
import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;


public class Control {
		private LabyrintheAbstrait laby;
		private Vector<UtilisateurAbstrait> utilisateurs = new Vector<UtilisateurAbstrait>();
		private Score[] scores = new Score[5];
		private CheminAbstrait chemin;
		private GenerateurAbstrait generateur = new GenerateurTaupe();;
		private long tempsDepart = System.currentTimeMillis()/1000;
		private int dernierTemps = 0;
		private boolean afficherSolution = false;
		private InterfaceDessin dessin;
		
		public Control(InterfaceDessin dessin){
			this.dessin = dessin;
		    scores = new Score[5];
		    for (int i = 0; i < 5; i++){
		    	scores[i] = new Score();
		    }
		    utilisateurs = new Vector<UtilisateurAbstrait>();
		}
		
		public void initUtilisateurs(){
			utilisateurs.clear();
			afficherSolution = false;
		}
		
		public void ajoutUtilisateur(int interfaceControl, Color couleur, int toucheHaut, int toucheBas, int toucheGauche, int toucheDroite, int toucheTriche, String nom){
			if(utilisateurs.isEmpty()){
				switch(interfaceControl){
					case 0: utilisateurs.add(new UtilisateurSouris(laby.getEntree().x,laby.getEntree().y,this, couleur, nom)); break;
					case 1: utilisateurs.add(new UtilisateurClavier(laby.getEntree().x,laby.getEntree().y, this, couleur, toucheHaut, toucheBas, toucheGauche, toucheDroite, toucheTriche, nom)); break;
					default: utilisateurs.add(new UtilisateurOrdinateur(laby.getEntree().x,laby.getEntree().y,this, couleur, nom)); break;
				}
			}
			else {
				switch(interfaceControl){
					case 0: utilisateurs.add(new UtilisateurSouris(laby.getSortie().x,laby.getSortie().y,this, couleur, nom)); utilisateurs.lastElement().changeSens(); break;
					case 1: utilisateurs.add(new UtilisateurClavier(laby.getSortie().x,laby.getSortie().y, this, couleur, toucheHaut, toucheBas, toucheGauche, toucheDroite, toucheTriche, nom)); utilisateurs.lastElement().changeSens(); break;
					default: utilisateurs.add(new UtilisateurOrdinateur(laby.getSortie().x,laby.getSortie().y,this, couleur, nom)); utilisateurs.lastElement().changeSens(); break;
				}
			}
		}
		
		
		public void ajoutUtilisateur(int xInitial, int yInitial, int interfaceControl, Color couleur, int toucheHaut, int toucheBas, int toucheGauche, int toucheDroite, int toucheTriche, String nom){
			switch(interfaceControl){
				case 0: utilisateurs.add(new UtilisateurSouris(xInitial,yInitial,this, couleur, nom)); break;
				case 1: utilisateurs.add(new UtilisateurClavier(xInitial, yInitial, this, couleur, toucheHaut, toucheBas, toucheGauche, toucheDroite, toucheTriche, nom)); break;
				default: utilisateurs.add(new UtilisateurOrdinateur(xInitial,yInitial,this, couleur, nom)); break;
			}
		}
		
		public void ajoutUtilisateur(){ //Par défaut, on ajoute un joueur ordinateur ROUGE à l'entrée
			utilisateurs.add(new UtilisateurOrdinateur(laby.getEntree().x,laby.getEntree().y,this,Color.red,"Ordinateur"));
		}
		
		public void jouer(){
			tempsDepart = System.currentTimeMillis()/1000;
			dernierTemps = 0;
		}
		
		public void setGenerateur(String nomGenerateur){
			if(nomGenerateur.equals("Exploration exhaustive")){
				generateur = new GenerateurTaupe();
			}
			else {
				generateur = new GenerateurFusion();
			}
		}
		
		public boolean initLabyrinthe(File fichierImage){
	    	try{
			    Forme forme = new Forme(Main.chargerImage(fichierImage)); 
			    scores = new Score[5];
			    for (int i = 0; i < 5; i++){
			    	scores[i] = new Score();
			    }
			    laby = generateur.genere(forme);
			    laby.initEntreeSortie();
			    dessin.setTailleCase(dessin.getHauteurDessin()/laby.getHauteur());
				dessin.initTampon();
				dessin.interdireDessin();
				dessin.sauverTampon(laby);
				dessin.autoriserDessin();
				utilisateurs = new Vector<UtilisateurAbstrait>();
				chemin = new Chemin (laby);
				return true;
	    	} catch (Exception e) {

				e.printStackTrace();
	    		return false;
	    	}
		}
		
		public boolean chargerLabyrinthe(File fichier){
			try {
				Vector<Object> resultats = new Vector<Object>();
				resultats = Main.charger(fichier); //Vecteur d'objets contenant le labyrinthe et le score
				laby = (LabyrintheAbstrait)resultats.get(0);
				scores = (Score[])resultats.get(1);
				laby.initEntreeSortie();
				dessin.setTailleCase(dessin.getHauteurDessin()/laby.getHauteur());
				dessin.initTampon();
				dessin.interdireDessin();
				dessin.sauverTampon(laby);
				dessin.autoriserDessin();
				return true;
			}
			catch(Exception e) {
				return false;
			}
		}
		
		
		public void souris(int bouton) {
			Iterator<UtilisateurAbstrait> iter = utilisateurs.iterator();
			UtilisateurAbstrait next;
			while(iter.hasNext()){
				next = iter.next();
				next.souris(bouton);
			}
			
		}
		
		public void lacheSouris(int bouton) {
			Iterator<UtilisateurAbstrait> iter = utilisateurs.iterator();
			UtilisateurAbstrait next;
			while(iter.hasNext()){
				next = iter.next();
				next.lacheSouris(bouton);
			}
			
		}

		public int getTemps() {
			int temps = (int)(System.currentTimeMillis()/1000 - tempsDepart);
			if(temps != dernierTemps){
				dessin.afficherTemps(temps);
		    	dernierTemps = temps;
			}
			return temps;
		}

		public void calcul(int xs, int ys) { //xs, ys coordonnées de la souris par rapport au point supérieur gauche du labyrinthe
			Iterator<UtilisateurAbstrait> iter = utilisateurs.iterator();
			UtilisateurAbstrait next;
			while(iter.hasNext()){
				next = iter.next();
				if(!next.estArrive()){
					next.coordonneesCurseur(xs/dessin.getTailleCase(), ys/dessin.getTailleCase());
					next.avanceUtilisateur();
					if((next.getSens() && next.x == laby.getSortie().x && next.y == laby.getSortie().y) || (!next.getSens() && next.x == laby.getEntree().x && next.y == laby.getEntree().y)){ //Si le joueur est arrivé au bout de son chemin
						if(next.estJoueur()) {
							if(next.aDemandeAide())	ajouterScore(next.getNom(), dernierTemps + (int)(0.1*laby.getHauteur()*laby.getLargeur()));
							else ajouterScore(next.getNom(), dernierTemps);
						}
						next.arrive();
					}
				}
	

			}
		}

		
		public void dessineSolution(Graphics g){
	    	chemin.dessine(g, laby, dessin.getTailleCase());
		}
		
		public void solution(){
			afficherSolution = true;
		}
		
		public void lacheClavier(int key) {
			Iterator<UtilisateurAbstrait> iter = utilisateurs.iterator();
			UtilisateurAbstrait next;
			while(iter.hasNext()){
				next = iter.next();
				next.lacheClavier(key);
			}
		}

		public void clavier(int key) {
			Iterator<UtilisateurAbstrait> iter = utilisateurs.iterator();
			UtilisateurAbstrait next;
			while(iter.hasNext()){
				next = iter.next();
				next.clavier(key);
			}
		}

		public GenerateurAbstrait getGenerateur() {
			return generateur;
		}
		
		public String getScores(){
			String resultat = "",nom;
			int valeur;
			for(int i = 0; i < 5; i++){
				nom = scores[i].getNom();
				valeur = scores[i].getValeur();
				if(nom.equals("") && valeur == Integer.MAX_VALUE){
					resultat += "\n";
				}
				else{
					resultat += "\n" + nom + " : " + valeur;
				}
			}
			return resultat;
		}

		public Score[] getScoreBrut() {
			return scores;
		}
		
		public void ajouterScore(String nom, int valeur){ //Score en nombre de secondes, les derniers sont premiers
			int i = 4;
			while(i >= 0 && scores[i].getValeur() > valeur){
				i--;
			}

			if(i < 4){
				for(int j = 3; j > i; j--){
					scores[j+1].setScore(scores[j].getNom(), scores[j].getValeur());
				}
				scores[++i].setScore(nom,valeur);
			}
		}

		public LabyrintheAbstrait getLaby() {
			return laby;
		}

		public InterfaceDessin getDessin() {
			return dessin;
		}

		public Vector<UtilisateurAbstrait> getUtilisateurs() {
			return utilisateurs;
		}

		public boolean estAfficheeSolution() {
			return afficherSolution;
		}

		public CheminAbstrait getChemin() {
			return chemin;
		}

		public int triche() {
			return chemin.getPointHasard();
		}
}
