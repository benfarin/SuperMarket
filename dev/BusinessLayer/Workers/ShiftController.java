package BusinessLayer.Workers;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import DataLayer.DALController;
import BusinessLayer.Workers.Enum.*;


public class ShiftController {
    private List<Shift> Shifts;
    private DALController DAL;
    
    public ShiftController(){
    	DAL=new DALController();
        Shifts = new LinkedList<>();
    }

    public void createShift(Date shiftDate, int managerID, int shiftType, HashMap<Integer, Integer> workers, HashMap<Integer, Integer> roles){
    	Shift s=new Shift(shiftDate, managerID,shiftType, workers, roles);
    	DAL.insert((java.sql.Date)shiftDate, managerID, shiftType);
    	for(int workerId: workers.keySet()) {
    		DAL.insertWS(workerId, s.getId());
    	}
    	for(Map.Entry<Integer,Integer> role: roles.entrySet()) {
    		DAL.insertSR(s.getId(), role.getKey(),role.getValue());
    	}
        Shifts.add(s);
    }

    public <T> void updateShift(Shift shift, ShiftField field, T newValue){
        switch (field) {
            case ShiftDate:
            	DAL.update(shift, field.toString(), (java.sql.Date)newValue);
                shift.setShiftDate((Date)newValue);
                break;
            case ManagerID:
            	DAL.update(shift, field.toString(), (Integer)newValue);
                shift.setManagerID((Integer) newValue);
                break;
            case ShiftType:
            	DAL.update(shift, field.toString(), (Integer)newValue);
                shift.setShiftType((Integer) newValue);
                break;
            case Workers:
            	for(int workerId: shift.getWorkers().keySet()) {
            		DAL.deleteWS(workerId, shift.getId());
            	}
            	for(int workerId: ((HashMap<Integer, Integer>)newValue).keySet()) {
            		DAL.insertWS(workerId, shift.getId());
            	}
                shift.setWorkers((HashMap<Integer, Integer>)newValue);
                break;
            case Roles:
            	for(int role:((HashMap<Integer, Integer>)newValue).keySet()) {
            		DAL.deleteSR(shift.getId(), role);
            	}    
            	for(Map.Entry<Integer,Integer> role: ((HashMap<Integer, Integer>)newValue).entrySet()) {
            		DAL.insertSR(shift.getId(), role.getKey(),role.getValue());
            	}
                shift.setRoles((HashMap<Integer,Integer>)newValue);
                break;
        }

    }
    public List<Shift> getWorkerShifts(Worker w){
    	List<Shift> wShifts = new LinkedList<Shift>();
    	for(Shift s:Shifts) {
    		if(s.getWorkers().containsKey(w.getId()))
    			wShifts.add(s);
    	}
    	return wShifts;
    }

    public Shift getShift(Date date,int shiftType) {
   	for(Shift s:Shifts) {
		if(s.getShiftDate()==date&&s.getShiftType()==shiftType)
			return s;
	}
   	return null;
   }
   public void printShift(Shift s) {
       System.out.println("\t\tShift Info:\n\n" +
               "\tShift date:"+s.getShiftDate()+"\n"+
               "\tManager ID:" +s.getManagerID()+"\n"+
               "\tShift Type:"+s.getShiftType()+"\n"+
               "\tWorker's list:"+s.getWorkers().keySet()+"\n"
       );
   }
    
    public void deleteShift(Shift shift){
    	DAL.delete(shift);
        Shifts.remove(shift);
    }

    public HashMap<Integer, Integer> getShiftWorkers(Shift sh) {
        return sh.getWorkers();
    }

    public void addWorkerToShift(Shift sh, int workerId, int roleId) {
    	DAL.insertWS(workerId, sh.getId());
    	DAL.updateSR(sh.getId(), roleId, sh.getRoles().get(roleId)-1);
        sh.addWorker(workerId, roleId);
    }

    public void removeWorkerFromShift(Shift sh, int id) {
    	HashMap <Integer, Integer> workers = sh.getWorkers();
    	DAL.deleteWS(id, sh.getId());
    	DAL.updateSR(sh.getId(), workers.get(id), sh.getRoles().get( workers.get(id))+1);
        workers.remove(id);
        sh.setWorkers(workers);
    }

    public HashMap<Integer, Integer> getShiftRoles(Shift sh) {
        return sh.getRoles();
    }

    public void addRoleToShift(Shift sh, int id, int amount) {
        HashMap<Integer, Integer> roles = sh.getRoles();
        if(roles.containsKey(id)) {
	        int currAmount = roles.get(id);
	        currAmount += amount;
	        roles.remove(id);
	        roles.put(id, currAmount);
	        DAL.updateSR(sh.getId(), id, currAmount);
        }
        else {
        	DAL.insertSR(sh.getId(), id, amount);
        	roles.put(id, amount);
        	sh.setRoles(roles);
        }
    }

    public void removeRoleToShift(Shift sh, int id, int amount) {
        HashMap<Integer, Integer> roles = sh.getRoles();
        int currAmount = roles.get(id);
        currAmount = currAmount-amount;
        roles.remove(id);
        if (currAmount > 0) {
	        DAL.updateSR(sh.getId(), id, currAmount);
            roles.put(id, amount);
        }
        else {
        	DAL.deleteSR(sh.getId(), id);
        }
        sh.setRoles(roles);
    }

    public boolean checkIfshiftExist(Date date, int shiftType) {
        return (getShift(date, shiftType) != null);
    }

	public void init() {
		Shifts=DAL.initShifts();
		
	}
}
