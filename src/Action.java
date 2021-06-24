import java.util.ArrayList;

public abstract class Action implements Cartes {
	private ArrayList<String> actions = new ArrayList<String>();

	public Action() 
	{
		actions.add("Annuler");
		actions.add("Defausser");
	}
	
	public ArrayList<String> getActions() 
	{
		return actions;
	}
	
	public void setActions(ArrayList<String> actions) 
	{
		this.actions = actions;
	}
	
}