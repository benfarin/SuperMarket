package BusinessLayer.Delivery;


public  class Employee {
    private int dId;
    private String dName;


    public Employee(int id, String name){
        this.dId=id;
        this.dName=name;
    }
    public Employee(){

    }

    public String getName(){
        return dName;
    }

    public void setName(String name){
        this.dName=name;
    }

    public void setId(int id){
        this.dId=id;
    }

    public int getId() {
        return dId;
    }
}
