package OrderState;

import java.util.Date;

import SecurityCode.Account;
import SecurityDecorator.Security;

public class LimitedOrder extends StockOrder {
	// private Date issueDate;
	private Date limitDate;
	protected OrderState orderWaiting;

	public LimitedOrder(String sell_buy,String type,
			Security security, int shareN, long profileId, Account acc,
			double price, Date limitDate) {
		super(sell_buy, type, security, shareN, profileId, acc);
		orderWaiting = new OrderWaiting(this);
		this.limitDate = limitDate;
		this.priceSet = price;

	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (obj instanceof LimitedOrder) {
				LimitedOrder limitedOrder = (LimitedOrder) obj;
				if (limitDate.equals(limitedOrder.getLimitDate())) {
					return true;
				}
				return false;
			}
		}
		return false;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public OrderState getOrderWaiting() {
		return orderWaiting;
	}

	public void setOrderWaiting(OrderState orderWaiting) {
		this.orderWaiting = orderWaiting;
	}

	public boolean isExpired() {
		Date currentDate;
		currentDate = new Date();
		if (currentDate.before(limitDate))
			return true;
		else
			return false;
	}

}
