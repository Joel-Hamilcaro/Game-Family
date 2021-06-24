public class Gommette extends Domino {
	
	public Gommette(Duo v) {
		super(v);
	}
	
	
	public static char color(int valeur) {
		switch(valeur%3)
		{
			case 0 : return 'R'; 
			case 1 : return 'G';
			case 2 : return 'B';
		}
		return ' ';
	}
	
	public static char forme(int valeur) {
		switch(valeur)
		{
			case 0 : return 'o'; 
			case 1 : return 'o';
			case 2 : return 'o';
			case 3 : return '~'; 
			case 4 : return '~';
			case 5 : return '~';
			case 6 : return '#';

		}
		return ' ';
	}
	
	public String toString()
	{
		 return forme(valeurs.getGauche())+""+color(valeurs.getGauche())+""+color(valeurs.getDroite())+""+forme(valeurs.getDroite());
	}
	
	public String toStringReverse() {
		  return forme(valeurs.getDroite())+""+color(valeurs.getDroite())+""+color(valeurs.getGauche())+""+forme(valeurs.getGauche());
	  }
	
	public String toString2mod4()
	  {
		  return forme(valeurs.getGauche())+""+color(valeurs.getGauche())+""+color(valeurs.getDroite())+""+forme(valeurs.getDroite());
	  }
	
	public String toString0mod4()
	  {
		  return  forme(valeurs.getDroite())+""+color(valeurs.getDroite())+""+color(valeurs.getGauche())+""+forme(valeurs.getGauche());
	  }
	
	
  
}
