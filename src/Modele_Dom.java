import java.util.ArrayList;
import java.util.Collections;

public class Modele_Dom extends Jeu {
	
  protected ArrayList<Domino> pioche;
  protected Duo coorNextG;
  protected Duo coorNextD;
  protected Domino droite;
  protected Domino gauche;

  public Modele_Dom() {
	  
  }
  public Modele_Dom(int nbrDeJoueurs) 
  {
	  
	  if (nbrDeJoueurs<2 || nbrDeJoueurs>4) nbrDeJoueurs=2;
	  for (int i=0; i<nbrDeJoueurs; i++) joueurs.add(new JoueurD());	
	  initialisation();
  }
  
  public void demarrer()
  {
	  initialisation();
  }
  public void tour(int choixDomino)
  {
	  JoueurD jc = (JoueurD)joueurs.get(courant);
	  jc.getMain().remove(choixDomino);
	  if (!estFini()) next();
  }
  
  public void debloquer()
  {
	  if (isBloque() && pioche.size()>0)
	  {
		  piocher();
		  passerTour();
	  }
  }
  
  public void passerTour() 
  {
		 if (!estFini()) next();
  }
  
  public void piocher() 
  {
	  	 JoueurD jc = ((JoueurD)(joueurs.get(courant)));
		 if (!estFini() && pioche.size()>0 && isBloque() ) 
			 {
			 	jc.getMain().add(pioche.get(0));
			 	pioche.remove(0);
			 }
  }
  
  public boolean isBloque() {
	  	 JoueurD jc = ((JoueurD)(joueurs.get(courant)));
	  	 for (int i=0; i<jc.getMain().size(); i++)
	  	 {
	  		 if (posable(jc.getMain().get(i)))
	  				 {
	  			 		return false;
	  				 }
	  	 }
	  	 return true;
  }
  
  public boolean toutBloque() {
	  int cr = courant;
	  boolean bloque = true;
	  for (int i=0; i<joueurs.size(); i++) {
		  	courant = i;
		  	bloque = bloque && isBloque();
	  }
	  courant = cr;
	  return bloque;
  }
  
  public void setGain()
  {
	  for (int i=0; i<joueurs.size();i++) {
		  JoueurD jc = ((JoueurD)(joueurs.get(i)));
		  if (jc.getMain().size()==0) jc.setPoints(0);
		  else 
		  {
			  int pts = 0;
			  for (int j=0; j<jc.getMain().size(); j++)
			  {
				  pts+=jc.getMain().get(j).getPoints();
			  }
			  jc.setPoints(pts);
		  }
	  }
  }
  
  public void finirJeu() {
	  if (!estFini())
		 {
			 return;
		 }
		 else
		 {
			 setGain();
			 int gain=( (JoueurD)(joueurs.get(0)) ).getPoints();
			 for (int i=1; i<joueurs.size(); i++) 
			 {
				 if (gain>( (JoueurD)(joueurs.get(i)) ).getPoints())
			     {
					 	gain=( (JoueurD)(joueurs.get(i)) ).getPoints();
				 }
			 }
			 for (int i=0; i<joueurs.size(); i++) 
			 {
				 if (gain==( (JoueurD)(joueurs.get(i)) ).getPoints()) joueurs.get(i).addVictoire();
			 }

		 }
  }

  public boolean estFini() {
	  for (int i=0; i<joueurs.size(); i++) {
		  	 JoueurD jc = ((JoueurD)(joueurs.get(i)));
		  	 if (jc.getMain().size()==0) return true;
	  }
	  return toutBloque();
  }
  
  public boolean poser(Domino d, int choix)
  {
	  switch(choix)
	  {
	  	case 0 : 
	  		
	  		return false;
	  	case 1 : 
	  		if (posableG(d))
	  		{
	  			poser(d,coorNextG);
	  			gauche=d;
	  			updateG();
	  			return true;
	  		}
	  		return false;
	  	case 2 : 
	  		if (posableD(d)) 
	  		{
	  			poser(d,coorNextD);
	  			droite=d;
	  			updateD();
	  			return true;
	  		}
	  		return false;
	  	default :
	  		return false;
	  }
  }
  
  public boolean posable(Domino c) {
	  return posableG(c) || posableD(c);
  }
  
  public boolean posableGD(Domino c) {
	  return posableG(c) && posableD(c);
  }
  
  public boolean posableG(Domino c) {
	 if (c.getValeurs().getDroite()==gauche.getValeurs().getGauche()) {
		 return true;
	 }
	 else if (c.getValeurs().getGauche()==gauche.getValeurs().getGauche()) {
		 c.pivoter();
		 return true;
	 }
	 else {
		 return false;
	 }
  }
  public boolean posableD(Domino c) {
		 if (c.getValeurs().getGauche()==droite.getValeurs().getDroite()) {
			 return true;
		 }
		 else if (c.getValeurs().getDroite()==droite.getValeurs().getDroite()) {
			 c.pivoter();
			 return true;
		 }
		 else {
			 return false;
		 }
	  }
  public void poser(Domino d, Duo coor)
  {
	  plateau.remplirCase(coor.getGauche(),coor.getDroite(),d);
  }
  
  public void updateG() {
	  int h = coorNextG.getGauche();
	  int l = coorNextG.getDroite();
	  if (plateau.horsLimites(h, l-1) && h%4!=1)
	  {
		  h--;
	  }
	  else if (plateau.horsLimites(h, l+1) && h%4==1)
	  {
		  h--;
	  }
	  else if (h%2==0)
	  {
		  h--;
	  }
	  else if (h%4==1)
	  {
		  l++;
	  }
	  else
	  {
		  l--;
	  }
	  coorNextG = new Duo(h,l);
  }
  
  public void updateD() {
	  int h = coorNextD.getGauche();
	  int l = coorNextD.getDroite();
	  if (plateau.horsLimites(h, l+1) && h%4!=1)
	  {
		  h++;
	  }
	  else if (plateau.horsLimites(h, l-1) && h%4==1)
	  {
		  h++;
	  }
	  else if (h%2==0)
	  {
		  h++;
	  }
	  else if (h%4==1)
	  {
		  l--;
	  }
	  else
	  {
		  l++;
	  }
	  coorNextD = new Duo(h,l);
  }
  
  
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
			  pioche.add(new Domino(new Duo(i,j)));
		  }
	  }
	  
	  Collections.shuffle(pioche);
	  switch (nbrDeJoueurs)
	  {
	  	case 2 : 
		  for (int i=0; i<nbrDeJoueurs; i++)
			{ 
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
				for (int j=0;j<6;j++)
				{
					((JoueurD)joueurs.get(i)).getMain().add(pioche.get(0));
					pioche.remove(0);
				}
			}
			break;
	  }
	  
	  Domino domRef = new Domino(new Duo(-1,0));
	  int numJref = -1;
	  for (int j=0; j<nbrDeJoueurs; j++)
	  {
		  JoueurD joueur = ((JoueurD)joueurs.get(j));
		  for (int i=0; i< joueur.getMain().size() ; i++ )
		  {
			  Domino dom=joueur.getMain().get(i);
			  if (dom.isDouble())
			  {
				  if (dom.getPoints()>domRef.getPoints())
				  {
					  domRef = dom;
					  numJref=j;
				  }
			  }
		  }
	  }
	  if (domRef.getPoints()!=-1 && numJref!=-1 ) {
		  courant = numJref;
		  plateau.remplirCase(7, 5, domRef);
		  droite = domRef;
		  gauche = domRef;
		  ((JoueurD)joueurs.get(courant)).getMain().remove(domRef);
		  next();
		  return;
	  }
	  for (int j=0; j<nbrDeJoueurs; j++)
	  {
		  JoueurD joueur = ((JoueurD)joueurs.get(j));
		  for (int i=0; i< joueur.getMain().size() ; i++ )
		  {
			  Domino dom=joueur.getMain().get(i);
			  if (dom.getPoints()>domRef.getPoints())
			  {
					  domRef = dom;
					  numJref=j;
			  }
		  }
	  }
	  if (domRef.getPoints()!=-1 && numJref!=-1) {
		  courant = numJref;
		  plateau.remplirCase(7, 5, domRef);
		  droite = domRef;
		  gauche = domRef;
		  ((JoueurD)joueurs.get(courant)).getMain().remove(domRef);
		  next();
		  return;
	  }
  }

  public ArrayList<Domino> getPioche() {
		return pioche;
	}
  
  	

  
  

  	
}