import java.util.ArrayList;
public class JoueurD extends Joueur {

	private int points;
	private ArrayList<Domino> main;
	
	public String mainToString()
	{
		String s = "";
		for (int i=0; i<main.size(); i++) 
		{
			s+=i+1+":["+main.get(i)+"]   ";
		}
		return s;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints(int points) {
		this.points+=points;
	}
	
	public void removeMain() {
		main = new ArrayList<Domino>();
	}

	public JoueurD() {
		points=0;
		main = new ArrayList<Domino>();
	}
	
	public ArrayList<Domino> getMain() {
		return main;
	}

	public int getPoints() {
		return points;
	}
	
	
	
	

}