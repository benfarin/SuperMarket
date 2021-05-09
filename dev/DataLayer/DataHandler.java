package DataLayer;

import java.util.Date;

public class DataHandler {
    private CategoryMapper catMapper;

    public DataHandler() {
        this.catMapper = new CategoryMapper();
    }
    //--------------CATEGORIES--------------
    public void addCatToData(String name,String super_cat, int discount, Date discountDate){
        try{
            catMapper.addCategory(name,super_cat,discount,discountDate);
        }
        catch (Exception e){
            System.out.println("got exception from database, try to add category\n"+e.getMessage());
        }
    }
    public void updateDiscounts(String name, int discount, Date discountDate){
        try{
            catMapper.updateDiscounts(name,discount,discountDate);
        }
        catch (Exception e){
            System.out.println("got exception from database, try to update discount\n"+e.getMessage());
        }
    }
    public void updateDiscDate(String name, Date discountDate){
        try{
            catMapper.updateDiscDate(name,discountDate);
        }
        catch (Exception e){
            System.out.println("got exception from database, try to update discount date\n"+e.getMessage());
        }
    }
    public void deleteCategory(String name){
        try{
            catMapper.deleteCategory(name);
        }
        catch (Exception e){
            System.out.println("got exception from database, try to delete category\n"+e.getMessage());
        }
    }
}
