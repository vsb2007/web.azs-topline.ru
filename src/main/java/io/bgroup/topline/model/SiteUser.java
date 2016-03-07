package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SiteUser {
    private String name = "";
    private String fio = "";
    private String phone = "";
    private String email = "";
    private String isEnable = "";
    private String isDelete = "";
    private String id;

    @Autowired
    private DbToplineWeb db;

    public void setDb(DbToplineWeb db) {
        this.db = db;
    }

    private String error;


    /*public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
*/
    public SiteUser() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public enum usersTableField {
        id_user, username, password, user_fio, user_phone, user_email, enabled, user_is_delete
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFio() {
        return fio;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setFio(String fio) {
        if (fio == null)
            this.fio = "";
        else
            this.fio = fio;
    }

    public void setPhone(String phone) {
        if (phone == null)
            this.phone = "";
        else
            this.phone = phone;
    }

    public void setEmail(String email) {
        if (email == null)
            this.email = "";
        else
            this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    public boolean getPermissionsAccess(String page) {
        return true;
    }

    public SiteUser findSiteUser(String name) {
        SiteUser redUser = null;
        String sql;
        List<Map<String, Object>> findUser = null;
        if (name != null) {
            sql = "select * from users where username='" + name + "'";
            findUser = db.getSelectResult(sql);
        }
        if (findUser != null && findUser.size() == 1) {
            try {
                Map row = findUser.get(0);
                redUser = new SiteUser();
                setSiteUserFromMapRow(redUser, row);
            } catch (Exception e) {
                this.error = "Ошибка: " + e;
            }
        }
        return redUser;
    }

    public ArrayList<SiteUser> getListSiteUsers(UsernamePasswordAuthenticationToken principal) {
        boolean returnNullFlag = true;
        for (GrantedAuthority grantedAuthority : principal.getAuthorities()){
                if (grantedAuthority.getAuthority().equals("ROLE_USERS")){
                    returnNullFlag = false;
                    break;
                }
        }
        if (returnNullFlag) return null;

        String sql;
        sql = "select * from users where username!='admin'";
        List<Map<String, Object>> listDbUser = db.getSelectResult(sql);
        if (listDbUser == null) {
            return null;
        }
        ArrayList<SiteUser> siteUserArrayList = new ArrayList<SiteUser>(listDbUser.size());
        for (Map row : listDbUser) {
            SiteUser tmpSiteUser = new SiteUser();
            setSiteUserFromMapRow(tmpSiteUser, row);
            siteUserArrayList.add(tmpSiteUser);
        }
        return siteUserArrayList;
    }

    private void setSiteUserFromMapRow(SiteUser redUser, Map row) {
        redUser.setName((String) row.get("username"));
        redUser.setEmail((String) row.get("user_email"));
        redUser.setFio((String) row.get("user_fio"));
        redUser.setPhone((String) row.get("user_phone"));
        redUser.setIsEnable((String) row.get("enabled").toString());
        redUser.setId((String) row.get("id_user").toString());
    }

    public SiteUser userAdd(UsernamePasswordAuthenticationToken principal,HttpServletRequest request){
        boolean returnNullFlag = true;
        for (GrantedAuthority grantedAuthority : principal.getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ROLE_USERSADD")){
                returnNullFlag = false;
                break;
            }
        }
        if (returnNullFlag) return null;

        SiteUser siteUser = null;
        //if (principal.) return null;
        /*
                HttpSession session = request.getSession();
        SiteUser siteUser = (SiteUser) session.getAttribute("dbUserName");
        if (siteUser == null || !siteUser.getName().equals("admin")) {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        String userNameFromFormUserAdd = request.getParameter("username");
        DbToplineWeb db = new DbToplineWeb();
        String sql;
        sql = "select * from users where user_name='" + userNameFromFormUserAdd + "'";
        ResultSet resultSet = null;
        resultSet = db.getSelectResult(sql);
        try {
            if (resultSet != null && resultSet.next()) {
                request.setAttribute("errorAddUser", "Пользователь существует");
                request.getRequestDispatcher("/users").forward(request, response);
            }
            if (resultSet != null && !resultSet.next()) {
                //String nameFromDb = resultSet.getString(usersFields.users_name.toString());
                sql = "INSERT INTO users (user_name) VALUES ('" + userNameFromFormUserAdd + "')";
                boolean flag = db.getInsertResult(sql);
                if (!flag)
                    request.setAttribute("errorAddUser", "Пользователь добавлен");
                else
                    request.setAttribute("errorAddUser", "Пользователь не добавлен, ошибка!!!");
                request.getRequestDispatcher("/users").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
         */
        return siteUser;
    }
}
