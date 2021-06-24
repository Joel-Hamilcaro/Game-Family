import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;

public class Modele_Puz {
	private String chemin;
	 private  BufferedImage image;
	 private  Image petiteImage ;
	 private int m,n,w,h;
	 private Puzzle [][] TabPuzzles2 ;
	 protected UndoManager undoManager=new UndoManager();
	private  final String base = "@#&$%*o!;.";
	 
	 public void saveMove(int x0,int y0,int x1,int y1) {
	  Move mo =new Move(x0,y0,x1,y1);
	  mo.deplacer();
	  ChangerEdit ce = new ChangerEdit(mo);
	  undoManager.addEdit(ce);
	  
	 }
	 public class Move{
		 private int k,l,p,q;
		 public Move(int d,int e,int f,int g) {
			 this.k=((int) d)/w;
			 this.l=((int) e)/h;
			 this.p=((int) f)/w;
			 this.q=((int) g)/h;
		 }
		 public void deplacer() {
			 changer(l,k,q,p);
		 }
		 public void undo() {
			 changer(q,p,l,k);
		 }		 
	 } 
	 public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
	public void t() {
		 for(int i=0 ;i<image.getHeight();i+=8) {	 
			 for(int j=0;j<image.getWidth();j+=4) {
				 int pixel=image.getRGB(j, i);
				 int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
				 float gray = 0.299f * r + 0.578f * g + 0.114f * b;
				 int index = Math.round(gray * (base.length() + 1) / 255);
				 System.out.print(index >= base.length() ? " " : String.valueOf(base.charAt(index)));
			 }
			 System.out.println();
		 }
		 if(!estFini()) {
			 afficherTab();
			System.out.println("Changez deux puzzles: (X0,Y0) (X1,Y1)");
		 }
		 
	 }
	 public void tt(BufferedImage image) {
		 for(int i=0 ;i<image.getHeight();i+=8) {	 
			 for(int j=0;j<image.getWidth();j+=4) {
				 int pixel=image.getRGB(j, i);
				 int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
				 float gray = 0.299f * r + 0.578f * g + 0.114f * b;
				 int index = Math.round(gray * (base.length() + 1) / 255);
				 System.out.print(index >= base.length() ? " " : String.valueOf(base.charAt(index)));
			 }
			 System.out.println();
		 }
	 }
	 
	 public Modele_Puz(String chemin,int m,int n) {
		 try {
			 this.chemin=chemin;	 
			 this.m=m;
			 this.n=n;
			 this.TabPuzzles2= new Puzzle[m][n];
			 this.initialistion();
		 }catch(IOException e) {
			 e.printStackTrace();
		 }
	 }
	 public void initialistion() throws IOException {	
		 this.image=ImageIO.read(new File(chemin));
		 this.petiteImage= ImageIO.read(new File(chemin)).getScaledInstance(image.getWidth()/2,image.getHeight()/2, BufferedImage.SCALE_DEFAULT);
		 this.h =(int)(((double)image.getHeight())/m);
		 this.w =(int)(((double)image.getWidth())/n);
		 for(int i=0 ;i<m;i++) {	 
			 for(int j=0;j<n;j++) {	
				 BufferedImage im = new BufferedImage(w, h, 1);
				 Color c= new Color(200,200,200);
				 for(int k=0;k<w;k++) {
					 for(int l=0;l<h;l++) {	
						 if(k!=w-1&&k!=0) {
							 if(l!=h-1&&l!=0) {
								 im.setRGB(k, l,this.image.getRGB(k+j*w,l+i*h));
							 }else if(l==h-1||l==0) {
								 im.setRGB(k, l,c.getRGB());
							 }
						 }else if(k==w-1||k==0) {
							 im.setRGB(k, l,c.getRGB());
						 }					
					}
				 }
				 TabPuzzles2[i][j]= new Puzzle(new Duo (i,j),im);					 
			 }
		 }
		 desorganiser();		 
	 }
	 public void desorganiser() {
		 Random ran = new Random ();
		 for(int i=0;i<m;i++) {
			 for(int j=0 ;j<n;j++) {
				 int k=ran.nextInt(m);
				 int l=ran.nextInt(n);
				 int p=ran.nextInt(m);
				 int q=ran.nextInt(n);
				 Puzzle tmp =TabPuzzles2[k][l];
				 TabPuzzles2[k][l]=TabPuzzles2[p][q];
				 TabPuzzles2[p][q]=tmp;	
				 
			 }
		 }
		 this.renouveler();	
	 }
	 public void changer(int k, int l,int p ,int q) {
		 if(k<0||k>=m||l<0||l>=n||p<0||p>=m||q<0||q>=n) {
			 System.err.println("Coordonnees fausse ");
		 }else {
			 Puzzle tmp =TabPuzzles2[k][l]; 
			 TabPuzzles2[k][l]=TabPuzzles2[p][q];
			 TabPuzzles2[p][q]=tmp;
			 renouveler();
		 }
		
	 }
	 public class ChangerEdit extends AbstractUndoableEdit{
		 private Move mo;
		 public ChangerEdit(Move mo) {
			 this.mo=mo;
		 }
		 public void undo() {
			 super.undo();
			 mo.undo();
		 }
		 public void redo() {
			 super.redo();
			 mo.deplacer();
		 }
	 }
	 
	 
	 public boolean estBon(int k,int l) {
		 return TabPuzzles2[k][l].getId().equals(new Duo(k,l));
	 }
	 public boolean estFini() {
		 for(int i=0;i<m;i++) {
			 for(int j=0 ;j<n;j++) {
				 if (!estBon(i,j)) {
					 return false;
				 }		 
			 }
		 }
		 return true;
	 }
	 public void renouveler() {		
		 for(int i=0 ;i<m;i++) {			 
			 for(int j=0;j<n;j++) {	
				for(int k=0;k<w;k++) {
					for(int l=0;l<h;l++) {					
						image.setRGB(j*w+k, i*h+l,this.TabPuzzles2[i][j].getImage().getRGB(k,l));
					}
				}		
			 }
		 }
	 }
	 public void suggerer() {
		 for(int i=0 ;i<m;i++) {			 
			 for(int j=0;j<n;j++) {	
				 if(!estBon(i,j)) {
					 int k=TabPuzzles2[i][j].getId().getGauche();
					 int l=TabPuzzles2[i][j].getId().getDroite();
					 changer(i,j,k,l);
					 break;
				 }			 
			 }
		 }
	 }
	 public BufferedImage getImage() {
		 return this.image;
	 }
	 public Image getPetiteImage() {
		 return this.petiteImage;
	 }
	 public int getM() {
		 return this.m;
	 }
	 public int getN() {
		 return this.n;
	 }
	 public Puzzle[][] getTabPuzzles2() {
		return TabPuzzles2;
	}
	public void afficherTab() {
		 for(int i=0;i<m;i++) {
			 for(int j=0;j<n;j++) {
				System.out.print("("+i+","+j+") ");
			 }
			 System.out.println();
		 }
	 }
	 
}
