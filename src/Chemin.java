import java.util.ArrayList;

public class Chemin extends Mines implements Cartes, Pivotable 
{

	private ArrayList<String> actions = new ArrayList<String>();
	
	public Chemin(boolean c, Quatuor v) 
	{
		super(c,v);
		actions.add("Annuler");
		actions.add("Defausser");
		actions.add("Poser");
		actions.add("Pivoter");
	}		
	
	public ArrayList<String> getActions() 
	{
		return actions;
	}
	
	public void setActions(ArrayList<String> actions) 
	{
		this.actions = actions;
	}

	@Override
	public void pivoter()
	{
		valeurs.pivoter();
		valeurs.pivoter();	
	}
	
}