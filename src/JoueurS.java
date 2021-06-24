import java.util.ArrayList;
public class JoueurS extends Joueur 
{

	private int gain;
	private ArrayList<Cartes> main;
	private ArrayList<Destruction> degats;
	private boolean chercheur;
	
	public JoueurS()
	{
		main = new ArrayList<Cartes>();
		degats = new ArrayList<Destruction>();
		gain=0;
	}
	
	public void reinitialiser() {
		main = new ArrayList<Cartes>();
		degats = new ArrayList<Destruction>();
	}

	public boolean isChercheur() {
		return chercheur;
	}
	
	public String getRole() {
		if (chercheur) return "CHERCHEUR";
		return "SABOTEUR";
	}
	
	public boolean isInvalide() {
		return degats.size()>0;
	}

	public void setRole(boolean r) 
	{
		chercheur=r;
		
	}
	
	public String toString() 
	{
		return getNom()+degatsToString();
	}
	
	public String mainToString()
	{
		String s = "";
		for (int i=0; i<main.size(); i++) 
		{
			s+=i+1+":["+main.get(i)+"]   ";
		}
		return s;
	}
	
	public String degatsToString()
	{
		if (degats.size()==0) return "";
		String s = "";
		for (int i=0; i<degats.size(); i++) 
		{
			s+="["+degats.get(i)+"]";
		}
		return s;
	}
	
	public boolean estDetruit(Destruction d)
	{
		for (int i=0; i<degats.size(); i++) 
		{
			if (degats.get(i).getType() == d.getType()) return true;
		}
		return false;
	}
	
	public boolean estDetruit(Reparation d)
	{
		for (int i=0; i<degats.size(); i++) 
		{
			if (degats.get(i).getType() == d.getType()) return true;
		}
		return false;
	}
	
	public Destruction getDegats(int type) 
	{
		for (int i=0; i<degats.size();i++) 
		{
			if (degats.get(i).getType()==type) return degats.get(i);
		}
		return null;
	}
	
	public ArrayList<Cartes> getMain()
	{
		return main;
	}
	
	public ArrayList<Destruction> getDegats() 
	{
		return degats;
	}
	
	public void addGain(int g) 
	{
		gain=gain+g;
	}
	
	public int getGain() 
	{
		return gain;
	}
	
	
}