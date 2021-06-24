import java.io.IOException;

public class ArriveeException extends IOException {
	private int h,l;
	
	public ArriveeException(int h, int l) {
		this.h=h;
		this.l=l;
	}

	public int getH() {
		return h;
	}

	public int getL() {
		return l;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setL(int l) {
		this.l = l;
	}
	
}
