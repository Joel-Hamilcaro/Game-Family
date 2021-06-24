public class Depart extends Mines {

  public Depart() {
	  super(true,new Quatuor (1,1,1,1));
	  this.continu=true;
  }
  
  public String toString() {
	  return "[[]]";
  }
  

 @Override
 public void setDiscontinu() {
	  continu=true;
 }

}