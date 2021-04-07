package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DefectiveReport extends Report {

    private List<Product> defectiveProducts;

    @Override
    public String exportReport() {
        String s = "";
        for (Product p : defectiveProducts)
            s+= "Product: "+p.getName()+"\nManufacture: "+p.getManufacture()+"\nNumber of defective items: "+p.getDefectiveItem()+"\n";

        return "DefectiveReport:\n" +
                "ID = " + Id +
                "\nDate = " + date +
                "\n\nDefective Products: \n"+s;
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

}
