import java.util.ArrayList;


public class VueT_Dom  extends VueTextuelle
{		
	protected Modele_Dom jeu;
	
	public VueT_Dom()
	{
		controleur = new ControleurText(this);
	}
	
	public void demarrer() throws JeuFiniException
	{
		System.out.println("*********************");
		System.out.println("*       DOMINO      *");
		System.out.println("*********************");
		System.out.println("Nombre de joueurs : ");
		
		int nbDeJoueur = controleur.askInt(2,4);
		
		jeu = new Modele_Dom(nbDeJoueur);
		while(!jeu.estFini()) 
		{
			jouerTour();		
		}
		finirJeu();
	 }
	
	public void jouerTour() throws JeuFiniException
	{
		JoueurD joueur = ((JoueurD)jeu.getJoueurs().get(jeu.getCourant()));
		ArrayList<Domino> main = joueur.getMain();
		afficherPlateau(jeu.getPlateau());			
			int choix = -1;
			boolean confirmer = false;
		while(!confirmer)
		{
			System.out.println("\n"+joueur);
			System.out.println(joueur.mainToString());
			if(jeu.isBloque()) 
			{
				System.out.println("Vous etes bloque\n");
				jeu.debloquer();
				return; 
			}
				System.out.print("Choisir domino : ");
				choix = controleur.askInt(1,main.size())-1;
				Domino domino = main.get(choix);
				confirmer = choisir(domino); 
		}
		jeu.tour(choix);
				
	}
	
	public boolean choisir(Domino domino) throws JeuFiniException
	{
		System.out.println(domino);
		for (int i=0; i<domino.getActions().size();i++) 
		{
			System.out.print(i+":"+domino.getActions().get(i)+"   ");
		}
		int choix = controleur.askInt(0,domino.getActions().size());
		return jeu.poser(domino,choix);
		
	}
	
	public void finirJeu()
	{
		jeu.finirJeu();
		System.out.println("\n*********************************************");
		for (int i=0; i<jeu.joueurs.size(); i++)
		{
			System.out.println(((jeu.getJoueurs().get(i)))+" ~ Point :"+((JoueurD)(jeu.getJoueurs().get(i))).getPoints()+" ~ Victoires : "+jeu.getJoueurs().get(i).getVictoires());
		}
		System.out.println("*********************************************");		
	}

	public void setJeu(Modele_Dom jeu) {
		this.jeu = jeu;
	}
	
	public static void afficherPlateau(Plateau plateau) {
		int largeur = plateau.getLargeur();
		int hauteur = plateau.getHauteur();
		String s = "    ";
		  for (int l=1; l<largeur-1; l++) {
			  s+=String.format("|%2d|", l);
		  }
		  for (int h=1; h<hauteur-1;h++) {
			  s+="\n"+String.format("|%2d|", h);
			  for (int l=1; l<largeur-1; l++) {
				  Domino b = (Domino)(plateau.getCase(h, l));
				  if (b==null) s+="    ";
				  else 
				  {	
					  if (h%2==0 && h%4==0)
					  {
						  s+=b.toString0mod4();						  
					  }
					  else if (h%2==0 && h%4!=0)
					  {
						  s+=b.toString2mod4();					  
					  }					 
					  else if (h%4==1) 
					  {
						  s+=b.toStringReverse();
					  }
					  else 
					  {
						  s+=b.toString();
					  }				 
				  }
			  }
		  }
		  System.out.println(s);
	}
	
	
}
