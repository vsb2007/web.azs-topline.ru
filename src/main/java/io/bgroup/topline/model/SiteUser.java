package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SiteUser {
    private String name = "";
    private String fio = "";
    private String phone = "";
    private String email = "";
    private boolean isEnable = true;
    private String isDelete = "";
    private int id;
    private String error;
    private Post post;
    private CompanyUnit companyUnit;
    private ArrayList<Role> rolesList;

    @Autowired
    private DbJdbcModel dbMvc;
    @Autowired
    PasswordEncoder passwordEncoderMvc;
    @Autowired
    Post postMvc;
    @Autowired
    CompanyUnit companyUnitMvc;
    @Autowired
    Role roleMvc;

    public SiteUser() {
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public ArrayList<Role> getRolesList() {
        return rolesList;
    }

    public void setRolesList(ArrayList<Role> rolesList) {
        this.rolesList = rolesList;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public enum usersTableField {
        id_user, username, password, user_fio, user_phone, user_email, enabled, user_is_delete,
        user_post_id, user_company_unit_id
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

    public int getId() {
        return id;
    }

    public boolean getIsEnable() {
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

    public Post getPost() {
        return post;
    }

    private void setPost(Post post) {
        this.post = post;
    }

    public CompanyUnit getCompanyUnit() {
        return companyUnit;
    }

    private void setCompanyUnit(CompanyUnit companyUnit) {
        this.companyUnit = companyUnit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    private SiteUser findSiteUser(String name) {
        SiteUser findUser = null;
        String sql;
        List<Map<String, Object>> findUsersList = null;

        if (name != null) {
            ArrayList<Object> arrayList = new ArrayList<Object>();
            arrayList.add(name);
            sql = "select * from users where username=?";
            ArrayList<Object> args = new ArrayList<Object>();
            args.add(name);
            findUsersList = dbMvc.getSelectResult(sql, args);
        }
        findUser = getSiteUserFromDbSelect(findUsersList);
        return findUser;
    }

    public SiteUser findSiteUser(UsernamePasswordAuthenticationToken principal) {
        return findSiteUser(principal.getName());
    }

    public SiteUser findSiteUser(int id_user) {
        SiteUser findUser = null;
        String sql;
        List<Map<String, Object>> findUsersList = null;
        sql = "select * from users where id_user=?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(id_user);
        findUsersList = dbMvc.getSelectResult(sql, args);
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
                //System.out.println(error);
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
        sql = "select * from users where id_user=?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(userIdFromForm);
        List<Map<String, Object>> findUsersList = dbMvc.getSelectResult(sql, args);
        redUser = getSiteUserFromDbSelect(findUsersList);
        return redUser;
    }

    public SiteUser findRedSiteUser(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!isUserHasRole(principal, "ROLE_USERS_RED")) return null;
        String userFindValue = request.getParameter("user-find-label");
        String userRedFormValue = request.getParameter("red_form");
        String userDelValue = request.getParameter("user-delete-id-label");
        SiteUser redUser = null;
        if (userFindValue != null && userFindValue.equals("1")) {
            redUser = findUser(request);
        } else if (userRedFormValue != null && userRedFormValue.equals("1")) {
            redUser = findUser(request);
            String tmpError = updateUserMessage(redUser, request);
            redUser = findSiteUser(redUser.getId());
            redUser.setError(tmpError);
        }
        return redUser;
    }

    private String updateUserMessage(SiteUser redUser, HttpServletRequest request) {
        try {
            String userNameFromForm = request.getParameter("user-name-label");
            String userPasswordFromForm = request.getParameter("user-password-label");
            String userFioFromForm = request.getParameter("user-fio-label");
            String userPhoneFromForm = request.getParameter("user-phone-label");
            String userEmailFromForm = request.getParameter("user-email-label");
            String userIdFromForm = request.getParameter("user-red-id-label");
            String userActiveFlagFromForm = request.getParameter("user-active-flag");
            String userPostIdFromForm = request.getParameter("postId");
            String userCompanyIdFromForm = request.getParameter("companyId");
            String userCompanyUnitIdFromForm = request.getParameter("companyUnitId");
            String sql;
            if (userNameFromForm != null
                    && !redUser.getName().equals(userNameFromForm)
                    && !userNameFromForm.equals("")) {
                sql = "update users set username=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userNameFromForm);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления имени";
                }
            }
            if (userPasswordFromForm != null && !userPasswordFromForm.equals("")) {
                String password = passwordEncoderMvc.encode(userPasswordFromForm);
                sql = "update users set password=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(password);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления пароля";
                }
            }
            if (userFioFromForm != null) {
                sql = "update users set user_fio=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userFioFromForm);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления ФИО";
                }
            }
            if (userPhoneFromForm != null) {
                sql = "update users set user_phone=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userPhoneFromForm);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления телефона";
                }
            }
            if (userEmailFromForm != null) {
                sql = "update users set user_email=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userEmailFromForm);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления email";
                }
            }
            if (userPostIdFromForm != null) {
                sql = "update users set user_post_id=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userPostIdFromForm);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления PostId";
                }
            }
            if (userCompanyIdFromForm != null) {
                sql = "update users set user_company_id=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userCompanyIdFromForm);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления CompanyId";
                }
            } else {
                sql = "update users set user_company_id='-1' where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления CompanyId -1";
                }
            }
            if (userCompanyUnitIdFromForm != null) {
                sql = "update users set user_company_unit_id=? where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userCompanyUnitIdFromForm);
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления CompanyUnitId";
                }
            } else {
                sql = "update users set user_company_unit_id='-1' where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления CompanyUnitId -1";
                }
            }
            if (userActiveFlagFromForm != null) {
                if (userActiveFlagFromForm.equals("0")) {
                    sql = "update users set enabled=true where id_user=?";
                    ArrayList<Object> args = new ArrayList<Object>();
                    args.add(userIdFromForm);
                    boolean flag = dbMvc.getUpdateResult(sql, args);
                    if (!flag) {
                        return "Ошибка обновления блокировки";
                    }
                }
            }
            if (userActiveFlagFromForm == null || !userActiveFlagFromForm.equals("0")) {
                sql = "update users set enabled=false where id_user=?";
                ArrayList<Object> args = new ArrayList<Object>();
                args.add(userIdFromForm);
                boolean flag = dbMvc.getUpdateResult(sql, args);
                if (!flag) {
                    return "Ошибка обновления блокировки";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Изменения сохранены";
    }

    public ArrayList<SiteUser> getListSiteUsers(UsernamePasswordAuthenticationToken principal) {
        if (!isUserHasRole(principal, "ROLE_USERS")) return null;
        String sql;
        sql = "select * from users where username!='admin' and user_is_delete=0";
        List<Map<String, Object>> listDbUser = dbMvc.getSelectResult(sql, null);
        if (listDbUser == null) {
            return null;
        }
        ArrayList<SiteUser> siteUserArrayList = new ArrayList<SiteUser>(listDbUser.size());
        long time = System.currentTimeMillis();
        for (Map row : listDbUser) {
            SiteUser tmpSiteUser = new SiteUser();
            setSiteUserFromMapRow(tmpSiteUser, row);
            siteUserArrayList.add(tmpSiteUser);
        }
        System.out.println(System.currentTimeMillis() - time);
        return siteUserArrayList;
    }

    private void setSiteUserFromMapRow(SiteUser redUser, Map row) {
        Object idUserObject = row.get("id_user");
        int idUser;
        idUser = ((Long) idUserObject).intValue();
        redUser.setId(idUser);
        redUser.setName((String) row.get("username"));
        redUser.setEmail((String) row.get("user_email"));
        redUser.setFio((String) row.get("user_fio"));
        redUser.setPhone((String) row.get("user_phone"));
        redUser.setIsEnable((Boolean) row.get("enabled"));
        redUser.setPost(postMvc.getPost((Integer) row.get("user_post_id")));
        redUser.setCompanyUnit(companyUnitMvc.getCompanyUnit((Integer) row.get("user_company_unit_id")));
    }

    public boolean userAdd(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!isUserHasRole(principal, "ROLE_USERS_ADD")) return false;
        String userNameFromFormUserAdd = request.getParameter("username");
        String sql;
        sql = "select * from users where username=?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(userNameFromFormUserAdd);
        List<Map<String, Object>> dbSelectResult = dbMvc.getSelectResult(sql, args);
        if (dbSelectResult != null && dbSelectResult.size() > 0) {
            this.error = "Пользователь существует";
            return false;
        } else {
            sql = "INSERT INTO users (username) VALUES (?)";
            boolean flag = dbMvc.getUpdateResult(sql, args);
            if (flag)
                this.error = "Пользователь добавлен";
            else
                this.error = "Пользователь не добавлен, ошибка!!!";
        }
        return true;
    }

    public boolean isUserHasRole(UsernamePasswordAuthenticationToken principal, String role) {
        for (GrantedAuthority grantedAuthority : principal.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }

    public String saveRoleForUserForAjax(HttpServletRequest request, UsernamePasswordAuthenticationToken principal) {
        String response = "Error";
        if (!isUserHasRole(principal, "ROLE_USERS_RED")) return "Нет прав на редактирование";
        String userId = (String) request.getParameter("userId").toString();
        int userIdInt = -1;
        try {
            userIdInt = Integer.parseInt(userId);
        } catch (Exception e) {
            userIdInt = -1;
        }
        String role = (String) request.getParameter("role").toString();
        String operation = (String) request.getParameter("operation").toString();
        String sql = null;
        SiteUser siteUserTmp = findSiteUser(userIdInt);
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(role);
        args.add(siteUserTmp.getName());
        if (siteUserTmp == null || siteUserTmp.getName() == null) return "не верные параметры";
        if (operation.equals("add")) {
            sql = "delete from user_roles where role = ? and username = ?";
            dbMvc.getUpdateResult(sql, args);
            sql = "insert into user_roles (role,username) values (?,?)";
            dbMvc.getUpdateResult(sql, args);
        } else if (operation.equals("remove")) {
            sql = "delete from user_roles where role = ? and username = ?";
            dbMvc.getUpdateResult(sql, args);
        } else return "что-то не так";

        return "Ok";
    }
}
