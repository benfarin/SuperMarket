package BusinessLayer.Delivery;
import java.util.List;

public class Document {
    private int id;
    private List<String> items;
    private Location location;

    public Document(int id,List<String> i, Location l){
        this.id=id;
        this.items=i;
        this.location=l;
    }

   public Location getLocation() {
       return location;
    }
    public double getApproximatelyTime1(){

        if (location.getSection().equals("South")) {
            return 3.5;
        }
        if (location.getSection().equals("North")) {
            return 2;
        }
        if (location.getSection().equals("West")) {
            return 5.2;
        }
        if (location.getSection().equals("East")) {
            return 3.4;
        }
        if (location.getSection().equals("Center")) {
            return 1.2;
        }

        return 0;
    }
}
