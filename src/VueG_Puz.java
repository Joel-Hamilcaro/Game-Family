import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
public class VueG_Puz extends VueG{
	private JButton startButton,restartButton,undoButton,redoButton,aideButton,moveButton;
	private JPanel panneau,ph;
	private PetiteImagePane pb;
	private ImagePane imagePane;
	private Modele_Puz mp;
	private int aide ;
	private boolean estStart=false;
	private JoueurP jp;
	
	private static final String INITIAL_LABEL_TEXT = "00:00:00";
	private long timeStart ;
	private JLabel label = new JLabel(INITIAL_LABEL_TEXT); 
	private CountThread thread = new CountThread();
	
	public VueG_Puz(Modele_Puz mp2) {
		jp=new JoueurP("");
		this.mp=mp2;
		aide=Math.min(mp.getM(),mp.getN())/2;
		this.setTitle("Puzzles");
		
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		this.startButton=new JButton ("Jouer");
		this.restartButton=new JButton ("Rejouer");
		this.undoButton =new JButton ("Annuler");
		this.redoButton =new JButton("Retablir");
		this.aideButton =new JButton("Aide");
		this.moveButton=new JButton("Echanger");
		this.setLocationRelativeTo(null);
		startButton.setEnabled(true);
		restartButton.setEnabled(false);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		aideButton.setEnabled(false);
		moveButton.setEnabled(false);
		
		startButton.addActionListener((ActionEvent e )->{
			start();
			estStart=true;
		});
		moveButton.addActionListener((ActionEvent e )->{
			mp.saveMove(this.imagePane.selection.getX0(),
						this.imagePane.selection.getY0(),
						this.imagePane.selection.getX1(),
						this.imagePane.selection.getY1());
			undoButton.setEnabled(true);
			if(this.mp.estFini()){
				startButton.setEnabled(false);
				restartButton.setEnabled(true);
				undoButton.setEnabled(false);
				redoButton.setEnabled(false);
				aideButton.setEnabled(false);
				moveButton.setEnabled(false);
				thread.run();
				jp.setTemps(label.getText());
				ph.add(label);
				validate();
				repaint();
			}
			moveButton.setEnabled(false);
			repaint();
		});
		 
		restartButton.addActionListener((ActionEvent e )->{
			try {
				ph.remove(label);
				mp.initialistion();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			start();
			
			this.timeStart =System.currentTimeMillis();
			aide=Math.min(mp.getM(),mp.getN())/2;
			this.repaint();
		});
		undoButton.addActionListener((ActionEvent e )->{
			if(mp2.undoManager.canUndo()) {
				mp2.undoManager.undo();
				repaint();
				redoButton.setEnabled(true);
			}else {
				undoButton.setEnabled(false);
			}
		});
		redoButton.addActionListener((ActionEvent e)->{
			if(mp2.undoManager.canRedo()) {
				mp2.undoManager.redo();
				repaint();
				undoButton.setEnabled(true);
			}else {
				redoButton.setEnabled(false);
			}
		});
		aideButton.addActionListener((ActionEvent e)->{
			mp.suggerer();
			this.repaint();	
			aide--;
			if(aide<=0) {
				aideButton.setEnabled(false);
			}
			undoButton.setEnabled(false);
			redoButton.setEnabled(false);
			moveButton.setEnabled(false);
			if(this.mp.estFini()){
				startButton.setEnabled(false);
				restartButton.setEnabled(true);
				aideButton.setEnabled(false);
				thread.run();
				jp.setTemps(label.getText());
				ph.add(label);
				validate();
				repaint();
			}
			validate();
			repaint();
		});
		bar.setLayout(new GridLayout(1,6));
		bar.add(this.startButton);
		bar.add(this.moveButton);
		bar.add(this.restartButton);
		bar.add(this.undoButton);
		bar.add(this.redoButton);
		bar.add(this.aideButton);
		this.setLayout(new GridLayout(1,2));
		panneau = new JPanel ();
		imagePane =new ImagePane();
		this.getContentPane().add(panneau);
		this.getContentPane().add(imagePane);
		
		panneau.setLayout(new GridLayout(2,1));
		ph = new JPanel();
		ph.add(label);
		pb = new PetiteImagePane();
		panneau.add(ph);
		panneau.add(pb);
		
		label.setText("Cliquer sur \"Jouer\" pour commencer.");
		ph.add(label);
		
		this.setSize(800,600);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
	}
	public  void start() {
		initialiser();
		this.timeStart=System.currentTimeMillis();
		restartButton.setEnabled(true);
		aideButton.setEnabled(true);
		startButton.setEnabled(false);
	 }
	
	public class CountThread extends Thread{
		private CountThread() {  
            setDaemon(true);  
        }
		public void run () {
                	long time = System.currentTimeMillis() - timeStart;  
                	label.setText("Bravo ! Votre temps :"+ format(time));  
		}			
		
		private String format(long time) {  
			int hour, minute, second, milli;  		   
            milli = (int) (time % 1000);  
            time = time / 1000;  
            second = (int) (time % 60);  
            time = time / 60;   
            minute = (int) (time % 60);  
            time = time / 60;   
            hour = (int) (time % 60);   
	        return String.format("%02d:%02d:%02d", hour, minute, second);  
	    }
	}	
	public class PetiteImagePane extends JPanel{
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			g.drawImage(mp.getPetiteImage(),mp.getImage().getWidth(null)/4, 0, this);
		}
		public PetiteImagePane() {
			this.setPreferredSize(new Dimension (mp.getPetiteImage().getWidth(null)/2,mp.getPetiteImage().getHeight(null)/2));
		}
	}
	public class ImagePane extends JPanel{
		private Selection selection = new Selection ();
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(mp.getImage(), 0, 0, this);			
		
		this.addMouseListener(selection);
		this.addMouseMotionListener(selection);
		int x0= selection.getX0();
		int y0=selection.getY0();
		int x1= selection.getX1();
		int y1=selection.getY1();
		g.setColor(new Color(0,255,0));
		((Graphics2D) g).fillOval(x0, y0, 10, 10);
		((Graphics2D) g).fillOval(x1, y1, 10, 10);
		}
		public ImagePane() {
		this.setPreferredSize(new Dimension (mp.getImage().getWidth(),mp.getImage().getHeight()));
		}			
		public class Selection extends MouseAdapter implements MouseMotionListener {
			private int x0,x1,y0,y1;
			public void mousePressed(MouseEvent e) {	
				if(estStart) {
					x0=e.getX();
					y0=e.getY();
				}				
			}
			public void mouseDragged(MouseEvent e) {
				if(estStart) {
					x1=e.getX();
					y1=e.getY();
					if((x1/mp.getW())!=(x0/mp.getW())||(y1/mp.getH())!=(y0/mp.getH())) {
						if(!mp.estFini()) {
							moveButton.setEnabled(true);
						}
					}
					imagePane.repaint();
				}
			}
			public void mouseMoved() {
				
			}
			public int getX0() {
				return x0;
			}
			public int getX1() {
				return x1;
			}
			public int getY0() {
				return y0;
			}
			public int getY1() {
				return y1;
			}		
		}		
	}
	
	@Override
	public void initialiser() {
		ph.remove(label);
		validate();
		repaint();
	}
}


