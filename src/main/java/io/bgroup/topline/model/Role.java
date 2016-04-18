package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Role {

    private String idRole;
    private String roleName;
    private int hasRole;

    @Autowired
    private DbModel dbMvc;

    public int getHasRole() {
        return hasRole;
    }

    public void setHasRole(int hasRole) {
        this.hasRole = hasRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public ArrayList<Role> getRoleList() {
        String sql = null;
        sql = "select * from user_roles where username='admin'";
        ArrayList<Role> rolesUrlsList = getUserRolesFromDbSelect(sql);
        return rolesUrlsList;
    }

    public ArrayList<Role> getRoleListByUser(String siteUserName) {
        if (siteUserName == null) return null;
        String sql = "SELECT * FROM \n" +
                "user_roles\n" +
                "left join\n" +
                "(select role as role2 from user_roles where username = '" + siteUserName + "') \n" +
                "ss on ss.role2 = user_roles.role\n" +
                "where user_roles.username = 'admin'" +
                "order by user_roles.role";
        ArrayList<Role> rolesUrlsList = getUserRolesFromDbSelect(sql);
        return rolesUrlsList;
    }

    private ArrayList<Role> getUserRolesFromDbSelect(String sql) {
        List<Map<String, Object>> roleListFromDb = null;
        roleListFromDb = dbMvc.getSelectResult(sql);
        if (roleListFromDb == null) return null;
        ArrayList<Role> roleList = null;
        for (Map row : roleListFromDb) {
            Role role = new Role();
            String roleUserRoleId = (String) row.get("user_role_id").toString();
            if (roleUserRoleId != null) role.setIdRole(roleUserRoleId);
            else role.setIdRole(null);
            String roleRole = (String) row.get("role").toString();
            if (roleRole != null) {
                role.setRoleName(roleRole);
            } else role.setRoleName(null);
            String roleRole2 = (String) row.get("role2");
            if (roleRole2 == null) {
                role.setHasRole(-1);
            } else role.setHasRole(1);
            if (roleList == null) roleList = new ArrayList<Role>();
            roleList.add(role);
        }
        return roleList;
    }
}
