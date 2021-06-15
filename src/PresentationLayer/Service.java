package PresentationLayer;
import BusinessLayer.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.Date;
import java.util.concurrent.ConcurrentHashMap;

import static Enum.ShiftField.ManagerID;

public class Service {
    DateTimeFormatter d = DateTimeFormatter.ofPattern("xxxx/yy/qq");
    Date t = new Date(System.currentTimeMillis());

    public static Scanner s;
    Controller c = new Controller();
    ShiftController ShiftCntrl;
    WorkersController WorkersCntrl;
    static Controller conn= new Controller();
    public Service(){
        s = new Scanner(System.in);
        this.ShiftCntrl = new ShiftController();
        this.WorkersCntrl = new WorkersController();
    }


    public void startMenu() {
    	System.out.println("Please enter your ID:\n");
    	int ID=Integer.parseInt(s.next());
        int op = 1;
    	while (!WorkersCntrl.isWorker(ID)) {
             System.out.println("Wrong Id Number\n" +
            "1)\tTry Again\n" +
            "2)\tExit");
    	    op = Integer.parseInt(s.next());
            if (op == 1){
                startMenu();
                break;
			}
            if (op == 2){
                Exit();
			}
            else
                System.out.println("Not a valid option, please try again.");
    	}
    	if (op == 1) {
            Worker w = WorkersCntrl.getWorker(ID);
            HashMap<Integer,Role> roles=WorkersCntrl.getRoles();
            System.out.println("Please choose you role:\n");
            for(Role r:roles.values()) {
                System.out.println("Role id:"+r.getId()+" RoleName: "+r.getRoleName());
            }
    	    int role=Integer.parseInt(s.next());
            // the roles Id is the number to choose
    	    while(!WorkersCntrl.workerHasRole(w, role)){
                System.out.println("This role does not match this worker\n" +
                "1)\tTry Again\n" +
                "2)\tExit\n");
                op = Integer.parseInt(s.next());
                if (op == 1){
                	startMenu();
			    }
                else if (op ==2){
                    Exit();
			    }
                else
                   System.out.println("Not a valid option, please try again.");
			}
            if (op == 1){
                if (role == 1)
                    ManagerMenu(w);
                else
                    WorkerMenu(w);
			}
    	}		
    }

    public void Initialize() {
    	WorkersCntrl.init();
    	ShiftCntrl.init();
    }

    // START WORKER MENU 
    public void WorkerMenu(Worker w){
        while(true) {
            System.out.println("Worker Menu - Choose option\n" +
            "1)\tWorker Shifts\n" +
            "2)\tWorker Info\n" +
            "3)\tAdd Constraint\n" +
            "4)\tEdit Constraint\n" +
            "5)\tDelete Constraint\n" +
            "6)\tDelete Constraint\n" +
            "7)\tExit\n");
            int op = Integer.parseInt(s.next());
            if (op == 1)
                printWorkerShifts(w);
            else if (op == 2)
                WorkersCntrl.printWorker(w);
            else if (op == 3)
                AddConstraint(w);
            else if (op == 4)
                EditConstrint(w);
            else if (op == 5)
                DeleteConstrint(w);
            else if (op == 6)
                break;
            else if (op == 7)
                Exit();
            else {
                System.out.println("Not a valid option, please try again.\n");
            }
        }
	}

    private void Exit() {
        System.exit(0);
    }

    public void printWorkerShifts(Worker w){
        Date today = new Date(System.currentTimeMillis());
        List<Shift> workerShifts = ShiftCntrl.getWorkerShifts(w);
        for (Shift s: workerShifts){
            ShiftCntrl.printShift(s);
        }
	}

    public void AddConstraint(Worker w){
        System.out.println("Enter constraint's date in DD/MM/YYYY format");
    	String d = s.next();
        int day = Integer.parseInt(d.substring(0,1));
        int month = Integer.parseInt(d.substring(3,4));
        int year = Integer.parseInt(d.substring(6,9));
        Date date = new Date(day, month, year);
        System.out.println("Enter shift type\n" +
        "1)\tMorning Shift\n" + 
        "2)\tEvening Shift\n");
    	int shiftType = Integer.parseInt(s.next());

        System.out.println("The constraint is one-time or regularly?\n" +
        "1)\tOne-Time\n" +
        "2)\tRegularly\n");
    	int op = Integer.parseInt(s.next());
        boolean isTemp = true;
        if (op == 2)
            isTemp = false;
        
        WorkersCntrl.addConstraintToWorker(w, new Constraint(isTemp, date, shiftType,w.getId()));
	}

    public void EditConstrint(Worker w){
        System.out.println("Enter the date of the constraint to edit in DD/MM/YYYY format");
    	String d = s.next();
        int day = Integer.parseInt(d.substring(0,1));
        int month = Integer.parseInt(d.substring(3,4));
        int year = Integer.parseInt(d.substring(6,9));
        Date date = new Date(day, month, year);

        System.out.println("What is the shift type of the constraint?\n" +
        "1)\tMorning Shift\n" + 
        "2)\tEvening Shift\n");
    	int shiftType = Integer.parseInt(s.next());

        Shift shift = ShiftCntrl.getShift(date, shiftType);
        Constraint c = WorkersCntrl.getConstraintByShift(w,shift);

        while(true){
            System.out.println("Choose what to edit\n" +
            "1)\tConstraint Type (One-Time/Regularly)\n" +
            "2)\tShift Date\n" +
            "3)\tShiftType (Morning Shift/Evening Shift\n" +
            "4)\tExit" );
            int op = Integer.parseInt(s.next());

            if (op == 1){
                System.out.println("The constraint is one-time or regularly?\n" +
                "1)\tOne-Time\n" +
                "2)\tRegularly\n");
    	        op = Integer.parseInt(s.next());
                boolean isTemp = true;
                if (op == 2)
                    isTemp = false;
                c.setTemp(isTemp);
            }
            else if (op == 2){
                System.out.println("Enter constraint's date in DD/MM/YYYY format");
    	        d = s.next();
                day = Integer.parseInt(d.substring(0,1));
                month = Integer.parseInt(d.substring(3,4));
                year = Integer.parseInt(d.substring(6,9));
                date = new Date(day, month, year);
                c.setConstraintDate(date);
            }
            else if (op == 3){
                System.out.println("What is the shift type of the constraint?" +
                "1)\tMorning Shift\n" + 
                "2)\tEvening Shift\n");
    	        shiftType = Integer.parseInt(s.next());
                c.setShiftType(shiftType);
            }
            else if (op == 4){
                Exit();
            }
            else{
                System.out.println("Not a valid option, please try again.");
            }
            if (op >= 1 && op <= 4) {
                System.out.println("Do you want to edit another field in this constraint?\n" +
                "1)\tYes\n" +
                "2)\tNo\n");
                op = Integer.parseInt(s.next());
                if (op == 2)
                    break;
            }
        }
    }

    public void DeleteConstrint(Worker w){
        System.out.println("Enter the date of the constraint to edit in DD/MM/YYYY format");
    	String d = s.next();
        int day = Integer.parseInt(d.substring(0,1));
        int month = Integer.parseInt(d.substring(3,4));
        int year = Integer.parseInt(d.substring(6,9));
        Date date = new Date(day, month, year);

        System.out.println("What is the shift type of the constraint?\n" +
        "1)\tMorning Shift\n" + 
        "2)\tEvening Shift\n");
    	int shiftType = Integer.parseInt(s.next());

        Shift s = ShiftCntrl.getShift(date, shiftType);
        WorkersCntrl.removeConstraint(w,s);
    }
// END OF WORKER MENU

// START MANAGER MENU
    public void ManagerMenu(Worker w){
        while(true) {
            System.out.println("Manager Menu - Choose option\n" +
            "1)\tWorkers Menu\n" +
            "2)\tShifts Menu\n" +
            "3)\tDeliveries Menu\n" +
            "4)\tExit\n");
            int op = Integer.parseInt(s.next());
            if (op == 1) {
                ManagerWorkerFirstMenu(w);
            }
            else if (op == 2)
                ShiftsMenu();
            else if (op == 3)
                DeliveriesMenu();
            else if (op == 4)
                Exit();
            else
                System.out.println("Not a valid option, please try again.");
        }
	}

    private void ManagerWorkerFirstMenu(Worker w) {
    	while (true) {
	        System.out.println("Worker Menu - Choose option\n" +
	        "1)\tAdd Worker\n" +
	        "2)\tChoose Worker\n" +
	        "3)\tReturn\n");
	        
	        int op = Integer.parseInt(s.next());
	        if(op == 1)
	        	addWorker();
	        else if (op == 2) {
	            System.out.println("Enter worker id");
	            int Id = Integer.parseInt(s.next());
	            Worker Cw = WorkersCntrl.getWorker(Id);
	            if(Cw==null) {
		            System.out.println("Wrong ID ,try again");
		            ManagerWorkerFirstMenu(w);
	            }
	            ManagerWorkerMenu(Cw);
	        }
	        else if(op == 3) {
	        	break;
	        }
    	}
    }
    private void addWorker() {
        //chek worker type
        Scanner s = new Scanner(System.in);
        System.out.println("enter the type of the worker");
        String s1 = s.nextLine();
        if(s1.equals("Driver")){
            System.out.println("Enter drivers name:");
            String name  = s.nextLine();
            System.out.println("Enter driver's id:");
            int id = s.nextInt();
            System.out.println("enter bank account");
            int bankAcc = s.nextInt();
            System.out.println("Enter bank number:");
            int bnumber = s.nextInt();
            System.out.println("enter salary");
            int salary = s.nextInt();
            Driver1 d = new Driver1(name,id,bankAcc,bnumber,salary,t,t);
            WorkersCntrl.createWorker(name,id,bankAcc,bnumber,salary);//adding a new driver
        }
        System.out.println("Enter worker id");
        int id = Integer.parseInt(s.next());
        System.out.println("Enter worker first name");
        String name ="";
        name=s.next();
        System.out.println("Enter worker last name");
        name+=" "+s.next();
        System.out.println("Enter worker bank acoount number");
        int bankAccountNumber = Integer.parseInt(s.next());
        System.out.println("Enter worker bank number");
        int bankNumber = Integer.parseInt(s.next());
        System.out.println("Enter worker salary");
        int salary = Integer.parseInt(s.next());
        WorkersCntrl.createWorker(name, id, bankAccountNumber, bankNumber, salary);
	}

	private void ManagerWorkerMenu(Worker w) {
        while(true) {
            System.out.println("Worker Menu - Choose option\n" +
            "1)\tWorker Shifts\n" +
            "2)\tWorker Info\n" +
            "3)\tWorker Constraints\n" +
            "4)\tWorker Roles\n" +
            "5)\tEdit Worker Info\n" +
            "6)\tAdd Role To Worker\n" +
            "7)\tRemove Role From Worker\n" +
            "8)\tRemove Worker\n" +
            "9)\tReturn\n");
            int op = Integer.parseInt(s.next());
            if (op == 1)
                printWorkerShifts(w);
            else if (op == 2)
                WorkersCntrl.printWorker(w);
            else if (op == 3)
                printWorkerConstraints(w);
            else if (op == 4)
                printWorkersRoles(w);
            else if (op == 5)
                editWorker(w);
            else if (op == 6)
                addRoleToWorker(w);
            else if (op == 7)
                removeRoleFromWorker(w);
            else if (op == 8) {
                removeWorker(w);
                break;
            }
            else if (op == 9)
                break;
            else {
                System.out.println("Not a valid option, please try again.");
            }
        }
    }

    private void removeWorker(Worker w) {
        WorkersCntrl.deleteWorker(w.getId());
    }

    private void removeRoleFromWorker(Worker w) {
        System.out.println("Enter role id to remove");
        int roleId = Integer.parseInt(s.next());
        while (WorkersCntrl.workerHasRole(w, roleId)){
            System.out.println("This worker don't have this role, please enter role id to remove, enter 0 to return back");
            roleId = Integer.parseInt(s.next());
            if (roleId == 0)
                break;
        }
        if (roleId != 0)
            WorkersCntrl.removeRoleFromWorker(w, roleId);
    }

    private void addRoleToWorker(Worker w) {
        System.out.println("Enter role id to add");
        int roleId = Integer.parseInt(s.next());
        Role r = WorkersCntrl.getRole(roleId);
        while (r == null){
            System.out.println("This role id does not exist, please enter role id, enter 0 to return back");
            roleId = Integer.parseInt(s.next());
            if (roleId == 0)
                break;
            else
                r = WorkersCntrl.getRole(roleId);
        }
        if (roleId != 0)
            WorkersCntrl.addRoleToWorker(w, roleId);
    }

    private void printWorkersRoles(Worker w) {
        List<Role> roles = WorkersCntrl.getWorkerRoles(w);
        for (Role r: roles) {
            WorkersCntrl.printRole(r);
        }
    }

    private void editWorker(Worker w) {
        while(true) {
            System.out.println("What do you want to edit in " + WorkersCntrl.getWorkerName(w) + " info:\n" +
            "1)\tWorker Name\n" +
            "2)\tWorker Id\n" +
            "3)\tWorker Bank Account Number\n" +
            "4)\tWorker Bank Number\n" +
            "5)\tWorker Salary\n" +
            "6)\tWorker Leaving Date\n" +
            "7)\tExit\n");
            int op = Integer.parseInt(s.next());

            if (op == 1){
                System.out.println("Enter new name");
                String name = s.next();

            }
            else if (op == 2){

            }
        }
    }

    private void printWorkerConstraints (Worker w) {
        HashMap<Integer, Constraint> constraints = WorkersCntrl.getWorkerConstraints(w);
        for (Constraint c: constraints.values()) {
            WorkersCntrl.printConstrint(c);
            System.out.println("");
        }
    }

    private void ShiftsMenu() {
        while(true) {
            System.out.println("Shift Menu - Choose option\n" +
            "1)\tCreate Shift\n" +
            "2)\tEdit Shift\n" +
            "3)\tReturn\n" +
            "4)\tExit\n");
            int op = Integer.parseInt(s.next());

            if (op == 1) {
                createShift();
            }
            else if (op == 2)
                EditShift();
            else if (op == 3)
                break;
            else if (op == 4)
                Exit();
            else {
                System.out.println("Not a valid option, please try again.");
            }
        }
    }

    private void createShift() {
        boolean exitFunc = false;
        System.out.println("Enter the date of shift in DD/MM/YYYY format");
        String d = s.next();
        int day = Integer.parseInt(d.substring(0,1));
        int month = Integer.parseInt(d.substring(3,4));
        int year = Integer.parseInt(d.substring(6,9));
        Date date = new Date(day, month, year);

        System.out.println("What is the shift type?\n" +
                "1)\tMorning Shift\n" +
                "2)\tEvening Shift\n");
        int shiftType = Integer.parseInt(s.next());

        if (ShiftCntrl.checkIfshiftExist(date, shiftType))
            System.out.println("this shift is already exist");
        else{
            System.out.println("Enter Manager id");
            int id = Integer.parseInt(s.next());
            Worker manager = WorkersCntrl.getWorker(id);
            while(!WorkersCntrl.workerHasRole(manager,1)) {
                System.out.println("this worker is not manager, enter new Manager id or 0 to return");
                id = Integer.parseInt(s.next());
                manager = WorkersCntrl.getWorker(id);
                if (id == 0) {
                    exitFunc = true;
                    break;
                }
            }
            if (!exitFunc){
                System.out.println("Add Roles to the shift:");
                HashMap<Integer,Integer> roles = new HashMap<>();
                while(true) {
                    System.out.println("Enter role id");
                    int roleId = Integer.parseInt(s.next());
                    while (!WorkersCntrl.isRole(roleId)){
                        System.out.println("this is not valid role id, enter role id or 0 to return");
                        roleId = Integer.parseInt(s.next());
                        if (roleId == 0) {
                            exitFunc = true;
                            break;
                        }
                    }
                    if (!exitFunc) {
                        System.out.println("Enter amount of this role in this shift");
                        int amount = Integer.parseInt(s.next());
                        roles.put(roleId, amount);
                        System.out.println("Do you want to add more roles?\n" +
                                "1)\tYes\n" +
                                "2)\tNo\n");
                        if (Integer.parseInt(s.next()) == 2)
                            break;
                    }
                }
                if (!exitFunc) {
                    HashMap<Integer, Integer> workers = new HashMap<>();
                    System.out.println("Add workers to the shift:");
                    while (true) {
                        System.out.println("Worker available for this shift: ");
                        HashMap<Worker, String> availableWorkers = workersRec(date,shiftType,roles, workers);
                        
                        for (String s :availableWorkers.values()) {
                        	System.out.println(s);
                        }
                        System.out.println("Enter Worker id to Add");
                        int workerId = Integer.parseInt(s.next());
                        Worker w = WorkersCntrl.getWorker(id);
                        System.out.println("Enter role id");
                        int roleId = Integer.parseInt(s.next());
                        while (!WorkersCntrl.workerHasRole(w, roleId)) {
                            System.out.println("this worker does not have this role, enter new Manager id or 0 to return");
                            id = Integer.parseInt(s.next());
                            w = WorkersCntrl.getWorker(id);
                            if (id == 0) {
                                exitFunc = true;
                                break;
                            }
                        }
                        if (id != 0) {
                            workers.put(workerId, roleId);
                            System.out.println("Do you want to add more workers?\n" +
                                    "1)\tYes\n" +
                                    "2)\tNo\n");
                            if (Integer.parseInt(s.next()) == 2)
                                break;
                        }
                    }
                    ShiftCntrl.createShift(date, manager.getId(), shiftType, workers, roles);
                }
            }
        }
    }

    private HashMap<Worker, String> workersRec(Date date, int shiftType, HashMap<Integer, Integer> roles, HashMap<Integer, Integer> workersInShift) {
		HashMap<Integer, Worker> workers = WorkersCntrl.getWorkers();
		HashMap<Worker, String> availableWorkers = new HashMap<Worker, String>();
		for (Worker w: workers.values()) {
			boolean available = true;
			HashMap<Integer, Constraint> constraints = w.getConstraints();
			for (Constraint c: constraints.values()) {
				if (c.getShiftType() == shiftType && c.getConstraintDate().equals(date))
					available = false;
			}
			if (available) {
				String strRoles = "";
				List<Role> wRoles = w.getRoles();
				for (Role wr: wRoles) {
					for(Integer r: roles.keySet())
					if (roles.get(r) > 0 && wr.getId() == r && !workersInShift.containsKey(w.getId()))
						strRoles += ", Role: "+wr.getId()+" - "+wr.getRoleName();
				}
				if (!strRoles.equals("")) {
					strRoles = strRoles.substring(1);
					strRoles = "Worker id: " + w.getId() + " Worker's name: "+ w.getName() + ": " + strRoles;
					availableWorkers.put(w, strRoles);
				}
			}
		}
		return availableWorkers;
	}

	private void EditShift() {
        System.out.println("Enter shift date in DD/MM/YYYY format");
        String d = s.next();
        int day = Integer.parseInt(d.substring(0,1));
        int month = Integer.parseInt(d.substring(3,4));
        int year = Integer.parseInt(d.substring(6,9));
        Date date = new Date(day, month, year);

        System.out.println("Enter shift type\n" +
                "1)\tMorning Shift\n" +
                "2)\tEvening Shift\n");
        int shiftType = Integer.parseInt(s.next());

        Shift sh = ShiftCntrl.getShift(date, shiftType);
        while(true) {
            System.out.println("Choose the field to edit\n" +
            "1)\tShift Manager\n" +
            "2)\tShift Workers\n" +
            "3)\tShift Roles\n" +
            "4)\tReturn\n");
            int op = Integer.parseInt(s.next());

            if (op == 1) {
                System.out.println("Enter new Manager id");
                int id = Integer.parseInt(s.next());
                Worker manager = WorkersCntrl.getWorker(id);
                while(!WorkersCntrl.workerHasRole(manager,1)) {
                    System.out.println("this worker is not manager, enter new Manager id or 0 to return");
                    id = Integer.parseInt(s.next());
                    manager = WorkersCntrl.getWorker(id);
                    if (id == 0)
                        break;
                }
            } else if (op == 2) {
                printShiftWorkers(sh);
                editShift(sh);
            } else if (op == 3) {
                printShiftRoles(sh);
                editRules(sh);
            } else if (op == 4) {
                break;
            } else{
                System.out.println("Not a valid option, please try again.");
            }
        }
    }

    private void editRules(Shift sh) {
        while(true) {
            System.out.println("Choose\n" +
                    "1)\tAdd Role To Shift\n" +
                    "2)\tRemove Role From Shift\n" +
                    "3)\tReturn\n");
            int op = Integer.parseInt(s.next());

            if (op == 1) {
                System.out.println("Enter role id to add");
                int id = Integer.parseInt(s.next());
                System.out.println("Enter amount to add");
                int amount = Integer.parseInt(s.next());
                ShiftCntrl.addRoleToShift(sh, id, amount);
            } else if (op == 2) {
                System.out.println("Enter role id to remove");
                int id = Integer.parseInt(s.next());
                System.out.println("Enter amount to add");
                int amount = Integer.parseInt(s.next());
                ShiftCntrl.removeRoleToShift(sh, id, amount);
            } else if (op == 3) {
                break;
            } else
                System.out.println("Not a valid option, please try again.");
        }
    }

    private void printShiftRoles(Shift sh) {
        HashMap<Integer, Integer> roles = ShiftCntrl.getShiftRoles(sh);
        for(int roleId: roles.keySet()){
            int roleAmount = roles.get(roleId);
            Role r = WorkersCntrl.getRole(roleId);
            System.out.println(r.getRoleName() + " , amount in the shift: " + roleAmount);
        }
    }

    private void editShift(Shift sh) {
        while(true) {
            System.out.println("Choose\n" +
            "1)\tAdd Worker To Shift\n" +
            "2)\tRemove Worker From Shift\n" +
            "3)\tReturn\n");
            int op = Integer.parseInt(s.next());

            if (op == 1) {
                System.out.println("Enter worker id to add");
                int id = Integer.parseInt(s.next());
                System.out.println("Enter role id");
                int roleId = Integer.parseInt(s.next());
                Worker w = WorkersCntrl.getWorker(id);
                while (WorkersCntrl.workerHasRole(w, roleId)){
                    System.out.println("This worker don't have this role, please enter role id to remove, enter 0 to return back");
                    roleId = Integer.parseInt(s.next());
                    if (roleId == 0)
                        break;
                }
                if (roleId == 0)
                    break;

                ShiftCntrl.addWorkerToShift(sh, id, roleId);
            } else if (op == 2) {
                System.out.println("Enter worker id to add");
                int id = Integer.parseInt(s.next());

                ShiftCntrl.removeWorkerFromShift(sh, id);
            } else if (op == 3) {
                break;
            } else{
                System.out.println("Not a valid option, please try again.");
            }
        }
    }

    private void printShiftWorkers(Shift sh) {
        HashMap<Integer, Integer> workers = ShiftCntrl.getShiftWorkers(sh);
        for(Integer id: workers.keySet()){
            Worker w = WorkersCntrl.getWorker(id);
            WorkersCntrl.printWorker(w);
        }
    }

    public static void DeliveriesMenu(){
        System.out.println("Choose Unit : ");
        System.out.println("1- Driver\n 2- Truck\n 3- Delivery \n 4-Location");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
//        scanner.nextLine();
        switch(option){
            case 1:
                DriverMenu();//this exist in the workers menu...
                break;
            case 2:
                TruckMenu();
                break;
            case 3:
                DeliveryMenu();
                break;
            case 4:
                LocationMenu();
                break;
            default:
                System.out.println("not in options");
                DeliveriesMenu();
                break;


        }
    }

    private static void LocationMenu(){
        System.out.println("Choose option : ");
        System.out.println("1- add Location\n 2- get Location\n 3- get All Locations \n");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
//        scanner.nextLine();
        switch(option){
            case 1:
                System.out.println("Enter location's address: ");
                String address = scanner.next();
                System.out.println("Enter phone Number: ");
                String phoneNum = scanner.next();
                System.out.println("Enter contact name: ");
                String name = scanner.next();
//                System.out.println("Enter truck's capacity");
//                int capacity = scanner.nextInt();
//                Truck truck = new Truck(license,model,weight,capacity);
//                conn.addTruck(truck);
                Location loc = new Location(address,phoneNum,name);
                conn.addLocation(loc);
                break;
            case 2:
                System.out.println("Enter location's address: ");
                String address1 = scanner.next();
                Location l = conn.getLocation(address1);
                printLocation(l);
                break;
            case 3:
                List<Location> locations= conn.getAllLocations();
                for(Location l1:locations){printLocation(l1);}
                break;
            default:
                System.out.println("not in options");
                System.out.println("Choose option : ");
                System.out.println("1- back to MainMenu\n ");
                int num = scanner.nextInt();
                switch (num){
                    case 1:
                        DeliveriesMenu();
                        break;
                    default:
                        LocationMenu();
                        break;
                }
        }

    }

    private static void printLocation(Location l) {
        System.out.println(l.toString());
    }

    private static void DeliveryMenu() {
        System.out.println("Choose option : ");
        System.out.println("1- create Delivery\n 2- get Delivery\n 3-  \n 4-  \n 5- ");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch(option){
            case 1:
                System.out.println("Enter Delivery weight : ");
                int weight = scanner.nextInt();
                System.out.println("Enter documents : ");
                List<Document> ldoc = new LinkedList<>();
                ldoc =addDocument(ldoc);
                System.out.println("enter order's id");
                int oid = scanner.nextInt();
                System.out.println("enter product id");
                int productid = scanner.nextInt();
                System.out.println("Enter product name");
                String s = scanner.nextLine();
                System.out.println("enter amount");
                int amount = scanner.nextInt();
                System.out.println("enter weight per unit");
                int weight1 = scanner.nextInt();
                System.out.println("Enter source");
                String makor = scanner.nextLine();
                System.out.println("Enter ya3ad");
                String ya3ad = scanner.nextLine();
//                BusinessLayer.DeliveryController.getInstance().addDelivery(weight,ldoc);
                DeliveryController.getInstance().createNewDelivery(oid,productid,s,amount,weight1,null,makor,ya3ad,new Date(System.currentTimeMillis()+7));
                break;
            case 2:
                System.out.println("Enter deliver's id");
                int id = scanner.nextInt();
                Delivery delivery = conn.getDelivery(id);
                System.out.println(delivery.toString());
                break;
//            case 3:
//                List<Truck> truckList= conn.getAllTrucks();
//                printTruck(truckList);
//            case 4:
//                truckList= conn.getAllTrucksA();
//                printTruck(truckList);
//            case 5 :
//                truckList= conn.getAllTrucksB();
//                printTruck(truckList);
            default:
                System.out.println("not in options");
                System.out.println("Choose option : ");
                System.out.println("1- back to MainMenu\n ");
                int num = scanner.nextInt();
                switch (num){
                    case 1:
                        DeliveriesMenu();
                        break;
                    default:
                        DeliveryMenu();
                        break;
                }
        }
    }


    private static List<Document> addDocument(List<Document> ldoc) {
        Scanner input = new Scanner(System.in);
        System.out.println("choose option:");
        System.out.println("1- add Document:");System.out.println("2- Exit:");
       int  option = input.nextInt();
       if(option==1) {
           System.out.println("Enter document id:");
           int id = input.nextInt();
           System.out.println("Enter location fields: ");
           Location l = addLocation();
           System.out.println("Enter items: ");
           List<String> items = addItems();
           Document doc = new Document(id, items, l);
           ldoc.add(doc);
           return addDocument(ldoc);
       }
       else{
           return ldoc;
       }

    }

    private static List<String> addItems() {
        List<String> itemsList = new LinkedList<>();
        Scanner items = new Scanner(System.in);
        System.out.println("Enter items: ");
        while(true){
            String s = items.next();
            itemsList.add(s);
            if(s=="quit"){break;}
        }
        return itemsList;
    }

    private static Location addLocation() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter location's address: ");
        String address = input.next();
        System.out.println("Enter phone number: ");
        String phoneNum = input.next();
        System.out.println("Enter contact name: ");
        String name = input.next();
        return new Location(address,phoneNum,name);
    }

    private static void TruckMenu() {
        System.out.println("Choose option : ");
        System.out.println("1- add Truck\n 2- get Truck\n 3- get All Trucks \n 4- get all available Trucks \n 5- get all busy Trucks");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
//        scanner.nextLine();
        switch(option){
            case 1:
                System.out.println("Enter Truck license");
                int license = scanner.nextInt();
                System.out.println("Enter Truck model");
                String model = scanner.next();
                System.out.println("Enter truck's weight");
                int weight = scanner.nextInt();
                System.out.println("Enter truck's capacity");
                int capacity = scanner.nextInt();
                Truck truck = new Truck(license,model,weight,capacity);
                conn.addTruck(truck);
                break;
            case 2:
                System.out.println("Enter truck's license ");
                int lice = scanner.nextInt();
                Truck tr = conn.getTruck(lice);
                printTruck(tr);
                break;
            case 3:
                List<Truck> truckList= conn.getAllTrucks();
                printTruck(truckList);
                break;
            case 4:
                truckList= conn.getAllTrucksA();
                printTruck(truckList);
                break;
            case 5 :
                truckList= conn.getAllTrucksB();
                printTruck(truckList);
                break;
            default:
                System.out.println("not in options");
                System.out.println("Choose option : ");
                System.out.println("1- back to MainMenu\n ");
                int num = scanner.nextInt();
                switch (num){
                    case 1:
                        DeliveriesMenu();
                        break;
                    default:
                        TruckMenu();
                        break;
                }
        }
    }

    private static void printTruck(Truck tr) {
        System.out.println("License Number :"+tr.getLicenseNum());
        System.out.println("Model :"+tr.getModel());
        System.out.println("Weight :"+tr.getWeight());
        System.out.println("Capacity :"+tr.getCapacity());
        if(tr.getDriver()!= null)
            System.out.println("driver Id :"+tr.getDriver().getId());
        if(tr.isBusy(new Date(System.currentTimeMillis()))){
            System.out.println("Truck's Availability: TRUE!");
        }
        else{System.out.println("Truck's Availability: FALSE!");}
    }
    private static void printTruck(List<Truck> tr) {
        for(Truck tr1:tr){printTruck(tr1);}
    }
    private static void DriverMenu() {
        System.out.println("Choose option : ");
        System.out.println("1- add Driver\n 2- get Driver\n 3- get All Drivers \n 4- get all available drivers \n 5- get all busy drivers");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
//        scanner.nextLine();
        switch(option){
            case 1:
                System.out.println("Enter driver's id");
                int id = scanner.nextInt();
                System.out.println("Enter driver's name");
                String name = scanner.next();
                System.out.println("Enter driver's license");
                int license = scanner.nextInt();
                Driver1 d= new Driver1(id,name,license);
                conn.addDriver(d);
            case 2:
                System.out.println("Enter driver's id: ");
                int driverId = scanner.nextInt();
                Driver driver = conn.getDriver(driverId);
                printDriver(driver);
//                TruckMenu();
            case 3:
                List<Driver> driverList= conn.getAllDrivers();
                printDriver(driverList);

            case 4:
                 driverList= conn.getAllDriversA();
                printDriver(driverList);
            case 5 :
                driverList= conn.getAllDriversB();
                printDriver(driverList);
            default:
                System.out.println("not in options");
                System.out.println("Choose option : ");
                System.out.println("1- back to MainMenu\n ");
                int num = scanner.nextInt();
                switch (num){
                    case 1:
                        DeliveriesMenu();
                    default:
                        DriverMenu();
                }
        }
    }

    private static void printDriver(Driver driver) {
        System.out.println("ID: "+ driver.getId());
        System.out.println("NAME: "+ driver.getName());
        System.out.println("license Allowed: "+ driver.getLicense());
        if(driver.isBusy()==1){System.out.println("Driver's Availability: FALSE!");}
        else{System.out.println("Driver's Availability: TRUE!");}
    }
    private static void printDriver(List<Driver> driverList) {
        for(Driver d:driverList){
            printDriver(d);
        }
    }
    public void loadData(){
        Driver1 d = new Driver1("alex",1,2043,66,10,t,null);
        d.setLicenseAllowed(20);
        Driver1 d1 = new Driver1("john",2,1232,65,8,t,null);
        d1.setLicenseAllowed(13);
        Driver1 d2 = new Driver1("micheal",3,1234,66,9,t,null);
        d2.setLicenseAllowed(10);
        Driver1 d3 = new Driver1("moshe",4,1919,63,14,t,null);
        d3.setLicenseAllowed(25);
        Driver1 d4 = new Driver1("david",5,6593,66,10,t,null);
        d4.setLicenseAllowed(12);
        Truck t = new Truck(1,"12",10,5);
        Truck t1 = new Truck(2,"15",12,7);
        Truck t2 = new Truck(3,"17",7,4);
        Truck t3 = new Truck(4,"11",15,11);
        Truck t4 = new Truck(5,"20",7,5);
        Truck t5 = new Truck(6,"19",13,8);
        Location l = new Location("Tel-Aviv","0501919191","Kobe");
        Location l1 = new Location("Haifa","0523456789","Moshe");
        Location l2 = new Location("BeerShiva","0545678912","Lior");
        Location l3 = new Location("Hadera","0526545654","soso");
        Location l4 = new Location("x","0525151515","jojo");
        c.addTruck(t);
        c.addTruck(t1);
        c.addTruck(t2);
        c.addTruck(t3);
        c.addTruck(t4);
        c.addTruck(t5);
        c.addDriver(d);
        c.addDriver(d1);
        c.addDriver(d2);
        c.addDriver(d3);
        c.addDriver(d4);
        c.addLocation(l);
        c.addLocation(l1);
        c.addLocation(l2);
        c.addLocation(l3);
        c.addLocation(l4);
        DeliveryController.getInstance().createNewDelivery(1,1,"Milk",10,1,null,"beersheva","tel-aviv",new java.util.Date(4/4/2021));
        DeliveryController.getInstance().addToExist(1,"bamba",4);
        Document1 doc = DeliveryController.getInstance().getDocument(1);
        doc.toString();

    }
}


