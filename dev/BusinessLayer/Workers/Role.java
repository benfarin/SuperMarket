package BusinessLayer.Workers;
public class Role extends BusinessObject {

    private int roleID;
    private String roleName;

    public Role(int roleID, String roleName){
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public int getRoleID() {
        return roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public int getId() {
		return roleID;
	}
}
