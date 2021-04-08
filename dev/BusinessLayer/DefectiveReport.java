package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DefectiveReport extends Report {

    private List<Product> defectiveProducts;

    @Override
    public String exportReport() {
        String s = "";
        int total = 0;
        for (Product p : defectiveProducts) {
            total += p.getDefectiveItem();
            s += "\nProduct: " + p.getName() + "\nManufacture: " + p.getManufacture() + "\nNumber of defective items: " + p.getDefectiveItem() + "\n";
        }
        String ret = "DefectiveReport:\n" +
                "ID = " + Id +
                "\nDate = " + date +
                "\n\nDefective Products: \n"+s;
        return ret +"\n"+ "Total defective products: "+total+"\n";
    }

    public DefectiveReport() {
        super();
        this.defectiveProducts = new LinkedList<>();
    }

    public void addProd(Product p){
        if(!defectiveProducts.contains(p))
            defectiveProducts.add(p);
    }

    public void deleteProd(Product p){
        if(defectiveProducts.contains(p))
            defectiveProducts.add(p);
    }
    public boolean isProdInRep(String prodName){
        for(Product p : defectiveProducts){
            if(prodName.equals(p.getName()))
                return true;
        }
        return false;
    }

}
