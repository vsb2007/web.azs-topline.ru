package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
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
    private Post post;
    private CompanyUnit companyUnit;

    @Autowired
    private DbModel dbMvc;
    @Autowired
    PasswordEncoder passwordEncoderMvc;
    @Autowired
    Post postMvc;
    @Autowired
    CompanyUnit companyUnitMvc;

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
            findUsersList = dbMvc.getSelectResult(sql);
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
        sql = "select * from users where id_user='" + id_user + "'";
        findUsersList = dbMvc.getSelectResult(sql);
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
        sql = "select * from users where id_user='" + userIdFromForm + "'";

        List<Map<String, Object>> findUsersList = dbMvc.getSelectResult(sql);

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
            System.out.println("Зашли сюда");
            redUser = findUser(request);
            System.out.println("Вышли отсюда");
        } else if (userRedFormValue != null && userRedFormValue.equals("1")) {
            redUser = findUser(request);
            String tmpError = updateUserMessage(redUser, request);
            redUser = findSiteUser(Integer.parseInt(redUser.getId()));
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
                sql = "update users set username='" + userNameFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления имени";
                }
            }
            if (userPasswordFromForm != null && !userPasswordFromForm.equals("")) {
                String password = passwordEncoderMvc.encode(userPasswordFromForm);
                sql = "update users set password='" + password + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления пароля";
                }
            }
            if (userFioFromForm != null) {
                // (!!!) не понятно, почему приходит в левой кодировке
            /*try {
                userFioFromForm = new String(request.getParameter("user-fio-label").getBytes("CP1252"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
                sql = "update users set user_fio='" + userFioFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления ФИО";
                }
            }
            if (userPhoneFromForm != null) {
                sql = "update users set user_phone='" + userPhoneFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления телефона";
                }
            }
            if (userEmailFromForm != null) {
                sql = "update users set user_email='" + userEmailFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления email";
                }
            }
            if (userPostIdFromForm != null) {
                sql = "update users set user_post_id='" + userPostIdFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления PostId";
                }
            }
            if (userCompanyIdFromForm != null) {
                sql = "update users set user_company_id='" + userCompanyIdFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления CompanyId";
                }
            } else {
                sql = "update users set user_company_id='-1' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления CompanyId -1";
                }
            }
            if (userCompanyUnitIdFromForm != null) {
                sql = "update users set user_company_unit_id='" + userCompanyUnitIdFromForm + "' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления CompanyUnitId";
                }
            } else {
                sql = "update users set user_company_unit_id='-1' where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
                    return "Ошибка обновления CompanyUnitId -1";
                }
            }
            if (userActiveFlagFromForm != null) {
                if (userActiveFlagFromForm.equals("0")) {
                    sql = "update users set enabled=true where id_user=" + userIdFromForm;
                    boolean flag = dbMvc.getInsertResult(sql);
                    if (flag) {
                        return "Ошибка обновления блокировки";
                    }
                }
            }
            if (userActiveFlagFromForm == null || !userActiveFlagFromForm.equals("0")) {
                sql = "update users set enabled=false where id_user=" + userIdFromForm;
                boolean flag = dbMvc.getInsertResult(sql);
                if (flag) {
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
        List<Map<String, Object>> listDbUser = dbMvc.getSelectResult(sql);
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
        Iterator<Map.Entry<String, Object>> iterator = row.entrySet().iterator();
        try {
            while (iterator.hasNext()) {
                Map.Entry<String, Object> pair = iterator.next();
                if (pair.getKey().equals("id_user")) {
                    redUser.setId(pair.getValue().toString());
                } else if (pair.getKey().equals("username")) {
                    redUser.setName(pair.getValue().toString());
                } else if (pair.getKey().equals("user_email")) {
                    if (pair.getValue() == null)
                        redUser.setEmail(null);
                    else redUser.setEmail(pair.getValue().toString());
                } else if (pair.getKey().equals("user_fio")) {
                    if (pair.getValue() == null)
                        redUser.setFio(null);
                    else redUser.setFio(pair.getValue().toString());
                } else if (pair.getKey().equals("user_phone")) {
                    if (pair.getValue() == null)
                        redUser.setPhone(null);
                    else redUser.setPhone(pair.getValue().toString());
                } else if (pair.getKey().equals("enabled")) {
                    if (pair.getValue() == null)
                        redUser.setIsEnable(null);
                    else redUser.setIsEnable(pair.getValue().toString());
                } else if (pair.getKey().equals("user_post_id")) {
                    if (pair.getValue() == null)
                        redUser.setPost(null);
                    else redUser.setPost(postMvc.getPost(pair.getValue().toString()));
                } else if (pair.getKey().equals("user_company_unit_id")) {
                    if (pair.getValue() == null) {
                        System.out.println("user_company_unit_id is null");
                        redUser.setCompanyUnit(null);
                    }
                    else{
                        System.out.println("user_company_unit_id: " + pair.getValue().toString());
                        redUser.setCompanyUnit(companyUnitMvc.getCompanyUnit(pair.getValue().toString()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean userAdd(UsernamePasswordAuthenticationToken principal, HttpServletRequest request) {
        if (!isUserHasRole(principal, "ROLE_USERS_ADD")) return false;
        String userNameFromFormUserAdd = request.getParameter("username");
        String sql;
        sql = "select * from users where username='" + userNameFromFormUserAdd + "'";
        List<Map<String, Object>> dbSelectResult = dbMvc.getSelectResult(sql);
        if (dbSelectResult != null && dbSelectResult.size() > 0) {
            this.error = "Пользователь существует";
            return false;
        } else {
            sql = "INSERT INTO users (username) VALUES ('" + userNameFromFormUserAdd + "')";
            boolean flag = dbMvc.getInsertResult(sql);
            if (!flag)
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
}
