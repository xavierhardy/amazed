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
package amazed;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import javax.imageio.ImageIO;

import amazed.game.Score;
import amazed.gui.graphics.Dessin;
import amazed.gui.graphics.InterfaceDessin;
import amazed.maze.Case;
import amazed.maze.Labyrinthe;
import amazed.maze.LabyrintheAbstrait;
import amazed.utils.Outils;
        
public class Main{
	private static InterfaceDessin dessin = new Dessin();
    
    
	public static void main(String[] args){}
    
    public static BufferedImage chargerImage(File fichierImage){
    	try {
			BufferedImage img = ImageIO.read(fichierImage);
			return img;
		} catch (IOException e) {
			System.out.println("Fichier "+ fichierImage.getName() +" introuvable");
		}
		return null;
    }
    
    public static Vector<Object> charger(File fichier){
        DataInputStream lecteur;
		Vector<Object> resultats = new Vector<Object>();
        int largeur, hauteur;
        boolean dansLaby, ouest, nord, est ,sud;
		try {
			lecteur = new DataInputStream(new BufferedInputStream(new FileInputStream(fichier)));

	        //Scores
	        Score[] scores = {new Score(),new Score(),new Score(),new Score(),new Score()};
	        int nbChars, valeur;
	        String nom;
	        for(int i = 0; i < 5; i++){
	        	nom = "";
	        	nbChars = lecteur.readInt(); //Nombre de caract�re du nom
	        	lecteur.skip(2); 
	        	for(int j = 0; j < nbChars; j++){
	        		nom += Character.toString(lecteur.readChar()); //Pour chaque caract�re, rajoute-le au nom
	        	}
	        	valeur = lecteur.readInt(); //Valeur du score
	        	scores[i] = new Score(nom,valeur);
	        	lecteur.skip(2); //Passe le caract�re de s�paration
	        }
	        largeur = lecteur.readInt(); //Lit la largeur
	        
	        lecteur.skip(2); //Passe le caract�re de s�paration
	        hauteur = lecteur.readInt();
	        
	        lecteur.skip(2); //Passe le caract�re de s�paration
	        LabyrintheAbstrait laby = new Labyrinthe(largeur,hauteur);
			for (int x = 0; x < largeur; x++){
				for(int y = 0; y < hauteur; y++){
					dansLaby = lecteur.readBoolean();
					if(dansLaby){ //Cette case est-elle dans le labyrinthe
						ouest = lecteur.readBoolean(); //Etat ouest
						nord = lecteur.readBoolean();
						est = lecteur.readBoolean(); 
						sud = lecteur.readBoolean();
						laby.carte[x][y] = new Case(nord, sud, est, ouest);
					}
					else {
						laby.carte[x][y] = new Case(!dansLaby);
					}
				}
			}
	        lecteur.close();
	        resultats.add(laby);
	        resultats.add(scores);
			return resultats;
		}
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
		return null;
    }
    
	public static boolean exporter(File fichier){ //Enregistre l'image du labyrinthe
    	try {
    		ImageIO.write(dessin.getLabyTampon(), Outils.getExtension(fichier), fichier);
    		return true;
		} catch (IOException e) {
			return false;
		}
	}
    
    
    public static boolean sauvegarder(Labyrinthe laby, Score scores[], File fichier){
    	  DataOutputStream bufferOut;
    	    try {
    	    	
    	    	//Largeur x Hauteur : XAAAA X X X XAAAA XAAAA X...
    	    	//X: cette case est-elle dans le labyrinthe
    	    	//Si X = 0, alors on ne rajoute rien
    	    	//Sinon, on rajoute AAAA avec dans l'ordre ouest, nord, est et sud
    	    	
    	    	boolean dansLaby;
				bufferOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichier)));
				
				//Score
		        for(int i = 0; i < 5; i++){
		        	bufferOut.writeInt(scores[i].getNom().length());
		        	bufferOut.writeChar('-');
		        	bufferOut.writeChars(scores[i].getNom());
		        	bufferOut.writeInt(scores[i].getValeur());
		        	bufferOut.writeChar('-');
		        }
				
				
				bufferOut.writeInt(laby.getLargeur()); //Note la largeur
				bufferOut.writeChar('x'); //Caract�re de s�paration
				bufferOut.writeInt(laby.getHauteur());
				bufferOut.writeChar(':'); //Caract�re de s�paration
				
				for (int x = 0; x < laby.getLargeur(); x++){
					for(int y = 0; y < laby.getHauteur(); y++){
						dansLaby = laby.carte[x][y].dansLaby();
						bufferOut.writeBoolean(dansLaby);
						if(dansLaby){
							bufferOut.writeBoolean(laby.carte[x][y].getOuest());
							bufferOut.writeBoolean(laby.carte[x][y].getNord());
							bufferOut.writeBoolean(laby.carte[x][y].getEst());
							bufferOut.writeBoolean(laby.carte[x][y].getSud());
						}
					}
				}
				
	    	    bufferOut.close();
	    	    return true;
    	    } catch (Exception e) {
    			return false;
			} 
    }
        
    public static boolean sauverResultats(HashMap<Integer, Long> valeurs, int lmax, File fichier){
  	  DataOutputStream bufferOut;
  	    try {
  	    	  	    	
				bufferOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichier)));
				bufferOut.writeChars("Largeur/Hauteur");
	        	bufferOut.writeChar(';');
	        	bufferOut.writeChars("Temps");
	        	bufferOut.writeChar('\n');
		        for(int i = 2; i < lmax; i++){
			        	bufferOut.writeChars(Integer.toString(i));
			        	bufferOut.writeChar(';');
			        	try{
			        		bufferOut.writeChars(Long.toString(valeurs.get(new Integer(i))));
			        	}
			        	catch(Exception e)
			        	{
			        		bufferOut.writeChars("Erreur");
			        	}
			        	bufferOut.writeChar('\n');
		        	}

	    	    bufferOut.close();
	    	    return true;
  	    } catch (Exception e) {
  			return false;
			} 
  }
    
    public static boolean sauverResultats2(HashMap<Point, Long> valeurs, int lmax, int hmax,File fichier){
    	  DataOutputStream bufferOut;
    	    try {
    	    	  	    	
  				bufferOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fichier)));
  				bufferOut.writeChars("Largeur");
  	        	bufferOut.writeChar(';');
  	        	bufferOut.writeChars("Hauteur");
  	        	bufferOut.writeChar(';');
  	        	bufferOut.writeChars("Temps");
  	        	bufferOut.writeChar('\n');
  		        for(int i = 2; i < lmax; i++){
  		        	for(int j = 1; (i != lmax && j <= i) || (i == lmax && j <= hmax); j++){ 
  			        	bufferOut.writeChars(Integer.toString(i));
  			        	bufferOut.writeChar(';');
  			        	bufferOut.writeChars(Integer.toString(j));
  			        	bufferOut.writeChar(';');
  			        	try{
  			        		bufferOut.writeChars(Long.toString(valeurs.get(new Point(i,j))));
  			        	}
  			        	catch(Exception e)
  			        	{
  			        		bufferOut.writeChars("Erreur");
  			        	}
  			        	bufferOut.writeChar('\n');
  		        	}
  		        }
  	    	    bufferOut.close();
  	    	    return true;
    	    } catch (Exception e) {
    			return false;
  			} 
    }
}