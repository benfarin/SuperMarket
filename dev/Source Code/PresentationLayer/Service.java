package PresentationLayer;
import BusinessLayer.*;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static Enum.ShiftField.ManagerID;

public class Service {

    public static Scanner s;
    ShiftController ShiftCntrl;
    WorkersController WorkersCntrl;

    public Service(){
        s = new Scanner(System.in).useDelimiter("\n");
        this.ShiftCntrl = new ShiftController();
        this.WorkersCntrl = new WorkersController();
    }

    public void startMenu() {
        while (true) {
            System.out.println("\t\tSuper-Li's Workers department\n\n" +
            "1)\tInitialize data\n" +
            "2)\tLogin\n" +
            "3)\tExit"
            );

            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    Initialize();
                    break;
                case 2:
                    LoginMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Not a valid option, please try again.\n");
                    break;
            }
        }
    }

    public void LoginMenu() {
    	System.out.println("Please enter your ID:\n");
    	int ID=Integer.parseInt(s.next());
        int op = 1;
    	while (!WorkersCntrl.isWorker(ID)) {
             System.out.println("Wrong Id Number\n" +
            "1)\tTry Again\n" +
            "2)\tReturn to Previous Menu\n" +
            "3)\tExit");
    	    op = s.nextInt();
            if (op == 1){
                System.out.println("Please enter your ID:\n");
    	        ID = s.nextInt();
			}
            if (op == 2){
                startMenu();
                break;
			}
            if (op == 3){
                Exit();
			}
            else
                System.out.println("Not a valid option, please try again.");
    	}
    	if (op == 1) {
            Worker w = WorkersCntrl.getWorker(ID);
            System.out.println("Please choose you role:\n" +
            "1)\tManager\n" +
            "2)\tCashier \n" +
            "3)\t StorageWorker");
    	    int role=s.nextInt();
            // the roles Id is the number to choose
    	    while(!WorkersCntrl.workerHasRole(w, role)){
                System.out.println("This role does not match this worker\n" +
                "1)\tTry Again\n" +
                "2)\tExit\n");
                op = Integer.parseInt(s.next());
                if (op == 1){
                    System.out.println("Please choose you role:\n" +
                    "1)\tManager\n" +
                    "2)\tCashier \n" +
                    "3)\t StorageWorker");
    	            role=Integer.parseInt(s.next());
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
        WorkersCntrl.createRole(1, "Manager");
        WorkersCntrl.createRole(2, "Cashier");
        WorkersCntrl.createRole(3, "StorageWorker");

        WorkersCntrl.createWorker("Nahum Yaakobi", 123, 234423, 12, 32324);
        Worker w1 = WorkersCntrl.getWorker(123);
        WorkersCntrl.addRoleToWorker(w1, 1);
        WorkersCntrl.createWorker("Shalom Shlomi", 456, 325, 12, 3242);
        Worker w2 = WorkersCntrl.getWorker(456);
        WorkersCntrl.addRoleToWorker(w2, 2);
        WorkersCntrl.createWorker("ron levy", 789, 234423, 12, 32324);
        Worker w3 = WorkersCntrl.getWorker(789);
        WorkersCntrl.addRoleToWorker(w3, 3);

        System.out.println("Done initialize");

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
            int op = s.nextInt();
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
        Date today = new Date();
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

        Shift s = ShiftCntrl.getShift(date, shiftType);

        WorkersCntrl.addConstraintToWorker(w, new Constraint(isTemp, date, shiftType,w, s));
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
        Constraint c = WorkersCntrl.getConstraint(w,shift);

        while(true){
            System.out.println("Choose what to edit\n" +
            "1)\tConstraint Type (One-Time/Regularly)\n" +
            "2)\tShift Date\n" +
            "3)\tShiftType (Morning Shift/Evening Shift\n" +
            "4)\tExit" );
            int op = s.nextInt();

            if (op == 1){
                System.out.println("The constraint is one-time or regularly?\n" +
                "1)\tOne-Time\n" +
                "2)\tRegularly\n");
    	        op = s.nextInt();
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
    	        shiftType = s.nextInt();
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
            "3)\tExit\n");
            int op = s.nextInt();
            if (op == 1) {
                System.out.println("Enter worker id:");
                int id = s.nextInt();
                ManagerWorkerMenu(WorkersCntrl.getWorker(id));
            }
            else if (op == 2)
                ShiftsMenu();
            else if (op == 3)
                Exit();
            else
                System.out.println("Not a valid option, please try again.");
        }
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
            int op = s.nextInt();
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
            int op = s.nextInt();

            if (op == 1){
                System.out.println("Enter new name");
                String name = s.next();

            }
            else if (op == 2){

            }
        }
    }

    private void printWorkerConstraints (Worker w) {
        HashMap<Shift, Constraint> constraints = WorkersCntrl.getWorkerConstraints(w);
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
            "3)\tAdd Shift\n" +
            "4)\tReturn\n" +
            "5)\tExit\n");
            int op = s.nextInt();

            if (op == 1) {
                createShift();
            }
            else if (op == 2)
                EditShift();
            else if (op == 3)
                Exit();
            else if (op == 4)
                Exit();
            else if (op == 5)
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
                        System.out.println("Enter worker id");
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
                    Shift sh = new Shift(date, manager.getId(), shiftType, workers, roles);
                    ShiftCntrl.addShift(sh);
                }
            }
        }
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
            int op = s.nextInt();

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
            int op = s.nextInt();

            if (op == 1) {
                System.out.println("Enter role id to add");
                int id = s.nextInt();
                System.out.println("Enter amount to add");
                int amount = s.nextInt();
                ShiftCntrl.addRoleToShift(sh, id, amount);
            } else if (op == 2) {
                System.out.println("Enter role id to remove");
                int id = s.nextInt();
                System.out.println("Enter amount to add");
                int amount = s.nextInt();
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
            int op = s.nextInt();

            if (op == 1) {
                System.out.println("Enter worker id to add");
                int id = s.nextInt();
                System.out.println("Enter role id");
                int roleId = s.nextInt();
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
                int id = s.nextInt();

                ShiftCntrl.removeWorkerFrom(sh, id);
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

}


