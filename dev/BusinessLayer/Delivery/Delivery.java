package BusinessLayer.Delivery;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class Delivery {
    private int id;
    private Date date;
    private int exitTime;
    private int numOfTruck;
    private String driverName;
    private List<Location> locations;
    private int truckW;
    private Truck truck;
    private int DeliveryWeight;

    public Delivery(int id, Date date, int exit, int truckNum, String driverN, int deliveryWeight, List<Location> locations, int truckWeight, Truck truck){
        this.id = id;
        this.date=date;
        this.exitTime=exit;
        this.numOfTruck=truckNum;
        this.driverName=driverN;
        this.locations=locations;
        this.truckW=truckWeight;
        this.truck=truck;
        this.DeliveryWeight=deliveryWeight;
    }

    public Delivery(int id, Date date, int numOfTruck, String driverName, List<Location> location, int truckW, int deliveryWeight) {
        this.id=id;
        this.date=date;
        this.numOfTruck = numOfTruck;
        this.driverName=driverName;
        this.locations=location;
        this.truckW=truckW;
        this.DeliveryWeight=deliveryWeight;
    }


    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int getExitTime() {
        return exitTime;
    }

    public int getNumOfTruck() {
        return numOfTruck;
    }

    public int getTruckW() {
        return truckW;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String getDriverName() {
        return driverName;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setExitTime(int exitTime) {
        this.exitTime = exitTime;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void setNumOfTruck(int numOfTruck) {
        this.numOfTruck = numOfTruck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public void setTruckW(int truckW) {
        this.truckW = truckW;
    }

    public int getDeliveryWeight() {
        return DeliveryWeight;
    }

    public void setDeliveryWeight(int deliveryWeight) {
        this.DeliveryWeight = deliveryWeight;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", date=" + date +
                ", exitTime=" + exitTime +
                ", numOfTruck=" + numOfTruck +
                ", driverName='" + driverName + '\'' +
                ", locations=" + locations +
                ", truckW=" + truckW +
                ", truck=" + truck +
                ", DeliveryWeight=" + DeliveryWeight +
                '}';
    }
}
