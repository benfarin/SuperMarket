//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package BusinessLayer;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.DefectiveReport;
import BusinessLayer.Inventory.InventoryController;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.ReportController;
import BusinessLayer.Inventory.StockReport;
import BusinessLayer.Suppliers.Contract;
import BusinessLayer.Suppliers.IncomingOrderController;
import BusinessLayer.Suppliers.OutgoingOrder;
import BusinessLayer.Suppliers.ProductPerSup;
import BusinessLayer.Suppliers.Supplier;
import BusinessLayer.Suppliers.SupplierController;
import DataLayer.DataHandler;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Facade {
    private InventoryController invCnt;
    private ReportController repCnt;
    private IncomingOrderController incoming_order_controller;
    private SupplierController supplierController;
    private DataHandler dataHandler;

    public Facade() {
        this.initialize();
        this.dataHandler = new DataHandler(this);
    }

    public String addCategory(String name, List<String> subCategories) {
        Category c = this.invCnt.addCategory(name, subCategories);
        if (c != null) {
            if (c.getSupCategory() != null) {
                this.dataHandler.addCatToData(c.getName(), c.getSupCategory().getName(), c.getDiscount(), c.getDiscountDate());
            }

            this.dataHandler.addCatToData(c.getName(), (String)null, c.getDiscount(), c.getDiscountDate());
        }

        return "the category " + name + " successfully added\n";
    }

    public Category addCatFromData(String name, String supCat, int discount, Date discountDate) {
        Category sup;
        if (supCat != null) {
            sup = this.invCnt.getCategory(supCat);
        } else {
            sup = null;
        }

        Category c = new Category(name, sup, new LinkedList(), new LinkedList(), discount, discountDate);
        this.invCnt.addCatFromData(c);
        return c;
    }

    public String addProduct(String name, String category, String manufacture, double priceFromSupplier, double priceToCustomer, int minimum) {
        if (this.invCnt.getCategory(category) == null) {
            return "Can't add product because category does not exist\nadd category first\n";
        } else {
            Category prodCat = this.invCnt.getCategory(category);
            Product p = this.invCnt.addProduct(name, prodCat, manufacture, priceFromSupplier, priceToCustomer, minimum);
            this.dataHandler.addProduct(p.getId(), p.getName(), p.getManufacture(), p.getCategory().getName(), p.getStoreQuantity(), p.getStorageQuantity(), p.getDiscount(), p.getDiscountDate(), p.getPriceFromSupplier(), p.getPriceToCustomer(), p.getDefectiveItem(), p.getMinimum(), p.getOrderAmount(), p.getPriceToCusHistory(), p.getPriceFromSupHistory());
            return "the product " + name + " successfully added\n";
        }
    }

    public Product addProductFromData(int id, String name, String manufacture, Category category, int storeQuantity, int storageQuantity, int discount, Date discountDate, double priceFromSupplier, double priceToCustomer, int defectiveItem, int minimum, Map<Double, Date> priceToCusHistory, Map<Double, Date> priceFromSupHistory) {
        return this.invCnt.addProductFromData(id, name, manufacture, category, storeQuantity, storageQuantity, discount, discountDate, priceFromSupplier, priceToCustomer, defectiveItem, minimum, priceToCusHistory, priceFromSupHistory);
    }

    public Product getProdByID(int id) {
        return this.invCnt.getProdByID(id);
    }

    public String setManufacture(String prodName, String manu) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "The product " + prodName + " does not exist\n";
        } else {
            this.invCnt.setManufacture(prodName, manu);
            this.dataHandler.updateManufacture(this.invCnt.getProduct(prodName).getId(), manu);
            return "set " + prodName + "'s manufacture to " + manu + "\n";
        }
    }

    public String setPriceFromSupplier(String prodName, double priceFromSupplier) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "The product " + prodName + " does not exist\n";
        } else {
            this.invCnt.setPriceFromSupplier(prodName, priceFromSupplier);
            this.dataHandler.updatePriceFromSupplier(this.invCnt.getProduct(prodName).getId(), priceFromSupplier);
            return "set " + prodName + "'s price from supplier to " + priceFromSupplier + "\n";
        }
    }

    public String setPriceToCustomer(String prodName, double priceToCustomer) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "The product " + prodName + " does not exist\n";
        } else {
            this.invCnt.setPriceToCustomer(prodName, priceToCustomer);
            this.dataHandler.updatePriceToCustomer(this.invCnt.getProduct(prodName).getId(), priceToCustomer);
            return "set " + prodName + "'s price to costumer to " + priceToCustomer + "\n";
        }
    }

    public String setProdDiscount(String prodName, int discount, Date discountDate) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "The product " + prodName + " does not exist\n";
        } else {
            this.invCnt.setProdDiscount(prodName, discount, discountDate);
            this.dataHandler.updateDiscount(this.invCnt.getProduct(prodName).getId(), discount, discountDate);
            return "set " + prodName + "'s discount to " + discount + "% until " + discountDate + "\n";
        }
    }

    public String setDefectiveItems(String prodName, int def) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "Can't reduce storage quantity because the product " + prodName + " does not exist\n";
        } else {
            this.invCnt.setDefectiveItems(prodName, def);
            this.dataHandler.updateDefectiveItems(this.invCnt.getProduct(prodName).getId(), def);
            return "set " + prodName + "'s defective items to " + def + "\n";
        }
    }

    public String setMinimum(String prodName, int minimum) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "Can't reduce storage quantity because the product " + prodName + " does not exist\n";
        } else {
            this.invCnt.setMinimum(prodName, minimum);
            this.dataHandler.updateMinimum(this.invCnt.getProduct(prodName).getId(), minimum);
            return "set " + prodName + "'s minimum to " + minimum + "\n";
        }
    }

    public String addStoreQuantity(String prodName, int add) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "Can't reduce storage quantity because the product " + prodName + " does not exist\n";
        } else {
            this.invCnt.addStoreQuantity(prodName, add);
            return "added " + add + " from " + prodName + " store quantity\n";
        }
    }

    public String reduceStoreQuantity(String prodName, int reduce) {
        return this.invCnt.getProduct(prodName) == null ? "Can't reduce storage quantity because the product " + prodName + " does not exist\n" : "Reduced " + reduce + " from " + prodName + " store quantity\n";
    }

    public String addStorageQuantity(String prodName, int add) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "Can't add storage quantity because the product " + prodName + " does not exist\n";
        } else {
            this.invCnt.addStorageQuantity(prodName, add);
            return "added " + add + " from " + prodName + " storage quantity\n";
        }
    }

    public String reduceStorageQuantity(String prodName, int reduce) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "Can't reduce storage quantity because the product " + prodName + " does not exist\n";
        } else {
            String s = "";
            if (this.invCnt.reduceStorageQuantity(prodName, reduce)) {
                Product p = this.invCnt.getProduct(prodName);
                this.AddNewOrder((long)p.getId(), 4 * p.getMinimum());
                s = "*** WARNING!!! " + prodName + "'s storage quantity is under the minimum ***\nsend order to supplier\n";
            }

            return "Reduced " + reduce + " from " + prodName + " storage quantity\n" + s;
        }
    }

    public String setStoreQuantity(String prodName, int storeQuantity) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "Can't set store quantity because the product " + prodName + " does not exist\n";
        } else {
            this.invCnt.setStoreQuantity(prodName, storeQuantity);
            this.dataHandler.updateStoreQuantity(this.invCnt.getProduct(prodName).getId(), storeQuantity);
            return "Store quantity changed to " + storeQuantity;
        }
    }

    public String setStorageQuantity(String prodName, int storageQuantity) {
        if (this.invCnt.getProduct(prodName) == null) {
            return "Can't set storage quantity because the product " + prodName + " does not exist\n";
        } else {
            String s = "";
            if (this.invCnt.setStorageQuantity(prodName, storageQuantity)) {
                Product p = this.invCnt.getProduct(prodName);
                this.AddNewOrder((long)p.getId(), 4 * p.getMinimum());
                s = "*** WARNING!!! " + prodName + "'s storage quantity is under the minimum ***\n";
            }

            this.dataHandler.updateStorageQuantity(this.invCnt.getProduct(prodName).getId(), storageQuantity);
            return "Storage quantity changed to " + storageQuantity + "\n" + s;
        }
    }

    public String printProduct(String prodName) {
        return this.invCnt.getProduct(prodName) == null ? "The product " + prodName + " does not exist\n" : this.invCnt.printProduct(prodName);
    }

    public String displayPFSHistory(String prodName) {
        return this.invCnt.getProduct(prodName) == null ? "The product " + prodName + " does not exist\n" : this.invCnt.displayPFSHistory(prodName);
    }

    public String displayPTCHistory(String prodName) {
        return this.invCnt.getProduct(prodName) == null ? "The product " + prodName + " does not exist\n" : this.invCnt.displayPTCHistory(prodName);
    }

    public String deleteCategory(String catName) {
        return this.invCnt.getCategory(catName) == null ? "Can't delete " + catName + " this category does not exist\n" : "The category " + catName + "was deleted";
    }

    public String addSub(String mainCat, String subC) {
        if (this.invCnt.getCategory(mainCat) == null) {
            return "Can't add sub-category because main category " + mainCat + " does not exist\nadd main category first\n";
        } else if (this.invCnt.getCategory(subC) == null) {
            return "Can't add sub-category " + subC + ",this category does not exist\nadd this category first\n";
        } else {
            Category subCat = this.invCnt.getCategory(subC);
            this.invCnt.addSub(mainCat, subCat);
            return "The sub-category " + subC + " successfully added to " + mainCat + "\n";
        }
    }

    public String deleteSubCat(String mainCat, String subC) {
        if (this.invCnt.getCategory(mainCat) == null) {
            return "Can't delete sub-category because main category " + mainCat + " does not exist\n";
        } else if (this.invCnt.getCategory(subC) == null) {
            return "Can't delete sub-category " + subC + ",this category does not exist\n";
        } else {
            Category subCat = this.invCnt.getCategory(subC);
            this.invCnt.deleteSubCat(mainCat, subCat);
            return "The sub-category " + subC + " successfully deleted from " + mainCat + "\n";
        }
    }

    public String deleteProdFromCat(String catName, String prodName) {
        if (this.invCnt.getCategory(catName) == null) {
            return "the category " + catName + " does not exist\n";
        } else if (this.invCnt.getProduct(prodName) == null) {
            return "Can't delete, the product " + prodName + " does not exist\n";
        } else {
            this.invCnt.deleteProd(catName, this.invCnt.getProduct(prodName));
            return prodName + " successfully deleted from " + catName + "\n";
        }
    }

    public String setCatDiscount(String catName, int discount, Date discountDate) {
        if (this.invCnt.getCategory(catName) == null) {
            return "the category " + catName + " does not exist\n";
        } else {
            this.invCnt.setCatDiscount(catName, discount, discountDate);
            this.dataHandler.updateDiscounts(catName, discount, discountDate);
            return "set " + catName + "'s discount to " + discount + " until " + discountDate.toString() + "\n";
        }
    }

    public String setCatDiscountDate(String catName, Date discountDate) {
        if (this.invCnt.getCategory(catName) == null) {
            return "the category " + catName + " does not exist\n";
        } else {
            this.invCnt.setCatDiscountDate(catName, discountDate);
            this.dataHandler.updateDiscDate(catName, discountDate);
            return "set " + catName + "'s discount date to " + discountDate.toString() + "\n";
        }
    }

    public String printCategory(String catName) {
        return this.invCnt.getCategory(catName) == null ? "the category " + catName + " does not exist\n" : this.invCnt.printCategory(catName);
    }

    public String addStockReport(List<String> categories) {
        List<Category> cats = new LinkedList();
        String ret = new String();
        Iterator var4 = categories.iterator();

        while(var4.hasNext()) {
            String s = (String)var4.next();
            ret = ret + s + ", ";
            Category c = this.invCnt.getCategory(s);
            if (c == null) {
                return "the category " + s + " does not exist\n";
            }

            cats.add(c);
        }

        StockReport sto = this.repCnt.addStockReport(cats);
        if (ret.length() > 1) {
            ret = ret.substring(0, ret.length() - 2);
        }

        List<String> catNames = new LinkedList();
        Iterator var10 = sto.getCategories().iterator();

        while(var10.hasNext()) {
            Category c = (Category)var10.next();
            catNames.add(c.getName());
        }

        this.dataHandler.addStock(sto.getID(), sto.getDate(), catNames);
        return "Added stock report about the categories: " + ret + "\n";
    }

    public void addDefectiveReportFromData(DefectiveReport re) {
        this.repCnt.addDefReport(re);
    }

    public void addStockReportFromData(StockReport re) {
        this.repCnt.addStockReport(re);
    }

    public String addDefReport(List<String> products) {
        List<Product> prods = new LinkedList();
        String ret = new String();
        Iterator var4 = products.iterator();

        while(var4.hasNext()) {
            String s = (String)var4.next();
            ret = ret + s + ", ";
            Product p = this.invCnt.getProduct(s);
            if (p == null) {
                return "the product " + s + " does not exist\n";
            }

            prods.add(p);
        }

        DefectiveReport d = this.repCnt.addDefReport(prods);
        if (ret.length() > 1) {
            ret = ret.substring(0, ret.length() - 2);
        }

        List<Integer> prodID = new LinkedList();
        Iterator var10 = d.getDefectiveProducts().iterator();

        while(var10.hasNext()) {
            Product p = (Product)var10.next();
            prodID.add(p.getId());
        }

        this.dataHandler.addDefective(d.getID(), d.getDate(), prodID);
        return "Added defective report about the products: " + ret + "\n this report ID: " + d.getID() + "\n";
    }

    public String addCatToStRep(int id, String category) {
        if (this.repCnt.getStoReport(id) == null) {
            return "The report " + id + " does not exist\n";
        } else {
            Category c = this.invCnt.getCategory(category);
            if (c == null) {
                return "the category " + category + " does not exist\n";
            } else if (!this.repCnt.addCatToStRep(id, c)) {
                return "The category " + category + " already exists in report " + id + "\n";
            } else {
                this.dataHandler.addStockCat(id, category);
                return "Added the category " + category + " to " + id + " report\n";
            }
        }
    }

    public String addProdToDefRep(int id, String product) {
        if (this.repCnt.getDefReport(id) == null) {
            return "The report " + id + " does not exist\n";
        } else {
            Product p = this.invCnt.getProduct(product);
            if (p == null) {
                return "the product " + product + " does not exist\n";
            } else if (!this.repCnt.addProdToDefRep(id, p)) {
                return "The product " + product + " already exists in report " + id + "\n";
            } else {
                this.dataHandler.addDefectiveProd(id, p.getId());
                return "Added the product " + product + " to " + id + " report\n";
            }
        }
    }

    public void orderToday() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        boolean dayIsHere;
        switch(this.repCnt.getDay()) {
            case 1:
                dayIsHere = cal.get(7) == 1;
                break;
            case 2:
                dayIsHere = cal.get(7) == 2;
                break;
            case 3:
                dayIsHere = cal.get(7) == 3;
                break;
            case 4:
                dayIsHere = cal.get(7) == 4;
                break;
            case 5:
                dayIsHere = cal.get(7) == 5;
                break;
            case 6:
                dayIsHere = cal.get(7) == 6;
                break;
            case 7:
                dayIsHere = cal.get(7) == 7;
                break;
            default:
                dayIsHere = false;
        }

        if (dayIsHere) {
            this.sendOrder();
        }

    }

    public void sendOrder() {
        new HashMap();
        HashMap<Integer, Integer> order = this.repCnt.sendReport();
        Iterator var2 = order.keySet().iterator();

        while(var2.hasNext()) {
            Integer i = (Integer)var2.next();
            if ((Integer)order.get(i) > 0) {
                this.incoming_order_controller.AddNewOrder((long)i, (Integer)order.get(i));
            }
        }

    }

    public String exportStockReport(int id) {
        return this.repCnt.getStoReport(id) == null ? "The report " + id + " does not exist\n" : this.repCnt.exportReport(id);
    }

    public String exportDefReport(int id) {
        return this.repCnt.getDefReport(id) == null ? "The report " + id + " does not exist\n" : this.repCnt.exportReport(id);
    }

    private void initialize() {
        ProductPerSup one = new ProductPerSup("milk 3%", new Long(123123L), 5.9D, (HashMap)null, new Long(82723L), (Supplier)null);
        ProductPerSup two = new ProductPerSup("Honey", new Long(34622L), 5.9D, (HashMap)null, new Long(34644L), (Supplier)null);
        ProductPerSup three = new ProductPerSup("Chocolate", new Long(67933L), 5.0D, (HashMap)null, new Long(122352L), (Supplier)null);
        ProductPerSup four = new ProductPerSup("Chocolate", new Long(67933L), 7.5D, (HashMap)null, new Long(2623643L), (Supplier)null);
        ProductPerSup five = new ProductPerSup("Banana", new Long(57423L), 10.3D, (HashMap)null, new Long(234L), (Supplier)null);
        ProductPerSup six = new ProductPerSup("Eggs L", new Long(52321L), 22.0D, (HashMap)null, new Long(2311L), (Supplier)null);
        Supplier moshe = new Supplier(12, new Long(13524L), "Moshe Inc", new LinkedList(), "Cash", "");
        Supplier itzik = new Supplier(5, new Long(124L), "Itzik & Sons", new LinkedList(), "Shotef+60", "1358223");
        HashMap<Integer, Supplier> initialSupplierMap = new HashMap();
        LinkedList<OutgoingOrder> allOrdersList = new LinkedList();
        initialSupplierMap.put(12, moshe);
        initialSupplierMap.put(5, itzik);
        moshe.getProducts().put(new Long(123123L), one);
        moshe.getProducts().put(new Long(67933L), three);
        itzik.getProducts().put(new Long(67933L), four);
        itzik.getProducts().put(new Long(34622L), two);
        itzik.getProducts().put(new Long(57423L), five);
        itzik.getProducts().put(new Long(52321L), six);
        one.setSupplier(moshe);
        two.setSupplier(itzik);
        three.setSupplier(moshe);
        four.setSupplier(itzik);
        five.setSupplier(itzik);
        six.setSupplier(itzik);
        Contract contractMoshe = new Contract("Friday", false, new HashMap());
        contractMoshe.AddPriceDiscount(50, 5);
        Contract contractItzik = new Contract("Sunday", false, new HashMap());
        contractItzik.AddPriceDiscount(40, 10);
        itzik.setContract(contractItzik);
        moshe.setContract(contractMoshe);
        this.supplierController = new SupplierController(initialSupplierMap);
        this.incoming_order_controller = new IncomingOrderController(this.supplierController.getAllProducts(), allOrdersList);
        this.repCnt = new ReportController();
        this.invCnt = new InventoryController();
        this.orderToday();
    }

    public void AddContact(int id_sup, String new_contact) {
        this.supplierController.AddContact(id_sup, new_contact);
    }

    public boolean IsSupplierExistInSystem(int id_sup) {
        return this.supplierController.IsSupplierExistInSystem(id_sup);
    }

    public Contract ShowContract(int id_sup) {
        return this.supplierController.ShowContract(id_sup);
    }

    public Supplier ShowSupInformation(int id_sup) {
        return this.supplierController.ShowSupInformation(id_sup);
    }

    public List<String> showContacts(int id_sup) {
        return this.supplierController.showContacts(id_sup);
    }

    public OutgoingOrder ShowOrder(Long id_order) {
        return this.incoming_order_controller.ShowOrder(id_order);
    }

    public boolean IsProductExistInSystem(Long id_product) {
        return this.incoming_order_controller.IsProductExistInSystem(id_product);
    }

    public void AddNewOrder(Long id_product, Integer amount) {
        this.incoming_order_controller.AddNewOrder(id_product, amount);
    }

    public boolean IsOrderExistInSystem(int id_order) {
        return this.incoming_order_controller.IsOrderExistInSystem(id_order);
    }

    public OutgoingOrder ShowOrderBySupplier(int id_sup) {
        return this.incoming_order_controller.ShowOrderBySupplier(id_sup);
    }

    public void AddNewSupplier(int id, Long company, String name, List<String> contacts, String payment, String bank) {
        this.supplierController.AddSupplier(id, company, name, contacts, payment, bank);
    }

    public void AddSupplierContract(int supplier_id) {
        this.supplierController.AddSupplierContract(supplier_id);
    }

    public String printProductSerialNumber(Integer supplier_id) {
        return this.incoming_order_controller.printProductsSerialNumbers(supplier_id);
    }

    public void DeleteSupplier(int id) {
        this.supplierController.DeleteSupplier(id);
    }

    public Category getCategory(String name) {
        return this.invCnt.getCategory(name);
    }

    public void setFirstId(int lastId) {
        invCnt.setFirstId(lastId);
    }
}
