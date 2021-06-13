package BusinessLayer;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Enum.ShiftField;
import Enum.WorkerField;

public class Facade {
    ShiftController ShiftCntrl;
    WorkersController WorkersCntrl;
    static Controller conn;
    public Facade(){
    	this.conn= new Controller();
        this.ShiftCntrl = new ShiftController();
        this.WorkersCntrl = new WorkersController();
    }
    public void addDriver(Driver d){
        conn.addDriver(d);
    }
    public void addTruck(Truck tr){
        conn.addTruck(tr);
    }
    public void addDelivery(Delivery dl){
        conn.addDelivery(dl);
    }
    public void addLocation(Location loc){
        conn.addLocation(loc);
    }

    public void addDocument(Document doc){
        conn.addDocument(doc);
    }

    public Driver getDriver(int id){
       return conn.getDriver(id);
    }
    public Truck getTruck(int license){
        return conn.getTruck(license);
    }
    public Delivery getDelivery(int id){
        return conn.getDelivery(id);
    }
    public Location getLocation(String address){
        return conn.getLocation(address);
    }
    public Document getDocument(int id){
        return conn.getDocument(id);
    }
    public List<Driver> getAllDrivers(){
        return conn.getAllDrivers();
    }
    public List<Truck> getAllTrucks(){
        return conn.getAllTrucks();
    }
    public List<Delivery> getAllDeliveries(){
        return conn.getAllDeliveries();
    }

    public List<Driver> getAllDriversA() {
        return conn.getAllDriversA();
    }

    public List<Driver> getAllDriversB() {
        return conn.getAllDriversB();
    }

    public List<Truck> getAllTrucksA() {
        return conn.getAllTrucksA();

    }

    public List<Truck> getAllTrucksB() {
        return conn.getAllTrucksB();
    }

    public List<Location> getAllLocations() {
        return conn.getAllLocations();
    }
    public void createShift(Date shiftDate, int managerID, int shiftType, HashMap<Integer, Integer> workers, HashMap<Integer, Integer> roles){
    	ShiftCntrl.createShift(shiftDate, managerID, shiftType, workers, roles);
    }

    public <T> void updateShift(Shift shift, ShiftField field, T newValue){
    	ShiftCntrl.updateShift(shift, field, newValue);
    }
    public List<Shift> getWorkerShifts(Worker w){

    	return ShiftCntrl.getWorkerShifts(w);
    }

    public Shift getShift(Date date,int shiftType) {
    	return ShiftCntrl.getShift(date, shiftType);
   }
   public void printShift(Shift s) {
	   ShiftCntrl.printShift(s);
   }
    
    public void deleteShift(Shift shift){
    	ShiftCntrl.deleteShift(shift);
    }

    public HashMap<Integer, Integer> getShiftWorkers(Shift sh) {
    	return ShiftCntrl.getShiftWorkers(sh);
    }

    public void addWorkerToShift(Shift sh, int workerId, int roleId) {
    	ShiftCntrl.addWorkerToShift(sh, workerId, roleId);
    }

    public void removeWorkerFromShift(Shift sh, int id) {
    	ShiftCntrl.removeWorkerFromShift(sh, id);
    }

    public HashMap<Integer, Integer> getShiftRoles(Shift sh) {
        return ShiftCntrl.getShiftRoles(sh);
    }

    public void addRoleToShift(Shift sh, int id, int amount) {
    	ShiftCntrl.addRoleToShift(sh, id, amount);
    }

    public void removeRoleToShift(Shift sh, int id, int amount) {
    	ShiftCntrl.removeRoleToShift(sh, id, amount);
    }

    public boolean checkIfshiftExist(Date date, int shiftType) {
        return ShiftCntrl.checkIfshiftExist(date, shiftType);
    }

	public void init() {
		ShiftCntrl.init();
		WorkersCntrl.init();
	}
    public void createWorker(String name, int id, int bankAccountNumber, int bankNumber, int salary){ 
    	WorkersCntrl.createWorker(name, id, bankAccountNumber, bankNumber, salary);
    }

    public <T> void updateWorker(int id, WorkerField field, T newValue){
    	WorkersCntrl.updateWorker(id, field, newValue);
    }
    public boolean isWorker(int id) {
    	return WorkersCntrl.isWorker(id);
    }
    public Worker getWorker(int id) {
    	return WorkersCntrl.getWorker(id);
    }
    public void createRole(int id, String name ) {
    	WorkersCntrl.createRole(id, name);
    }
    public Role getRole(int id) {
    	return WorkersCntrl.getRole(id);
    }
    public void printRole(Role role) {
    	WorkersCntrl.printRole(role);
    }
    public boolean workerHasRole(Worker w,int roleid) {
    	return WorkersCntrl.workerHasRole(w, roleid);
    }
    public void printWorker(Worker w) {
    	WorkersCntrl.printWorker(w);
    }
    public Constraint getConstraintByShift(Worker w,Shift s) {
    	return WorkersCntrl.getConstraintByShift(w, s);
    }
    public void removeConstraint(Worker w,Shift s) {
    	WorkersCntrl.removeConstraint(w, s);
    	
    }
    public HashMap<Integer,Constraint> getWorkerConstraints(Worker w){
    	return WorkersCntrl.getWorkerConstraints(w);
    }

    public List<Role> getWorkerRoles(Worker w){
        return WorkersCntrl.getWorkerRoles(w);
    }

    public void printConstrint(Constraint c) {
    	WorkersCntrl.printConstrint(c);
    }
    public String getWorkerName(Worker w) {
    	return WorkersCntrl.getWorkerName(w);
    }
    
    public void deleteWorker(int id){
    	WorkersCntrl.deleteWorker(id);
    }

    public void addConstraintToWorker(Worker w, Constraint c){
    	WorkersCntrl.addConstraintToWorker(w, c);
    }

    public void addRoleToWorker(Worker w, int roleid) {
    	WorkersCntrl.addRoleToWorker(w, roleid);
    }
    public void removeRoleFromWorker(Worker w,int roleid) {
    	WorkersCntrl.removeRoleFromWorker(w, roleid);
    }

    public boolean isRole(int roleId) {
        return WorkersCntrl.isRole(roleId);
    }

	public HashMap<Integer, Worker> getWorkers() {
		return WorkersCntrl.getWorkers();
	}

	public HashMap<Integer, Role> getRoles() {
		return WorkersCntrl.getRoles();
	}
}
