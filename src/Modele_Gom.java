import java.util.ArrayList;
import java.util.Collections;

public class Modele_Gom extends Modele_Dom {

  private int manche=1;

  
  public Modele_Gom(int nbrDeJoueurs) {
	  if (nbrDeJoueurs<2 || nbrDeJoueurs>4) nbrDeJoueurs=2;
	  for (int i=0; i<nbrDeJoueurs; i++) joueurs.add(new JoueurD());		  
	  initialisation();
  }
  
  public boolean toutEstFini() {
	  return (this.manche>=3 && super.estFini());
  }
  
  @Override
  public void setGain()
  {
	  if (!estFini()) return;
	  for (int i=0; i<joueurs.size();i++) 
	  {
		  JoueurD jc = ((JoueurD)(joueurs.get(i)));
		  jc.addPoints(jc.getMain().size());
	  }
  }
  
  public void newManche() {
	  initialisation();
	  manche++;
  }

  @Override
  public boolean posableG(Domino c) {
		if (manche==3) return super.posableG(c);
		else if (manche==2)
		{
			if (Gommette.color(c.getValeurs().getDroite())==Gommette.color(gauche.getValeurs().getGauche())) {
				 return true;
			 }
			 else if (Gommette.color(c.getValeurs().getGauche())==Gommette.color(gauche.getValeurs().getGauche())) {
				 c.pivoter();
				 return true;
			 }
			 else {
				 return false;
			}
		}
		else if (manche==1)
		{
			if (Gommette.forme(c.getValeurs().getDroite())==Gommette.forme(gauche.getValeurs().getGauche())) {
				 return true;
			 }
			 else if (Gommette.forme(c.getValeurs().getGauche())==Gommette.forme(gauche.getValeurs().getGauche())) {
				 c.pivoter();
				 return true;
			 }
			 else {
				 return false;
			}
		}
		return false;
  }
  
  @Override
  public boolean posableD(Domino c) {
	  	if (manche==3) return super.posableD(c);
		else if (manche==2)
		{
			 if (Gommette.color(c.getValeurs().getGauche())==Gommette.color(droite.getValeurs().getDroite())) {
				 return true;
			 }
			 else if (Gommette.color(c.getValeurs().getDroite())==Gommette.color(droite.getValeurs().getDroite())) {
				 c.pivoter();
				 return true;
			 }
			 else {
				 return false;
			 }
		 }
		else if (manche==1)
		{
			 if (Gommette.forme(c.getValeurs().getGauche())==Gommette.forme(droite.getValeurs().getDroite())) {
				 return true;
			 }
			 else if (Gommette.forme(c.getValeurs().getDroite())==Gommette.forme(droite.getValeurs().getDroite())) {
				 c.pivoter();
				 return true;
			 }
			 else {
				 return false;
			 }
		}
	  	return false;
  }
  
  
  @Override
  public void initialisation() {
		  int nbrDeJoueurs = joueurs.size();
		  plateau = new Plateau(15,11);
		  coorNextG = new Duo(7,4);
		  coorNextD = new Duo(7,6);
		  
		  pioche = new ArrayList<Domino>();
		  for (int i=6; i>=0; i--) 
		  {
			  for (int j=0; j<=i; j++) 
			  {
				  pioche.add(new Gommette(new Duo(i,j)));
			  }
		  }
		  
		  Collections.shuffle(pioche);
		  switch (nbrDeJoueurs)
		  {
		  	case 2 : 
			  for (int i=0; i<nbrDeJoueurs; i++)
				{ 
					((JoueurD)joueurs.get(i)).removeMain();
					for (int j=0;j<7;j++)
					{
						((JoueurD)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);

					}

				}
				break;
		  	default : 
		  		for (int i=0; i<nbrDeJoueurs; i++)
				{ 
					((JoueurD)joueurs.get(i)).removeMain();
					for (int j=0;j<6;j++)
					{
						((JoueurD)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
				break;
		  	}
		 
			  courant = 0+(int)(Math.random()*joueurs.size());
			  plateau.remplirCase(7,5,pioche.get(0));
			  droite = pioche.get(0);
			  gauche = pioche.get(0);
			  pioche.remove(0);
			  return;
		  }
	  

  public int getManche() {
	  return manche;
  }
  
  

}