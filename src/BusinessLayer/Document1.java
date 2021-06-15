package BusinessLayer;

import javafx.util.Pair;

import java.util.Date;
import java.util.List;

public class Document1 {
    private int id;
    private List<Pair<String,Integer>> items;
    private String makor;
    private int truckNum;
    private Date date;
    private int exitTime;
    private String ya3ad;
    private String driverName;


    public Document1(int id,List<Pair<String,Integer>> lItems,String makor,int truckN,Date d,int exitTime,String ya3ad,String driverN){
        this.id=id;
        this.items=lItems;
        this.makor=makor;
        this.truckNum=truckN;
        this.date=d;
        this.exitTime=exitTime;
        this.ya3ad=ya3ad;
        this.driverName=driverN;
    }
    public void addmotsar(Pair<String,Integer> motsar){
        this.items.add(motsar);
    }

    public List<Pair<String,Integer>> getList(){
        return this.items;
    }



    public Date getDate(){
        return this.date;
    }

    public void changeDate(Date date){
        this.date = date ;
    }



    @Override
    public String toString() {//todo change this
        return "Document1{" +
                "id=" + id +
                ", items=" + items +
                ", makor=" + makor +
                ", truckNum=" + truckNum +
                ", date=" + date +
                ", exitTime=" + exitTime +
                ", ya3ad=" + ya3ad +
                ", driverName='" + driverName + '\'' +
                '}';
    }
}
