package SecurityDecorator;

class Listable extends SecurityDecorator{

	public Listable(Security sec) {
		super(sec);
		// TODO Auto-generated constructor stub
	}
	
	public void list(){
		
	}
	
	public void display(){
		super.display();
	}
}