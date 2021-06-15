package BusinessLayer;
import com.sun.javafx.collections.MappingChange;
import javafx.util.Pair;
import sun.util.calendar.BaseCalendar;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Collections;
import java.util.List;

public class DeliveryController {
    SimpleDateFormat s = new SimpleDateFormat("HH.mm.ss Z");
    String sr = s.format(new Date());
    private Map<Pair<Truck,Date>,Document1> theMap = new HashMap<>();
    private HashMap<Integer,Document1> allDocuments = new HashMap<>();
    private Map<Date,List<Document1>> documentANDdate = new HashMap<>();
    private List<Document> documents = new LinkedList<>();
    private Map<Date,Document> futureDeliveries;
    private List<Date> datesList = new LinkedList<>();
    private Comp getcomp = new Comp();
    private static DeliveryController instance = null;
    private List<Delivery> deliveries;//all deliveries
    private List<Truck> trucks;// all the trucks
    private List<Driver> drivers;// all drivers
    private Map<Driver,Truck> dtMap;//the driver ride this truck in this delivery
    private Map<Delivery,Driver> ddMap;// this delivery goes to this driver

    private DeliveryController(){
        List<Delivery> deliveries= new LinkedList<Delivery>();//all deliveries
        List<Truck> trucks= new LinkedList<Truck>();// all the trucks
        List<Driver> drivers= new LinkedList<Driver>();// all drivers
        HashMap<Driver,Truck> dtMap= new HashMap<Driver, Truck>();//the driver ride this truck in this delivery
        HashMap<Delivery,Driver> ddMap= new HashMap<Delivery, Driver>();

    }

    public void addDriverAndTruck(Driver driver,Truck truck){
        dtMap.put(driver,truck);
    }
    public void addDeliverDriver(Delivery deliv,Driver driver){
        ddMap.put(deliv,driver);
    }
    public static DeliveryController getInstance(){
        if(instance==null){
            instance= new DeliveryController();
        }
        return instance;
    }

//    public List<Document> getSortedLocation(List<Document> doc){// sort locations
////        Collections.sort(doc);
////        return doc;
//    }


    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public Map<Delivery, Driver> getDdMap() {
        return ddMap;
    }

    public Map<Driver, Truck> getDtMap() {
        return dtMap;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }
    public void addTruck(Truck truck){
        this.trucks.add(truck);
    }
    public void addDriver(Driver driver){
        if(driver!=null)
        this.drivers.add(driver);
    }






//    public void addDelivery(int DeliveryWeight, List<Document> documents)  {
//        List<Location> locations= new LinkedList<>();
//        for(int i=0; i<documents.size();i++){
//            locations.add(documents.get(i).getLocation());
//        }
//        Pair<Truck,Boolean> ftruck= FindTruck();
//        if(ftruck.getValue()) {
//            Pair<Driver, Boolean> fDriver = FindDriver(ftruck.getKey());
//            if (fDriver.getValue()) {
//                Delivery d = new Delivery(1,(new Date(dtf.format(now))),x, ftruck.getKey().getLicenseNum(), fDriver.getKey().getName(), DeliveryWeight, locations, ftruck.getKey().getWeight(), ftruck.getKey());
//                conn.addDelivery(d);
//                deliveries.add(d);
//                dtMap.put(fDriver.getKey(), ftruck.getKey());
//                ddMap.put(d, fDriver.getKey());
//                ftruck.getKey().setBusy(1);
//                fDriver.getKey().setBusy(1);
//                sort(documents);
//                fDriver.getKey().setDocuments(documents);
//            }
//        }
//        else{
//            throw new RuntimeException("The weight is higher than capacity!");
//        }
//    }

    //    public void createNewDelivery(Truck truck,Driver driver,Delivery delivery,List<Location> location) throws Exception {
//        if(chekIfOk(truck,driver,delivery)){
//            Delivery dd = new Delivery(new Date(10),9, truck.getLicenseNum(), driver.getName(),delivery.getDeliveryWeight(),location, truck.getWeight(),truck);
//            deliveries.add(dd);
//        }
//        else{
//            throw new Exception("There is no Truck that can be Available for this Delivery!");
//        }
//        System.out.println(":D");
//    }
//
    public boolean chekIfOk(Truck tr,Driver dr,Delivery dl){
          if(tr.getWeight()+dl.getDeliveryWeight()>dr.getLicense()){
              throw new RuntimeException("The driver can't do this Delivery!!");
          }
          if(dr.isBusy()==1){
              return false;
          }
          return true;
    }
    public Pair<Truck,Boolean> FindTruck(Date date){
        boolean find= false;
        int i=0;
        Truck truck=null;
        while (!find && i<=trucks.size()){
            if(!trucks.get(i).isBusy(date)){
                find= true;
                truck=trucks.get(i);
            }
            i++;
        }
        return new Pair<>(truck,find);
    }
    public Pair<Driver,Boolean> FindDriver(Truck truck){
        boolean find = false;
        int i=0;
        Driver driver= null;
        while(!find&& i<= drivers.size()){
            if(drivers.get(i).getLicense() >= truck.getWeight()+truck.getCapacity()){
                find =true;
                driver= drivers.get(i);
            }
            i++;
        }
        return new Pair<Driver, Boolean>(driver,find);
    }


    public double getApproximatelyTime(List<Location> loc){
        for (int i = 0; i < loc.size(); i++) {
            if (loc.get(i).equals("South")) {
                return 3.5;
            }
            if (loc.get(i).equals("North")) {
                return 2;
            }
            if (loc.get(i).equals("West")) {
                return 5.2;
            }
            if (loc.get(i).equals("East")) {
                return 3.4;
            }
            if (loc.get(i).equals("Center")) {
                return 1.2;
            }
        }
        return 0;
    }
    public void addDelivery(Delivery delv){
        deliveries.add(delv);
    }

    public void sort(List<Document> list){
        Collections.sort(list,getcomp);
    }

    public void createNewDelivery(int oid,int productid,String productName,int amount,int weightPerUnit,List<String> contacts,String makor,String ya3ad,Date date){
        List<Pair<String,Integer>> l = new LinkedList<Pair<String,Integer>>();
        Pair<String,Integer> p = new Pair(productName,amount);
        l.add(p);
        int weight = weightPerUnit * amount;
        for(Truck t : trucks){
            if(!t.isBusy(date)&&weight<t.getCapacity()){
                Driver d = FindDriver(t).getKey();
                if(d!=null){
                    int truckN = t.getLicenseNum();
                    String driverName = d.getName();
                    Document1 doc = new Document1(oid,l,makor,truckN,date,10,ya3ad,driverName);
                    allDocuments.put(oid,doc);
                    documentANDdate.get(date).add(doc);
                    theMap.put(new Pair<>(t,date),doc);
                    Delivery d1 = new Delivery(oid,date,10,truckN,driverName,makor,ya3ad,t.getWeight(),weight);
                }
            }
        }

    }

    public boolean addToExist(int id,String productName,int amount){
        Pair<String,Integer> newmotsar = new Pair<>(productName,amount);
        if(!allDocuments.containsKey(id)){return false;}
        Document1 d = allDocuments.get(id);
        d.addmotsar(newmotsar);
        return true;
    }



    public void newUrgenDelivery(int id,String productName,int amount,Date date,String makor,String ya3ad,int weightPerUnit){
        int weight1 = amount*weightPerUnit;
        List<Pair<String,Integer>> l1 =  new LinkedList<>();
        Pair<String,Integer> p1 = new Pair(productName,amount);
        l1.add(p1);
        for(Truck t : trucks){
            if(!t.isBusy(date)&&t.getCapacity()>=weight1){
                Driver d = FindDriver(t).getKey() ;
                int truckN = t.getLicenseNum();
                String driverN = d.getName();
                Document1 doc = new Document1(id,l1,makor,truckN,date,10,ya3ad,driverN);
                allDocuments.put(id,doc);
                if(documentANDdate.containsKey(date)) {
                    documentANDdate.get(date).add(doc);
                }
                else{
                    List<Document1> l = new LinkedList<>();
                    l.add(doc);
                    documentANDdate.put(date,l);
                }
            }
            else{
                for(Truck t1 : trucks){
                    if(t1.isBusy(date)&&t1.getCapacity()>weight1){
                        Pair<Truck,Date> x = new Pair(t1,date);
                        Driver d1 = FindDriver(t1).getKey();
                        String d1Name = d1.getName();
                        Document1 d = theMap.get(x);
                        d.changeDate(new Date(date.getTime()+7));
                        Document1 docu = new Document1(id,l1,makor,t1.getLicenseNum(),date,10,ya3ad,d1Name);
                        allDocuments.put(id,docu);
                        if(documentANDdate.containsKey(date)){
                            documentANDdate.get(date).add(docu);
                        }
                        else{
                            List<Document1> l2 = new LinkedList<>();
                            l2.add(docu);
                            documentANDdate.put(date,l2);
                        }
                    }
                }
            }
        }
    }
    public Document1 getDocument(int id){
        return allDocuments.get(id);
    }



}