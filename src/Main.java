import java.awt.EventQueue;

public class Main {
    private Menu menu;
    
    public Main() {
    	menu = new Menu();
    }
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
			   new Main();
			}
			
		});		
		    
		
		
		
	}
			
}
		

