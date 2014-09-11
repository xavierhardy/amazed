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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FenetreJoueurs extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Fenetre pere;
	private JPanel panneauPrincipal = new JPanel();
	private JTabbedPane panneauOnglets = new JTabbedPane();
	private JPanel panBoutons = new JPanel();
	private JButton boutonOK = new JButton("OK");
	private JButton boutonAnnuler = new JButton("Annuler");	
	
	public FenetreJoueurs(int nombreJoueurs, Fenetre pere){
		this.pere = pere;
	    this.setTitle("Nouvelle partie");
	    this.setSize(305, 335);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false); 
	   	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    
	    //Fenêtre
	    for(int i = 1; i <= nombreJoueurs; i++){
	        	 panneauOnglets.addTab("Joueur "+ i, null, new PanneauJoueur(i),"Joueur " + i);
	         }
	         
	         panneauPrincipal.setLayout(new BorderLayout());
	         panneauPrincipal.add(panneauOnglets, BorderLayout.CENTER);
	         panneauPrincipal.add(panBoutons, BorderLayout.SOUTH);

	         panBoutons.setLayout(new FlowLayout());
	         
	         panBoutons.add(boutonOK);
	         boutonOK.addActionListener(this);
	         boutonOK.setActionCommand("1");

	         panBoutons.add(boutonAnnuler);
	         boutonAnnuler.addActionListener(this);
	         boutonAnnuler.setActionCommand("0");
	         
	         this.setContentPane(panneauPrincipal);
	         this.setVisible(true);

	      }
	
	public void actionPerformed(ActionEvent e) {
		pere.setEnabled(true);
		
		if(Integer.parseInt(e.getActionCommand()) == 1){
			boolean sourisUtilisee = false;
			pere.jouer();
			for(int i = 0; i < panneauOnglets.getTabCount(); i++){
				PanneauJoueur pan = (PanneauJoueur)panneauOnglets.getComponentAt(i);
				int interfaceControl = pan.getInterface();
				if(interfaceControl == 0){ //On s'assure que 2 joueurs ne soient pas controlé par la souris.
					if(sourisUtilisee){
						interfaceControl = 1;
					}
					else {
						sourisUtilisee = true;
					}
				}
				System.out.println(pan.getNom() + " " + interfaceControl + " " + pan.getHaut() + " " + pan.getBas() + " " + pan.getGauche() + " " + pan.getDroite() + " " + pan.getR() + " " + pan.getV() + " " + pan.getB());
				pere.ajouterJoueur(pan.getNom(),interfaceControl,pan.getHaut(),pan.getBas(),pan.getGauche(),pan.getDroite(), pan.getTriche(),pan.getR(),pan.getV(),pan.getB());				
			}
			this.setVisible(false);
		}
		else this.setVisible(false);
	}
}
