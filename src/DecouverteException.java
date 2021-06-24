import java.io.IOException;

public class DecouverteException extends IOException {
	
	private Arrivee arrivee;
	public DecouverteException(Arrivee a) 
	{
		arrivee=a;
	}
	
	public String toString()
	{
		arrivee.revelerArrivee();
		String s = arrivee.toString();
		arrivee.cacherArrivee();
		return s;
	}
	
}
