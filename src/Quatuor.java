public class Quatuor extends Duo {
	
  private int haut;
  private int bas;
  
  public Quatuor(int g, int d, int h, int b) {
	  super(g,d);
	  this.haut=h;
	  this.bas=b;
  }

  public void pivoter() {
	  int tmp = this.gauche;
	  this.gauche=this.bas;
	  this.bas=this.droite;
	  this.droite=this.haut;
	  this.haut=tmp;
  }
  
  	
  public void antiPivoter() {
	  int tmp = this.gauche;
	  this.gauche=this.haut;
	  this.haut=this.droite;
	  this.droite=this.bas;
	  this.bas=tmp;
  }

  public int getHaut() {
	  return haut;
  }

  public int getBas() {
	  return bas;
  }

  public void setHaut(int haut) {
	  this.haut = haut;
  }

  public void setBas(int bas) {
	  this.bas = bas;
  }

  
}