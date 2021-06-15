package BusinessLayer.Workers;
import java.sql.Date;
import java.util.HashMap;


public class Shift extends BusinessObject {
private Date ShiftDate;
private int ManagerID;
private int ShiftType;
private HashMap<Integer,Integer> Workers;
private HashMap<Integer,Integer> Roles;
int shiftID;
int nextID=1;

public Shift(Date shiftDate, int managerID, int shiftType, HashMap<Integer, Integer> workers, HashMap<Integer, Integer> roles) {
	this.ShiftDate = shiftDate;
	this.ManagerID = managerID;
	this.ShiftType = shiftType;
	this.Workers = workers;
	this.Roles = roles;
	this.shiftID=nextID;
	nextID++;
}

public Shift(Date shiftDate, int managerID, int shiftType,int ID) {
	this.ShiftDate = shiftDate;
	this.ManagerID = managerID;
	this.ShiftType = shiftType;
	this.shiftID = ID;
	if(ID>=nextID) {
		nextID=ID+1;
	}
}

public void addRole(int roleid,int amount) {
	if(Roles.containsKey(roleid))
		Roles.replace(roleid, Roles.get(roleid)+amount);
	else
		Roles.put(roleid, amount);
}
public void updateRole(int roleid,int amount) {
	if(Roles.containsKey(roleid))
		Roles.replace(roleid,amount);
	else
		Roles.put(roleid, amount);
	
}
public void deleteRole(int roleid) {
	Roles.remove(roleid);
}
public void addWorker(int workerid,int roleid) {
	if(Roles.get(roleid)==0) {
		System.out.println("This role is already occupied.");
	}
	else if(Roles.get(roleid)==null)
		System.out.println("There's no such role");
	else {
	updateRole(roleid,Roles.get(roleid)-1);
	Workers.put(workerid,roleid);
	}
}
public void updateWorkersRole(int workerid,int newrole) {
	if(Roles.get(newrole)==0) {
		System.out.println("This role is already occupied.");
	}
	else if(Roles.get(newrole)==null)
		System.out.println("There's no such role");
	else {
	updateRole(newrole,Roles.get(newrole)-1);
	updateRole(Workers.get(workerid),Roles.get(Workers.get(workerid))+1);
	Workers.replace(workerid,newrole);
	}
}
public void deleteWorker(int workerid) {
	updateRole(Workers.get(workerid),Roles.get(Workers.get(workerid))+1);
	Workers.remove(workerid);
}

public Date getShiftDate() {
	return ShiftDate;
}
public void setShiftDate(Date shiftDate) {
	ShiftDate = shiftDate;
}
public int getManagerID() {
	return ManagerID;
}
public void setManagerID(int managerID) {
	ManagerID = managerID;
}
public int getShiftType() {
	return ShiftType;
}
public void setShiftType(int shiftType) {
	ShiftType = shiftType;
}
public HashMap<Integer, Integer> getWorkers() {
	return Workers;
}
public void setWorkers(HashMap<Integer, Integer> workers) {
	Workers = workers;
}
public HashMap<Integer, Integer> getRoles() {
	return Roles;
}
public void setRoles(HashMap<Integer, Integer> roles) {
	Roles = roles;
}

public int getId() {
	return shiftID;
}
}
