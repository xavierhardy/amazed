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

import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FenetreTouche extends JFrame implements KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanneauJoueur pere;
	private JPanel panneau = new JPanel();
	private JLabel label;
	private JTextField champResultat;
	private int controlConcerne;
	
	public FenetreTouche(int controlConcerne, PanneauJoueur pere, JTextField champResultat){
		this.pere = pere;
		
		this.controlConcerne = controlConcerne;
		this.champResultat = champResultat;
        this.setTitle("Appuyer sur une touche");
        this.setResizable(false);
        this.setSize(200, 70);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        String nomTouche;
        switch(controlConcerne){
			case 1: nomTouche = "Bas"; break;
			case 2: nomTouche = "Gauche"; break;
			case 3: nomTouche = "Droite"; break;
			case 4: nomTouche = "Aide"; break;
			default: nomTouche = "Haut"; break;
        }
        label = new JLabel("Appuyer sur la touche "+ nomTouche);
   
        panneau.setLayout(new FlowLayout());
        panneau.add(label);

        this.setContentPane(panneau);
        this.addKeyListener(this);
        this.setVisible(true);
	}

	public void keyReleased(KeyEvent e) {
		pere.toucheRetournee(e, champResultat, controlConcerne);
		this.setVisible(false);
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
}
