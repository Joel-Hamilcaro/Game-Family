
public class VueT_Gom extends VueT_Dom {
	
	public VueT_Gom()
	{
		super();
	}
	
	public void demarrer() throws JeuFiniException
	{
		System.out.println("*********************");
		System.out.println("*  DOMINO-GOMMETTES *");
		System.out.println("*********************");
		System.out.println("Nombre de joueurs : ");
		
		int nbDeJoueur = controleur.askInt(2,4);
		
		jeu = new Modele_Gom(nbDeJoueur);
		while(!((Modele_Gom)jeu).toutEstFini()) 
		{
			System.out.println("*********************");
			System.out.println("*      MANCHE "+((Modele_Gom)jeu).getManche()+"     *");
			System.out.println("*********************");
			jouerManche();		
		}
		finirJeu();
	 }
	
	public void jouerManche() throws JeuFiniException
	{
		while(!((Modele_Gom)jeu).estFini())
		{
			System.out.println("*********************************************");
			jouerTour();
		}
		finirManche();
		((Modele_Gom)jeu).newManche();
	}
	
	public void finirManche()
	{
		((Modele_Gom)jeu).setGain();
		System.out.println("\n*********************************************");
		for (int i=0; i<((Modele_Gom)jeu).joueurs.size(); i++)
		{
			System.out.println(((((Modele_Gom)jeu).getJoueurs().get(i)))+" ~ Point :"+((JoueurD)(((Modele_Gom)jeu).getJoueurs().get(i))).getPoints());
		}
		System.out.println("*********************************************");		
	}

	public void finirJeu()
	{
		((Modele_Gom)jeu).finirJeu();
		System.out.println("\n*********************************************");
		for (int i=0; i<((Modele_Gom)jeu).joueurs.size(); i++)
		{
			System.out.println(((((Modele_Gom)jeu).getJoueurs().get(i)))+" ~ Victoires : "+((Modele_Gom)jeu).getJoueurs().get(i).getVictoires());
		}
		System.out.println("*********************************************");		
	}

	
}
