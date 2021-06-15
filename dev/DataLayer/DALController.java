package DataLayer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import BusinessLayer.Workers.*;
public class DALController {
    protected Connection connect() {  
    	
        String url = "jdbc:sqlite:ADSS_Group_I'\'dev'\'SQL_Inventory_Suppliers.db";
        Connection conn = null;  
        try {  
        	Class.forName("org.sqlite.JDBC");
        	conn = DriverManager.getConnection(url);  
        } catch (SQLException | ClassNotFoundException e) {  
            System.out.println(e.getMessage());  
        }  
        return conn;  
    }
    
	public void update(BusinessObject dto, String field, String value) {
	    String sql = "UPDATE "+dto.getClass().getSimpleName()+"s "+" SET "+field+" = ?"
	            + "WHERE  ID= ?";

	    try (Connection conn = this.connect();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        // set the corresponding param
	        pstmt.setString(1,value);
	        pstmt.setInt(2,dto.getId());
	        
	        // update 
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	
	public void delete(BusinessObject dto) {
	    String sql = "DELETE FROM "+dto.getClass().getSimpleName()+"s "
	            + "WHERE ID=? ";

	    try (Connection conn = this.connect();
	        PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        // set the corresponding param
	        pstmt.setInt(1,dto.getId());
	        // update 
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}
	public void update(BusinessObject dto, String field, int value) {
	    String sql = "UPDATE "+dto.getClass().getSimpleName()+"s "+" SET "+field+" = ?"
	            + "WHERE  ID= ?";

	    try (Connection conn = this.connect();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        // set the corresponding param
	        pstmt.setInt(1,value);
	        pstmt.setInt(2,dto.getId());
	        
	        // update 
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}

	public void update(BusinessObject dto, String field,Date value) {
	    String sql = "UPDATE "+dto.getClass().getSimpleName()+"s "+" SET "+field+" = ?"
	            + "WHERE  ID= ?";

	    try (Connection conn = this.connect();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        // set the corresponding param
	        pstmt.setDate(1,value);
	        pstmt.setInt(2,dto.getId());
	        
	        // update 
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    
}
    public void insert(int id,String name, int bankAccountNumber,int bankNumber,int salary) {
        String sql = "INSERT INTO Workers(ID,Name,BankAccountNumber,BankNumber,Salary,JoiningDate) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, bankAccountNumber);
            pstmt.setInt(4, bankNumber);
            pstmt.setInt(5, salary);
            pstmt.setDate(6, new Date(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(int roleid,String rolename) {
        String sql = "INSERT INTO Roles(ID,RoleName) VALUES(?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roleid);
            pstmt.setString(2, rolename);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(Date date,int managerid,int shifttype) {
        String sql = "INSERT INTO Shifts(Date,ManagerID,ShiftType) VALUES(?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, date);
            pstmt.setInt(2, managerid);
            pstmt.setInt(3, shifttype);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(int isTemp,Date date,int shifttype,int workerid) {
        String sql = "INSERT INTO Constraints(IsTemp,Date,ShiftType,WorkerID) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, isTemp);
            pstmt.setDate(2, date);
            pstmt.setInt(3, shifttype);
            pstmt.setInt(4, workerid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertWS(int workerid,int shiftid) {
        String sql = "INSERT INTO WorkersShifts(WorkerID,ShiftID) VALUES(?,?)";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workerid);  
            pstmt.setInt(2, shiftid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteWS(int workerid,int shiftid) {
        String sql = "DELETE FROM WorkersShifts WHERE WorkerID=? AND ShiftID=?)";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workerid);  
            pstmt.setInt(2, shiftid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertWR(int workerid,int roleid) {
        String sql = "INSERT INTO WorkersRoles(WorkerID,RoleID) VALUES(?,?)";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workerid);  
            pstmt.setInt(2, roleid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteWR(int workerid,int roleid) {
        String sql = "DELETE FROM WorkersRoles WHERE WorkerID=? AND RoleID=?)";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workerid);  
            pstmt.setInt(2, roleid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertSR(int shiftid,int roleid,int amount) {
        String sql = "INSERT INTO ShiftsRoles(ShiftID,RoleID,Amount) VALUES(?,?,?)";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shiftid);  
            pstmt.setInt(2, roleid);
            pstmt.setInt(3, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void updateSR(int shiftid,int roleid,int amount) {
	    String sql = "UPDATE ShiftsRoles SET Amount = ? , "
	            + "WHERE  ShiftID= ? AND RoleID=?";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, amount);  
            pstmt.setInt(2, shiftid);
            pstmt.setInt(3, roleid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteSR(int shiftid,int roleid) {
        String sql = "DELETE FROM ShiftsRoles WHERE WorkerID=? AND RoleID=?)";
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, shiftid);  
            pstmt.setInt(2, roleid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	public HashMap<Integer, Worker> initWorkers() {
	        String sql = "SELECT * FROM Workers";
        	HashMap<Integer, Worker> workers = new HashMap<Integer, Worker>();
	        try (Connection conn = this.connect();
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            // loop through the result set
	            while (rs.next()) {
	                Worker w = new Worker(rs.getString("Name"),
	                				   rs.getInt("ID"),
	                                   rs.getInt("BankAccountNumber"),
	                                   rs.getInt("BankNumber"),
	                                   rs.getInt("Salary"),
	                                   rs.getDate("JoiningDate"),
	                                   rs.getDate("LeavingDate"));
	                workers.put(w.getId(), w);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        
	        return workers;
	}
	
	public HashMap<Integer, Role> initRoles() {
        String sql = "SELECT * FROM Roles";
    	HashMap<Integer, Role> roles = new HashMap<Integer, Role>();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Role r = new Role(rs.getInt("ID"),rs.getString("RoleName"));
                roles.put(r.getId(), r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return roles;
	}
	
	public List<Shift> initShifts() {
        String sql = "SELECT * FROM Shifts";
    	List<Shift> shifts = new LinkedList<Shift>();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                Shift s = new Shift (rs.getDate("Date"),
                				   rs.getInt("ManagerID"),
                                   rs.getInt("ShiftType"),
                                   rs.getInt("ID"));
                shifts.add(s);
            }
            for(Shift s: shifts) {
            	HashMap<Integer, Integer> workerInShift = initWorkersInShift(s.getId());
            	HashMap<Integer, Integer> rolesInShift = initRolesInShift(s.getId());
            	s.setRoles(rolesInShift);
            	s.setWorkers(workerInShift);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return shifts;
	}
	
	public HashMap<Integer, Integer> initWorkersInShift(int id) {
        String sql = "SELECT * FROM WorkersShifts WHERE ShiftID = ?";
    	HashMap<Integer, Integer> w = new HashMap<Integer, Integer>();

        try (Connection conn = this.connect();
	        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);  
            ResultSet rs    = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
                w.put(rs.getInt("WorkerID"), rs.getInt("RoleID")); // add role id to data base
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return w;
	}
	
	public HashMap<Integer, Integer> initRolesInShift(int id) {
        String sql = "SELECT * FROM ShiftsRoles WHERE ShiftID = ?";
    	HashMap<Integer, Integer> r = new HashMap<Integer, Integer>();

        try (Connection conn = this.connect();
	        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);  
            ResultSet rs    = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
                r.put(rs.getInt("RoleID"), rs.getInt("Amount")); // add amount to data base
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return r;
	}
	
	public HashMap<Integer, Constraint> initWorkersConstraints(int id) {
        String sql = "SELECT * FROM Constraints WHERE WorkerID = ?";
    	HashMap<Integer, Constraint> c = new HashMap<Integer, Constraint>();

        try (Connection conn = this.connect();
	        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);  
            ResultSet rs    = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
            	boolean temp=false;
            	if(rs.getInt("IsTemp")==1)
            		temp=true;
                Constraint cons = new Constraint (temp,
     				   rs.getDate("Date"),
                        rs.getInt("ShiftType"),
                        rs.getInt("WorkerID"),
                        rs.getInt("ID"));
                c.put(cons.getId(), cons); 
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return c;
	}
	public List<Integer> initWorkersRoles(int id) {
        String sql = "SELECT * FROM WorkersRoles WHERE WorkerID = ?";
        List<Integer> r = new LinkedList<Integer>();

        try (Connection conn = this.connect();
	        PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);  
            ResultSet rs    = pstmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
                r.add(rs.getInt("RoleID"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return r;
	}
}
    
    
    
    

