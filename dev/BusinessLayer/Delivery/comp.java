package BusinessLayer.Delivery;
import java.util.Comparator;


class Comp implements Comparator<Document> {
    @Override
    public int compare(Document o1, Document o2) {
        if(o1.getApproximatelyTime1()> o2.getApproximatelyTime1())
            return 1;
        if(o1.getApproximatelyTime1()< o2.getApproximatelyTime1())
            return  -1;
        return 0;
    }
}