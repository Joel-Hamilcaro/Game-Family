import java.awt.image.BufferedImage;

public class Puzzle extends Piece {
	private Duo id;
	private BufferedImage image;
	public Puzzle (Duo id ,BufferedImage image) {
		this.id=id;
		this.image=image;
	}
	public Duo getId() {
		return this.id;
	}

	public String toString() {
		return " Puzzle:"+this.id;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public void setImage(BufferedImage image) {
		this.image=image;
	}
	
	
}
