public class VueT_Puz extends VueTextuelle {
	public VueT_Puz() throws JeuFiniException {
		controleur = new ControleurText(this);

		Modele_Puz mp =new Modele_Puz("./Images/jbr.jpg",3,2);
		mp.t();	
		boolean b=true;
		while(b) {
			int k = controleur.askInt();
			int l = controleur.askInt();
			int p = controleur.askInt();
			int q = controleur.askInt();
			mp.changer(k, l, p, q);
			mp.t();
			mp.renouveler();
			if(mp.estFini()) {
				b=false;
				finirJeu();
			}		
		}		
	}
	public void finirJeu() throws JeuFiniException {
		System.out.println("Bravo!");
		throw new JeuFiniException();
	}
}
