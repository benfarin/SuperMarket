package BusinessLayer.Delivery;
import java.util.LinkedList;
import java.util.List;

public class Driver extends Employee {
    private int licenseAllowed;
    private List<Document> documents;
    private boolean busy=false;
    public Driver(int id,String name,int license,List<Document> doc){
        super(id,name);
        this.licenseAllowed=license;
        this.documents=doc;

    }
    public Driver(int id,String name,int license){
        super(id,name);
        this.licenseAllowed=license;

    }
    public Driver(){
        super();
        busy=false;
        documents= new LinkedList<>();

    }

    public int getLicense(){
        return licenseAllowed;
    }
    public List<Document> getDocs(){
        return documents;
    }
    public int getDriverId(){
        return getId();
    }
    public void setBusy(boolean busy) {
        this.busy = busy;
    }
    public boolean isBusy() {
        return busy;
    }
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    public void setLicenseAllowed(int licenseAllowed) {
        this.licenseAllowed = licenseAllowed;
    }

}
