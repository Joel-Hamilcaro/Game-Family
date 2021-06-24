
public class ControleurGraphique_Dom  {
	private VueG_Dom vue;
	private Modele_Dom jeu;
	private int choixAction=-1;
	private int choixCarte=-1;
	private boolean confirmer=false;
	
	public ControleurGraphique_Dom(VueG_Dom v)
    { 
    	vue=v;
    }
	  
	
	public void demarrer()
	{
		int nbDeJoueur = this.choixAction;
		
		jeu = new Modele_Dom(nbDeJoueur);
		vue.setJeu(jeu);
		vue.initialiser();
		if(!jeu.estFini()) 
		{
			jouerTour();		
		}
	 }
	
	public void jouerTour()
	{

		Plateau plateau = jeu.getPlateau();
		JoueurD joueur = ((JoueurD)jeu.getJoueurs().get(jeu.getCourant()));
			
		if(!confirmer)
		{		
			if(jeu.isBloque()) 
			{
				if (jeu.getPioche().size()>0)
				{
					vue.initialiser();
					vue.repaint();
					vue.validate();
					return;
				}
				else 
				{
					vue.passer();
					vue.repaint();
					vue.validate();
					return;
				}
			}		
			vue.initialiser();
			return;
		}
		jeu.tour(choixCarte);
		choixCarte=-1;
		choixAction=-1;
		confirmer = false;
		if (!jeu.estFini()) vue.initialiser();
		else 
			finirJeu();
		return;
	}
				
	
	public void choisir(Domino domino) {
		JoueurD joueur = ((JoueurD)jeu.getJoueurs().get(jeu.getCourant()));
		vue.choisir(domino);	
		return;
	}
	
	public void action(Domino domino) {
		confirmer = jeu.poser(domino, choixAction); 
		jouerTour();
	}
	
	public void piocher() {
		jeu.piocher();	
		passer();
	}
	
	public void passer() {
		jeu.passerTour();	
		choixCarte=-1;
		choixAction=-1;
		confirmer = false;
		if (!jeu.estFini()) vue.initialiser();
		else finirJeu(); 
	}
	
	public void finirJeu()
	{
		jeu.finirJeu();
		vue.finirJeu();
	}

	public void setJeu(Modele_Dom jeu) {
		this.jeu = jeu;
	}
	
	
	public void setChoixCarte(int choix) {
		this.choixCarte = choix;
	}
	public void setChoixAction(int choix) {
		this.choixAction = choix;
	}


	public Modele_Dom getJeu() {
		return jeu;
	}
	
	
	


}
