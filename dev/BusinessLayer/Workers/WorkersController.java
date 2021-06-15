package BusinessLayer.Workers;
import BusinessLayer.Workers.Enum.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import DataLayer.DALController;
import DataLayer.MapperData;

public class WorkersController {

    private HashMap<Integer, Worker> workers;
    private HashMap<Integer,Role> roles;
    private DALController DAL;
    public WorkersController(){
    	DAL=new DALController();
        workers = new HashMap<Integer, Worker>();
        roles = new HashMap<Integer,Role>();
        init();
    }

    public void createWorker(String name, int id, int bankAccountNumber, int bankNumber, int salary){ //TODO: no need to put JD and LD as parameters
        DAL.insert(id, name, bankAccountNumber, bankNumber, salary);
    	workers.put(id, new Worker(name, id, bankAccountNumber, bankNumber, salary, new Date(System.currentTimeMillis()), null));
    }

    public <T> void updateWorker(int id, WorkerField field, T newValue){
        Worker toUpdate = workers.get(id);
        switch (field) {
            case name:
                toUpdate.setName((String)newValue);
                DAL.update(toUpdate,field.toString(),(String)newValue);
                break;
            case id:
                toUpdate.setId((Integer) newValue);
                DAL.update(toUpdate,field.toString(),(Integer)newValue);
                break;
            case bankNumber:
                toUpdate.setBankNumber((Integer) newValue);
                DAL.update(toUpdate,field.toString(),(Integer)newValue);
                break;
            case bankAccountNumber:
                toUpdate.setBankAccount((Integer) newValue);
                DAL.update(toUpdate,field.toString(),(Integer)newValue);
                break;
            case salary:
                toUpdate.setJoiningDate((Date)newValue);
                DAL.update(toUpdate,field.toString(),(java.sql.Date)newValue);
                break;
            case leavingDate:
                toUpdate.setLeavingDate((Date)newValue);
                DAL.update(toUpdate,field.toString(),(java.sql.Date)newValue);
                break;
        }
            workers.remove(id);
            workers.put(toUpdate.getId(), toUpdate);

    }
    public boolean isWorker(int id) {
    	return(workers.containsKey(id));
    }
    public Worker getWorker(int id) {
    	return(workers.get(id));
    }
    public void createRole(int id, String name ) {
    	roles.put(id, new Role(id,name));
    	DAL.insert(id, name);
    }
    public Role getRole(int id) {
    	return roles.get(id);
    }
    public void printRole(Role role) {
    	System.out.println("Role Name: "+role.getRoleName()+"\t"+"Role ID: "+role.getRoleID());
    }
    public boolean workerHasRole(Worker w,int roleid) {
    	return(w.getRoles().contains(getRole(roleid)));
    }
    public void printWorker(Worker w) {
        System.out.println("\t\tWorker's Info:\n\n" +
                "\tName:"+w.getName()+"\n"+   
                "\tID:"+w.getId()+"\n"+
                "\tBank account Number:" +w.getBankAccount()+"\n"+
                "\tBank number:"+w.getBankNumber()+"\n"+
                "\tSalary:" +w.getSalary()+"\n"+
                "\tJoining date:"+w.getJoiningDate()+"\n"+
                "\tLeaving date:"+w.getLeavingDate()+"\n"
        );
    }
    public Constraint getConstraintByShift(Worker w,Shift s) {
    	HashMap<Integer,Constraint> constraints = w.getConstraints();
    	for (Constraint c : constraints.values()) {
    		if (c.getShiftType() == s.getShiftType() && c.getConstraintDate().equals(s.getShiftDate()))
    			return c;
    	}
    	return null;
    }
    public void removeConstraint(Worker w,Shift s) {
    	HashMap<Integer,Constraint> constraints = w.getConstraints();
    	for (Constraint c : constraints.values()) {
    		if (c.getShiftType() == s.getShiftType() && c.getConstraintDate().equals(s.getShiftDate())) {
    	    	DAL.delete(c);
        		w.getConstraints().remove(c.getId());
    		}
    	}

    	
    }
    public HashMap<Integer,Constraint> getWorkerConstraints(Worker w){
    	return(w.getConstraints());
    }

    public List<Role> getWorkerRoles(Worker w){
        return (w.getRoles());
    }

    public void printConstrint(Constraint c) {
        System.out.println("\t\tConstraint's Info:\n\n" +
                "\tConstraint Date:"+c.getConstraintDate()+"\n"+
                "\tIs Temp constraint:" +c.isTemp()+"\n"+
                "\tShift type:"+c.getShiftType()+"\n"+
                "\tWorker's Name:" +workers.get(c.getWorker()).getName()+"\n"
        );
    }
    public String getWorkerName(Worker w) {
    	return w.getName();
    }
    
    public void deleteWorker(int id){
    	Worker w=workers.get(id);
    	DAL.delete(w);
        workers.remove(id);
    }

    public void addConstraintToWorker(Worker w, Constraint c){
    	int istemp=0;
    	if(c.isTemp())
    		istemp=1;
    	DAL.insert(istemp,(java.sql.Date)c.getConstraintDate(),c.getShiftType(),w.getId());
        w.addConstraint(c);
    }

    public void addRoleToWorker(Worker w, int roleid) {
    	DAL.insertWR(w.getId(), roleid);
        List<Role> s = w.getRoles();
        s.add(roles.get(roleid));
        w.setRoles(s);
    }
    public void removeRoleFromWorker(Worker w,int roleid) {
    	DAL.deleteWR(w.getId(), roleid);
        List<Role> s=w.getRoles();
        s.remove(roles.get(roleid));
        w.setRoles(s);
    }

    public boolean isRole(int roleId) {
        return roles.containsKey(roleId);
    }

	public HashMap<Integer, Worker> getWorkers() {
		return workers;
	}

	public void init() {

		workers = DAL.initWorkers();
	    roles = DAL.initRoles();
	    for(Worker w:workers.values()) {
	    List<Role> r = new LinkedList<Role>();
	    w.setConstraints(DAL.initWorkersConstraints(w.getId()));
	    for(int roleid:DAL.initWorkersRoles(w.getId())) {
	    	r.add(roles.get(roleid));
	    }
	    w.setRoles(r);
	    }
	    }

	public HashMap<Integer, Role> getRoles() {
		return roles;
	}
	}

    
   

