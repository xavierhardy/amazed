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
package amazed.gui;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import amazed.Control;
import amazed.Main;
import amazed.maze.Labyrinthe;
import amazed.utils.FiltreFichiersImages;
import amazed.utils.FiltreFichiersSimple;
import amazed.utils.Outils;
 
public class Fenetre extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Panneau pan;
    
    private Control control;
    private JPanel panDisposition = new JPanel();
    private JPanel panBoutons = new JPanel();
    private final JFileChooser boiteSelectionImage = new JFileChooser();
    private final JFileChooser boiteSelectionLaby = new JFileChooser();
    
    private JButton boutonSolution = new JButton("Solution");
    private JButton boutonResolutionProg = new JButton("R�solution progressive");
    
    private JMenuBar menu = new JMenuBar();
    
    private JMenu menuFichier = new JMenu("Fichier");
    
    //Boutons du menu Fichier
    private JMenuItem itemCharger = new JMenuItem("Charger");
    private JMenuItem itemSauvegarder = new JMenuItem("Sauvegarder");
    private JMenuItem itemExporter = new JMenuItem("Exporter");
    private JMenuItem itemQuitter = new JMenuItem("Quitter");
    
    private JMenu menuLabyrinthe = new JMenu("Labyrinthe");
    
    //Boutons du menu Labyrinthe
    private JMenuItem itemForme = new JMenuItem("Charger forme");
    private JMenuItem itemGenerer = new JMenuItem("Changer g�n�rateur");
    
    private JMenu menuJouer = new JMenu("Jouer");
    
    //Boutons du menu Jouer
    private JMenuItem itemUnJoueur = new JMenuItem("1 joueur"); 
    private JMenuItem itemDeuxJoueurs = new JMenuItem("2 joueurs");
    private JMenuItem itemScore = new JMenuItem("Score");
    
    private JMenu menuAide = new JMenu("Aide");
    
    //Boutons du menu Aide
    private JMenuItem itemAide = new JMenuItem("Aide"); 
    private JMenuItem itemApropos = new JMenuItem("� propos");
    
    private JLabel labelTemps = new JLabel("Temps: ");
    
    @SuppressWarnings("unused")
	private FenetreJoueurs fenetreJoueurs;
    
     public Fenetre(Control control){
    	 this.control = control;
         this.setTitle("Labyrinthe"); //Titre
         this.setSize(500, 500); //Taille
         this.setLocationRelativeTo(null); //Au milieu
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Fermer l'application quand on ferme la fen�tre 
         
         //Configuration des Filtres des boites de s�lections
         boiteSelectionImage.addChoosableFileFilter(new FiltreFichiersSimple("bmp","Image BMP","Image BMP (*.bmp)"));
         boiteSelectionImage.addChoosableFileFilter(new FiltreFichiersSimple("gif","Image GIF","Image GIF (*.gif)"));
         boiteSelectionImage.addChoosableFileFilter(new FiltreFichiersSimple("jpeg","jpg","Image JPEG","Image JPEG (*.jpg, *.jpeg)"));
         boiteSelectionImage.addChoosableFileFilter(new FiltreFichiersSimple("png","Image PNG","Image PNG (*.png)"));
         boiteSelectionImage.addChoosableFileFilter(new FiltreFichiersSimple("tiff","tif","Image TIFF","Image TIFF (*.tiff, *.tif)"));
         boiteSelectionImage.addChoosableFileFilter(new FiltreFichiersImages());
         boiteSelectionImage.setAcceptAllFileFilterUsed(false);
         
         boiteSelectionLaby.addChoosableFileFilter(new FiltreFichiersSimple("laby","Labyrinthe","Labyrinthe (*.laby)"));
         boiteSelectionLaby.setAcceptAllFileFilterUsed(false);
         
         //Menu d�roulant
         ajouter(menuFichier	, menu, KeyEvent.VK_F);
         ajouter(menuLabyrinthe , menu, KeyEvent.VK_L);
         ajouter(menuJouer		, menu, KeyEvent.VK_J);
         ajouter(menuAide		, menu, KeyEvent.VK_A);
         
         //Sous-menu Fichier
         ajouter(itemCharger	, menuFichier, "0", KeyEvent.VK_C);
         ajouter(itemSauvegarder, menuFichier, "1", KeyEvent.VK_S, false);
         menuFichier.addSeparator();
         ajouter(itemExporter	, menuFichier, "2", KeyEvent.VK_E, false);
         menuFichier.addSeparator();
         ajouter(itemQuitter	, menuFichier, "3", KeyEvent.VK_Q);

         //Sous-menu Labyrinthe
         ajouter(itemForme		, menuLabyrinthe, "10", KeyEvent.VK_F);
         ajouter(itemGenerer	, menuLabyrinthe, "11", KeyEvent.VK_G);        

         //Sous-menu Jouer
         ajouter(itemUnJoueur	, menuJouer, "20", KeyEvent.VK_1, false);
         ajouter(itemDeuxJoueurs, menuJouer, "21", KeyEvent.VK_2, false);
         menuJouer.addSeparator();
         ajouter(itemScore		, menuJouer, "22", KeyEvent.VK_S, false);
         
         //Sous-menu Aide
         ajouter(itemAide	, menuAide, "30", KeyEvent.VK_F1, ActionEvent.CTRL_MASK);
         ajouter(itemApropos, menuAide, "31", KeyEvent.VK_F1, ActionEvent.SHIFT_MASK);

         this.setJMenuBar(menu);

         //Fen�tre
         panDisposition.setLayout(new BorderLayout()); //Panneau permettant d'ordonner dans l'ordre Nord/Sud/Est/Ouest/Centre
         panDisposition.add(pan = new Panneau(control), BorderLayout.CENTER); //C'est ici qu'on va dessiner notre labyrinthe
         panDisposition.add(panBoutons, BorderLayout.SOUTH); //Sous-Panneau contenant les boutons

         panBoutons.setLayout(new FlowLayout()); //On lui dit d'ordonner les boutons dans l'ordre d'insertion
         //de la gauche vers la droite, de haut en bas
         
         //Panneau du bas avec les boutons
         ajouter(boutonSolution		 , panBoutons, "100", false);
         ajouter(boutonResolutionProg, panBoutons, "101", false);

         panBoutons.add(labelTemps);
         this.setContentPane(panDisposition);

         pan.addMouseListener(pan);
         pan.addKeyListener(pan);
         this.setVisible(true);
      }

 	private void ajouter(JMenu sousMenu, JMenuBar menu, int keyCode) {
        sousMenu.setMnemonic(keyCode);
        menu.add(sousMenu);
	}
     
     private void ajouter(JButton bouton, JPanel panneau, String actionCommand, boolean enabled) {
         bouton.addActionListener(this);
         bouton.setActionCommand(actionCommand);
         bouton.setEnabled(false);
         panneau.add(bouton);
	}

	private void ajouter(JMenuItem item, JMenu menu, String actionCommand, int keyCode, int maskKeyCode) {
         item.setAccelerator(KeyStroke.getKeyStroke(keyCode, maskKeyCode));
         item.addActionListener(this);
         item.setActionCommand(actionCommand);
         menu.add(item);
	}

	private void ajouter(JMenuItem item, JMenu menu, String actionCommand, int keyCode) {
        item.setMnemonic(keyCode);
        item.addActionListener(this);
        item.setActionCommand(actionCommand);
        menu.add(item);
	}
     
    private void ajouter(JMenuItem item, JMenu menu, String actionCommand, int keyCode, boolean enabled) {
        item.setMnemonic(keyCode);
        item.addActionListener(this);
        item.setActionCommand(actionCommand);
        item.setEnabled(enabled);
        menu.add(item);
	}
     
    private void activerBoutons(){ //Active tout les boutons qui ne peuvent fonctionner que lorsqu'un labyrinthe est affich�
    	itemSauvegarder.setEnabled(true);
        itemExporter.setEnabled(true);
        itemUnJoueur.setEnabled(true);
        itemDeuxJoueurs.setEnabled(true);
        itemScore.setEnabled(true);
        boutonSolution.setEnabled(true);
        boutonResolutionProg.setEnabled(true);
    }

	public Panneau getPanneau(){
    	 return pan;
     }
     
     public void afficherMessage(String message){
    	 JOptionPane.showMessageDialog(this, message,"Message", JOptionPane.PLAIN_MESSAGE);
     }
     
     public void afficherAide(){
    	 String message;
    	 message  = "Pour cr�er un labyrinthe � partir d'un dessin, choisissez d'abord le g�n�rateur choisi dans Labyrinthe -> Changer g�n�rateur .\n";
    	 message += "Ensuite cliquer sur Charger forme pour charger une image. Chaque pixel de couleur blanche est consid�r� comme en dehors\n";
    	 message += "du labyrinthe. La forme constitu�e des pixels non blancs doit �tre connexe. Si la forme n'est pas connexe, elle est remplac�e   \n";
    	 message += "par un rectangle.\n\n";
    	 message += "Pour enregistrer le labyrinthe, il suffit de cliquer sur Fichier -> Sauvegarder. Pour charger, le labyrinthe, il suffit de cliquer sur\n";
    	 message += "Charger. Le bouton Exporter sert � exporter le labyrinthe sous forme d'image.\n\n";
    	 message += "Pour jouer, cliquer sur le nombre de joueurs souhait� dans le menu Jouer. La touche Aide permet de voir une indication sur le\n";
    	 message += "chemin � suivre, il provoque un malus sur le score. Pour afficher les 5 meilleurs scores, cliquer sur Score dans le menu Jouer.\n\n";
    	 message += "Pour afficher la solution, cliquer sur le bouton Solution (arr�te la partie en cours).\n";
    	 message += "De m�me pour voir la r�solution du labyrinthe par l'ordinateur, cliquer sur le bouton R�solution progressive.";
    	 JOptionPane.showMessageDialog(this, message,"Aide", JOptionPane.PLAIN_MESSAGE);
     }
     
     
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		int reponseBoite;
		String resultat;

        int command = Integer.parseInt(e.getActionCommand()); //On ne peut pas utiliser de switch avec une cha�ne de caract�res donc on convertit notre String en int 
        switch(command){
	        case 0 : reponseBoite =  boiteSelectionLaby.showOpenDialog(this);
	        	if(reponseBoite == boiteSelectionLaby.APPROVE_OPTION) {
	        		if(!control.chargerLabyrinthe(boiteSelectionLaby.getSelectedFile())){
	        			JOptionPane.showMessageDialog(this,	"Impossible d'ouvrir le fichier.","Erreur",JOptionPane.ERROR_MESSAGE);
	        		}
	        		else activerBoutons();
	        	} 
	        	break;
	        case 1 : reponseBoite =  boiteSelectionLaby.showSaveDialog(this);
	        	if(reponseBoite == boiteSelectionLaby.APPROVE_OPTION) {
	        		if(Main.sauvegarder((Labyrinthe)control.getLaby(), control.getScoreBrut(), boiteSelectionLaby.getSelectedFile())){
	        			JOptionPane.showMessageDialog(this, "Labyrinthe enregistr�.");
	        		}
	        		else{
	        			JOptionPane.showMessageDialog(this,	"Impossible d'enregistrer le labyrinthe.","Erreur",JOptionPane.ERROR_MESSAGE);
	        		}
	        	}
		        break;
	        case 2 : reponseBoite = boiteSelectionImage.showSaveDialog(this); 
	        	if(reponseBoite == boiteSelectionImage.APPROVE_OPTION) {
	        		if(Main.exporter(boiteSelectionImage.getSelectedFile())){
	        			JOptionPane.showMessageDialog(this, "Image enregistr�e.");
	        		}
	        		else{
	        			JOptionPane.showMessageDialog(this,	"Impossible d'enregistrer l'image.","Erreur",JOptionPane.ERROR_MESSAGE);
	        		}
	        	}
		        break;
	        case 10: 
	        	reponseBoite =  boiteSelectionImage.showOpenDialog(this);
        		if(!control.initLabyrinthe(boiteSelectionImage.getSelectedFile())){
        			JOptionPane.showMessageDialog(this,	"Impossible d'ouvrir l'image.","Erreur",JOptionPane.ERROR_MESSAGE);
        		}
        		else activerBoutons();
		        break;
        	case 11: 
        		Object[] possibilitesGene = {"Fusion al�atoire de chemins", "Exploration exhaustive"};
        		resultat = (String)JOptionPane.showInputDialog(this,
        		                    "Choisir le g�n�rateur de labyrinthe � utiliser",
        		                    "G�n�rateur de Labyrinthe",
        		                    JOptionPane.PLAIN_MESSAGE,
        		                    null, possibilitesGene,
        		                    control.getGenerateur().getNom());

        		if ((resultat != null) && (resultat.length() > 0)) { //Si on clique sur OK
        			control.setGenerateur(resultat);
        		}
        		break;
	        case 20: fenetreJoueurs = new FenetreJoueurs(1, this); this.setEnabled(false); break;
	        case 21: fenetreJoueurs = new FenetreJoueurs(2, this); this.setEnabled(false); break;
	        case 22: 
	        	JOptionPane.showMessageDialog(this,	"5 meilleurs scores sur ce labyrinthe" + control.getScores() ,"Score",JOptionPane.PLAIN_MESSAGE);
	        	break;
	        case 30: afficherAide(); break;
	        case 31: JOptionPane.showMessageDialog(this,"Par Dimitri FOURNIER et Xavier HARDY - Copyright 2011","� propos",JOptionPane.PLAIN_MESSAGE) ; break;
	        case 100: control.initUtilisateurs(); control.solution(); break;
	        case 101: control.initUtilisateurs(); control.ajoutUtilisateur(); break;
	        default: System.exit(0); break;
        }
		
	}

	public void ajouterJoueur(String nom, int interfaceControl, int haut, int bas, int gauche, int droite, int triche, int r, int v, int b) {
		control.ajoutUtilisateur(interfaceControl, new Color(r,v,b), haut, bas, gauche, droite, triche, nom);
	}

	public void afficherTemps(int secondes){
		labelTemps.setText("Temps: "+ Outils.presenterTemps(secondes));
	}
	
	public void jouer() {
		control.jouer();
		control.initUtilisateurs();
	}

	public int getHauteurPanneau() {
		return pan.getHeight();
	}

	public void initTampon() {
		pan.initTampon();		
	}
	   
}	

