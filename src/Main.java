import PresentationLayer.Service;
public class Main {

    public static void main (String[] args){
        Service s = new Service();
        s.Initialize();
        s.startMenu();
        s.loadData();
    }

}
