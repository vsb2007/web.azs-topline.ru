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
    private DbJdbcModel dbMvc;

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
        ArrayList<Role> rolesUrlsList = getUserRolesFromDbSelect(sql,null);
        return rolesUrlsList;
    }

    public ArrayList<Role> getRoleListByUser(String siteUserName) {
        if (siteUserName == null) return null;
        ArrayList<Object> args = new ArrayList<Object>();
        String sql = "SELECT * FROM " +
                "user_roles " +
                "left join " +
                "(select role as role2 from user_roles where username = ?) " +
                "ss on ss.role2 = user_roles.role " +
                "where user_roles.username = 'admin' " +
                "order by user_roles.role";
        args.add(siteUserName);
        ArrayList<Role> rolesUrlsList = getUserRolesFromDbSelect(sql,args);
        return rolesUrlsList;
    }

    private ArrayList<Role> getUserRolesFromDbSelect(String sql,ArrayList<Object> args) {
        List<Map<String, Object>> roleListFromDb = null;
        roleListFromDb = dbMvc.getSelectResult(sql,args);
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
