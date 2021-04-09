package BusinessLayer;
import java.util.Date;

public class Constraint {
private boolean isTemp;
private Date constraintDate;
private int shiftType;
private Worker worker;
private Shift shift;

public Constraint(boolean temp,Date date,int shifttype,Worker emp,Shift shf) {
	this.isTemp=temp;
	this.constraintDate=date;
	this.shiftType=shifttype;
	this.worker=emp;
	this.shift=shf;
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

public Worker getWorker() {
	return worker;
}

public void setWorker(Worker worker) {
	this.worker = worker;
}

public Shift getShift() {
	return shift;
}

public void setShift(Shift shift) {
	this.shift = shift;
}

}
