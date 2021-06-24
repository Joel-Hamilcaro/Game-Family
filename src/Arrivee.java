public class Arrivee extends Mines implements Pivotable{

  private final boolean tresor;
  private boolean cache;

  public Arrivee(boolean t, Quatuor v){
	  super(true,v);
	  this.tresor=t;
	  this.cache=true;
  }

  public void revelerArrivee() {
	  this.cache=false;
  }
  
  public void cacherArrivee() {
	  this.cache=true;
  }
  

  @Override
  public String toString() {
	 if (cache) return "####";
	 String s = "";
	  
	 if (valeurs.getGauche()==1) s="-";
	 else s=" ";
	  
	 if (valeurs.getHaut()==1 && valeurs.getBas()==1) s+="|";
	 else if (valeurs.getHaut()==1 && valeurs.getBas()==0) s+="'";
	 else if (valeurs.getHaut()==0 && valeurs.getBas()==1) s+=",";
	 else s+=" ";
	  
	 if (tresor) return "GOLD";
	 else if (centre) s+="-";
	 else s+=" ";
	  
	 if (valeurs.getDroite()==1) s+="-";
	 else s+=" ";
	  
	 return s;
	 
  }

  @Override
  public void pivoter() {
	valeurs.pivoter();
	valeurs.pivoter();
  }
  
  public boolean estCache() {
	  return cache;
  }
  
  public boolean estTresor() {
	  return tresor;
  }
  
 

}