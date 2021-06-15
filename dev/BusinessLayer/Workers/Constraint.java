package BusinessLayer.Workers;
import java.util.Date;

public class Constraint extends BusinessObject {
private boolean isTemp;
private Date constraintDate;
private int shiftType;
private int workerID;
private int constraintID;
private int nextID=0;

public Constraint(boolean temp,Date date,int shifttype,int emp) {
	this.isTemp=temp;
	this.constraintDate=date;
	this.shiftType=shifttype;
	this.workerID=emp;
	this.constraintID=nextID;
	nextID++;
}
public Constraint(boolean temp,Date date,int shifttype,int emp,int id) {
	this.isTemp=temp;
	this.constraintDate=date;
	this.shiftType=shifttype;
	this.workerID=emp;
	this.constraintID=id;
	if(id>=nextID) {
		nextID=id+1;
	}
}
public boolean isTemp() {
	return isTemp;
}

public void setTemp(boolean isTemp) {
	this.isTemp = isTemp;
}

public Date getConstraintDate() {
	return constraintDate;
}

public void setConstraintDate(Date constraintDate) {
	this.constraintDate = constraintDate;
}

public int getShiftType() {
	return shiftType;
}

public void setShiftType(int shiftType) {
	this.shiftType = shiftType;
}

public int getWorker() {
	return workerID;
}

public void setWorker(int worker) {
	this.workerID = worker;
}

@Override
public int getId() {
	return constraintID;
}

}
