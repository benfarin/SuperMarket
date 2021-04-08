import BusinessLayer.Category;
import BusinessLayer.Product;
import PresentationLayer.Service;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args){

        Category trop = new Category("tropical fruits",new LinkedList<>());
        Category summer = new Category("summer fruits",new LinkedList<>());
        Category hila = new Category("hila",new LinkedList<>());
        List<Category> cats = new LinkedList<>();
        cats.add(trop);
        cats.add(summer);
        Category fruits = new Category("fruits",cats);
        summer.addSub(hila);
        //// fruits-->
                //tropical , summer-->
                                // hila
        Product apple = new Product("apple",hila,"jj",2.33,3.22,20);
        Service s = new Service();
        s.startMenu();
    }
}
