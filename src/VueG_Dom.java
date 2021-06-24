import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueG_Dom extends VueG {
	protected ControleurGraphique_Dom controleur;
	protected Modele_Dom jeu;
	private JPanel ecran;
    private Dimension dimension;

    public VueG_Dom()
    {
    	this.controleur = new ControleurGraphique_Dom(this);
    	this.dimension = new Dimension(800,600);
    	this.setLayout(new GridLayout(1,1)); 
    	this.setSize(dimension); 
    	this.getContentPane().setSize(dimension); 
    	this.getContentPane().setPreferredSize(dimension); 

    	this.setTitle("Domino");
    	this.setLocationRelativeTo(null);
    	setAlwaysOnTop(true);
    	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.setResizable(false);
    	demarrer();
    }
	
  	public void demarrer()
  	{
  		System.err.println("Domino en cours de partie ...");
  		if (ecran!=null) this.getContentPane().remove(ecran);
  		this.ecran = new EcranDemarrage();
  			BorderLayout layout = new BorderLayout();
  			layout.setHgap(10);
  			layout.setVgap(10);
  			this.ecran.setLayout(layout);
  			JPanel panneauBoutons = new JPanel();
  				panneauBoutons.setLayout(new GridLayout(1,3));
  				BoutonChoix bouton2 = new BoutonChoix("2 joueurs");
  		  			bouton2.addActionListener( (ActionEvent event) -> 
  		  			{ 
  		  				controleur.setChoixAction(2) ; 
	  		  			controleur.demarrer();
  		  			} );
  		  		panneauBoutons.add(bouton2);
  		  		BoutonChoix bouton3 = new BoutonChoix("3 joueurs");
		  			bouton3.addActionListener( (ActionEvent event) -> 
		  			{
		  				controleur.setChoixAction(3);
	  		  			controleur.demarrer();
	  				; } );
	  		  	panneauBoutons.add(bouton3);
	  		  	BoutonChoix bouton4 = new BoutonChoix("4 joueurs");
	  		  		bouton4.addActionListener( (ActionEvent event) -> 
	  		  		{ 
	  		  			controleur.setChoixAction(4) ; 
	  		  			controleur.demarrer();
	  		  		} );
  			  	panneauBoutons.add(bouton4);
  			 this.ecran.add(panneauBoutons, BorderLayout.SOUTH);	  	
  		this.getContentPane().add(this.ecran);
  		this.validate();
  	}
  	
  	public void initialiser() {
  		 
  		this.getContentPane().remove(ecran);
  		this.ecran = new EcranJeu();
	  		
  		if (controleur.getJeu().isBloque() && controleur.getJeu().getPioche().size()>0)
		{
  			BoutonChoix btt = new BoutonChoix("Piocher");
  			btt.addActionListener
  			( 
  				(ActionEvent e) ->		
  				{
  					controleur.piocher();
  				}
  			);
  			((EcranJeu)(this.ecran)).getPanneauJoueur().getPanneauChoix().add(btt);
  		}
  		else if (controleur.getJeu().isBloque())
		{
  			BoutonChoix btt = new BoutonChoix("Passer");
  			btt.addActionListener
  			( 
  				(ActionEvent e) ->		
  				{
  					controleur.passer();
  				}
  			);
  			((EcranJeu)(this.ecran)).getPanneauJoueur().getPanneauChoix().add(btt);
  		}
  		
  		this.getContentPane().add(ecran);
  		validate();	
  	}
  	
  	public void finirJeu()
  	{
  		this.getContentPane().remove(ecran);
  		this.ecran = new EcranFin();
  		BoutonChoix btt = new BoutonChoix("Recommencer");
		btt.addActionListener
		( 
			(ActionEvent e) ->		
			{
				this.demarrer();
			}
		);
		((EcranFin)(this.ecran)).getPanneauRes().add(btt);
		
		btt = new BoutonChoix("Quitter");
		btt.addActionListener
		( 
			(ActionEvent e) ->		
			{
				this.dispose();
			}
		);
		((EcranFin)(this.ecran)).getPanneauRes().add(btt);
		this.repaint();
		this.validate();
  		this.getContentPane().add(ecran);
  		validate();	
  	}

	public void choisir(Domino domino)
	{
		this.initialiser();
		for (int i=0; i<domino.getActions().size();i++) 
		{
			final int y = i;
			BoutonChoix btt = new BoutonChoix(domino.getActions().get(i));
			btt.addActionListener
			( 
				(ActionEvent e) ->		
				{
					controleur.setChoixAction(y);
					controleur.action(domino);
				}
			);
			((EcranJeu)(this.ecran)).getPanneauJoueur().getPanneauChoix().add(btt);
			this.repaint();
			this.validate();
		}		
	}
	
	public void piocher() {
		this.initialiser();
		BoutonChoix btt = new BoutonChoix("Piocher");
		btt.addActionListener
		( 
			(ActionEvent e) ->		
			{
				controleur.piocher();
			}
		);
		((EcranJeu)(this.ecran)).getPanneauJoueur().getPanneauChoix().add(btt);
		this.repaint();
		this.validate();
	}
	
	public void passer() {
		this.initialiser();
		BoutonChoix btt = new BoutonChoix("Passer");
		btt.addActionListener
		( 
			(ActionEvent e) ->		
			{
				controleur.passer();
			}
		);
		((EcranJeu)(this.ecran)).getPanneauJoueur().getPanneauChoix().add(btt);
		this.repaint();
		this.validate();
	}
	
	public Modele_Dom getJeu() {
		return jeu;
	}
	
	public void setJeu(Modele_Dom j) {
		jeu=j;
	}

	public ControleurGraphique_Dom getControleur() {
		return controleur;
	}
	
	private class EcranDemarrage extends JPanel
	{
		
		
		@Override
		public void paintComponent(Graphics g){  
			Graphics2D g2D = (Graphics2D)g;
		   	g2D.setPaint(new Color(129, 27, 4)); 
		 	g2D.fillRect(0, 0, this.getWidth(), this.getHeight()); 
			Font font = new Font("TimesRoman", Font.BOLD, 70);
		 	g.setFont(font);
			g.setColor(Color.black); 
			g.drawString("Domino", this.getWidth()/2-120, this.getHeight()/2); 
	    } 
	}

	private class EcranJeu extends JPanel
	{
		
		private  PanelJoueur panneauJoueur=new PanelJoueur();
		private PanelPlateau panneauPlateau=new PanelPlateau();
		
		public EcranJeu() {
			
			this.setBackground(new Color(158, 97, 2));
			this.setLayout(new BorderLayout());
			
		    
		    Dimension dimP = new Dimension(new Dimension ((int)VueG_Dom.this.getSize().getWidth(), (int)VueG_Dom.this.getSize().getHeight()/6*5));
		    panneauPlateau.setPreferredSize(dimP);
		    panneauPlateau.repaint();
		    
		    this.add(panneauPlateau, BorderLayout.CENTER);

		   
		    Dimension dimJ = new Dimension(new Dimension ((int)VueG_Dom.this.getSize().getWidth(), (int)VueG_Dom.this.getSize().getHeight()/6*1));
		    panneauJoueur.setPreferredSize(dimJ);
		    panneauJoueur.repaint();
		   
		    this.add(panneauJoueur, BorderLayout.PAGE_END);
		}
		
		public PanelJoueur getPanneauJoueur()
		{
			return this.panneauJoueur;
		}
		
	}
	
	private class PanelPlateau extends JPanel
	{
	
		private Plateau plateau;
		
		public PanelPlateau()
		{
			plateau = jeu.getPlateau();
			final int hauteur = jeu.getPlateau().getHauteur();
			final int largeur = jeu.getPlateau().getLargeur();	
			this.setLayout(null);
			final int y0 = (int)dimension.getHeight()/6*5;
			final int x0 = (int)dimension.getWidth();
			final int x=x0/largeur; 
			final int y=y0/hauteur; 
			for (int h=0; h<=hauteur-2;h++) {
			for (int l=0; l<=largeur-2; l++) {
					  if (plateau.getCase(h, l)!=null) {
						  Domino d = (Domino)plateau.getCase(h, l);
						  BoutonDomino b = null;					 
						  if (h%2==0 && h%4==0)
						  {
							  b=new BoutonDomino(d,0);
							  b.setBounds(x*l+x, y*h, y, x);						  
						  }
						  else if (h%2==0 && h%4!=0)
						  {
							  b=new BoutonDomino(d,0);
							  b.setBounds(x*l-x/2, y*h, y, x);						  
						  }			 
						  else if (h%4==1) 
						  {
							  b=new BoutonDomino(d,-1);
							  b.setBounds(x*l, y*h+y/3, x, y);

						  }
						  else 
						  {
							  b=new BoutonDomino(d,1);
							  b.setBounds(x*l, y*h+y/2, x, y);
						  }
						  b.setEnabled(false);
						  b.setBorderPainted(false);
						  this.add(b);

					  }
					  
				  }
			  }
		}
		
		@Override
		public void paintComponent(Graphics g){  
			Graphics2D g2D = (Graphics2D)g;
			g2D.setPaint(new Color(14, 27, 71)); 
			
			g2D.fillRect(0, 0, this.getWidth(), this.getHeight()); 
		} 
	}
	
	private class PanelJoueur extends JPanel
	{
		
		private Joueur joueur = VueG_Dom.this.getJeu().getJoueurs().get(VueG_Dom.this.getJeu().getCourant());
		private JPanel panneauMain , panneauChoix;

		public PanelJoueur()
		{
			JoueurD jc = (JoueurD)joueur;
			this.setSize((int)dimension.getWidth(),(int)dimension.getHeight()/6);
			panneauMain = new JPanel();
			panneauMain.setLayout(new FlowLayout());
			Dimension dim = new Dimension((int)this.getSize().getWidth(), (int)this.getSize().getHeight()/6*5);
			panneauMain.setPreferredSize(dim) ;
			this.setBackground(Color.BLACK);
			panneauMain.setBackground(new Color(255,255,255)); 
			
			
			JLabel label = new JLabel(joueur.toString());
    		label.setBounds(40,1,100,25);
			panneauMain.add(label);
  
    		for (int i=0; i<jc.getMain().size(); i++) {
    			BoutonDomino domino = new BoutonDomino(jc.getMain().get(i),0,i);
    			panneauMain.add(domino);
    		}

		    this.panneauChoix = new JPanel();
		    this.panneauChoix.setBackground(Color.DARK_GRAY);
		    Dimension dimS = new Dimension((int)this.getSize().getWidth(), (int)this.getSize().getHeight()/6*1);
		    this.panneauChoix.setLayout(new GridLayout(1,3));
		    
			this.setLayout(new BorderLayout());
			this.add(this.panneauMain, BorderLayout.CENTER);
			this.add(this.panneauChoix, BorderLayout.PAGE_END);
			
			
		}
		
		
		
		public JPanel getPanneauMain() {
			return panneauMain;
		}

		public JPanel getPanneauChoix()
		{
			return panneauChoix;
		}
		
	
	}

	private class BoutonDomino extends JButton
	{
		private Domino domino;
		private int position;
		public BoutonDomino(Domino d,int p, int num)
		{
			super("");
			position=p;
			this.domino=d;
			if (p!=0) this.setPreferredSize(new Dimension(60,30));
			else this.setPreferredSize(new Dimension(30,60));
			this.setBorderPainted(false);
			this.setFocusPainted(false);
			this.setContentAreaFilled(false);
			this.addActionListener
			(
					(ActionEvent event) -> 
					{ 
						controleur.setChoixCarte(num);
						controleur.choisir(domino);
					}
			);		
		}
		
		public BoutonDomino(Domino d)
		{
			this(d,0,-1);		
		}
		
		public BoutonDomino(Domino d,int p)
		{
			this(d,p,-1);		
		}
		
		@Override
		public void paintComponent(Graphics g){
			 BufferedImage imageH = null;
			 BufferedImage imageB = null;
			 int haut = this.domino.getValeurs().getGauche();
			 int bas = this.domino.getValeurs().getDroite();
			 try {
			      imageH = ImageIO.read(new File("../Images fournies-20181220/Dice"+haut+".png"));
			      imageB = ImageIO.read(new File("../Images fournies-20181220/Dice"+bas+".png"));
			 } catch (IOException e) {
			      e.printStackTrace();
			 }
			
			 	Graphics2D g2D = (Graphics2D)g;
			 	if (position==1) {
				   	g2D.setPaint(Color.WHITE);
				 	g2D.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20);
				 	g2D.setPaint(Color.BLACK);	 	
				 	
				    g2D.drawImage(imageH, 0, 0, this.getWidth()/2, this.getHeight(), this);
				 	g2D.drawImage(imageB, this.getWidth()/2,0, this.getWidth()/2, this.getHeight(), this);
				 	
				 	g2D.setStroke(new BasicStroke(3));
				 	g2D.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10); 
				 	
			 	}
			 	else if (position==-1) {
				   	g2D.setPaint(Color.WHITE);
				 	g2D.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20); 
				 	g2D.setPaint(Color.BLACK);
				 	
				 	
				    g2D.drawImage(imageB, 0, 0, this.getWidth()/2, this.getHeight(), this);
				 	g2D.drawImage(imageH, this.getWidth()/2, 0,this.getWidth()/2, this.getHeight(), this);
				 	
				 	g2D.setStroke(new BasicStroke(3));
				 	g2D.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
				 
			 	}
			 	else if (position==0 && this.isEnabled()){
				   	g2D.setPaint(Color.WHITE);
				 	g2D.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20); 
				 	g2D.setPaint(Color.BLACK);
				 	
				    g2D.drawImage(imageH, 0, 0, this.getWidth(), this.getHeight()/2, this);
				 	g2D.drawImage(imageB, 0, this.getHeight()/2, this.getWidth(), this.getHeight()/2, this);
				 	
				 	g2D.setStroke(new BasicStroke(3));
				 	g2D.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 1, 1);
			 	}
			 	else if (position==0 && !this.isEnabled()){
				   	g2D.setPaint(Color.WHITE); 
				 	g2D.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 20, 20); 
				 	g2D.setPaint(Color.BLACK);
				 	
				    g2D.drawImage(imageH, 0, 0, this.getWidth(), this.getHeight()/2, this);
				 	g2D.drawImage(imageB, 0, this.getHeight()/2, this.getWidth(), this.getHeight()/2, this);
				 	
					g2D.setStroke(new BasicStroke(3));
				 	g2D.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10); 
			 	}
			 	
			 	if (this.getModel().isPressed())
			 	{
				 	g2D.setPaint(Color.GREEN);
				 	g2D.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 1, 1); 
			 	}			 				 				 	
		}				
	}
		
	
		
		private class BoutonChoix extends JButton
		{
			private BoutonChoix(String s)
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
	
	private class EcranFin extends JPanel
	{
	
		private PanelRes panneauRes=new PanelRes();
		private PanelPlateau panneauPlateau=new PanelPlateau();
	
		public EcranFin() {
			this.setLayout(new BorderLayout());
			Dimension dimP = new Dimension(new Dimension ((int)VueG_Dom.this.getSize().getWidth(), (int)VueG_Dom.this.getSize().getHeight()/6*5));
			panneauPlateau.setPreferredSize(dimP);
			panneauPlateau.repaint();
			
			this.add(panneauPlateau, BorderLayout.CENTER);
					
			Dimension dimJ = new Dimension(new Dimension ((int)VueG_Dom.this.getSize().getWidth(), (int)VueG_Dom.this.getSize().getHeight()/6*1));
			panneauRes.setPreferredSize(dimJ);
			panneauRes.setLayout(new FlowLayout(FlowLayout.CENTER));
			panneauRes.repaint();
			this.add(panneauRes, BorderLayout.PAGE_END);				
		}
			
		public JPanel getPanneauRes(){
			return panneauRes;
		}
	}
	
	private class PanelRes extends JPanel{
		@Override
		public void paintComponent(Graphics g){  
			Graphics2D g2D = (Graphics2D)g;
			g2D.setPaint(new Color(129, 27, 4));
			g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
			String s = "";
			g.setColor(Color.WHITE);
			Font font = new Font("TimesRoman", Font.BOLD, 20);
		 	g.setFont(font);
			for (int i=0; i<jeu.joueurs.size(); i++)
			{
				s=jeu.getJoueurs().get(i)+"  ~  Valeur des dominos restants : "+((JoueurD)(jeu.getJoueurs().get(i))).getPoints()+"  ~  Victoires : "+jeu.getJoueurs().get(i).getVictoires()+"\n";
				g.drawString(s, 150, 50+i*20);
			}						
		} 
	}					
}
