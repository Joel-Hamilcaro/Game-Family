public class Destruction extends Action {

	private  int type; 	
	public Destruction(int t) {
		type=t;
		getActions().add("Detruire");
	}
	
	public String toString(){
		String s = "?";
		if (type==1) s="Chariot (-)";
		if (type==2) s="Lampe (-)";
		if (type==3) s="Outils (-)";
		return s;
	}

	public int getType() {
		return type;
	}
	
	
}