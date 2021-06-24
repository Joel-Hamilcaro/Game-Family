public class JoueurP extends Joueur {
	private String temps;	
	public JoueurP(String temps) {
		this.temps = temps;
	}
	public void setTemps(String t) {
		this.temps=t;
	}
	public String getTemps() {
		return this.temps;
	}
}