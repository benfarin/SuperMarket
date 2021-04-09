package BusinessLayer;
import java.time.LocalDate;
import java.util.*;

public class Worker
{
    private String name;
    private int id;
    private int bankAccountNumber;
    private int bankNumber;
    private int salary;
    private Date joiningDate;
    private Date leavingDate;
    private List<Role> roles;
	private HashMap<Shift, Constraint> constraints;


	public Worker(String name, int id, int bankAccountNumber, int bankNumber, int salary, Date joiningDate, Date leavingDate)
    {
        this.name = name;
        this.id = id;
        this.bankAccountNumber = bankAccountNumber;
        this.bankNumber = bankNumber;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.leavingDate = leavingDate;
        this.constraints = new HashMap<Shift, Constraint>();
        this.roles = new LinkedList<Role>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }
    public List<Role> getRoles() {
		return roles;
	}
    public void setId(int id){
        this.id = id;
    }

    public int getBankAccount()
    {
        return bankAccountNumber;
    }

    public void setBankAccount(int bankAccountNumber)
    {
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getBankNumber()
    {
        return bankNumber;
    }

    public void setBankNumber(int bankNumber)
    {
        this.bankNumber = bankNumber;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public Date getJoiningDate()
    {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate)
    {
        this.joiningDate = joiningDate;
    }

    public Date getLeavingDate()
    {
        return leavingDate;
    }

    public void setLeavingDate(Date leavingDate)
    {
        this.leavingDate = leavingDate;
    }
    public HashMap<Shift, Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(HashMap<Shift, Constraint> constraints) {
		this.constraints = constraints;
	}

    public void addConstraint(Constraint c){
        constraints.put(c.getShift(), c);
    }

    public<T> void updateConstraint(Shift shift, int desire, T newValue){
        Constraint toUpdate = constraints.get(shift);
        if (desire == 1)
            toUpdate.setTemp((Boolean)newValue);
        else if (desire == 2){
            toUpdate.setConstraintDate((Date)newValue); // TODO: setDate needs to set the day as well.
        }
        else if (desire == 3){
            toUpdate.setShiftType((Integer) newValue);
        }
            constraints.remove(shift);
            constraints.put(shift, toUpdate);

    }

    public void setRoles(List<Role> r){
	    this.roles = r;
    }
    public void deleteConstraint(Shift shift){
        constraints.remove(shift);
    }
}