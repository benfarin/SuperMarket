//package BusinessLayer;
//
//import javax.print.Doc;
//import java.util.*;
//import java.util.List;
//
//public class main {
//    public static void main(String[] args) {
////        Document doc = new Document();
////        Truck truck = new Truck(1, "19", 10, 4);
//        // Driver dr = new Driver(1,"moshe",15);
//        System.out.println("Enter option:  ");
//        Scanner scanner = new Scanner(System.in);
//        int option = scanner.nextInt();
//        scanner.nextLine();
//        switch(option){
//            case 1 :// date locations deliveryweight
//                try{
//                    System.out.println("Enter delivery date: ");
//                    String date = scanner.nextLine();
//                    System.out.println("Enter the locations ");
//                    String loc = scanner.nextLine();
//                    System.out.println("Enter delivery weight");
//                    int dWeight = scanner.nextInt();
//                    Truck tr = DeliveryController.getInstance().FindTruck(dWeight).getKey();
//                    int numOfTruck = tr.getLicenseNum();
//                    int truckWeight = tr.getWeight();
//                    Driver driver = DeliveryController.getInstance().FindDriver(tr).getKey();
//                    String driverName = driver.getName();
//                    //
//                    System.out.println("Enter address:  ");
//                    String address = scanner.nextLine();
//                    System.out.println("Enter contact name:  ");
//                    String contactName = scanner.nextLine();
//                    System.out.println("Enter phoneNumber ");
//                    String phoneNumber = scanner.nextLine();
//                    Location location = new Location(address,contactName,phoneNumber);
//                    List<Location> loccc = new ArrayList<Location>(3);
//                    loccc.add(location);
//                    Delivery delv = new Delivery(1,date,1,numOfTruck,driverName,dWeight,loccc,truckWeight,tr);
//                    DeliveryController.getInstance().addDelivery(delv);
//                    DeliveryController.getInstance().addDeliverDriver(delv,driver);
//                    DeliveryController.getInstance().addDriverAndTruck(driver,tr);
//                }
//                catch(Exception e){
//                    System.out.println("didn't found matching requirements");
//                }
//                break;
//            case 2:
//                try{
//                    Driver d= new Driver();
//                    System.out.println("Enter driver id:  ");
//                    d.setId(scanner.nextInt());
//                    System.out.println("Enter licenseDriver:  ");
//                    d.setLicenseAllowed(scanner.nextInt()) ;
//                    System.out.println("Enter driver name:  ");
//                    System.out.println("aaaa");
//                    d.setName(scanner.next());
//                    //Driver driver = new Driver(id,driverName1,license);
//                    DeliveryController.getInstance().addDriver(d);
//                    System.out.println("Driver added successfully");
//                }
//                catch(Exception e){
//                    System.out.println("Wrong inputs");
//                }
//                break;
//            case 3:
//                try{
//                    System.out.println("Enter license number: ");
//                    int licenseNum = scanner.nextInt();
//                    System.out.println("Enter model: ");
//                    String model = scanner.nextLine();
//                    System.out.println("Enter weight:  ");
//                    int weight = scanner.nextInt();
//                    System.out.println("Enter the capacity: ");
//                    int capacity = scanner.nextInt();
//                    Truck truck = new Truck(licenseNum,model,weight,capacity);
//                    DeliveryController.getInstance().addTruck(truck);
//                }
//                catch(Exception e){
//                    System.out.println("Something Wrong in inputs");
//                }
//                break;
//            case 4:
//                try{
//                    System.out.println("The number of the Drivers is : " + DeliveryController.getInstance().getDrivers().size());
//                }
//                catch (Exception e){
//                    System.out.println("  ");
//                }
//                break;
//            case 5:
//                System.out.println("The number of the trucks is : " + DeliveryController.getInstance().getTrucks().size());
//        }
//    }
//}
