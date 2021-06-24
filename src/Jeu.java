import java.util.ArrayList;
public abstract class Jeu {

	protected Plateau plateau;
	protected ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
	protected int courant = 0;
	protected int choix;

	public abstract void initialisation();
	public abstract boolean estFini();
	public void setChoix(int c) {
		choix=c;
	}
	public Plateau getPlateau() {
		return plateau;
	}
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}
	public int getChoix() {
		return choix;
	}
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}
	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}
	public int getCourant() {
		return courant;
	}
	public void next() {
		if (courant==joueurs.size()-1) courant = 0;
		else courant++;
	}
	
	
	
}