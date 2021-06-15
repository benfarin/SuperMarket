package BusinessLayer.Suppliers;

import java.util.HashMap;

public class Contract {

    private String daysOfSupply;
    private boolean needsDelivery;
    private String location;
    HashMap<Integer, Integer> totalPriceDiscount;

    public Contract(String days, boolean delivery, HashMap<Integer, Integer> discounts, String location) {
        daysOfSupply = days;
        needsDelivery = delivery;
        totalPriceDiscount = discounts;
        this.location= location;

    }
    public Contract(String days, boolean delivery){
        daysOfSupply = days;
        needsDelivery = delivery;
        totalPriceDiscount = new HashMap<Integer, Integer>();
    }

    public void AddPriceDiscount(int amount,int precent) { // TODO:need be add to the facade by the arguments
//        Scanner io = new Scanner(System.in);
//        System.out.println("Enter an amount for precent discount");
//        int amount = io.nextInt();
//        System.out.println("Enter an amount for precent discount");
//        int percent = io.nextInt();
        totalPriceDiscount.put(amount, precent);

    }
    public double getTotalPriceDiscount(double amount,double price) {
        if(totalPriceDiscount!=null) {
            int min_key = 0;
            for (Integer key : totalPriceDiscount.keySet()) {
                if (key < amount && min_key < key)
                    min_key = key;
            }
            if (min_key!=0){
                return(1 - (totalPriceDiscount.get(min_key) * 0.01)) * amount * price;
            }
            else {
                return amount*price;
            }
        }
        else {
            return amount*price;
        }
    }
    /*public double getTotalPriceDiscount(double amount,double price) {
        int min_key=0;
        for (Integer key : totalPriceDiscount.keySet()) {
            if(key<amount&&min_key<key)
                min_key=key;
        }
        return (1-(totalPriceDiscount.get(min_key)*0.01))*amount*price; // TODO: change from niv
    }*/ // old version of getTotalPriceDiscount


    public boolean isNeedsDelivery() {
        return needsDelivery;
    }

    public String getDaysOfSupply() {
        return daysOfSupply;
    }

    public String getLocation() {
        return location;
    }

    public String toString() {
        String s;
        s="days of supply:\t" + daysOfSupply+"\n";
        s+="Location of pickup:\t"+location+"\n";
        if (needsDelivery)
            s+="Needs Delivery\n";
        else
            s+="Does NOT need Delivery\n\n";
        s+="Total Price Discounts:\nPrice  :  % Discount\n";
        for (int i = 0; i < totalPriceDiscount.size(); i++) { // printing all the discounts
            s+=totalPriceDiscount.keySet().toArray()[i] + "\t:\t" + totalPriceDiscount.get(totalPriceDiscount.keySet().toArray()[i])+"\n";
        }
        return s;
    }
}

