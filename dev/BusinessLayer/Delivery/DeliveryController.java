package BusinessLayer.Delivery;

import java.util.List;

//import com.sun.javafx.collections.MappingChange;
//import javafx.util.Pair;
//import java.util.*;
//import java.util.Collections;
//import java.util.List;
//
public class DeliveryController {
    public DeliveryController() { }
   public void NewUrgentDelivery(long id , long serialNum, int amount, double weight, List<String> cotacts, String location, String daysOfSup){
       System.out.println("New urgent delivery accepted\n");
   }
   public void addToExistUregnt(long id, long serialNum, int amount, double weight){
       System.out.println("added new order to existing delivery\n");
   }

    public void createNewDelivery(Long id, Long supplierSerialNum, int amount, double weightPerUnit, List<String> contacts, String location, String daysOfSupply) {
        System.out.println("New Delivery created for periodic order");
    }

    public void addToExist(Long id, Long supplierSerialNum, int amount, double weightPerUnit) {
        System.out.println("added new order to existing delivery(periodic)\n");
    }
}
//    Controller conn = new Controller();
//    private Comp getcomp = new Comp();
//    private static DeliveryController instance = null;
//    private List<Delivery> deliveries;//all deliveries
//    private List<Truck> trucks;// all the trucks
//    private List<Driver> drivers;// all drivers
//    private Map<Driver,Truck> dtMap;//the driver ride this truck in this delivery
//    private Map<Delivery,Driver> ddMap;// this delivery goes to this driver
//
//    private DeliveryController(){
//        List<Delivery> deliveries= new LinkedList<Delivery>();//all deliveries
//        List<Truck> trucks= new LinkedList<Truck>();// all the trucks
//        List<Driver> drivers= new LinkedList<Driver>();// all drivers
//        HashMap<Driver,Truck> dtMap= new HashMap<Driver, Truck>();//the driver ride this truck in this delivery
//        HashMap<Delivery,Driver> ddMap= new HashMap<Delivery, Driver>();
//
//    }
//
//    public void addDriverAndTruck(Driver driver,Truck truck){
//        dtMap.put(driver,truck);
//    }
//    public void addDeliverDriver(Delivery deliv,Driver driver){
//        ddMap.put(deliv,driver);
//    }
//    public static DeliveryController getInstance(){
//        if(instance==null){
//            instance= new DeliveryController();
//        }
//        return instance;
//    }
//
////    public List<Document> getSortedLocation(List<Document> doc){// sort locations
//////        Collections.sort(doc);
//////        return doc;
////    }
//
//
//    public List<Delivery> getDeliveries() {
//        return deliveries;
//    }
//
//    public List<Driver> getDrivers() {
//        return drivers;
//    }
//
//    public List<Truck> getTrucks() {
//        return trucks;
//    }
//
//    public Map<Delivery, Driver> getDdMap() {
//        return ddMap;
//    }
//
//    public Map<Driver, Truck> getDtMap() {
//        return dtMap;
//    }
//
//    public void setDrivers(List<Driver> drivers) {
//        this.drivers = drivers;
//    }
//
//    public void setTrucks(List<Truck> trucks) {
//        this.trucks = trucks;
//    }
//    public void addTruck(Truck truck){
//        this.trucks.add(truck);
//    }
//    public void addDriver(Driver driver){
//        if(driver!=null)
//        this.drivers.add(driver);
//    }
//
//
//    public void addDelivery(int DeliveryWeight, List<Document> documents)  {
//        List<Location> locations= new LinkedList<>();
//        for(int i=0; i<documents.size();i++){
//            locations.add(documents.get(i).getLocation());
//        }
//        Pair<Truck,Boolean> ftruck= FindTruck(DeliveryWeight);
//        if(ftruck.getValue()) {
//            Pair<Driver, Boolean> fDriver = FindDriver(ftruck.getKey());
//            if (fDriver.getValue()) {
//                Delivery d = new Delivery(1,new Date(1998/07/22), 12, ftruck.getKey().getLicenseNum(), fDriver.getKey().getName(), DeliveryWeight, locations, ftruck.getKey().getWeight(), ftruck.getKey());
//                conn.addDelivery(d);
//                deliveries.add(d);
//                dtMap.put(fDriver.getKey(), ftruck.getKey());
//                ddMap.put(d, fDriver.getKey());
//                ftruck.getKey().setBusy(true);
//                fDriver.getKey().setBusy(true);
//                sort(documents);
//                fDriver.getKey().setDocuments(documents);
//            }
//        }
//        else{
//            throw new RuntimeException("The weight is higher than capacity!");
//        }
//    }
//
//    //    public void createNewDelivery(Truck truck,Driver driver,Delivery delivery,List<Location> location) throws Exception {
////        if(chekIfOk(truck,driver,delivery)){
////            Delivery dd = new Delivery(new Date(10),9, truck.getLicenseNum(), driver.getName(),delivery.getDeliveryWeight(),location, truck.getWeight(),truck);
////            deliveries.add(dd);
////        }
////        else{
////            throw new Exception("There is no Truck that can be Available for this Delivery!");
////        }
////        System.out.println(":D");
////    }
////
//    public boolean chekIfOk(Truck tr,Driver dr,Delivery dl){
//          if(tr.getWeight()+dl.getDeliveryWeight()>dr.getLicense()){
//              throw new RuntimeException("The driver can't do this Delivery!!");
//          }
//          if(dr.isBusy()){
//              return false;
//          }
//          return true;
//    }
//    public Pair<Truck,Boolean> FindTruck(int DeliveryWeight){
//        boolean find= false;
//        int i=0;
//        Truck truck=null;
//        while (!find && i<=trucks.size()){
//            if(trucks.get(i).getCapacity()>= DeliveryWeight && !trucks.get(i).isBusy()){
//                find= true;
//                truck=trucks.get(i);
//            }
//            i++;
//        }
//        return new Pair<>(truck,find);
//    }
//    public Pair<Driver,Boolean> FindDriver(Truck truck){
//        boolean find = false;
//        int i=0;
//        Driver driver= null;
//        while(!find&& i<= drivers.size()){
//            if(drivers.get(i).getLicense() >= truck.getWeight()+truck.getCapacity()){
//                find =true;
//                driver= drivers.get(i);
//            }
//            i++;
//        }
//        return new Pair<Driver, Boolean>(driver,find);
//    }
//
//
//    public double getApproximatelyTime(List<Location> loc){
//        for (int i = 0; i < loc.size(); i++) {
//            if (loc.get(i).equals("South")) {
//                return 3.5;
//            }
//            if (loc.get(i).equals("North")) {
//                return 2;
//            }
//            if (loc.get(i).equals("West")) {
//                return 5.2;
//            }
//            if (loc.get(i).equals("East")) {
//                return 3.4;
//            }
//            if (loc.get(i).equals("Center")) {
//                return 1.2;
//            }
//        }
//        return 0;
//    }
//    public void addDelivery(Delivery delv){
//        deliveries.add(delv);
//    }
//
//    public void sort(List<Document> list){
//        Collections.sort(list,getcomp);
//    }
//
//}