public abstract class Mines extends Piece {

  protected boolean centre;
  protected Quatuor valeurs;
  protected boolean continu=false;

  public Mines(boolean c, Quatuor v) {
	  this.centre =c;
	  this.valeurs=v;
  }
  
  @Override
  public String toString() {
	  String s = "";
	  
	  if (valeurs.getGauche()==1) s="-";
	  else s=" ";
	  
	  if (valeurs.getHaut()==1 && valeurs.getBas()==1) s+="|";
	  else if (valeurs.getHaut()==1 && valeurs.getBas()==0) s+="'";
	  else if (valeurs.getHaut()==0 && valeurs.getBas()==1) s+=",";
	  else s+="-";
	  
	  if (centre && continu) s+="-";
	  else if (centre && !continu) s+="~";
	  else s+=":";
	  
	  if (valeurs.getDroite()==1) s+="-";
	  else s+=" ";
	  
	  return s;
	  
  }
  
  	public Quatuor getValeurs() {
  		return valeurs;
  	}

	public boolean isCentre() {
		return centre;
	}
	
	public boolean isContinu() {
		return continu;
	}
	
	public void setContinu() {
		continu=true;
	}
	
	public void setDiscontinu() {
		continu=false;
	}

}