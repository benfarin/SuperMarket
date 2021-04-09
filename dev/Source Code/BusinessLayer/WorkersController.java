package BusinessLayer;
import Enum.WorkerField;

import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class WorkersController {

    private HashMap<Integer, Worker> workers;
    private HashMap<Integer,Role> roles;

    public WorkersController(){
        workers = new HashMap<Integer, Worker>();
        roles = new HashMap<Integer,Role>();
    }

    public void createWorker(String name, int id, int bankAccountNumber, int bankNumber, int salary){ //TODO: no need to put JD and LD as parameters
        workers.put(id, new Worker(name, id, bankAccountNumber, bankNumber, salary, new Date(), null));
    }

    public <T> void updateWorker(int id, WorkerField field, T newValue){
        Worker toUpdate = workers.get(id);
        switch (field) {
            case name:
                toUpdate.setName((String)newValue);
                break;
            case id:
                toUpdate.setId((Integer) newValue);
                break;
            case bankNumber:
                toUpdate.setBankNumber((Integer) newValue);
                break;
            case bankAccountNumber:
                toUpdate.setBankAccount((Integer) newValue);
                break;
            case joiningDate:
                toUpdate.setJoiningDate((Date)newValue);
                break;
            case leavingDate:
                toUpdate.setLeavingDate((Date)newValue);
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
                "\tID:"+w.getId()+"\n"+
                "\tBank account Number:" +w.getBankAccount()+"\n"+
                "\tBank number:"+w.getBankNumber()+"\n"+
                "\tSalary:" +w.getSalary()+"\n"+
                "\tJoining date:"+w.getJoiningDate()+"\n"+
                "\tLeaving date:"+w.getLeavingDate()+"\n"
        );
    }
    public Constraint getConstraint(Worker w,Shift s) {
    	return(w.getConstraints().get(s));
    }
    public void removeConstraint(Worker w,Shift s) {
    	w.getConstraints().remove(s);
    }
    public HashMap<Shift,Constraint> getWorkerConstraints(Worker w){
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
                "\tWorker's Name:" +c.getWorker().getName()+"\n"
        );
    }
    public String getWorkerName(Worker w) {
    	return w.getName();
    }
    
    public void deleteWorker(int id){
        workers.remove(id);
        //TODO: delete him from shifts and so.
    }

    public void addConstraintToWorker (Worker w, Constraint c){
        w.addConstraint(c);
    }

    public void addRoleToWorker(Worker w, int roleid) {
        List<Role> s = w.getRoles();
        s.add(roles.get(roleid));
        w.setRoles(s);
    }
    public void removeRoleFromWorker(Worker w,int roleid) {
        List<Role> s=w.getRoles();
        s.remove(roles.get(roleid));
        w.setRoles(s);
    }

    public boolean isRole(int roleId) {
        return roles.containsKey(roleId);
    }
}
