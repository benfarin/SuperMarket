package BusinessLayer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Enum.ShiftField;


public class ShiftController {
    private List<Shift> Shifts;
    
    
    public ShiftController(){
        Shifts = new LinkedList<>();
    }

    public void createShift(Date shiftDate, int managerID, int shiftType, HashMap<Integer, Integer> workers, HashMap<Integer, Integer> roles){ 
        Shifts.add(new Shift(shiftDate, managerID,shiftType, workers, roles));
    }

    public <T> void updateShift(Shift shift, ShiftField field, T newValue){
        switch (field) {
            case ShiftDate:
                shift.setShiftDate((Date)newValue);
                break;
            case ManagerID:
                shift.setManagerID((Integer) newValue);
                break;
            case ShiftType:
                shift.setShiftType((Integer) newValue);
                break;
            case Workers:
                shift.setWorkers((HashMap<Integer, Integer>)newValue);
                break;
            case Roles:
                shift.setRoles((HashMap<Integer,Integer>)newValue);
                break;
        }

    }
    public List<Shift> getWorkerShifts(Worker w){
    	List<Shift> wShifts = null;
    	for(Shift s:Shifts) {
    		if(s.getWorkers().containsKey(w.getId()))
    			wShifts.add(s);
    	}
    	return wShifts;
    }

    public void addShift(Shift shift) {
        Shifts.add(shift);
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
        Shifts.remove(shift);
    }

    public HashMap<Integer, Integer> getShiftWorkers(Shift sh) {
        return sh.getWorkers();
    }

    public void addWorkerToShift(Shift sh, int workerId, int roleId) {
        sh.addWorker(workerId, roleId);
    }

    public void removeWorkerFrom(Shift sh, int id) {
        HashMap <Integer, Integer> workers = sh.getWorkers();
        workers.remove(id);
        sh.setWorkers(workers);
    }

    public HashMap<Integer, Integer> getShiftRoles(Shift sh) {
        return sh.getRoles();
    }

    public void addRoleToShift(Shift sh, int id, int amount) {
        HashMap<Integer, Integer> roles = sh.getRoles();
        int currAmount = roles.get(id);
        currAmount += amount;
        roles.remove(id);
        roles.put(id, currAmount);
        sh.setRoles(roles);
    }

    public void removeRoleToShift(Shift sh, int id, int amount) {
        HashMap<Integer, Integer> roles = sh.getRoles();
        int currAmount = roles.get(id);
        currAmount = currAmount-amount;
        roles.remove(id);
        if (currAmount > 0)
            roles.put(id, amount);
        sh.setRoles(roles);
    }

    public boolean checkIfshiftExist(Date date, int shiftType) {
        return (getShift(date, shiftType) != null);
    }
}
