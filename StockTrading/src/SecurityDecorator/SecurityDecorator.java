package SecurityDecorator;

abstract class SecurityDecorator extends Security{
	protected Security sec;
	
	
	public SecurityDecorator(Security sec){
		this.sec=sec;
	}
	
	public void display(){
		sec.display();
		
	}
}