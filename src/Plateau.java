public class Plateau {

  private int hauteur;
  private int largeur;
  private Piece[][] cases;
  
  public Plateau(int hauteur,int largeur) {
	  this.largeur=largeur;
	  this.hauteur=hauteur;
	  this.cases=new Piece[this.hauteur][this.largeur];
  }

  public boolean CaseEstVide(int h ,int l) {
	  if (cases[h][l]==null) {
		  return true;
	  }else {
		  return false;
	  }
  }

  public void remplirCase(int h, int l, Piece p) {
	  cases[h][l]=p;
  }

  public boolean horsLimites(int h, int l) {  
	  return (h>this.hauteur-2||h<1||l>this.largeur-2||l<1);
  }

public Piece viderCase(int h, int l) {	  
	  Piece p = cases[h][l];
	  cases[h][l]=null;
	  return p;
  }
  
  public String toString() {
	  String s = "    ";
	  for (int l=1; l<largeur-1; l++) {
		  s+=String.format("|%2d|", l);
	  }
	  for (int h=1; h<hauteur-1;h++) {
		  s+="\n"+String.format("|%2d|", h);
		  for (int l=1; l<largeur-1; l++) {
			  if (cases[h][l]==null) s+="    ";
			  else s+=cases[h][l];
		  }
	  }
	  return s;
  }
  
  public Piece getCase(int h, int l) {	  
	  return cases[h][l];
  }
  
  public int getHauteur() {
	return hauteur;
  }

  public int getLargeur() {
	return largeur;
  }

  
  
}