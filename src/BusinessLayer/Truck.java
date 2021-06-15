package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Truck {
    private int licenseNum;
    private String model;
    private int weight;
    private int capacity;
    private int busy=0;
    private Driver driver;
    private List<Date> dateList = new LinkedList<>();

    public Truck(int license,String model,int weight,int capacity){
        this.licenseNum=license;
        this.model=model;
        this.weight=weight;
        this.capacity=capacity;
        this.busy=0;
    }

    public void setDriver(Driver driv){
        int license = driv.getLicense();
        if(license>=weight+capacity){
            this.driver=driv;
        }
    }
    // check if the driver is the same as inn the past
//    public void get(Driver d ){
//        int x =d.getLicense();
//        int x1 = d.getLicense()+1;
//        if(licenseNum>weight+capacity||isBusy()==1){this.driver=d;}
//    }

    public boolean isBusy(Date date) {
        for(int i=0;i<dateList.size();i++){
            if(dateList.get(i).equals(date)){
                return true;
            }
        }
        dateList.add(date);
        return false;
    }



    public Driver getDriver() {
        return driver;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLicenseNum() {
        return licenseNum;
    }

    public int getWeight() {
        return weight;
    }

    public String getModel() {
        return model;
    }

    public void setBusy(int busy) {
        this.busy = busy;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setLicenseNum(int licenseNum) {
        this.licenseNum = licenseNum;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
