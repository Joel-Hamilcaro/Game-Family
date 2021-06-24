public class Reparation extends Action {

  private int type;
  public Reparation(int t) {
	  type=t;
	  getActions().add("Reparer");

  }

  public int getType() {
	  return type;
  }
  
  public String toString(){
		String s = "?";
		if (type==1) s="Chariot (+)";
		if (type==2) s="Lampe (+)";
		if (type==3) s="Outils (+)";
		return s;
	}

	
	  
  
}