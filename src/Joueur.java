public abstract class Joueur {
	
	private static int id = 0;
	private String nom;
	private int victoires;

	public Joueur() {
		this.victoires = 0;
		this.nom="Joueur "+(++id);
	}
	public String toString()
	{
		return nom;
	}
	public static int getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public int getVictoires() {
		return victoires;
	}
	public void addVictoire() {
		victoires++;
	}

	
}