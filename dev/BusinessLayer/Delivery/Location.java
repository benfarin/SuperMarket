package BusinessLayer.Delivery;

public class Location {
    enum Section{
        North, South, East, West, Center;
    }
    private String address;
    private String phoneNum;
    private String contactName;
    private Section section;
//    private int deliveryId;

    public Location(String address,String phoneNum,String contactName,Section section){
        this.address=address;
        this.contactName=contactName;
        this.phoneNum=phoneNum;
        this.section=section;
    }
    public Location(String address,String phoneNum,String contactName){
        this.address=address;
        this.contactName=contactName;
        this.phoneNum=phoneNum;
    }

    public Section getSection() {
        return section;
    }

    public String getAddress() {
        return address;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", contactName='" + contactName + '\'' +
                ", section=" + section +
                '}';
    }
}
