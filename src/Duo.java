public class Duo{

  protected int gauche;
  protected int droite;

  public Duo(int g, int d) {
	  this.gauche=g;
	  this.droite=d;
  }
  
  public void pivoter() {
	  int tmp =this.gauche;
	  this.gauche=this.droite;
	  this.droite=tmp;
  }

  public int getGauche() {
	  return gauche;
  }
  public int getDroite() {
	  return droite;
  }

  public void setGauche(int gauche) 
  {
	this.gauche = gauche;
  }

  public void setDroite(int droite) {
	  this.droite = droite;
  }
  
  public boolean equals(Duo du) 
  {
	if ((this.gauche==du.getGauche())&&(this.droite==du.getDroite())) {
		return true;
	}
	return false;
  }
}