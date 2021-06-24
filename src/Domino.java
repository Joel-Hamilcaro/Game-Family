import java.util.ArrayList;

public class Domino extends Piece implements Pivotable{

  protected Duo valeurs;
  protected ArrayList<String> actions = new ArrayList<String>();

  public Domino (Duo v) {
	  this.valeurs=v;
	  actions.add("Annuler");
	  actions.add("Poser a gauche");
	  actions.add("Poser a droite");
  }

  public boolean isDouble() {
	  return valeurs.getDroite()==valeurs.getGauche();
  }
  
  public int getPoints() {
	  int points=valeurs.getDroite()+valeurs.getGauche();
	  return points;
  }
  
  
  
  public Duo getValeurs() {
	return valeurs;
}
public ArrayList<String> getActions() 
	{
		return actions;
	}
  
  @Override
  public String toString() {
	  return "<"+valeurs.getGauche()+""+valeurs.getDroite()+"<";
  }
  
  public String toStringReverse() {
	  return ">"+valeurs.getDroite()+""+valeurs.getGauche()+">";
  }
  public String toString2mod4()
  {
	  return "^"+valeurs.getGauche()+""+valeurs.getDroite()+"v";
  }
  public String toString0mod4()
  {
	  return "v"+valeurs.getDroite()+""+valeurs.getGauche()+"^";
  }
  @Override
  public void pivoter() {
	valeurs.pivoter();	
  }

}