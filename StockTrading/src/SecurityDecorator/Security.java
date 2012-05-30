package SecurityDecorator;

public abstract class Security{
	private double price;
	private long numb;

	public long getnumber(){
		return numb;
	}
	public double getPrice() {
		return price;
	}

	public void setnumber(long number){
		numb=number;
	}
	
	public void setPrice(double val) {
		price = val;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Security){
			Security s = (Security)obj;
			if(price==s.getPrice()&&numb==s.getnumber())
				return true;
			return false;
		}
		return false;
	}
	
	public abstract void display();
}