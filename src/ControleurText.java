import java.util.InputMismatchException;
import java.util.Scanner;

public class ControleurText 
{
	
	private Scanner sc = new Scanner(System.in);
	private VueTextuelle vue;
	
	public ControleurText(VueTextuelle v) 
	{
		vue=v;
		System.err.println("Vous pouvez quitter la partie a tout moment en tapant \"-75\"");
	}
	
	public int askInt() throws JeuFiniException 
	{
		try 
		{
			int x = sc.nextInt();
			if (x==-75)
			{
				System.out.println("Fin du jeu ............................... ");
				throw new JeuFiniException();
			}
			return x;
		} 
		catch (InputMismatchException e) 
		{
			System.out.println("Entree invalide : Reessayer");
			sc = new Scanner(System.in);
			return askInt();
		}
	}	
	
	public int askInt(int min, int max) throws JeuFiniException 
	{
		int x = min-1;
		while(x<min || x>max)
		{
			System.out.println("Veuillez choisir un nombre entre "+min+" et "+max+".");
			try 
			{
				x = sc.nextInt();		
				if (x == -75)
					{
						System.out.println("Fin du jeu ............................... ");
						throw new JeuFiniException();
					}
			}
			catch (InputMismatchException e)
			{
				sc = new Scanner(System.in);
				return 	askInt(min,max);
			}
		}
		return x;
	} 
}
