
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame{
	
	private BoutonMenu dominoButton,gommetteButton,puzzleButton,saboteurButton;
	private EcranMenu ecran = new EcranMenu();
	
	public Menu() {
		reinitialisation();
	}
	public void reinitialisation() {
		if (ecran!=null) this.getContentPane().remove(ecran);
		this.ecran = new EcranMenu();
		this.ecran.setLayout(new BorderLayout());
		this.setTitle("Famille de Jeux - POOIG");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setAlwaysOnTop(false);
		
		this.getContentPane().add(ecran);
		this.setSize(1200,600);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		dominoButton = new BoutonMenu ("Domino");
		gommetteButton = new BoutonMenu ("Domino-Gommette");
		puzzleButton = new BoutonMenu ("Puzzle");
		saboteurButton = new BoutonMenu ("Saboteur");
		
		dominoButton.setEnabled(true);
		gommetteButton.setEnabled(true);
		puzzleButton.setEnabled(true);
		saboteurButton.setEnabled(true);
		
		JPanel panneauBoutons = new JPanel();
		panneauBoutons.setLayout(new GridLayout(1,4));
		ecran.setLayout(new BorderLayout());
		ecran.add(panneauBoutons,BorderLayout.SOUTH);
		panneauBoutons.add(dominoButton, BorderLayout.SOUTH);
		panneauBoutons.add(gommetteButton, BorderLayout.SOUTH);
		panneauBoutons.add(puzzleButton, BorderLayout.SOUTH);
		panneauBoutons.add(saboteurButton, BorderLayout.SOUTH);
		
		dominoButton.addActionListener((ActionEvent e )->{
			panneauBoutons.remove(dominoButton);
			panneauBoutons.remove(gommetteButton);
			panneauBoutons.remove(puzzleButton);
			panneauBoutons.remove(saboteurButton);
			
			BoutonMenu dButton = new BoutonMenu ("GUI Domino");
			BoutonMenu dTButton = new BoutonMenu ("Console Domino");
			panneauBoutons.setLayout(new GridLayout(2,1));
			panneauBoutons.add(dButton);
			panneauBoutons.add(dTButton);
			
			dButton.addActionListener((ActionEvent ee )->{
				VueG_Dom vue = new VueG_Dom();
				this.reinitialisation();
				vue.setVisible(true);
				vue.setAlwaysOnTop(true);			
			});
			dTButton.addActionListener((ActionEvent ee )->{
				VueT_Dom vueTDom = new VueT_Dom(); 
				try {
			    	this.dispose();
					vueTDom.demarrer();
				} catch (JeuFiniException e1) {
					this.reinitialisation();
					this.setVisible(true);
				}
			});	
			validate();
		});
		
		gommetteButton.addActionListener((ActionEvent e )->{
			VueT_Gom vueTGom = new VueT_Gom();
			try {
		    	this.dispose();
				vueTGom.demarrer();
			} catch (JeuFiniException e1) {
				this.reinitialisation();
				this.setVisible(true);
			}
	    	
		});
		
		puzzleButton.addActionListener((ActionEvent e )->{
			panneauBoutons.remove(dominoButton);
			panneauBoutons.remove(gommetteButton);
			panneauBoutons.remove(puzzleButton);
			panneauBoutons.remove(saboteurButton);
			
			BoutonMenu pButton = new BoutonMenu ("GUI Puzzle");
			BoutonMenu pTButton = new BoutonMenu ("Console Puzzle");
			
			panneauBoutons.setLayout(new GridLayout(2,1));
			panneauBoutons.add(pButton);
			panneauBoutons.add(pTButton);
			
			pButton.addActionListener((ActionEvent ee )->{
				Modele_Puz mp =new Modele_Puz("../Images/jbr.jpg",3,2);
				VueG_Puz vp=new VueG_Puz(mp);
				vp.pack();
				this.reinitialisation();
				this.setVisible(true);
			});
			
			pTButton.addActionListener((ActionEvent ee )->{
				try {
					this.dispose();
					new VueT_Puz();
				} catch (JeuFiniException e1) {
					this.reinitialisation();
					this.setVisible(true);
				}			
			});				
			validate();
		});
		

		saboteurButton.addActionListener((ActionEvent e )->{
			VueT_Sab vueTSab = new VueT_Sab();
			try {
				this.dispose();
				vueTSab.demarrer();
			} catch (JeuFiniException e1) {
				this.reinitialisation();
				this.setVisible(true);
			}
		});

		validate();
		repaint();
	
	}
	
	private class EcranMenu extends JPanel {
	
		@Override
		public void paintComponent(Graphics g){  
			Graphics2D g2D = (Graphics2D)g;
		   	g2D.setPaint(new Color(108, 172, 216)); 
		 	g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
			Font font = new Font("TimesRoman", Font.BOLD, 70);
		 	g.setFont(font);
			g.setColor(Color.black); 
			g.drawString("Famille de Jeux", this.getWidth()/2-300, this.getHeight()/2);
			font = new Font("TimesRoman", Font.BOLD, 50);

			g.drawString("POOIG ~ Groupe 75", this.getWidth()/2-300, this.getHeight()/2+100);		 	
	    } 
	}
	
	private class BoutonMenu extends JButton
	{				
		private  BoutonMenu(String s)
		{
			super(s);
			setBorderPainted(true);
			setFocusPainted(false);
			setContentAreaFilled(true);
		}
		
		public void paintComponent(Graphics g){ 
		    Graphics2D g2 = (Graphics2D)g;
		    g2.setPaint(Color.black);
		    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		    g2.setColor(new Color(255,255,255));	    
		    g2.drawString(this.getText(),20,15);
		}						 		 
        }		

}
