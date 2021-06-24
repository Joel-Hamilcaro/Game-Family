public class Decouverte extends Action {
	public Decouverte() 
	{
		getActions().add("Reveler arrivee (Haut)");
		getActions().add("Reveler arrivee (Milieu)");
		getActions().add("Reveler arrivee (Bas)");
	}
	
	public String toString() 
	{
		return "Decouverte";
	}
}