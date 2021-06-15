package BusinessLayer.Delivery;
public class Truck {
    private int licenseNum;
    private String model;
    private int weight;
    private int capacity;
    private boolean busy=false;
    private Driver driver;

    public Truck(int license,String model,int weight,int capacity){
        this.licenseNum=license;
        this.model=model;
        this.weight=weight;
        this.capacity=capacity;
        this.busy=true;
    }

    public void setDriver(Driver driv){
        int license = driv.getLicense();
        if(license>=weight+capacity){
            this.driver=driv;
        }
    }

    public boolean isBusy() {
        return busy;
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

    public void setBusy(boolean busy) {
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
