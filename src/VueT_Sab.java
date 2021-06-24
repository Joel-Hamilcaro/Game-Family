import java.util.ArrayList;


public class VueT_Sab extends VueTextuelle
{

	private Modele_Sab jeu;

	public VueT_Sab()
	{
		controleur = new ControleurText(this);
	}

	public void demarrer() throws JeuFiniException
	{
		System.out.println("*********************");
		System.out.println("*      SABOTEUR     *");
		System.out.println("*********************");
		System.out.println("Nombre de joueurs : ");
		
		int nbDeJoueur = controleur.askInt(3,10);
		
		jeu = new Modele_Sab(nbDeJoueur);
		
		while(!jeu.estFini()) 
		{
			System.out.println("*********************");
			System.out.println("*      MANCHE "+jeu.getManche()+"     *");
			System.out.println("*********************");
			jouer();		
		}
		finirJeu();
	 }
	
	public void jouer() throws JeuFiniException
	{
		while(!jeu.mancheFini())
		{
			System.out.println("*********************************************");
			jouerTour();
		}
		finirManche();
		jeu.newManche();
		
	}
	public void jouerTour() throws JeuFiniException
	{

		Plateau plateau = jeu.getPlateau();
		JoueurS joueur = ((JoueurS)jeu.getJoueurs().get(jeu.getCourant()));
		String role = joueur.getRole();
		ArrayList<Cartes> main = joueur.getMain();	
		System.out.println(plateau);
		if (main.size()==0)
		{
			System.out.println("Vous n'avez plus de cartes en main");
			jeu.passerTour();
			return;
		}

		int choix = -1;
		boolean confirmer = false;
		while(!confirmer)
		{
			System.out.println("\n"+joueur+" ~ "+role);
			System.out.println(joueur.mainToString());
			System.out.print("Choisir carte : ");
			choix = controleur.askInt(1,main.size())-1;
			Cartes carte = main.get(choix);
			if (carte instanceof Action)
			{
				
				confirmer = choisir((Action)(main.get(choix))); 
			}
			if (carte instanceof Chemin)
			{
				confirmer = choisir((Chemin)(main.get(choix))); 
			}
		}
		jeu.tour(choix);
		
	}
	public boolean choisir(Action a) throws JeuFiniException 
	{
		System.out.println("Carte action :");
		for (int i=0; i<a.getActions().size();i++) 
		{
			System.out.print(i+":"+a.getActions().get(i)+"   ");
		}
		int choix = controleur.askInt(0,a.getActions().size());
		if (a instanceof Decouverte)
		{			
			try {
				return jeu.decouvrir((Decouverte)a,choix);
			} catch (DecouverteException e) {
				System.out.println(e);
				return true;
			}		
		}
		if (a instanceof Eboulement)
		{			
			try {
				return jeu.ebouler((Eboulement)a,choix);
			} catch (ChoixCaseException e) {
				System.out.println(e.getMessage());
				System.out.println("Coordonees de la case");
				System.out.print("   Hauteur:");
				int h = controleur.askInt();
				System.out.print("   Largeur:");
				int l = controleur.askInt();
				if (jeu.eboulable(h,l))
				{
					jeu.ebouler((Eboulement)a,h,l); 
					return true;
				}
				else 
				{
					System.out.println("Ce n'est pas un chemin eboulable");
					return false;
				}
			}		
		}
		if (a instanceof Destruction)
		{
			try {
				return jeu.detruire((Destruction)a,choix);
			} 
			catch (DestructionException e) 
			{
				System.out.println(e.getMessage());
				for (int j=0; j<jeu.getJoueurs().size(); j++) System.out.print(jeu.getJoueurs().get(j)+"   ");;
				int k = controleur.askInt(1,jeu.getJoueurs().size());
				if (jeu.outilDestructible((Destruction)a,(JoueurS)jeu.getJoueurs().get(k-1)))
				{
					jeu.detruireOutil((Destruction)a,(JoueurS)jeu.getJoueurs().get(k-1));
					return true;
				}
				else 
				{
					System.out.println("Vous ne pouvez pas effetuer cette action.");
					return false;
				}
			}
		}
		if (a instanceof Reparation)
		{
			try {
				return jeu.reparer((Reparation)a,choix);
			} 
			catch (ReparationException e) 
			{
				System.out.println(e.getMessage());
				for (int j=0; j<jeu.getJoueurs().size(); j++) System.out.print(jeu.getJoueurs().get(j)+"   ");;
				int k = controleur.askInt(1,jeu.getJoueurs().size());
				if (jeu.outilReparable((Reparation)a,(JoueurS)jeu.getJoueurs().get(k-1)))
				{
					jeu.reparerOutil((Reparation)a,(JoueurS)jeu.getJoueurs().get(k-1));
					return true;
				}
				else 
				{
					System.out.println("Vous ne pouvez pas effetuer cette action.");
					return false;
				}
			}
		}
		return false;	
	}
	public boolean choisir(Chemin a) throws JeuFiniException
	{
		System.out.println("Carte chemin :");
		for (int i=0; i<a.getActions().size();i++) 
		{
			System.out.print(i+":"+a.getActions().get(i)+"   ");
		} 
		
		int choix = controleur.askInt(0,a.getActions().size());
		
		try {
			return jeu.actionDeChemin((Chemin)a,choix);
		} catch (ChoixCaseException e) {
			System.out.println(e.getMessage());
			System.out.println("Coordonees de la case");
			System.out.print("   Hauteur:");
			int h = controleur.askInt();
			System.out.print("   Largeur:");
			int l = controleur.askInt();
			if (jeu.posable(a,h,l))
			{
				jeu.poser(a,h,l); 
				return true;
			}
			else 
			{
				System.out.println("On ne peut pas poser ce chemin ici.");
				return false;
			}
		}	
	}
	
	public void finirManche()
	{
		jeu.finirManche();
		
		System.out.println(jeu.getPlateau().toString());
		System.out.println("*********************************************");
		for (int i=0; i<jeu.joueurs.size(); i++)
		{
			System.out.print(((JoueurS)jeu.getJoueurs().get(i)));
			if (((JoueurS)jeu.getJoueurs().get(i)).isChercheur()) 
			{
				System.out.print(" CHERCHEUR ");
				System.out.println(" Gains : "+((JoueurS)jeu.getJoueurs().get(i)).getGain() );

			}
			else
			{
				System.out.print(" SABOTEUR ");
				System.out.println(" Gains : "+((JoueurS)jeu.getJoueurs().get(i)).getGain() );
			}
		}
		System.out.println("*********************************************");
	}

	public void finirJeu()
	{
		jeu.finirJeu();
		
		System.out.println("\n*********************************************");
		for (int i=0; i<jeu.joueurs.size(); i++)
		{
			System.out.println(((jeu.getJoueurs().get(i)))+" ~ Victoires : "+jeu.getJoueurs().get(i).getVictoires());
		}
		System.out.println("*********************************************");
	}

	
	
	
		
	
	
}
