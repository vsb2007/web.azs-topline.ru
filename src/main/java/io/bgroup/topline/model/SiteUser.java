package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private String error;

    @Autowired
    private DbModel db;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void setDb(DbModel db) {
        this.db = db;
    }

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

    private SiteUser findSiteUser(String name) {
        SiteUser findUser = null;
        String sql;
        List<Map<String, Object>> findUsersList = null;
        if (name != null) {
            sql = "select * from users where username='" + name + "'";
            findUsersList = db.getSelectResult(sql);
        }
        findUser = getSiteUserFromDbSelect(findUsersList);
        return findUser;
    }

    public SiteUser findSiteUser(UsernamePasswordAuthenticationToken principal) {

        return findSiteUser(principal.getName());
    }

    private SiteUser findSiteUser(int id_user) {
        SiteUser findUser = null;
        String sql;
        List<Map<String, Object>> findUsersList = null;
        sql = "select * from users where id_user='" + id_user + "'";
        findUsersList = db.getSelectResult(sql);
        findUser = getSiteUserFromDbSelect(findUsersList);
        return findUser;
    }

    private SiteUser getSiteUserFromDbSelect(List<Map<String, Object>> findUsers) {
        SiteUser findUser = null;
        if (findUsers != null && findUsers.size() == 1) {
            try {
                Map row = findUsers.get(0);
                findUser = new SiteUser();
                setSiteUserFromMapRow(findUser, row);
            } catch (Exception e) {
                this.error = "Ошибка: " + e;
            }
        }
        return findUser;
    }

    private SiteUser findUser(HttpServletRequest request) {
        String userNameFromButtonValue = request.getParameter("buttonuserred");
        String sql;
        SiteUser redUser = null;

        if (userNameFromButtonValue != null && !userNameFromButtonValue.equals("")) {
            redUser = findSiteUser(userNameFromButtonValue);
            return redUser;
        }

        String userIdFromForm = request.getParameter("user-red-id-label");
        sql = "select * from users where id_user='" + userIdFromForm + "'";

        List<Map<String, Object>> findUsersList = db.getSelectResult(sql);

        redUser = getSiteUserFromDbSelect(findUsersList);
        return redUser;
    }

    public SiteUser findRedSiteUser(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!isUserHasRole(principal, "ROLE_USERSRED")) return null;

        String userFindValue = request.getParameter("user-find-label");
        String userRedFormValue = request.getParameter("red_form");
        String userDelValue = request.getParameter("user-delete-id-label");

        SiteUser redUser = null;

        if (userFindValue != null && userFindValue.equals("1")) {
            redUser = findUser(request);
        } else if (userRedFormValue != null && userRedFormValue.equals("1")) {
            redUser = findUser(request);
            String tmpError = updateUserMessage(redUser, request);
            redUser = findSiteUser(Integer.parseInt(redUser.getId()));
            redUser.setError(tmpError);
        }
        //return findSiteUser(request.getParameter("buttonuserred"));
        return redUser;
    }

    private String updateUserMessage(SiteUser redUser, HttpServletRequest request) {
        String userNameFromForm = request.getParameter("user-name-label");
        String userPasswordFromForm = request.getParameter("user-password-label");
        String userFioFromForm = request.getParameter("user-fio-label");
        String userPhoneFromForm = request.getParameter("user-phone-label");
        String userEmailFromForm = request.getParameter("user-email-label");
        String userIdFromForm = request.getParameter("user-red-id-label");
        String userActiveFlagFromForm = request.getParameter("user-active-flag");

        String sql;
        if (userNameFromForm != null
                && !redUser.getName().equals(userNameFromForm)
                && !userNameFromForm.equals("")) {
            sql = "update users set username='" + userNameFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                return "Ошибка обновления имени";
            }
        }
        if (userPasswordFromForm != null && !userPasswordFromForm.equals("")) {
            String password = passwordEncoder.encode(userPasswordFromForm);
            sql = "update users set password='" + password + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                return "Ошибка обновления пароля";
            }
        }
        if (userFioFromForm != null) {
            sql = "update users set user_fio='" + userFioFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                return "Ошибка обновления ФИО";

            }
        }
        if (userPhoneFromForm != null) {
            sql = "update users set user_phone='" + userPhoneFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                return "Ошибка обновления телефона";
            }
        }
        if (userEmailFromForm != null) {
            sql = "update users set user_email='" + userEmailFromForm + "' where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                return "Ошибка обновления email";
            }
        }
        if (userActiveFlagFromForm != null) {
            if (userActiveFlagFromForm.equals("0")) {
                sql = "update users set enabled=true where id_user=" + userIdFromForm;

                boolean flag = db.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления блокировки";
                }
            }
        }
        if (userActiveFlagFromForm == null || !userActiveFlagFromForm.equals("0")) {
            sql = "update users set enabled=false where id_user=" + userIdFromForm;
            boolean flag = db.getInsertResult(sql);
            if (flag) {
                return "Ошибка обновления блокировки";
            }
        }

        return "Изменения сохранены";
    }

    public ArrayList<SiteUser> getListSiteUsers(UsernamePasswordAuthenticationToken principal) {
        if (!isUserHasRole(principal, "ROLE_USERS")) return null;
        String sql;
        sql = "select * from users where username!='admin' and user_is_delete=0";
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

    public boolean userAdd(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!isUserHasRole(principal, "ROLE_USERSADD")) return false;
        String userNameFromFormUserAdd = request.getParameter("username");
        String sql;
        sql = "select * from users where user_name='" + userNameFromFormUserAdd + "'";
        List<Map<String, Object>> dbSelectResult = db.getSelectResult(sql);
        if (dbSelectResult != null && dbSelectResult.size() > 0) {
            this.error = "Пользователь существует";
            return false;
        } else {
            sql = "INSERT INTO users (username) VALUES ('" + userNameFromFormUserAdd + "')";
            boolean flag = db.getInsertResult(sql);
            if (!flag)
                this.error = "Пользователь добавлен";
            else
                this.error = "Пользователь не добавлен, ошибка!!!";
        }
        return true;
    }

    private boolean isUserHasRole(UsernamePasswordAuthenticationToken principal, String role) {
        boolean returnNullFlag = true;
        for (GrantedAuthority grantedAuthority : principal.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
