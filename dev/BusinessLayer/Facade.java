package BusinessLayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Facade {
    private InventoryController invCnt;
    private ReportController repCnt;

    public Facade() {
        this.repCnt = new ReportController();
        this.invCnt = new InventoryController();

    }
    public String addCategory (String name, List<String> subCategories){
        invCnt.addCategory(name,subCategories);
        return "the category " + name + " successfully added";
    }
    public String addProduct (String name,String category, String manufacture, double priceFromSupplier, double priceToCustomer, int minimum){
        if(invCnt.getCategory(category)==null){
            return "Can't add product because category does not exist\n"+"add category first";
        }
        Category prodCat = invCnt.getCategory(category);
        Product p = new Product(name,prodCat,manufacture,priceFromSupplier,priceToCustomer,minimum);
        return "the product " + name + " successfully added";
    }

    //-------------------------PRODUCT--------------------------

    public String setManufacture(String prodName,String manu){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setManufacture(prodName,manu);
        return "set "+ prodName+"'s manufacture to " + manu;
    }
    public String setPriceFromSupplier(String prodName,double priceFromSupplier){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setPriceFromSupplier(prodName,priceFromSupplier);
        return "set "+ prodName+"'s price from supplier to " + priceFromSupplier;
    }
    public String setPriceToCustomer(String prodName , double priceToCustomer){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setPriceToCustomer(prodName,priceToCustomer);
        return "set "+ prodName+"'s price to costumer to " + priceToCustomer;
    }
    public String setProdDiscount(String prodName,int discount, Date discountDate){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setProdDiscount(prodName,discount,discountDate);
        return "set "+ prodName+"'s discount to " +discount+ "% until " + discountDate;
    }
    public String setDefectiveItems (String prodName, int def){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setDefectiveItems(prodName,def);
        return "set "+ prodName+"'s defective items to " + def;
    }
    public String setMinimum(String prodName , int minimum) {
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setMinimum(prodName,minimum);
        return "set "+ prodName+"'s minimum to " + minimum;
    }
    public String addStoreQuantity(String prodName, int add){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.addStoreQuantity(prodName,add);
        return "added "+ add+" from " + prodName+" store quantity";
    }
    public String reduceStoreQuantity(String prodName, int reduce){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        if(invCnt.reduceStoreQuantity(prodName,reduce)){
            // invite this product from Suppliers!!!!!!
        }
        return "Reduced "+ reduce+" from " + prodName+" store quantity";

    }
    public String addStorageQuantity(String prodName, int add){
        if(invCnt.getProduct(prodName) == null){
            return "Can't add storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.addStorageQuantity(prodName,add);
        return "added "+ add+" from " + prodName+" storage quantity";
    }
    public String reduceStorageQuantity(String prodName, int reduce){
        if(invCnt.getProduct(prodName) == null){
            return "Can't reduce storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.reduceStorageQuantity(prodName,reduce);
        return "Reduced "+ reduce+" from " + prodName+" storage quantity";
    }
    public String setStoreQuantity(String prodName, int storeQuantity){
        if(invCnt.getProduct(prodName) == null){
            return "Can't set store quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setStoreQuantity(prodName,storeQuantity);
        return "Store quantity changed to " + storeQuantity;
    }
    public String setStorageQuantity(String prodName, int storageQuantity){
        if(invCnt.getProduct(prodName) == null){
            return "Can't set storage quantity because the product "+prodName+" does not exist\n";
        }
        invCnt.setStorageQuantity(prodName,storageQuantity);
        return "Storage quantity changed to " + storageQuantity;
    }
    public String printProduct(String prodName){
        if(invCnt.getProduct(prodName) == null){
            return "The product "+prodName+" does not exist\n";
        }
        return invCnt.printProduct(prodName);
    }

    //-------------------------CATEGORY--------------------------


    public String addSub(String mainCat,String subC){
        if(invCnt.getCategory(mainCat)==null){
            return "Can't add sub-category because main category "+mainCat+" does not exist\n"+"add main category first";
        }
        if(invCnt.getCategory(subC)==null){
            return "Can't add sub-category "+subC+",this category does not exist\n"+"add this category first";
        }

        Category subCat = invCnt.getCategory(subC);
        invCnt.addSub(mainCat,subCat);
        return "The sub-category " + subC + " successfully added to " + mainCat;
    }
    public String deleteSubCat(String mainCat, String subC){
        if(invCnt.getCategory(mainCat)==null){
            return "Can't delete sub-category because main category "+mainCat+" does not exist\n";
        }
        if(invCnt.getCategory(subC)==null){
            return "Can't delete sub-category "+subC+",this category does not exist\n";
        }

        Category subCat = invCnt.getCategory(subC);
        invCnt.deleteSubCat(mainCat,subCat);
        return "The sub-category " + subC + " successfully deleted from " + mainCat;
    }
    public String deleteProdFromCat(String catName ,String prodName){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        if(invCnt.getProduct(prodName) == null){
            return "Can't delete, the product "+prodName+" does not exist\n";
        }
        invCnt.deleteProd(catName,invCnt.getProduct(prodName));
        return  prodName + " successfully deleted from " + catName;
    }
    public String setCatDiscount(String catName, int discount, Date discountDate){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        invCnt.setCatDiscount(catName,discount,discountDate);
        return "set " +catName+ "'s discount to "+ discount + "until "+ discount;
    }
    public String setCatDiscountDate(String catName, Date discountDate){
        if(invCnt.getCategory(catName)==null){
            return "the category "+catName+" does not exist\n";
        }
        invCnt.setCatDiscountDate(catName,discountDate);
        return "set " +catName+ "'s discount date to "+ discountDate;
    }
    public String printCategory(String catName) {
        if (invCnt.getCategory(catName) == null)
            return "the category " + catName + " does not exist\n";
        return invCnt.printCategory(catName);
    }
    //-------------------Report--------------------
    public String addStockReport(List<String> categories){
        List<Category> cats = new LinkedList<>();
        String ret = new String();
        for(String s : categories) {
            ret = ret+s+", ";
            Category c = invCnt.getCategory(s);
            if(c == null){
                return "the category "+s+" does not exist\n";
            }
            cats.add(c);
        }
        repCnt.addStockReport(cats);
        ret = ret.substring(0,ret.length()-1);
        return "Added stock report about the categories: " + ret;
    }
    public String addDefReport(List<String> products){
        List<Product> prods = new LinkedList<>();
        String ret = new String();
        for(String s : products) {
            ret = ret+s+", ";
            Product p = invCnt.getProduct(s);
            if(p == null){
                return "the product "+s+" does not exist\n";
            }
            prods.add(p);
        }
        repCnt.addDefReport(prods);
        ret = ret.substring(0,ret.length()-1);
        return "Added defective report about the product: " + ret;
    }
    public String addCatToStRep(int id, String category){
        if(repCnt.getStoReport(id) == null)
            return "The report "+id+ " does not exist\n";

        Category c = invCnt.getCategory(category);
        if(c==null)
            return "the category "+category+" does not exist\n";
        repCnt.addCatToStRep(id,c);
        return "Added the category " + category+ " to "+ id + " report";
    }
    public String addProdToDefRep(int id, String product){
        if(repCnt.getDefReport(id) == null)
            return "The report "+id+ " does not exist\n";

        Product p = invCnt.getProduct(product);
        if(p==null)
            return "the product "+product+" does not exist\n";
        repCnt.addProdToDefRep(id,p);
        return "Added the product " + product+ " to "+ id + " report";
    }
    public String exportReport(int id){
        if(repCnt.getStoReport(id) == null)
            return "The report "+id+ " does not exist\n";
       return repCnt.exportReport(id);
    }
}
