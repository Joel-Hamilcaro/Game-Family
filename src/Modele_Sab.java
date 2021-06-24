import java.util.ArrayList;
import java.util.Collections;

public class Modele_Sab extends Jeu {
		
	  private ArrayList<Cartes> pioche;
	  private ArrayList<Cartes> defausse;
	  private int manche;

	  public Modele_Sab(int nbrJoueurs)
	  {
		  if (nbrJoueurs<3 || nbrJoueurs>10) nbrJoueurs=3; // default	  
		  for (int i=0; i<nbrJoueurs; i++) joueurs.add(new JoueurS());	  	  
		  newManche(1);
	  }
 
	  public void tour(int choixCarte)
	  {
		  JoueurS jc = (JoueurS)joueurs.get(courant);
		  jc.getMain().remove(choixCarte);
		  if (pioche.size()>0)
		  {
			  jc.getMain().add(pioche.get(0));
			  pioche.remove(0);
		  }
			 if (!mancheFini()) next();
	  }
	  public void passerTour() {
			 if (!mancheFini()) next();
	  }
	  
	  public void finirManche()
	  {
		 if (!mancheFini())
		 {
			 return;
		 }
		 else
		 {
			 setGain();
		 }
	  }
	  
	  public void newManche(){
		  newManche(manche+1);
	  }
	  
	  public void finirJeu() {
		  if (!estFini())
			 {
				 return;
			 }
			 else
			 {
				 int gain = 0;
				 for (int i=0; i<joueurs.size(); i++) 
				 {
					 if (gain<( (JoueurS)(joueurs.get(i)) ).getGain()) gain=( (JoueurS)(joueurs.get(i)) ).getGain();
				 }
				 for (int i=0; i<joueurs.size(); i++) 
				 {
					 if (gain==( (JoueurS)(joueurs.get(i)) ).getGain()) joueurs.get(i).addVictoire();
				 }

			 }
	  }
	  
	  @Deprecated
	  public void tour(int i, boolean defausser)
	  {
		  if (i!=-1)
		  {
			  JoueurS jc = (JoueurS)joueurs.get(courant);
			  Cartes c = jc.getMain().get(i);
			  if (c instanceof Decouverte || c instanceof Eboulement || c instanceof Reparation || defausser) defausse.add(jc.getMain().get(i));
			  jc.getMain().remove(i);
			  if (pioche.size()>0)
			  {
				  jc.getMain().add(pioche.get(0));
				  pioche.remove(0);
			  }
		  }
		  next();
	  }
	  
	  public boolean estFini() {
		  return manche>3;
	  }
	  
	   public boolean mancheFini() {
	   		return (tresorTrouve() || plusDeCartes());
	   }
	  
	   public boolean tresorTrouve()
	   {
	 	  return (!((Arrivee)(plateau.getCase(8, 14))).estCache() && ((Arrivee)(plateau.getCase(8, 14))).estTresor()) ||
	 			 (!((Arrivee)(plateau.getCase(10, 14))).estCache() && ((Arrivee)(plateau.getCase(10, 14))).estTresor()) ||
	 			 (!((Arrivee)(plateau.getCase(12, 14))).estCache() && ((Arrivee)(plateau.getCase(12, 14))).estTresor()) ;
	   }
	   
	   public boolean plusDeCartes() 
	   {
		   for (int i=0; i<joueurs.size(); i++) 
		   {
			   if ( ( (JoueurS)( joueurs.get(i) ) ).getMain().size()!=0 ) return false;
		   }
		   return true;
	   }
	   
	  
	  
	    public boolean vainqueurs()
	    {
	    	if (mancheFini() && tresorTrouve()) return true;
	    	if (mancheFini() && plusDeCartes()) return false;
	    	else return false;
	    }
	  
	   public void setGain()
	   {
		   if (vainqueurs())
		   {
			   int c = courant;
			   if ((((JoueurS)(joueurs.get(c))).isChercheur())) ((JoueurS)(joueurs.get(c))).addGain(500);
			   int i=500;
			   while(i>0) {
				   next();
				   if (courant!=c && ((JoueurS)(joueurs.get(courant))).isChercheur()) 
				   {
					   ((JoueurS)(joueurs.get(courant))).addGain(1);
					   i--;
				   }
			   }
		   }
		   else
		   {
			   int i=1000;
			   while(i>0) 
			   {
				   next();
				   if (!(((JoueurS)(joueurs.get(courant))).isChercheur())) 
				   {
					   ((JoueurS)(joueurs.get(courant))).addGain(1);
					   i--;
				   }
			   }
		   }
	   }
	   
	   
	   
	   
	   public boolean decouvrir(Decouverte a, int choix) throws DecouverteException
	   {
		   switch(choix) 
			  {
			  	case 0 :
			  		return false;
			  	case 1 : 
			  		defausse.add(a);
			  		return true;
				case 2 : 
			  		defausse.add(a);
					throw new DecouverteException(getArrivee1());					
				case 3 :
			  		defausse.add(a);
					throw new DecouverteException(getArrivee2());					
				case 4 : 
			  		defausse.add(a);
					throw new DecouverteException(getArrivee3());					
				default : 
					return false;
			  }	
	   }
	   
	   public boolean ebouler(Eboulement a, int choix) throws ChoixCaseException
	   {
		   switch(choix) 
		   {
		   		case 0 :
		   			return false;
		   		case 1 : 
			  		defausse.add(a);
		   			return true;
				case 2 :
			  		defausse.add(a);
					throw new ChoixCaseException("Veuillez choisir le chemin � d閠ruire.");
				default :
					return false;
		   }
	   }
	   
	   public boolean detruire(Destruction a, int choix) throws DestructionException
	   {
		   switch(choix)
			{
				case 0 :
					return false;
				case 1 : 
			  		defausse.add(a);
					return true;
				case 2 : 
					throw new DestructionException("Veuillez choisir le joueur � attaquer.");
				default :
					return false;
			}
	   }
	   

	   public boolean reparer(Reparation a, int choix) throws ReparationException
	   {
		   switch(choix)
			{
				case 0 :
					return false;
				case 1 : 
			  		defausse.add(a);
					return true;
				case 2 : 
					throw new ReparationException("Veuillez choisir le joueur � aider.");
				default :
					return false;
			}
	   }
		
	  
	   public boolean actionDeChemin(Chemin a, int choix) throws ChoixCaseException
	   {
	 	  switch(choix) 
	 	  {
	 	  	case 0 :
	 	  		return false;
	 	  	case 1 : 
		  		defausse.add(a);
	 	  		return true;	 	  		
	 		case 2 : 
	 			if ( ( (JoueurS)(joueurs.get(courant)) ).isInvalide() )
	 			{
	 				return false;
	 			}	 			
	 			throw new ChoixCaseException("Veuillez choisir o� poser le chemin.");
	 		case 3 : 
	 			a.pivoter();
	 			return false;
	 		default : 
	 			return false;
	 	  }
	 	  
	   }
	   
	   @Deprecated
	  public void action(Chemin a, int choix) throws ChoixCaseException
	  {
		  if (choix>a.getActions().size() || choix<=0) 
		  {
				return;
		  }
		  switch(choix) 
		  {
			case 2 : 
				throw new ChoixCaseException("Veuillez choisir o� poser le chemin.");
			case 3 : 
				a.pivoter();
			default : 
				return;
		  }
		  
	  }

	  public boolean posable(Chemin c ,int h, int l) {
		  if (plateau.horsLimites(h, l)) return false;
		  if (getMines(h,l) != null) return false;	  
		  Mines mh = getMines(h-1,l);
		  Mines mg = getMines(h,l-1);
		  Mines md = getMines(h,l+1);
		  Mines mb = getMines(h+1,l);
		  
		  if(
		  		mh==null &&
		  		mg==null &&
		  		md==null &&
		  		mb==null 
			) return false;
		  
		  if (mh instanceof Arrivee && liaisonHB(mh,c)==-1 ) {
			  ((Arrivee)mh).pivoter();
		  }
		  if (mg instanceof Arrivee && liaisonGD(mg,c)==-1 ) {
			  ((Arrivee)mg).pivoter();
		  }
		  if (md instanceof Arrivee && liaisonDG(md,c)==-1 ) {
			  ((Arrivee)md).pivoter();
		  }
		  if (mb instanceof Arrivee && liaisonBH(mb,c)==-1 ) {
			  ((Arrivee)mb).pivoter();
		  }
		  
		 if (liaisonHB(mh,c)==0 && liaisonGD(mg,c)==0 && liaisonDG(md,c)==0 && liaisonBH(mb,c)==0)
		 {
			 return false;
		 }
		 if (liaisonHB(mh,c)==-1 || liaisonGD(mg,c)==-1 || liaisonDG(md,c)==-1 || liaisonBH(mb,c)==-1)
		 {
			 return false;
		 }
		 if (
				 ( liaisonHB(mh,c)==1 && !(mh instanceof Arrivee) ) || 
				 ( liaisonGD(mg,c)==1 && !(mg instanceof Arrivee) ) || 
		  		 ( liaisonDG(md,c)==1 && !(md instanceof Arrivee) ) || 
				 ( liaisonBH(mb,c)==1 && !(mb instanceof Arrivee) ) 
			)
		 {
			 return true;
		 }
		 return false;
		 
	  }
	  
	  public void poser(Chemin c, int h, int l) 
	  {	  
		  if (posable(c,h,l)) {

			  Mines c2 = getMines(h-1,l);
			  if (c2 instanceof Arrivee && c.isCentre() && c.isContinu()) 
			  {
				 ( (Arrivee)c2 ).revelerArrivee() ;
				  plateau.remplirCase(h,l,c);
				  updateContinuite();
			  }
			  c2 = getMines(h,l-1);
			  if (c2 instanceof Arrivee && c.isCentre() && c.isContinu()) 
			  {
					 ( (Arrivee)c2 ).revelerArrivee() ;
					  plateau.remplirCase(h,l,c);
					  updateContinuite();
			  }
			  c2 = getMines(h,l+1);
			  if (c2 instanceof Arrivee && c.isCentre() && c.isContinu()) 
			  {
					 ( (Arrivee)c2 ).revelerArrivee() ;
					  plateau.remplirCase(h,l,c);
					  updateContinuite();
			  }
			  c2 = getMines(h+1,l);
			  
			  if (c2 instanceof Arrivee && c.isCentre() && c.isContinu()) 
			  {
					 ( (Arrivee)c2 ).revelerArrivee() ;
					  plateau.remplirCase(h,l,c);
					  updateContinuite();
			  }
			  plateau.remplirCase(h,l,c);	
			  updateContinuite();
		  }
	  }
	  
	  
	  public void updateContinuite()
	  {
		  for (int y=1; y<plateau.getHauteur()-1;y++)
		  {
			  for (int x=1; x<plateau.getLargeur()-1; x++)
			  {
				  if (getMines(y,x)!=null)  getMines(y,x).setDiscontinu();
			  }
		  }
		  updateContinuite(getMines(10,6),10,6);
	  }
	  
	  public void updateContinuite(Mines c, int h, int l) {
		  
		  Mines mh = getMines(h-1,l);
		  Mines mg = getMines(h,l-1);
		  Mines md = getMines(h,l+1);
		  Mines mb = getMines(h+1,l);
		  if (
					 ( mh!=null && liaisonHB(mh,c)==1 && mh.isContinu() && mh.isCentre() ) || 
					 ( mg!=null && liaisonGD(mg,c)==1 && mg.isContinu() && mg.isCentre()) || 
			  		 ( md!=null && liaisonBH(md,c)==1 && md.isContinu() && md.isCentre()) ||
			  		 ( mb!=null && liaisonBH(mb,c)==1 && mb.isContinu() && mb.isCentre()) 
			 )
		  {
			 c.setContinu();
			 if (c instanceof Arrivee) ((Arrivee)c).revelerArrivee();
		  }
		  if (mh!=null && !mh.isContinu()) updateContinuite(mh,h-1,l);
		  if (mg!=null && !mg.isContinu()) updateContinuite(mg,h,l-1);
		  if (md!=null && !md.isContinu()) updateContinuite(md,h,l+1);
		  if (mb!=null && !mb.isContinu()) updateContinuite(mb,h+1,l);	 

	  }
	  public int liaisonGD(Mines m1, Mines m2)
	  {
		 if (m1==null || m2==null) return 0;
		 if (m1.getValeurs().getDroite()==0 && m2.getValeurs().getGauche()==0) return 0;
		 if (m1.getValeurs().getDroite()==1 && m2.getValeurs().getGauche()==1) return 1;
		 if (m1.getValeurs().getDroite()==0 && m2.getValeurs().getGauche()==1) return -1;
		 if (m1.getValeurs().getDroite()==1 && m2.getValeurs().getGauche()==0) return -1;
		 return -1;
	  }
	  
	  
	  public int liaisonDG(Mines m1, Mines m2)
	  {
		 if (m1==null || m2==null) return 0;
		 if (m1.getValeurs().getGauche()==0 && m2.getValeurs().getDroite()==0) return 0;
		 if (m1.getValeurs().getGauche()==1 && m2.getValeurs().getDroite()==1) return 1;
		 if (m1.getValeurs().getGauche()==0 && m2.getValeurs().getDroite()==1) return -1;
		 if (m1.getValeurs().getGauche()==1 && m2.getValeurs().getDroite()==0) return -1;
		 return -1;
	  }
	  
	  public int liaisonHB(Mines m1, Mines m2)
	  {
		 if (m1==null || m2==null) return 0;
		 if (m1.getValeurs().getBas()==0 && m2.getValeurs().getHaut()==0) return 0;
		 if (m1.getValeurs().getBas()==1 && m2.getValeurs().getHaut()==1) return 1;
		 if (m1.getValeurs().getBas()==0 && m2.getValeurs().getHaut()==1) return -1;
		 if (m1.getValeurs().getBas()==1 && m2.getValeurs().getHaut()==0) return -1;
		 return -1;

	  }
	  
	  public int liaisonBH(Mines m1, Mines m2)
	  {
		 if (m1==null || m2==null) return 0;
		 if (m1.getValeurs().getHaut()==0 && m2.getValeurs().getBas()==0) return 0;
		 if (m1.getValeurs().getHaut()==1 && m2.getValeurs().getBas()==1) return 1;
		 if (m1.getValeurs().getHaut()==0 && m2.getValeurs().getBas()==1) return -1;
		 if (m1.getValeurs().getHaut()==1 && m2.getValeurs().getBas()==0) return -1;
		 return -1;
	  }

		 public boolean eboulable(int h, int l) 
		 {
			  if (plateau.horsLimites(h, l)) return false;
			  if (getMines(h,l) instanceof Chemin) return true;
			  return false;
		 }
		 
		 public void ebouler(Eboulement e, int h, int l)
		 {
			  if (eboulable(h,l)) 
			  {
				  defausse.add((Cartes)plateau.viderCase(h,l));
				  updateContinuite();
			  }
		 }

		public boolean outilDestructible(Destruction d, JoueurS joueur) 
		{
		  return (!joueur.estDetruit(d));
		}
		  
		public void detruireOutil(Destruction d, JoueurS joueur)
		{
			  if (outilDestructible(d,joueur))
			  {
				  joueur.getDegats().add(d);
			  }
		}

	  public boolean outilReparable(Reparation r, JoueurS joueur) 
	  {
		  return (joueur.estDetruit(r));
	  }
	  
	  public void reparerOutil(Reparation d, JoueurS joueur)
	  {
		  if (outilReparable(d,joueur))
		  {
			  defausse.add(joueur.getDegats(d.getType()));
			  joueur.getDegats().remove(joueur.getDegats(d.getType()));
		  }
	  }

	  public Arrivee getArrivee1() 
	  {
		  return (Arrivee)(plateau.getCase(8,14));
	  }
	  
	  
	  public Arrivee getArrivee2() 
	  {
		  return (Arrivee)(plateau.getCase(10, 14));
	  }
	  
	  
	  public Arrivee getArrivee3() 
	  {
		  return (Arrivee)(plateau.getCase(12, 14));
	  }
	  
	  public Mines getMines(int h, int l) 
	  {
		  return (Mines)(plateau.getCase(h,l));
	  }
	  
	  public int getManche() {
		return manche;
	  }
	  
	  public ArrayList<Cartes> getPioche() {
			return pioche;
		}

	  public ArrayList<Cartes> getDefausse() {
			return defausse;
	  }

	public void newManche(int numManche) 
	  {
		  manche=numManche;
		  ArrayList<Boolean> roles = new ArrayList<Boolean>();
		  for (int i=0; i<joueurs.size();i++)
		  {
			  ((JoueurS)joueurs.get(i)).reinitialiser();
		  }
		  switch(joueurs.size())
		  {
		  	case 3 : 
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{ 
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<6;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;
		  	case 4 : 
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{ 
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<6;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;
		  	case 5 :
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{ 
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<6;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;
		  	case 6 :
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{ 
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<5;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;
		  	case 7 :
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{ 
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<5;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;	
		  	case 8 :
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{ 
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<4;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;	
		  	case 9 :
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<4;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;	
		  	case 10 :
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(false);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
		  		roles.add(true);
				Collections.shuffle(roles);
				initialisation();
		  		for (int i=0; i<joueurs.size(); i++)
		  		{ 
					((JoueurS)joueurs.get(i)).setRole(roles.get(i));
					for (int j=0;j<4;j++)
					{
						((JoueurS)joueurs.get(i)).getMain().add(pioche.get(0));
						pioche.remove(0);
					}
				}
		  		break;	
		  } 
	  }
	  public void initialisation()
	  {	  
		  plateau = new Plateau(21,21);
		  plateau.remplirCase(10, 6, new Depart());
		  ArrayList<Arrivee> arrivees = new ArrayList<Arrivee>();
		  arrivees.add(new Arrivee(true, new Quatuor(1,1,1,1)));
		  arrivees.add(new Arrivee(false, new Quatuor(1,0,1,0)));
		  arrivees.add(new Arrivee(false, new Quatuor(1,0,0,1)));
		  Collections.shuffle(arrivees);
		  plateau.remplirCase(8, 14, arrivees.get(0));
		  plateau.remplirCase(10, 14, arrivees.get(1));
		  plateau.remplirCase(12, 14, arrivees.get(2));
		  pioche = new ArrayList<Cartes>();
		  defausse = new ArrayList<Cartes>();

		  for (int i=0; i<5; i++) 
		  { // *40
			  pioche.add(new Chemin(true,new Quatuor(1,1,1,0)));
			  pioche.add(new Chemin(true,new Quatuor(0,1,1,1)));
			  pioche.add(new Chemin(false,new Quatuor(1,0,0,0)));
			  pioche.add(new Chemin(true,new Quatuor(1,1,1,1)));
			  pioche.add(new Chemin(true,new Quatuor(1,1,0,0)));
			  pioche.add(new Chemin(true,new Quatuor(0,1,0,1)));
			  pioche.add(new Chemin(false,new Quatuor(1,1,1,0)));
			  pioche.add(new Chemin(true,new Quatuor(0,0,1,1)));		  
		  }
		  for (int i=0; i<3; i++)
		  {
		  	pioche.add(new Eboulement()); // *3
			pioche.add(new Decouverte()); // *6
			pioche.add(new Decouverte());
		  	pioche.add(new Reparation(1)); // *3
			pioche.add(new Reparation(2)); // *3
		  	pioche.add(new Reparation(3)); // *3
			pioche.add(new Destruction(1)); // *3
			pioche.add(new Destruction(2)); // *3
		  	pioche.add(new Destruction(3)); // *3
		  }
		  Collections.shuffle(pioche);
	  }
	  
	  public void testerFin() {
		  getArrivee1().pivoter();
		  getArrivee2().pivoter();
		  getArrivee3().pivoter();
		
		  for (int i=1;i<=2;i++) 
		  {
			  poser(new Chemin(true,new Quatuor(1,1,1,1)),10+i,6);
			  poser(new Chemin(true,new Quatuor(1,1,1,1)),10-i,6);
		  }
		  for (int i=7;i<13;i++) {
			  poser(new Chemin(true,new Quatuor(1,1,1,1)),10,i);
			  poser(new Chemin(true,new Quatuor(1,1,0,1)),8,i);
			  poser(new Chemin(true,new Quatuor(1,1,1,0)),12,i);
		  }
		  defausse = pioche;
		  pioche = new ArrayList<Cartes>();
		  pioche.add(new Eboulement());
		  pioche.add(new Reparation(1));
		  pioche.add(new Chemin(true,new Quatuor(1,1,1,1)));
		  pioche.add(new Chemin(true,new Quatuor(1,1,1,1)));
		  ( (JoueurS)(joueurs.get(0)) ).getDegats().add(new Destruction(1));
		  
	  }
}
