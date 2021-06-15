package BusinessLayer;

import java.sql.Date;

public class Driver1 extends Worker{
    private int license;


    public Driver1(String name, int id, int bankAccountNumber, int bankNumber, int salary, Date joiningDate, Date leavingDate) {
        super(name, id, bankAccountNumber, bankNumber, salary, joiningDate, leavingDate);
    }

    public Driver1(int id, String name, int license) {
        super(id,name,license);
    }

    public int getLicense(){
        return this.license;
    }

    public void setLicenseAllowed(int x){
        this.license=x;
    }





}
