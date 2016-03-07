package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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
        String sql;
        if (!principal.getName().equals("admin")) {
            return null;
        }

        sql = "select * from users where username!='admin'";
        List<Map<String, Object>> listDbUser = db.getSelectResult(sql);
        if (listDbUser == null) {
            return null;
        }
        ArrayList<SiteUser> siteUserArrayList = new ArrayList<SiteUser>(listDbUser.size());
        int i = 0;
        for (Map row : listDbUser) {
            SiteUser tmpSiteUser = new SiteUser();
            setSiteUserFromMapRow(tmpSiteUser, row);
            System.out.println(i++);
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
}
