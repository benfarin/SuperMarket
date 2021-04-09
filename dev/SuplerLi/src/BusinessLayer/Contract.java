package BusinessLayer;

import java.util.HashMap;
import java.util.Scanner;

public class Contract {

    private String daysOfSupply;
    private boolean needsDelivery;
    HashMap<Integer, Integer> totalPriceDiscount; //TODO: Change the price to Double

    public Contract(String days, boolean delivery) {
        daysOfSupply = days;
        needsDelivery = delivery;

    }

    public void AddPriceDiscount() {
        Scanner io = new Scanner(System.in);
        System.out.println("Enter an amount for precent discount");
        int amount = io.nextInt();
        System.out.println("Enter an amount for precent discount");
        int percent = io.nextInt();
        totalPriceDiscount.put(amount, percent);

    }

    public double getTotalPriceDiscount(double amount,double price) {
        int min_key=0;
        for (Integer key : totalPriceDiscount.keySet()) {
            if(key<amount&&min_key<key)
                min_key=key;
        }
        return min_key*amount*price;
    }

    public Contract(String days, boolean delivery, HashMap<Integer, Integer> discounts) {
        daysOfSupply = days;
        needsDelivery = delivery;
        totalPriceDiscount = discounts;
    }

    public String toString() {
        System.out.println("days of supply is:  " + daysOfSupply);
        if (needsDelivery)
            System.out.println(" Need Delivery");
        else
            System.out.println("need Delivery");
        for (int i = 0; i < totalPriceDiscount.size(); i++) { // printing all the discounts
            System.out.println(totalPriceDiscount.keySet().toArray()[i] + "  :  " + totalPriceDiscount.get(totalPriceDiscount.keySet().toArray()[i]));
        }
    }
}

