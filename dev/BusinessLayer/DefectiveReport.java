package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DefectiveReport extends Report {

    private List<Product> defectiveProducts;

    @Override
    public String exportReport() {
        return "DefectiveReport:\n" +
                "ID = " + Id +
                "\nDate = " + date +
                "\nDefective Products = " + defectiveProducts;
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
