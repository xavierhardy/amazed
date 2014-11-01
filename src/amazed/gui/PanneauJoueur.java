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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
	
public class PanneauJoueur extends JPanel implements PropertyChangeListener, FocusListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SpringLayout sl = new SpringLayout();
	private JLabel labelNom = new JLabel("Nom:");
	private JTextField textFieldNom = new JTextField("Inscrire votre nom ici...", 20);
	
	private JLabel labelInterface = new JLabel("Interface:");
	private String[] interfaceUtilisateurs = {"Souris", "Clavier"};
	
	private JLabel labelTouches = new JLabel("Touches du clavier:");

	private JLabel labelHaut = new JLabel("Haut:");
	private JTextField textFieldHaut = new JTextField("Haut", 20);
	
	private JLabel labelBas = new JLabel("Bas:");
	private JTextField textFieldBas = new JTextField("Bas", 20);
	
	private JLabel labelGauche = new JLabel("Gauche:");
	private JTextField textFieldGauche = new JTextField("Gauche", 20);
	
	private JLabel labelDroite = new JLabel("Droite:");
	private JTextField textFieldDroite = new JTextField("Droite", 20);
	
	private JLabel labelTriche = new JLabel("Aide:");
	private JTextField textFieldTriche = new JTextField("NUMPAD0", 20);
	
	private JLabel labelCouleur = new JLabel("Couleur:");
	
	private JLabel labelR = new JLabel("R");
	private JFormattedTextField textFieldR = new JFormattedTextField();
	
	private JLabel labelV = new JLabel("V");
	private JFormattedTextField textFieldV = new JFormattedTextField();
	
	private JLabel labelB = new JLabel("B");
	private JFormattedTextField textFieldB = new JFormattedTextField();
	
	private JComboBox<String> listInterfaces = new JComboBox<String>(interfaceUtilisateurs);
	private boolean toucheNonSelectionnee = true;
	
	private int toucheHaut = KeyEvent.VK_UP;
	private int toucheBas = KeyEvent.VK_DOWN;
	private int toucheGauche = KeyEvent.VK_LEFT;
	private int toucheDroite = KeyEvent.VK_RIGHT;
	private int toucheTriche = KeyEvent.VK_NUMPAD0;
	
	private JTextField panneauCouleur = new JTextField();

	private void mettreEnDessousDe(Component c1, Component c2){ //On met c1 sous c2
		sl.putConstraint(SpringLayout.NORTH, c1, 5, SpringLayout.SOUTH, c2);		
	}
	
	private void mettreADroiteDe(Component c1, Component c2){
		sl.putConstraint(SpringLayout.WEST, c1, 5, SpringLayout.EAST, c2);
	}
	
	private void mettreEnHaut(Component c){
		sl.putConstraint(SpringLayout.NORTH, c, 5, SpringLayout.NORTH, this);
	}
	
	private void mettreAGauche(Component c){
		sl.putConstraint(SpringLayout.WEST, c, 5, SpringLayout.WEST, this);
	}
	
	
	public PanneauJoueur(int preset){		
		textFieldNom.setText("Joueur " + preset);
		switch(preset){
			case 2: listInterfaces.setSelectedIndex(1); toucheHaut = KeyEvent.VK_Z; toucheBas = KeyEvent.VK_S; toucheGauche = KeyEvent.VK_Q; toucheDroite = KeyEvent.VK_D; toucheTriche = KeyEvent.VK_E; textFieldR.setValue(new Integer(0)); textFieldV.setValue(new Integer(0)); textFieldB.setValue(new Integer(255)); break;
			case 3: listInterfaces.setSelectedIndex(1); toucheHaut = KeyEvent.VK_U; toucheBas = KeyEvent.VK_J; toucheGauche = KeyEvent.VK_H; toucheDroite = KeyEvent.VK_K; toucheTriche = KeyEvent.VK_I; textFieldR.setValue(new Integer(255)); textFieldV.setValue(new Integer(255)); textFieldB.setValue(new Integer(0)); break;
			default: textFieldR.setValue(new Integer(255)); textFieldV.setValue(new Integer(0)); textFieldB.setValue(new Integer(0)); break;
		}
		textFieldHaut.setText(KeyEvent.getKeyText(toucheHaut));
		textFieldBas.setText(KeyEvent.getKeyText(toucheBas));
		textFieldGauche.setText(KeyEvent.getKeyText(toucheGauche));
		textFieldDroite.setText(KeyEvent.getKeyText(toucheDroite));
		textFieldTriche.setText(KeyEvent.getKeyText(toucheTriche));
		
		this.setLayout(sl);
        
		this.add(labelNom);
		mettreEnHaut(labelNom);
		mettreAGauche(labelNom);

		this.add(textFieldNom);
		mettreEnHaut(textFieldNom);
		mettreADroiteDe(textFieldNom,labelNom);
		
		this.add(labelInterface);
		mettreAGauche(labelInterface);
		mettreEnDessousDe(labelInterface,textFieldNom);
		
		this.add(listInterfaces);
		mettreADroiteDe(listInterfaces,labelInterface);
		mettreEnDessousDe(listInterfaces,textFieldNom);
				
		this.add(labelTouches);
		mettreAGauche(labelTouches);
		mettreEnDessousDe(labelTouches,listInterfaces);
		
		this.add(labelHaut);
		mettreAGauche(labelHaut);
		mettreEnDessousDe(labelHaut,labelTouches);
		
		this.add(textFieldHaut);
		mettreADroiteDe(textFieldHaut,labelHaut);
		mettreEnDessousDe(textFieldHaut,labelTouches);
		textFieldHaut.addFocusListener(this);
		
		this.add(labelBas);
		mettreAGauche(labelBas);
		mettreEnDessousDe(labelBas,textFieldHaut);
		
		this.add(textFieldBas);
		mettreADroiteDe(textFieldBas,labelBas);
		mettreEnDessousDe(textFieldBas,textFieldHaut);
		textFieldBas.addFocusListener(this);
		
		this.add(labelGauche);
		mettreAGauche(labelGauche);
		mettreEnDessousDe(labelGauche,textFieldBas);
		
		this.add(textFieldGauche);
		mettreADroiteDe(textFieldGauche,labelGauche);
		mettreEnDessousDe(textFieldGauche,textFieldBas);
		textFieldGauche.addFocusListener(this);
		
		this.add(labelDroite);
		mettreAGauche(labelDroite);
		mettreEnDessousDe(labelDroite,textFieldGauche);
		
		this.add(textFieldDroite);
		mettreADroiteDe(textFieldDroite,labelDroite);
		mettreEnDessousDe(textFieldDroite,textFieldGauche);
		textFieldDroite.addFocusListener(this);
		
		this.add(labelTriche);
		mettreAGauche(labelTriche);
		mettreEnDessousDe(labelTriche,textFieldDroite);
		
		this.add(textFieldTriche);
		mettreADroiteDe(textFieldTriche,labelTriche);
		mettreEnDessousDe(textFieldTriche,textFieldDroite);
		textFieldTriche.addFocusListener(this);
		
		this.add(labelCouleur);
		mettreAGauche(labelCouleur);
		mettreEnDessousDe(labelCouleur,textFieldTriche);
		
		this.add(labelR);
		mettreADroiteDe(labelR,labelCouleur);
		mettreEnDessousDe(labelR,textFieldTriche);
		
		this.add(textFieldR);
		mettreADroiteDe(textFieldR,labelR);
		mettreEnDessousDe(textFieldR,textFieldTriche);
		textFieldR.setColumns(3);
		textFieldR.addPropertyChangeListener("value", this);

		this.add(labelV);
		mettreADroiteDe(labelV,textFieldR);
		mettreEnDessousDe(labelV,textFieldTriche);
		
		this.add(textFieldV);
		mettreADroiteDe(textFieldV,labelV);
		mettreEnDessousDe(textFieldV,textFieldTriche);
		textFieldV.setColumns(3);
		textFieldV.addPropertyChangeListener("value", this);
		
		this.add(labelB);
		mettreADroiteDe(labelB,textFieldV);
		mettreEnDessousDe(labelB,textFieldTriche);
		
		this.add(textFieldB);
		mettreADroiteDe(textFieldB,labelB);
		mettreEnDessousDe(textFieldB,textFieldTriche);
		textFieldB.setColumns(3);
		textFieldB.addPropertyChangeListener("value", this);
		
		this.add(panneauCouleur);
		mettreADroiteDe(panneauCouleur,textFieldB);
		mettreEnDessousDe(panneauCouleur,textFieldTriche);
		panneauCouleur.setColumns(3);
		panneauCouleur.setEnabled(false);
		panneauCouleur.setBackground(new Color(Integer.parseInt(textFieldR.getText()),Integer.parseInt(textFieldV.getText()), Integer.parseInt(textFieldB.getText())));
		//panneauCouleur.setSize(10, 10);
		
        this.setVisible(true);
	}

	public void propertyChange(PropertyChangeEvent e) { //Les seuls PropertyChangeListener existant sont sur les champs R V et B
		if(((Integer)(e.getNewValue())).intValue() > 255 || ((Integer)(e.getNewValue())).intValue() < 0){
			((JFormattedTextField)e.getSource()).setValue(e.getOldValue());
		}
		panneauCouleur.setBackground(new Color(Integer.parseInt(textFieldR.getText()),Integer.parseInt(textFieldV.getText()), Integer.parseInt(textFieldB.getText())));
	}

	@SuppressWarnings("unused")
	public void focusGained(FocusEvent e) {
		FenetreJoueurs fenetreMere = (FenetreJoueurs) this.getParent().getParent().getParent().getParent().getParent();
		if(toucheNonSelectionnee){
			if(textFieldHaut.isFocusOwner()){
				textFieldNom.requestFocus();
				FenetreTouche fen = new FenetreTouche(0, this, textFieldHaut);
				toucheNonSelectionnee = false;
				fenetreMere.setEnabled(false);
			}
			else if (textFieldBas.isFocusOwner()){
				textFieldNom.requestFocus();
				FenetreTouche fen = new FenetreTouche(1, this, textFieldBas);
				toucheNonSelectionnee = false;
				fenetreMere.setEnabled(false);
			}
			else if (textFieldGauche.isFocusOwner()){
				textFieldNom.requestFocus();
				FenetreTouche fen = new FenetreTouche(2, this, textFieldGauche);
				toucheNonSelectionnee = false;
				fenetreMere.setEnabled(false);
			}
			else if (textFieldDroite.isFocusOwner()){
				textFieldNom.requestFocus();
				FenetreTouche fen = new FenetreTouche(3, this, textFieldDroite);
				toucheNonSelectionnee = false;
				fenetreMere.setEnabled(false);
			}
			else if (textFieldTriche.isFocusOwner()){
				textFieldNom.requestFocus();
				FenetreTouche fen = new FenetreTouche(4, this, textFieldTriche);
				toucheNonSelectionnee = false;
				fenetreMere.setEnabled(false);
			}
		}
	}

	public void focusLost(FocusEvent e) {
	}

	public void toucheRetournee(KeyEvent e, JTextField t,int toucheConcernee) {
		FenetreJoueurs fenetreMere = (FenetreJoueurs) this.getParent().getParent().getParent().getParent().getParent();
		String nomTouche = KeyEvent.getKeyText(e.getKeyCode());
		System.out.println("Touche appuy�e");
		t.setText(nomTouche);
		toucheNonSelectionnee = true;
		fenetreMere.setEnabled(true);
		
		switch(toucheConcernee){
			case 1: toucheBas = e.getKeyCode(); break;
			case 2: toucheGauche = e.getKeyCode(); break;
			case 3: toucheDroite = e.getKeyCode(); break;
			case 4: toucheTriche = e.getKeyCode(); break;
			default: toucheHaut = e.getKeyCode(); break;
		}
	}
	
	public String getNom(){
		return textFieldNom.getText();
	}
	
	public int getInterface(){
		return listInterfaces.getSelectedIndex();
	}
	
	public int getHaut(){
		return toucheHaut;
	}
	
	public int getBas(){
		return toucheBas;
	}

	public int getGauche(){
		return toucheGauche;
	}
	
	public int getDroite(){
		return toucheDroite;
	}
	
	public int getR(){
		return Integer.parseInt(textFieldR.getText());
	}
	
	public int getV(){
		return Integer.parseInt(textFieldV.getText());
	}
	
	public int getB(){
		return Integer.parseInt(textFieldB.getText());
	}

	public int getTriche() {
		return toucheTriche;
	}
}