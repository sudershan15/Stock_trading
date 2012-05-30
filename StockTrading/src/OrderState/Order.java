package OrderState;

import SecurityCode.Account;
import SecurityDecorator.Security;

public abstract class Order {
	public static final String LIMITED_ORDER="LimitedOrder";
	public static final String MARKET_ORDER="MarketOrder";
	public static final String MUTUAL_FUND_ORDER="MutualFundOrder";
	public static final String SELLER="Seller";
	public static final String BUYER="Buyer";
	public static final String CANCELLED="Cancelled";
	public static final String EXPIRED="Expired";
	public static final String SPLITTED="Splitted";
	public static final String FULFILLED="Fulfilled";
	public static final String PENDING="Pending";
	
	protected String status;
	protected String orderType;
	protected String seller_Or_buyer;
	protected String combinedOrderId; //format as : profileName_accountName_orderId
	protected long profileId;
	protected long accountId;
	protected Security security;
	protected int share_Number;
	protected Account account;

	
	protected OrderState orderReceived;
	protected OrderState orderFulfilled;
	protected OrderState orderInactive;
	protected OrderState orderState;
	
	public Order(String sell_buy,String type,Security security,int shareN,long profileId,Account acc){
		orderReceived = new OrderReceived(this);
		orderFulfilled = new OrderFulfilled(this);
		orderInactive = new OrderInactive(this);
		
		this.seller_Or_buyer = sell_buy;
		this.orderType = type;
		this.security = security;
		this.share_Number = shareN;
		this.profileId = profileId;
		this.account = acc;
		this.accountId = acc.getAccId();
		
		this.combinedOrderId = generateOrderId();
	
	}
	
	public void displayorder(){
		System.out.println("Ordertype: "+orderType+" Security: "+security+" Share number: "+share_Number+" Account: "+account);
	}
	public void processingOrder(){
		orderState.processingOrder();
	}
	
	public OrderState getOrderState(){
		return orderState;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			if (obj instanceof Order) {
				Order o = (Order) obj;
				if (orderType.equals(o.getOrderType()) && seller_Or_buyer.equals(o.getSeller_Or_buyer())
						&& combinedOrderId==o.getCombinedOrderId() && profileId==o.getProfileId()
						&& accountId ==o.getAccountId() && share_Number==o.getShare_Number() &&security.equals(o.getSecurity())) {
					return true;
				}
				return false;
			}
		}
		return false;
	}
		

	private String generateOrderId(){
		String orderId;
		long randamNum = (long) (Math.random()*10000);
		orderId = Long.toString(profileId)+"-"+Long.toString(accountId)+"-"+Long.toString(randamNum);
		return orderId;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSeller_Or_buyer() {
		return seller_Or_buyer;
	}

	public void setSeller_Or_buyer(String seller_Or_buyer) {
		this.seller_Or_buyer = seller_Or_buyer;
	}

	public String getCombinedOrderId() {
		return combinedOrderId;
	}

	public void setCombinedOrderId(String combinedOrderId) {
		this.combinedOrderId = combinedOrderId;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public int getShare_Number() {
		return share_Number;
	}

	public void setShare_Number(int share_Number) {
		this.share_Number = share_Number;
	}

	public OrderState getOrderReceivedState(){
		return orderReceived;
	}
	
	public OrderState getOrderFulfilledState(){
		return orderFulfilled;
	}
	
	public OrderState getOrderInactiveState(){
		return orderInactive;
	}
	
	public void setState(OrderState s){
		this.orderState = s;
	}
	
	public void addObserver(){}
	public void deleteObserver(){}
	abstract public void notifyAccount(String type);
	abstract public void notifyMessenger();
	abstract public void notifySecurity();



}
