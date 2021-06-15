package BusinessLayer.Delivery;

import java.util.List;

import DataLayer.*;

public class Controller {

    DriverData driverData = new DriverData();
    DeliveryData deliveryData = new DeliveryData();
    TruckData truckData = new TruckData();
    locationData locationData = new locationData();
    DocumentData documentData = new DocumentData();

    public void addDriver(Driver d){
        driverData.save(d);
    }
    public void addTruck(Truck tr){
        truckData.save(tr);
    }
    public void addDelivery(Delivery dl){
        deliveryData.save(dl);
    }
    public void addLocation(Location loc){
        locationData.save(loc);
    }

    public void addDocument(Document doc){
        documentData.save(doc);
    }

    public Driver getDriver(int id){
       return driverData.get(id);
    }
    public Truck getTruck(int license){
        return truckData.get(license);
    }
    public Delivery getDelivery(int id){
        return deliveryData.get(id);
    }
    public Location getLocation(String address){
        return locationData.get(address);
    }
    public Document getDocument(int id){
        return documentData.get(id);
    }
    public List<Driver> getAllDrivers(){
        return driverData.getAll();
    }
    public List<Truck> getAllTrucks(){
        return truckData.getAll();
    }
    public List<Delivery> getAllDeliveries(){
        return deliveryData.getAll();
    }

    public List<Driver> getAllDriversA() {
        return driverData.getAvailableDrivers();
    }

    public List<Driver> getAllDriversB() {
        return driverData.getBusyDrivers();
    }

    public List<Truck> getAllTrucksA() {
        return truckData.getAvailableTrucks();

    }

    public List<Truck> getAllTrucksB() {
        return truckData.getBusyTrucks();
    }

    public List<Location> getAllLocations() {
        return locationData.getAll();
    }
//    public List<Location> gettAlllocations(){
//        return locationData.getAll();
//    }

}
